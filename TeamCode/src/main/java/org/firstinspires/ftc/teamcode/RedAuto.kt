package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class RedAuto : LinearOpMode() {
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
                2, -1 -> {
                    // get first stone
                    forward(2550)
                    right(600)
                    setRightGrabberPosition(GrabberPosition.CLOSED)
                    forward(200)
                    // move stone
                    backward(1700)
                    turnRight(90.0, timeoutS = 1.5)
                    forward(4300, 0.7, timeoutS = 5.0)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(.5)

                    backward(1200)
                    left(3000)
                }

                1 -> {
                    // get first stone
                    forward(2750)
                    right(650)
                    setLeftGrabberPosition(GrabberPosition.CLOSED)
                    wait(.3)
                    // move stone
                    backward(1000)
                    turnRight(90.0, timeoutS = 1.5)
                    forward(4300, 0.9)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    wait(.2)

                    backward(1200)
                    left(1000)
                }

                0 -> {
                    // get first stone
                    forward(2750)
                    left(100)
                    setLeftGrabberPosition(GrabberPosition.CLOSED)
                    wait(.3)
                    // move stone
                    backward(1000)
                    turnRight(90.0, timeoutS = 1.5)
                    forward(5200, 0.9)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    wait(.2)

                    backward(1200)
                    left(1000)
                }
            }
        }
    }
}
