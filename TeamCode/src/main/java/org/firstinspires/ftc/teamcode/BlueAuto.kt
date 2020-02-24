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

            setLeftArmPosition(ArmPosition.DOWN)
            setRightArmPosition(ArmPosition.DOWN)
            setLeftGrabberPosition(GrabberPosition.OPEN)
            setRightGrabberPosition(GrabberPosition.OPEN)
            wait(0.5)

            val stonePosition = skystonePosition
            telemetry.addData("Skystones", stonePosition)
            telemetry.update()

            when (stonePosition) {
                0, -1 -> {
                    // get first stone
                    forward(2400)
                    left(800)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1200)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(7500)
                    setLeftArmPosition(ArmPosition.UP)
                    forward(1300)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(9800)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1500)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1700)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(9000)
                    setLeftArmPosition(0.4)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1500)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.3)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3600, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3800)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    left(1500, timeoutS = 2.0)

                    // park
                    forward(1000)
                    right(2500)
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
