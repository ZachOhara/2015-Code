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

package io.github.thunderbots.resQ.encoder;

import io.github.thunderbots.lightning.drive.DriveSystem;
import io.github.thunderbots.lightning.opmode.Autonomous;
import io.github.thunderbots.lightning.utility.Util;
import io.github.thunderbots.resQ.ResQRobot;

public abstract class ResQAutoSkydiversEncoder extends Autonomous {
	
	private static final double MOVEMENT_POWER = 0.8;
	private static final long STEP_DELAY = 1500; // in milliseconds
	
	@Override
	protected ResQRobot getRobot() {
		return (ResQRobot) super.getRobot();
	}
	
	@Override
	protected void initializeOpMode() {
		super.initializeOpMode();
		this.setRobot(new ResQRobot());
	}
	
	/**
	 * -1 is Red, 1 is Blue
	 */
	protected abstract int getSide();

	@Override
	protected void main() {
		DriveSystem ds = this.getRobot().getDrive(); // for brevity
		
		// start in front of the driver station
		
		// drive forward to the beacon repair area
		ds.driveInches(MOVEMENT_POWER, 60);
		Util.sleep(STEP_DELAY);
		// rotate to square with the basket
		ds.rotateDegrees(MOVEMENT_POWER * this.getSide(), 15 * this.getSide());
		Util.sleep(STEP_DELAY);
		// move forward a little bit to reach the bucket
		ds.driveInches(MOVEMENT_POWER, 6);
		Util.sleep(STEP_DELAY);
		// TODO: drop the skydivers here
		// back up to get room to rotate
		ds.driveInches(-MOVEMENT_POWER, -6);
		Util.sleep(STEP_DELAY);
		// rotate to go back to the mountain
		ds.rotateDegrees(-MOVEMENT_POWER * this.getSide(), -15 * this.getSide());
		Util.sleep(STEP_DELAY);
		// go back to the mountain
		ds.driveInches(-MOVEMENT_POWER, -30);
		Util.sleep(STEP_DELAY);
		// rotate to face the mountain
		ds.rotateDegrees(MOVEMENT_POWER * this.getSide(), 90 * this.getSide());
		Util.sleep(STEP_DELAY);
		// drive up the mountain
		ds.driveInches(MOVEMENT_POWER, 10);
		Util.sleep(STEP_DELAY);
		
		// ayy lmao
	}
	
}