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
import io.github.thunderbots.lightning.opmode.LightningOpMode;

import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * @author Zach Ohara
 */
public class SensorTest extends LightningOpMode {

	private TouchSensor touch;

	@Override
	protected void initializeRobot() {
		this.touch = (TouchSensor) Lightning.getSensor("touchsensor");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void main() {
		while (this.opModeIsActive()) {
			Lightning.sendTelemetryData("pressed", this.touch.isPressed());
			Lightning.sendTelemetryData("value", this.touch.getValue());
		}
	}

}
