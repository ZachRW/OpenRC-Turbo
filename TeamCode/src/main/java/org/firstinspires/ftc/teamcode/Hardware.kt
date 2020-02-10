package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.bosch.BNO055IMUImpl
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry

open class Hardware(hardwareMap: HardwareMap, protected val telemetry: Telemetry) {
    val leftIMU: BNO055IMUImpl
    val rightIMU: BNO055IMUImpl

    val frontLeft: DcMotor
    val frontRight: DcMotor
    val backLeft: DcMotor
    val backRight: DcMotor
    val wheels: Array<DcMotor>
    val wheelLabels: Array<String>

    private val slide: DcMotor
    private val leftSuck: CRServo
    private val rightSuck: CRServo
    private val clawSlide: CRServo
    private val claw: Servo
    private val flicker: Servo
    private val leftArm: Servo
    private val leftGrabber: Servo
    private val rightArm: Servo
    private val rightGrabber: Servo


    init {
        with(hardwareMap) {
            frontLeft = dcMotor["fl"]
            frontRight = dcMotor["fr"]
            backLeft = dcMotor["bl"]
            backRight = dcMotor["br"]
            slide = dcMotor["l slide"]
            leftSuck = crservo["l suck"]
            rightSuck = crservo["r suck"]
            clawSlide = crservo["c slide"]
            claw = servo["claw"]
            flicker = servo["flick"]
            leftArm = servo["l arm"]
            leftGrabber = servo["l grab"]
            rightArm = servo["r arm"]
            rightGrabber = servo["r grab"]
            leftIMU = get(BNO055IMUImpl::class.java, "l imu")
            rightIMU = get(BNO055IMUImpl::class.java, "r imu")
        }

        wheels = arrayOf(frontLeft, backLeft, frontRight, backRight)
        wheelLabels = arrayOf("FL", "FR", "BL", "BR")

        frontLeft.direction = Direction.REVERSE
        backLeft.direction = Direction.REVERSE
        slide.direction = Direction.REVERSE
        rightSuck.direction = Direction.REVERSE

        wheels.forEach {
            it.zeroPowerBehavior = ZeroPowerBehavior.BRAKE

            it.mode = RunMode.STOP_AND_RESET_ENCODER
            it.mode = RunMode.RUN_USING_ENCODER
        }

        slide.zeroPowerBehavior = ZeroPowerBehavior.BRAKE

        telemetry.addLine("Hardware Initialized")
        telemetry.update()
    }

    internal fun setLeftArmPosition(position: ArmPosition) {
        leftArm.position = position.left
    }

    internal fun setLeftGrabberPosition(position: GrabberPosition) {
        leftGrabber.position = position.left
    }

    internal fun setRightArmPosition(position: ArmPosition) {
        rightArm.position = position.right
    }

    internal fun setRightGrabberPosition(position: GrabberPosition) {
        rightGrabber.position = position.right
    }

    internal fun setFlickerPosition(position: Double) {
        flicker.position = position
    }

    internal fun setClawPosition(position: Double) {
        claw.position = position
    }

    internal fun setClawSlidePower(power: Double) {
        clawSlide.power = power
    }

    internal fun setMecanumPower(
        forwards: Double,
        strafe: Double,
        turn: Double,
        speed: Double = 1.0,
        reverse: Boolean = false
    ) {
        val forwards0 = if (reverse) -forwards else forwards
        val strafe0 = if (reverse) -strafe else strafe

        frontLeft.power = (forwards0 - strafe0 + turn) * speed
        frontRight.power = (forwards0 + strafe0 - turn) * speed
        backLeft.power = (forwards0 + strafe0 + turn) * speed
        backRight.power = (forwards0 - strafe0 - turn) * speed
    }

    internal fun setLinearSlidePower(power: Double) {
        slide.power = power
    }

    internal fun setSuckPower(left: Double, right: Double) {
        leftSuck.power = left
        rightSuck.power = right
    }
}

enum class ArmPosition(val left: Double, val right: Double) {
    DOWN(0.25, 0.65),
    UP(0.5, 0.4)
}

enum class GrabberPosition(val left: Double, val right: Double) {
    CLOSE(0.0, 1.0),
    OPEN(0.4, 0.58)
}
