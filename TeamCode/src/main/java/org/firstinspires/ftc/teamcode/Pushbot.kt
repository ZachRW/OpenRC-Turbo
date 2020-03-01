package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp
class Pushbot : OpMode() {
    private lateinit var hardware: Hardware

    private var speed = 1.0
    private val reverse = ToggleBoolean()

    private val leftArmPos = Toggle(*ArmPosition.values())
    private val rightArmPos = Toggle(*ArmPosition.values())
    private val leftGrabberPos = Toggle(*GrabberPosition.values())
    private val rightGrabberPos = Toggle(*GrabberPosition.values())

    private val clawPos = Toggle(0.85, 1.0)
    private val flickerPos = Toggle(1.0, 0.6)

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
            }

            with(gamepad2) {
                clawPos.input(x)
                flickerPos.input(a)
                leftArmPos.input(dpad_up)
                rightArmPos.input(y)
                leftGrabberPos.input(dpad_left)
                rightGrabberPos.input(b)

                setLinearSlidePower(left_stick_y.toDouble())

                setClawPosition(clawPos.output())
                setFlickerPosition(flickerPos.output())

                setSuckPower(
                    if (left_bumper) -1.0 else left_trigger.toDouble(),
                    if (right_bumper) -1.0 else right_trigger.toDouble()
                )

                setLeftArmPosition(leftArmPos.output())
                setLeftGrabberPosition(leftGrabberPos.output())
                setRightArmPosition(rightArmPos.output())
                setRightGrabberPosition(rightGrabberPos.output())
            }
        }
    }
}
