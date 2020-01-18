package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp
class GyroTest : LinearOpMode() {
    override fun runOpMode() {
        val hardware = AutoHardware(this)

        telemetry.addLine("Initialization Finished")
        telemetry.update()

        waitForStart()

        with(hardware) {
            setClawPosition(0.5)
            setFlickerPosition(1.0)

            while (opModeIsActive()) {
                when {
                    gamepad1.dpad_left -> {
                        left(6000)
                        telemetry.addLine("Moved left")
                        telemetry.update()
                    }
                    gamepad1.dpad_right -> {
                        right(6000)
                        telemetry.addLine("Moved right")
                        telemetry.update()
                    }
                    gamepad1.left_bumper -> {
                        turnLeft(90.0)
                        telemetry.addLine("Turned left")
                        telemetry.update()
                    }
                    gamepad1.right_bumper -> {
                        turnRight(90.0)
                        telemetry.addLine("Turned right")
                        telemetry.update()
                    }
                    gamepad1.x -> {
                        correctHeading()
                        telemetry.addLine("Corrected heading")
                        telemetry.update()
                    }
                }
            }
        }
    }
}