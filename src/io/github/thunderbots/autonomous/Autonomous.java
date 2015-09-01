/* Copyright (C) 2015 Thunderbots-5604
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

package io.github.thunderbots.autonomous;

import io.github.thunderbots.sdk.drive.DriveMotorSet;
import io.github.thunderbots.sdk.drive.DriveSystem;
import io.github.thunderbots.sdk.drive.TankDrive;
import io.github.thunderbots.sdk.opmode.TLinearOpMode;

/**
 * @author Pranav Mathur
 */
public class Autonomous extends TLinearOpMode {
	
	private DriveSystem drive;
	
	private static final String[] DRIVE_MOTOR_NAMES = {"front_left", "front_right", "back_left", "back_right"};

	@Override
	protected void initializeRobot() {
		DriveMotorSet motorSet = new DriveMotorSet(DRIVE_MOTOR_NAMES);
		this.drive = new TankDrive(motorSet);
	}

	@Override
	protected void main() {
		this.drive.driveSeconds(.5, 3);
	}

}