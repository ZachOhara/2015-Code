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

package io.github.thunderbots.minibot2015;

import io.github.thunderbots.lightning.annotation.OpMode;
import io.github.thunderbots.lightning.opmode.TeleOp;

@OpMode(type = "TeleOp", name = "Minibot")
public class MinibotTeleOp extends TeleOp {
	
	protected MinibotRobot getRobot() {
		return (MinibotRobot) super.getRobot();
	}

	@Override
	protected void initializeOpMode() {
		this.setRobot(new MinibotRobot());
		this.getRobot().initializeRobot();
	}

}
