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

package io.github.thunderbots.robotInOneWeek;

import io.github.thunderbots.lightning.Lightning;
import io.github.thunderbots.lightning.hardware.Motor;
import io.github.thunderbots.lightning.hardware.Servo;
import io.github.thunderbots.lightning.robot.Robot;
import io.github.thunderbots.lightning.utility.Telemetry;

public class Ri1WRobot extends Robot {

	/**
	 * The motor responsible for the sweeper on the robot.
	 */
	private Motor sweeper;

	/**
	 * The servo responsible for tilting the bucket.
	 */
	private Servo bucketTiltServo;

	/**
	 * The servo responsible for "booping" on the left side of the robot.
	 *
	 * <pre>
	 * Boop the
	 *    _
	 *   / \
	 *   |O|
	 *    Y
	 *    |
	 *   / \
	 *  /   \
	 *
	 *  THESE
	 * </pre>
	 */
	private Servo leftBooperServo;

	/**
	 * The servo responsible for "booping" on the right side of the robot.
	 */
	private Servo rightBooperServo;

	/**
	 * Determines whether the left booping-arm is currently activated.
	 */
	private boolean isLeftBooping;

	/**
	 * Determines whether the right booping-arm is currently activated.
	 */
	private boolean isRightBooping;

	/**
	 * The first of the CR servos responsible for pushing the blocks out of the bucket.
	 */
	private Motor bucketFingers1;

	/**
	 * The second of the CR servos responsible for pushing the blocks out of the bucket.
	 */
	private Motor bucketFingers2;

	public static final String SWEEPER_NAME = "sweeper";
	public static final String BUCKET_TILT_SERVO_NAME = "bucket_tilt";
	public static final String[] BOOPER_SERVO_NAMES = {"left_booper", "right_booper"};
	public static final String[] BUCKET_FINGERS_NAMES = {"fingers1", "fingers2"};

	public static final double BOOPER_DOWN = 0.13;
	public static final double BOOPER_UP = 0.82;

	/*
	 * These values are relevant when the bucket dumps to the right. Use their negatives
	 * for a left dump.
	 */
	public static final double BUCKET_TILT_DELTA = 0.15;
	public static final double BUCKET_FINGERS_POWER = 1;

	@Override
	public String[] getDriveMotorNames() {
		return new String[] {"front_left", "front_right", "back_left", "back_right"};
	}

	@Override
	public void initializeRobot() {
		this.sweeper = Lightning.getMotor(Ri1WRobot.SWEEPER_NAME);
		this.bucketTiltServo = Lightning.getServo(Ri1WRobot.BUCKET_TILT_SERVO_NAME);
		this.leftBooperServo = Lightning.getServo(Ri1WRobot.BOOPER_SERVO_NAMES[0]);
		this.rightBooperServo = Lightning.getServo(Ri1WRobot.BOOPER_SERVO_NAMES[1]);
		this.unboopLeft();
		this.unboopRight();
		this.centerBucket();
		try {
			this.bucketFingers1 = Lightning.getMotor(Ri1WRobot.BUCKET_FINGERS_NAMES[0]);
			this.bucketFingers2 = Lightning.getMotor(Ri1WRobot.BUCKET_FINGERS_NAMES[1]);
		} catch (Exception ex) {
			Telemetry.sendData("Null: ", "Fingers are null");
		}
	}

	/**
	 * Collect debug information from various parts of the robot's hardware, and send them
	 * through the telemetry link.
	 */
	public void addDebugInformation() {
		Telemetry.sendData("Sweeper", this.sweeper.getPower());
		Telemetry.sendData("Bucket", this.bucketTiltServo.getPosition());
		Telemetry.sendData("Left boop", this.leftBooperServo.getPosition());
		Telemetry.sendData("Right boop", this.rightBooperServo.getPosition());
		if (this.bucketFingers1 != null) {
			Telemetry.sendData("Fingers 1", this.bucketFingers1.getPower());
		}
		if (this.bucketFingers2 != null) {
			Telemetry.sendData("Fingers 2", this.bucketFingers2.getPower());
		}
	}

	/**
	 * Sets the power of the sweeper motor.
	 *
	 * @param power the power of of the sweeper motor
	 */
	public void setSweeperPower(double power) {
		this.sweeper.setPower(power);
	}

	/**
	 * Toggles the activated-deactivated status of the left booping-arm.
	 */
	public void toggleLeftBooper() {
		if (this.isLeftBooping) {
			this.unboopLeft();
		} else {
			this.boopLeft();
		}
	}

	/**
	 * Activates the left booping-arm.
	 */
	public void boopLeft() {
		this.leftBooperServo.moveToPosition(Servo.MAX_POSITION - Ri1WRobot.BOOPER_DOWN);
		this.isLeftBooping = true;
	}

	/**
	 * De-activates the left booping-arm.
	 */
	public void unboopLeft() {
		this.leftBooperServo.moveToPosition(Servo.MAX_POSITION - Ri1WRobot.BOOPER_UP);
		this.isLeftBooping = false;
	}

	/**
	 * Toggles the activated-deactivated status of the right booping-arm.
	 */
	public void toggleRightBooper() {
		if (this.isRightBooping) {
			this.unboopRight();
		} else {
			this.boopRight();
		}
	}

	/**
	 * Activates the right booping-arm.
	 */
	public void boopRight() {
		this.rightBooperServo.moveToPosition(Ri1WRobot.BOOPER_DOWN);
		this.isRightBooping = true;
	}

	/**
	 * De-activates the right booping-arm.
	 */
	public void unboopRight() {
		this.rightBooperServo.moveToPosition(Ri1WRobot.BOOPER_UP);
		this.isRightBooping = false;
	}

	/**
	 * Re-centers the bucket.
	 */
	public void centerBucket() {
		this.bucketTiltServo.center();
		if (this.bucketFingers1 != null) {
			this.bucketFingers1.stop();
		}
		if (this.bucketFingers2 != null) {
			this.bucketFingers2.stop();
		}
	}

	/**
	 * Dumps the bucket to the left.
	 */
	public void dumpBucketLeft() {
		this.dumpBucket(-1);
	}

	/**
	 * Dumps the bucket to the right.
	 */
	public void dumpBucketRight() {
		this.dumpBucket(1);
	}

	/**
	 * Dumps the center bucket in the given direction.
	 *
	 * @param dir -1 if the bucket should dump to the left, or +1 if the bucket should dump
	 * to the right.
	 */
	private void dumpBucket(int dir) {
		this.bucketTiltServo.move(dir * Ri1WRobot.BUCKET_TILT_DELTA);
		if (this.bucketFingers1 != null) {
			this.bucketFingers1.setPower(dir * Ri1WRobot.BUCKET_FINGERS_POWER);
		}
		if (this.bucketFingers2 != null) {
			this.bucketFingers2.setPower(dir * Ri1WRobot.BUCKET_FINGERS_POWER);
		}
	}

}
