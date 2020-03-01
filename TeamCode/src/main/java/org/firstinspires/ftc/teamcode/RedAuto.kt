package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class RedAuto : LinearOpMode() {
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
                2, -1 -> {
                    // get first stone
                    forward(2550)
                    right(800)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    right(5500)
                    forward(800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(2000)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.5)

                    // get second stone
                    left(2000)
                    backward(800)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    leftAndServos(6900)
                    forward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setLeftArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(900)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    right(6600)
                    forward(800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(2000)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.5)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    left(3200)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    right(1500, timeoutS = 2.0)

                    // park
                    forward(800)
                    left(2500)
                }

                // pattern B
                1 -> {
                    forward(2550)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    right(6300)
                    forward(800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(2000)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.5)

                    // get second stone
                    left(2000)
                    backward(800)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    leftAndServos(7300)
                    forward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    right(7300)
                    forward(800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(2000)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.5)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    left(3200)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    right(1500, timeoutS = 2.0)

                    // park
                    forward(800)
                    left(2500)
                }

                // pattern C
                0 -> {
                    forward(2550)
                    right(400)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setLeftArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(900)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    right(6500)
                    forward(800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(2000)
                    setLeftGrabberPosition(GrabberPosition.OPEN)
                    wait(0.5)

                    // get second stone
                    left(2000)
                    backward(800)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    turnToTarget(0.0)
                    leftAndServos(8300)
                    forward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    wait(0.5)
                    setRightArmPosition(ArmPosition.LOW_UP)
                    // move stone
                    backward(900)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    right(8300)
                    forward(800)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    right(2000)
                    setRightGrabberPosition(GrabberPosition.OPEN)
                    wait(0.5)

                    // move build plate
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    wait(0.5)
                    backward(3300, timeoutS = 3.0)
                    setLeftArmPosition(ArmPosition.UP)
                    setRightArmPosition(ArmPosition.UP)
                    left(3200)
                    setLeftArmPosition(ArmPosition.DOWN)
                    setRightArmPosition(ArmPosition.DOWN)
                    setLeftGrabberPosition(GrabberPosition.CLOSE)
                    setRightGrabberPosition(GrabberPosition.CLOSE)
                    forward(1000)
                    right(1500, timeoutS = 2.0)

                    // park
                    forward(800)
                    left(2500)
                }
            }
        }
    }
}
