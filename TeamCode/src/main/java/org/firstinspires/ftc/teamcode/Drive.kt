package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import edu.spa.ftclib.internal.state.ToggleBoolean
import edu.spa.ftclib.internal.state.ToggleDouble

@TeleOp
class Drive : OpMode() {
    private lateinit var hardware: Hardware

    private var speed = 1.0
    private val reverse = ToggleBoolean()
    private val leftArmUp = ToggleBoolean()
    private val leftGrabberOpen = ToggleBoolean()
    private val rightArmUp = ToggleBoolean()
    private val rightGrabberOpen = ToggleBoolean()

    private val clawPos = ToggleDouble(doubleArrayOf(0.9, 1.0))
    private val flickerPos = ToggleDouble(doubleArrayOf(1.0, 0.0))

    override fun init() {
        telemetry.addLine("Initializing...")
        telemetry.update()

        hardware = Hardware(hardwareMap, telemetry)

        telemetry.addLine("Initialization Finished")
        telemetry.update()
    }

    override fun loop() {
        with(hardware) {
            with(gamepad1) {
                reverse.input(x)
                leftArmUp.input(left_bumper)
                leftGrabberOpen.input(left_trigger > 0)
                rightArmUp.input(right_bumper)
                rightGrabberOpen.input(right_trigger > 0)

                when {
                    dpad_up ->
                        speed = 1.0

                    dpad_left || dpad_right ->
                        speed = 0.5

                    dpad_down ->
                        speed = 0.2
                }

                setMecanumPower(
                    -left_stick_y.toDouble(),
                    left_stick_x.toDouble(),
                    right_stick_x.toDouble(),
                    speed,
                    reverse.output()
                )

                setLeftArmPosition(
                    if (leftArmUp.output()) ArmPosition.UP else ArmPosition.DOWN
                )
                setLeftGrabberPosition(
                    if (leftGrabberOpen.output()) GrabberPosition.OPEN else GrabberPosition.CLOSE
                )
                setRightArmPosition(
                    if (rightArmUp.output()) ArmPosition.UP else ArmPosition.DOWN
                )
                setRightGrabberPosition(
                    if (rightGrabberOpen.output()) GrabberPosition.OPEN else GrabberPosition.CLOSE
                )
            }

            with(gamepad2) {
                clawPos.input(x)
                flickerPos.input(a)

                setLinearSlidePower(left_stick_y.toDouble())

                setClawPosition(clawPos.output())
                setFlickerPosition(flickerPos.output())

                setClawSlidePower(
                    when {
                        dpad_up -> 1.0
                        dpad_down -> -1.0
                        else -> 0.0
                    }
                )

                setSuckPower(
                    if (left_bumper) -1.0 else left_trigger.toDouble(),
                    if (right_bumper) -1.0 else right_trigger.toDouble()
                )
            }
        }
    }
}
