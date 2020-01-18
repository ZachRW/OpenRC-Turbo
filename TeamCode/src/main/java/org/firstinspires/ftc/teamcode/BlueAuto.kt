package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class BlueAuto : LinearOpMode() {
    override fun runOpMode() {
        val hardware = AutoHardware(this)

        telemetry.addLine("Initialization Finished")
        telemetry.update()

        waitForStart()

        with(hardware) {
            setClawPosition(0.5)
            setFlickerPosition(1.0)
            setLeftArmPosition(ArmPosition.DOWN)
            setLeftGrabberPosition(GrabberPosition.OPEN)
            setRightArmPosition(ArmPosition.DOWN)
            setRightGrabberPosition(GrabberPosition.OPEN)

            wait(1.5)

            val stonePosition = skystonePosition
            telemetry.addData("Skystones", stonePosition)
            telemetry.update()

            when (stonePosition) {
                0, -1 -> {
                    // get first stone
                    forward(2750)
                    left(800)
                    setRightGrabberPosition(GrabberPosition.CLOSED)
                    wait(.25)
                    // move stone
                    backward(1700)
                    turnLeft(90.0)
                    forward(3500, 0.9)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(.5)

                    backward(1000)
                }

                1 -> {
                    // get first stone
                    forward(2750)
                    setRightGrabberPosition(GrabberPosition.CLOSED)
                    wait(.3)
                    // move stone
                    backward(1000)
                    turnLeft(90.0)
                    forward(4000, 0.9)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(.2)

                    backward(1200)
                }

                2 -> {
                    // get first stone
                    forward(2550, 0.7)
                    right(850)
                    forward(250)
                    setRightGrabberPosition(GrabberPosition.CLOSED)
                    wait(.3)
                    // move stone
                    backward(1000)
                    turnLeft(90.0)
                    forward(4800, 0.7)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(.2)

                    backward(900)
                }
            }
        }
    }
}
