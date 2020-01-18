package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.control.PIDCoefficients
import com.acmerobotics.roadrunner.control.PIDFController
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvInternalCamera
import org.openftc.easyopencv.OpenCvInternalCamera.CameraDirection
import kotlin.math.PI
import kotlin.math.abs

const val DEFAULT_SPEED = 0.6
const val DEFAULT_TIMEOUT = 10.0

class AutoHardware(private val linearOpMode: LinearOpMode) :
    Hardware(linearOpMode.hardwareMap, linearOpMode.telemetry) {
    private val timer = ElapsedTime()
    private val skystoneDetector = SkystoneDetector(telemetry)
    private var targetHeading = 0.0

    private val movePID = PIDFController(PIDCoefficients(0.7, 0.0, 0.0))
        .apply {
            setInputBounds(0.0, 2 * PI)
            setOutputBounds(-1.0, 1.0)
        }
    private val turnPID = PIDFController(PIDCoefficients(1.2, 0.5, 0.0))
        .apply {
            setInputBounds(0.0, 2 * PI)
            setOutputBounds(-1.0, 1.0)
        }


    internal val skystonePosition: Int
        get() = skystoneDetector.skystonePosition()

    private val heading: Double
        get() = (leftIMU.angularOrientation.firstAngle + rightIMU.angularOrientation.firstAngle) / 2.0

    private val headingVelocity: Double
        get() = (leftIMU.angularVelocity.zRotationRate + rightIMU.angularVelocity.zRotationRate) / 2.0

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

    // Back is treated as front for autonomous movement

    internal fun backward(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(
        ticks,
        speed,
        Direction.FORWARD,
        Direction.FORWARD,
        Direction.FORWARD,
        Direction.FORWARD,
        timeoutS,
        "Backward"
    )

    internal fun forward(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(
        ticks,
        speed,
        Direction.BACKWARD,
        Direction.BACKWARD,
        Direction.BACKWARD,
        Direction.BACKWARD,
        timeoutS,
        "Forward"
    )

    internal fun left(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(
        ticks,
        speed,
        Direction.BACKWARD,
        Direction.FORWARD,
        Direction.FORWARD,
        Direction.BACKWARD,
        timeoutS,
        "Left"
    )

    internal fun right(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(
        ticks,
        speed,
        Direction.FORWARD,
        Direction.BACKWARD,
        Direction.BACKWARD,
        Direction.FORWARD,
        timeoutS,
        "Right"
    )

    internal fun turnRight(
        degrees: Double,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) {
        targetHeading -= degrees * PI / 180
        correctHeading(speed, timeoutS)
    }

    internal fun turnLeft(
        degrees: Double,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) {
        targetHeading += degrees * PI / 180
        correctHeading(speed, timeoutS)
    }

    internal fun wait(seconds: Double) {
        timer.reset()
        while (timer.seconds() < seconds && linearOpMode.opModeIsActive()) {
            linearOpMode.idle()
        }
    }

    internal fun correctHeading(speed: Double = DEFAULT_SPEED, timeoutS: Double = DEFAULT_TIMEOUT) {
        turnPID.targetPosition = targetHeading

        wheels.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
        }

        turnPID.update(heading)
        while ((abs(turnPID.lastError) > 0.01 || headingVelocity > 0.01)
            && timer.seconds() < timeoutS
            && linearOpMode.opModeIsActive()
        ) {
            val correction = turnPID.update(heading)

            frontLeft.power = -correction * speed
            frontRight.power = correction * speed
            backLeft.power = -correction * speed
            backRight.power = correction * speed

            telemetry.addData("Heading", heading)
            telemetry.addData("Velocity", headingVelocity)
            telemetry.addData("Correction", correction)
            telemetry.update()
        }

        wheels.forEach { it.power = 0.0 }
    }

    private fun move(
        ticks: Int,
        speed: Double,
        flDirection: Direction,
        frDirection: Direction,
        blDirection: Direction,
        brDirection: Direction,
        timeoutS: Double,
        action: String
    ) {
        movePID.targetPosition = heading

        wheels.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
        }

        timer.reset()
        while (targetDistance(ticks) > 50 && timer.seconds() < timeoutS && linearOpMode.opModeIsActive()) {
            val correction = movePID.update(heading)

            frontLeft.power = flDirection.applySign(speed) - correction
            frontRight.power = frDirection.applySign(speed) + correction
            backLeft.power = blDirection.applySign(speed) - correction
            backRight.power = brDirection.applySign(speed) + correction

            telemetry.addData("Action", action)
            telemetry.addData("Target Distance", targetDistance(ticks))
            telemetry.addData("Correction", correction)
            telemetry.addData("Heading", heading)
            telemetry.update()
        }

        wheels.forEach { it.power = 0.0 }

        correctHeading()
    }

    private fun targetDistance(target: Int): Int {
        val averageTravel = wheels.sumBy { abs(it.currentPosition) } / wheels.size
        return target - averageTravel
    }

    enum class Direction(private val sign: Int) {
        FORWARD(1),
        BACKWARD(-1);

        fun applySign(value: Double) = value * sign
    }
}
