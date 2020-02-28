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
                    left(7400)
                    setLeftArmPosition(ArmPosition.UP)
                    forward(1300)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(9700)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1400)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1800)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(9000)
                    setLeftArmPosition(0.4)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1400)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.3)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    left(1500, timeoutS = 2.0)

                    // park
                    forward(700)
                    right(2500)
                }

                1 -> {
                    // get first stone
                    forward(2750)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1200)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(8200)
                    setLeftArmPosition(ArmPosition.UP)
                    forward(1300)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(10500)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1400)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1800)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(9800)
                    setLeftArmPosition(0.4)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1400)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.3)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    left(1500, timeoutS = 2.0)

                    // park
                    forward(700)
                    right(2500)
                }

                2 -> {
                    // get first stone
                    forward(2400)
                    right(800)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1300)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(9000)
                    setLeftArmPosition(ArmPosition.UP)
                    forward(1300)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(11300)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1400)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.UP)
                    // move stone
                    backward(1800)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(10600)
                    setLeftArmPosition(0.4)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1400)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.3)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    left(1500, timeoutS = 2.0)

                    // park
                    forward(700)
                    right(2500)
                }
            }
        }
    }
}
