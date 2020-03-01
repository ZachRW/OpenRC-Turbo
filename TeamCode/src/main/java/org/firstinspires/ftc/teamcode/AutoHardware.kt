package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvInternalCamera
import org.openftc.easyopencv.OpenCvInternalCamera.CameraDirection
import kotlin.math.PI
import kotlin.math.abs

const val DEFAULT_SPEED = 1.0
const val DEFAULT_TIMEOUT = 10.0
const val DEFAULT_TURN_TIMEOUT = 1.5

class AutoHardware(private val linearOpMode: LinearOpMode) :
    Hardware(linearOpMode.hardwareMap, linearOpMode.telemetry) {
    private val timer = ElapsedTime()
    private val skystoneDetector = SkystoneDetector(telemetry)

    private var targetHeading = 0.0

    private val heading: Double
        get() = (leftIMU.angularOrientation.firstAngle
                + rightIMU.angularOrientation.firstAngle) / 2 * 180 / PI

    private val angularVelocity: Double
        get() = (leftIMU.angularVelocity.zRotationRate
                + rightIMU.angularVelocity.zRotationRate) / 2 * 180 / PI

    internal val skystonePosition: Int
        get() = skystoneDetector.skystonePosition()

    internal fun initSkystoneDetector() {
        val cameraViewId = linearOpMode.hardwareMap.appContext.run {
            resources.getIdentifier("cameraMonitorViewId", "id", packageName)
        }

        OpenCvInternalCamera(CameraDirection.BACK, cameraViewId).apply {
            openCameraDevice()
            setPipeline(skystoneDetector)
            startStreaming(960, 720, OpenCvCameraRotation.UPRIGHT)
        }
    }

    internal fun initGyros() {
        telemetry.addLine("Calibrating Gyros...")
        telemetry.update()

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

        while ((!leftIMU.isGyroCalibrated || !rightIMU.isGyroCalibrated)
            && linearOpMode.opModeIsActive()
        ) {
            telemetry.addLine("Calibrating Gyros")
            telemetry.update()
        }
    }

    // Back is treated as front for autonomous movement

    internal fun forward(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(FORWARD, ticks, speed, timeoutS, "Forward")

    internal fun backward(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(BACKWARD, ticks, speed, timeoutS, "Backward")

    internal fun left(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(LEFT, ticks, speed, timeoutS, "Left")

    internal fun right(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TIMEOUT
    ) = move(RIGHT, ticks, speed, timeoutS, "Right")

    internal fun turnRight(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TURN_TIMEOUT
    ) = move(TURN_RIGHT, ticks, speed, timeoutS, "Right Turn")

    internal fun turnLeft(
        ticks: Int,
        speed: Double = DEFAULT_SPEED,
        timeoutS: Double = DEFAULT_TURN_TIMEOUT
    ) = move(TURN_LEFT, ticks, speed, timeoutS, "Left Turn")

    internal fun turnToTarget(target: Double = targetHeading) {
        targetHeading = target

        setMotorPower(1.0, if (heading < target) TURN_LEFT else TURN_RIGHT)

        wheels.forEach { it.mode = RunMode.RUN_USING_ENCODER }
        while ((abs(heading - target) > 2 || abs(angularVelocity) > 0)
            && linearOpMode.opModeIsActive()
        ) {
            val distance = heading - target

            val direction =
                if (distance < 0) TURN_LEFT else TURN_RIGHT
            val speed = if (abs(distance) > 60) {
                1.0
            } else {
                (abs(distance) / 40).coerceAtLeast(0.0)
            }

            setMotorPower(speed, direction)

            telemetry.addData("Heading", heading)
            telemetry.addData("Target", targetHeading)
            telemetry.addData("Velocity", angularVelocity)
            telemetry.update()
        }
    }

    internal fun leftAndServos(ticks: Int) {
        val direction = LEFT
        val speed = 1.0
        val timeoutS = 10.0
        val action = "Left and Servos"

        wheels.zip(direction).forEach { (wheel, wheelDirection) ->
            wheel.mode = RunMode.STOP_AND_RESET_ENCODER

            wheel.targetPosition = (ticks * wheelDirection).toInt()
            wheel.power = abs(speed * wheelDirection)

            wheel.mode = RunMode.RUN_TO_POSITION
        }

        timer.reset()
        while (wheelsBusy() && timer.seconds() < timeoutS && linearOpMode.opModeIsActive()) {
            telemetry.addLine(action + "\n")
            telemetry.addData("Motor", "Position |  Target  | Distance")
            for ((index, wheel) in wheels.withIndex()) {
                telemetry.addData(
                    wheelLabels[index],
                    "%8d | %8d | %8d",
                    wheel.currentPosition,
                    wheel.targetPosition,
                    wheel.targetPosition - wheel.currentPosition
                )

                if (wheel.currentPosition > 3000) {
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                }
            }
            telemetry.update()
        }
    }

    internal fun rightAndServos(ticks: Int) {
        val direction = RIGHT
        val speed = 1.0
        val timeoutS = 10.0
        val action = "Right and Servos"

        wheels.zip(direction).forEach { (wheel, wheelDirection) ->
            wheel.mode = RunMode.STOP_AND_RESET_ENCODER

            wheel.targetPosition = (ticks * wheelDirection).toInt()
            wheel.power = abs(speed * wheelDirection)

            wheel.mode = RunMode.RUN_TO_POSITION
        }

        timer.reset()
        while (wheelsBusy() && timer.seconds() < timeoutS && linearOpMode.opModeIsActive()) {
            telemetry.addLine(action + "\n")
            telemetry.addData("Motor", "Position |  Target  | Distance")
            for ((index, wheel) in wheels.withIndex()) {
                telemetry.addData(
                    wheelLabels[index],
                    "%8d | %8d | %8d",
                    wheel.currentPosition,
                    wheel.targetPosition,
                    wheel.targetPosition - wheel.currentPosition
                )

                if (wheel.currentPosition > 3000) {
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                }
            }
            telemetry.update()
        }
    }

    internal fun backAndTurnLeft(ticks: Int) = move(
        BACK_AND_TURN_LEFT,
        ticks,
        0.7,
        10.0,
        "Back and Turn Left"
    )

    internal fun wait(seconds: Double) {
        timer.reset()
        while (timer.seconds() < seconds && linearOpMode.opModeIsActive()) {
            linearOpMode.idle()
        }
    }

    private fun setMotorPower(speed: Double, direction: Direction) {
        wheels.zip(direction).forEach { (wheel, wheelDirection) ->
            wheel.power = speed * wheelDirection
        }
    }

    private fun move(
        direction: Direction,
        ticks: Int,
        speed: Double,
        timeoutS: Double,
        action: String
    ) {
        wheels.zip(direction).forEach { (wheel, wheelDirection) ->
            wheel.mode = RunMode.STOP_AND_RESET_ENCODER

            wheel.targetPosition = (ticks * wheelDirection).toInt()
            wheel.power = abs(speed * wheelDirection)

            wheel.mode = RunMode.RUN_TO_POSITION
        }

        timer.reset()
        while (wheelsBusy() && timer.seconds() < timeoutS && linearOpMode.opModeIsActive()) {
            telemetry.addLine(action + "\n")
            telemetry.addData("Motor", "Position |  Target  | Distance")
            for ((index, wheel) in wheels.withIndex()) {
                telemetry.addData(
                    wheelLabels[index],
                    "%8d | %8d | %8d",
                    wheel.currentPosition,
                    wheel.targetPosition,
                    wheel.targetPosition - wheel.currentPosition
                )
            }
            telemetry.update()
        }
    }

    private fun wheelsBusy(): Boolean =
        wheels.any { abs(it.targetPosition - it.currentPosition) > 60 }
}

private class Direction(fl: Double, fr: Double, bl: Double, br: Double) :
    List<Double> by listOf(fl, fr, bl, br)

private val FORWARD = Direction(-1.0, -1.0, -1.0, -1.0)
private val BACKWARD = Direction(1.0, 1.0, 1.0, 1.0)
private val LEFT = Direction(1.0, -1.0, -1.0, 1.0)
private val RIGHT = Direction(-1.0, 1.0, 1.0, -1.0)
private val TURN_LEFT = Direction(-1.0, 1.0, -1.0, 1.0)
private val TURN_RIGHT = Direction(1.0, -1.0, 1.0, -1.0)
private val BACK_AND_TURN_LEFT = Direction(0.0, 1.0, 0.0, 1.0)
