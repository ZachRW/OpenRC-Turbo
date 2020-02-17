package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class BlueAuto : LinearOpMode() {
    override fun runOpMode() {
        val hardware = AutoHardware(this).apply {
            initSkystoneDetector()
            initGyros()
        }

        telemetry.addLine("Initialization Finished")
        telemetry.update()

        waitForStart()

        with(hardware) {
            setClawPosition(0.5)
            setFlickerPosition(1.0)

            val stonePosition = skystonePosition
            telemetry.addData("Skystones", stonePosition)
            telemetry.update()

            setLeftArmPosition(ArmPosition.DOWN)
            setRightArmPosition(ArmPosition.DOWN)
            wait(1.5)

            setLeftGrabberPosition(GrabberPosition.OPEN)
            setRightGrabberPosition(GrabberPosition.OPEN)

            when (stonePosition) {
                0, -1 -> {
                    // get first stone
                    forward(2400)
                    left(1200)
                    forward(450)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(500)
                    turnToTarget(90.0)
                    forward(6000, 1.0)
                    setLeftArmPosition(0.8)
                    turnDrop()

                    // get second stone
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    forward(8800, 1.0, 5.0)
                    turnToTarget(0.0)
                    forward(1600)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1000)
                    turnToTarget(90.0)
                    forward(9000, 1.0)
                    turnToTarget(0.0)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1500)

                    //move build plate
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

                1 -> {
                    // get first stone
                    forward(2750)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(.3)
                    // move stone
                    backward(1000)
                    turnLeft(1650)
                    forward(4000, 0.9)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(.2)

                    // get second stone
                    backward(100)
                    turnRight(120, 1.0, 1.0)
                    backward(6200)
                    turnRight(1300)
                    forward(1200)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(.5)
                    // move stone
                    backward(1650)
                    turnLeft(1600)
                    forward(6400, 0.9)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    backward(1200)
                }

                2 -> {
                    // get first stone
                    forward(2550, 0.7)
                    right(850)
                    forward(250)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(.3)
                    // move stone
                    backward(1000)
                    turnLeft(1650)
                    forward(4800, 0.7)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(.2)

                    // get second stone
                    backward(100)
                    turnRight(160, 1.0, 1.5)
                    backward(6100, 1.0)
                    turnRight(1500)
                    right(750, timeoutS = 3.0)
                    forward(1300, 0.7)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(.5)
                    // move stone
                    backward(1650)
                    left(900)
                    turnLeft(1850)
                    forward(6500, 1.0)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    backward(900)
                }
            }
        }
    }
}
