package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous
class GyroTest : LinearOpMode() {
    override fun runOpMode() {
        val hardware = AutoHardware(this).apply {
            initSkystoneDetector()
            initGyros()
        }

        telemetry.addLine("Initialization Finished")
        telemetry.update()

        waitForStart()

        with(hardware) {
            turnToTarget(90.0)
        }
    }
}