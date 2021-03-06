/* Copyright (C) 2015-2016 Thunderbots Robotics
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.thunderbots.testing;

import io.github.thunderbots.lightning.Lightning;
import io.github.thunderbots.annotation.OpMode;
import io.github.thunderbots.lightning.control.ButtonHandler;
import io.github.thunderbots.lightning.control.JoystickButton;
import io.github.thunderbots.lightning.control.JoystickListener;
import io.github.thunderbots.lightning.control.ButtonHandler.PressType;
import io.github.thunderbots.lightning.opmode.LightningOpMode;
import io.github.thunderbots.lightning.utility.Telemetry;

@OpMode(name="Joystick events test", type="test")
public class JoystickEventsTest extends LightningOpMode implements JoystickListener {

	@Override
	protected void initializeOpMode() {
		super.initializeOpMode();
		Lightning.getJoystickMonitor(1).registerJoystickListener(this);
	}

	@Override
	protected void main() {
		while (this.opModeIsActive()) {
			// repeat endlessly here
		}
	}
	
	/* (non-Javadoc)
	 * 
	 * JoystickEventsTest.java - tests the fundamental principals of the joystick event
	 * system.
	 * 
	 * Setup:
	 * 
	 * This test only requires that a joystick be attached to the driver station phone.
	 * No specific robot hardware is required.
	 * 
	 * Expected behavior:
	 * 
	 * When the joystic is registered in the driver station as the primary joystick, all
	 * information will be labeled "joy1". If the joystick is the secondary joystick, the
	 * information will be labeled as "joy2".
	 * 
	 * The only buttons that are monitored by this test are the A and B buttons. Whenever
	 * either button is pressed, the driver station screen should read "_ button pressed".
	 * When either button is released, the driver station screen should read "_ button
	 * released". In both cases, the underscore will be replaced by either "A" or "B"
	 * depending on what button was pressed.
	 * 
	 */
	
	@ButtonHandler(button = JoystickButton.A, joystick = 1, type = PressType.PRESS)
	public void onAButtonPress() {
		Telemetry.sendData("joy1", "A button pressed");
	}
	
	@ButtonHandler(button = JoystickButton.A, joystick = 1, type = PressType.RELEASE)
	public void onAButtonRelease() {
		Telemetry.sendData("joy1", "A button released");
	}
	
	@ButtonHandler(button = JoystickButton.B, joystick = 1, type = PressType.PRESS)
	public void onBButtonPress() {
		Telemetry.sendData("joy1", "B button pressed");
	}
	
	@ButtonHandler(button = JoystickButton.B, joystick = 1, type = PressType.RELEASE)
	public void onBButtonRelease() {
		Telemetry.sendData("joy1", "B button released");
	}
	
	@ButtonHandler(button = JoystickButton.A, joystick = 2, type = PressType.PRESS)
	public void onJ2AButtonPress() {
		Telemetry.sendData("joy2", "A button pressed");
	}
	
	@ButtonHandler(button = JoystickButton.A, joystick = 2, type = PressType.RELEASE)
	public void onJ2AButtonRelease() {
		Telemetry.sendData("joy2", "A button released");
	}
	
	@ButtonHandler(button = JoystickButton.B, joystick = 2, type = PressType.PRESS)
	public void onJ2BButtonPress() {
		Telemetry.sendData("joy2", "B button released");
	}
	
	@ButtonHandler(button = JoystickButton.B, joystick = 2, type = PressType.RELEASE)
	public void onJ2BButtonRelease() {
		Telemetry.sendData("joy2", "B button released");
	}

}
