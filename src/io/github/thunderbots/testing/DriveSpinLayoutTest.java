package io.github.thunderbots.testing;

import io.github.thunderbots.lightning.annotation.OpMode;
import io.github.thunderbots.lightning.opmode.TeleOpTest;
import io.github.thunderbots.lightning.utility.Telemetry;

@OpMode (type = "Teleop", name = "Drive-Spin layout test")
public class DriveSpinLayoutTest extends TeleOpTest {
	
	@Override 
	protected void mainLoop() {
		Telemetry.sendTelemetryData("Control", "Drive-Spin control layout");
	}
	
}
