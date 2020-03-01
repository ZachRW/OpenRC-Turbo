package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class FoundationTest : LinearOpMode() {
    override fun runOpMode() {
        val hardware = AutoHardware(this)

        telemetry.addLine("Initialization Finished")
        telemetry.update()

        waitForStart()

        with(hardware) {
            setLeftGrabberPosition(GrabberPosition.OPEN)
            setRightGrabberPosition(GrabberPosition.OPEN)
            setLeftArmPosition(ArmPosition.DOWN)
            setRightArmPosition(ArmPosition.DOWN)
            wait(0.5)

            backward(2500, .75)
            right(4000, 1.0, 3.0)
            left(1000, .5, 1.0)
            forward(2500, 1.0)
            setLeftArmPosition(ArmPosition.UP)
            setRightArmPosition(ArmPosition.UP)
            backward(2000, 1.0)
        }
    }
}