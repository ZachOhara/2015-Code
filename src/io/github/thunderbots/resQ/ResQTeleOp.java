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

package io.github.thunderbots.resQ;

import io.github.thunderbots.lightning.Lightning;
import io.github.thunderbots.lightning.annotation.Active;
import io.github.thunderbots.lightning.annotation.OpMode;
import io.github.thunderbots.lightning.opmode.TeleOp;
import io.github.thunderbots.resQ.ResQRobot;

@OpMode(name = "ResQTeleOp", type = "TeleOp")
@Active
public class ResQTeleOp extends TeleOp {

	@Override
	protected ResQRobot getRobot() {
		return (ResQRobot) super.getRobot();
	}

	@Override
	protected void initializeOpMode() {
		this.setRobot(new ResQRobot());
		this.getRobot().initializeRobot();
	}
	
	protected void mainLoop() {
		if (Lightning.getJoystick(1).rightBumper())
			this.getRobot().setLegPower(1, 1);
		else if (Lightning.getJoystick(1).leftBumper())
			this.getRobot().setLegPower(-1, -1);
		else
			this.getRobot().setLegPower(0, 0);
		
		if (Lightning.getJoystick(2).aButton()) {
			this.getRobot().setLeftBooper(true);
			this.getRobot().setRightBooper(true);
		}
		if (Lightning.getJoystick(2).bButton()) {
			this.getRobot().setLeftBooper(false);
			this.getRobot().setRightBooper(false);
		}
	}
		

}
