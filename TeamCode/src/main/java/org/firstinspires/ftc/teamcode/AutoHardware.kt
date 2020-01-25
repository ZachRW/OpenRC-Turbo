package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.control.PIDCoefficients
import com.acmerobotics.roadrunner.drive.MecanumDrive
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvInternalCamera
import org.openftc.easyopencv.OpenCvInternalCamera.CameraDirection

class AutoHardware(private val linearOpMode: LinearOpMode) :
    Hardware(linearOpMode.hardwareMap, linearOpMode.telemetry) {
    private val timer = ElapsedTime()
    private val skystoneDetector = SkystoneDetector(telemetry)

    private val drive = object : MecanumDrive(0.0, 0.0, 0.0, 0.0) {
        override val rawExternalHeading: Double
            get() = (leftIMU.angularOrientation.firstAngle + rightIMU.angularOrientation.firstAngle) / 2.0

        override fun getWheelPositions(): List<Double> =
            wheels.map { it.currentPosition.toDouble() }

        override fun setMotorPowers(
            frontLeft: Double,
            rearLeft: Double,
            rearRight: Double,
            frontRight: Double
        ) {
            this@AutoHardware.frontLeft.power = frontLeft
            this@AutoHardware.backLeft.power = rearLeft
            this@AutoHardware.backRight.power = rearRight
            this@AutoHardware.frontRight.power = frontRight
        }
    }

    private val movePID = PIDCoefficients(5.0, 0.0, 0.0)
    private val turnPID = PIDCoefficients(2.0, 0.0, 0.0)

    private val pathFollower = HolonomicPIDVAFollower(movePID, movePID, turnPID)

    internal val skystonePosition: Int
        get() = skystoneDetector.skystonePosition()

    init {
        initGyros()
        initSkystoneDetector()
    }

    private fun initSkystoneDetector() {
        val cameraViewId = linearOpMode.hardwareMap.appContext.run {
            resources.getIdentifier("cameraMonitorViewId", "id", packageName)
        }

        OpenCvInternalCamera(CameraDirection.BACK, cameraViewId).apply {
            openCameraDevice()
            setPipeline(skystoneDetector)
            startStreaming(960, 720, OpenCvCameraRotation.UPRIGHT)
        }
    }

    private fun initGyros() {
        leftIMU.initialize(BNO055IMU.Parameters().apply {
            angleUnit = BNO055IMU.AngleUnit.RADIANS
            accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
            loggingEnabled = true
            loggingTag = "Left IMU"
            accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()
        })

        rightIMU.initialize(BNO055IMU.Parameters().apply {
            angleUnit = BNO055IMU.AngleUnit.RADIANS
            accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
            loggingEnabled = true
            loggingTag = "Right IMU"
            accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()
        })

        while (linearOpMode.opModeIsActive() && (!leftIMU.isGyroCalibrated || !rightIMU.isGyroCalibrated)) {
            telemetry.addLine("Calibrating Gyros")
            telemetry.update()
        }
    }

    private fun updateMotorPower() {
        val signal = pathFollower.update(drive.poseEstimate)

        drive.setDriveSignal(signal)
    }
}
