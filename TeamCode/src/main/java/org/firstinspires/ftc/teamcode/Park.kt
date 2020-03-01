package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class Park : LinearOpMode() {
    override fun runOpMode() {
        val hardware = AutoHardware(this)

        telemetry.addLine("Initialization Finished")
        telemetry.update()

        waitForStart()

        with(hardware) {
            setClawPosition(0.5)
            setFlickerPosition(0.6)

            setLeftArmPosition(ArmPosition.DOWN)
            setRightArmPosition(ArmPosition.DOWN)
            setLeftGrabberPosition(GrabberPosition.CLOSE)
            setRightGrabberPosition(GrabberPosition.CLOSE)
            wait(2.0)
            setLeftGrabberPosition(GrabberPosition.OPEN)
            setRightGrabberPosition(GrabberPosition.OPEN)
            wait(3.0)
        }
    }
}