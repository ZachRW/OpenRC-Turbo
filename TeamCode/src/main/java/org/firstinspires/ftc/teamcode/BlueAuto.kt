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
            setFlickerPosition(0.6)

            setLeftArmPosition(ArmPosition.DOWN)
            setRightArmPosition(ArmPosition.DOWN)
            setLeftGrabberPosition(GrabberPosition.OPEN)
            setRightGrabberPosition(GrabberPosition.OPEN)
            wait(0.5)

            val stonePosition = skystonePosition
            telemetry.addData("Skystones", stonePosition)
            telemetry.update()

            when (stonePosition) {
                // pattern A
                0, -1 -> {
                    // get first stone
                    forward(2400)
                    left(800)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(1200)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(4000)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1000)
                    left(3400)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    right(3200)
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(6300)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1200)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(1600)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(6200)
                    setLeftArmPosition(0.4)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    forward(700)
                    left(3400)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(300)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3200)
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

                // pattern B
                1 -> {
                    // get first stone
                    forward(2750)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(1200)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(4800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    forward(900)
                    left(3400)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    right(3200)
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(7300)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1200)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(1600)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(7000)
                    setLeftArmPosition(0.4)
                    setRightArmPosition(ArmPosition.UP)
                    forward(400)
                    left(3700)
                    forward(300)

                    // move build plate
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3200)
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

                // pattern C
                2 -> {
                    // get first stone
                    forward(2400)
                    right(1100)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(1300)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(5600)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    forward(1000)
                    left(3400)
                    setRightGrabberPosition(GrabberPosition.OPEN)

                    // get second stone
                    right(3200)
                    backward(1000)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    rightAndServos(8600)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(1200)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(1600)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    left(7800)
                    setLeftArmPosition(0.4)
                    setRightArmPosition(ArmPosition.UP)
                    forward(700)
                    left(3400)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    forward(300)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(3200)
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
