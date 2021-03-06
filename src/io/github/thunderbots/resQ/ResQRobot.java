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

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import io.github.thunderbots.lightning.Lightning;
import io.github.thunderbots.lightning.hardware.CRServo;
import io.github.thunderbots.lightning.hardware.Motor;
import io.github.thunderbots.lightning.hardware.Servo;
import io.github.thunderbots.lightning.robot.Robot;
import io.github.thunderbots.lightning.utility.Alliance;

public class ResQRobot extends Robot {
	
	private Motor leftLeg;
	private Motor rightLeg;
	private Motor armPivot;
	private Motor armExtensor;
	
	private Servo leftClimberArm;
	private Servo rightClimberArm;
	private Servo leftBooper;
	private Servo rightBooper;
	private Servo leftBucketBlocker;
	private Servo rightBucketBlocker;
	
	private CRServo blockSweeperLeft;
	private CRServo blockSweeperRight;
	
	private TouchSensor touch;
	private ColorSensor color;
	
	private static final double LEG_MOTOR_SPEED = 1.0;
	
	private static final double SWEEPER_SERVO_SPEED = 1.0;
	
	private static final double ARM_BUCKET_ROTATION_SPEED = 1.0;
	
	/**
	 * Values representing the positions for each booper in the open and 
	 * close positions. These were found by trial and error.
	 */
	private static final double LEFT_BOOPER_UP_POSITION = 1.0,
			LEFT_BOOPER_DOWN_POSITION = 0.45, 
			RIGHT_BOOPER_UP_POSITION = 0.0, 
			RIGHT_BOOPER_DOWN_POSITION = 0.55;
	
	/**
	 * Values representing the positions for each bucket blocker in the open
	 * and closed positions. These have yet to be tested
	 */
	private static final double LEFT_BUCKET_BLOCKER_BLOCKED_POSITION = 0.9,
			LEFT_BUCKET_BLOCKER_OPEN_POSITION = 0.0,
			RIGHT_BUCKET_BLOCKER_BLOCKED_POSITION = 0.0,
			RIGHT_BUCKET_BLOCKER_OPEN_POSITION = 0.9;
	
	private static final double ENCODER_TICKS_PER_DRIVE_INCH = 131.25;
	private static final double ENCODER_TICKS_PER_ROTATION_DEGREE = 13.85;
	
	private boolean objectTrack = false;
	
	@Override
	public String[] getDriveMotorNames() {
		return new String[] {"front_left", "front_right", "back_left", "back_right"};
	}

	@Override
	public void initializeRobot() {
		this.leftLeg = Lightning.getMotor("left_leg");
		this.rightLeg = Lightning.getMotor("right_leg");
		this.armPivot = Lightning.getMotor("arm_pivot");
		this.armExtensor = Lightning.getMotor("arm_extensor");
		this.leftClimberArm = Lightning.getServo("climber_arm_left");
		this.rightClimberArm = Lightning.getServo("climber_arm_right");
		this.leftBooper = Lightning.getServo("left_booper");
		this.rightBooper = Lightning.getServo("right_booper");
		this.leftBucketBlocker = Lightning.getServo("left_blocker");
		this.rightBucketBlocker = Lightning.getServo("right_blocker");
		this.blockSweeperRight = (CRServo) Lightning.getMotor("block_sweeper_right");
		this.blockSweeperLeft = (CRServo) Lightning.getMotor("block_sweeper_left");
		
		try {
			this.touch = Lightning.getRobotHardware().touchSensor.get("touch");
		} catch (IllegalArgumentException ignore) {}
		
		try {
			this.color = Lightning.getRobotHardware().colorSensor.get("color");
		} catch (IllegalArgumentException ignore) {}
		
		//These values tested and accurate as of December 9, 2015
		this.getDrive().setEncoderTicksPerDriveInch(ENCODER_TICKS_PER_DRIVE_INCH);
		this.getDrive().setEncoderTicksPerRotationDegree(ENCODER_TICKS_PER_ROTATION_DEGREE);
		
		//Initialize the climber arm servos in their down positions
		this.moveRightClimberArm(0);
		this.moveLeftClimberArm(1);
		
		//Initialize the booper and blocker servos to their closed positions
		this.closeLeftBlocker();
		this.closeRightBlocker();
		this.closeLeftBooper();
		this.closeRightBooper();

	}
	
	
	
	/*
	 * Walking methods (legs)
	 */
	
	public void walkForward() {
		this.setLegPower(LEG_MOTOR_SPEED);
	}
	
	public void walkBackward() {
		this.setLegPower(-LEG_MOTOR_SPEED);
	}
	
	public void stopWalking() {
		this.setLegPower(0);
	}
	
	private void setLegPower(double pow) {
		this.leftLeg.setPower(pow);
		this.rightLeg.setPower(pow);
	}
	
	/*
	 * Sweeper Servo methods
	 */
	
	private void setSweeperPower(double pow) {
		this.blockSweeperLeft.setPower(-pow);
		this.blockSweeperRight.setPower(pow);
	}
	
	public void blockObject() {
		this.setSweeperPower(SWEEPER_SERVO_SPEED);
	}
	
	public void letObject() {
		this.setSweeperPower(0);
	}
	
	public void toggleObject() {
		if (this.objectTrack) {
			this.blockObject();
		} else {
			this.letObject();
		}
		
		this.objectTrack = !(this.objectTrack);
	}
	/*
	 * Arm bucket rotation methods
	 */
	
	public void armPivotForward() {
		this.setArmPivotPower(ARM_BUCKET_ROTATION_SPEED);
	}
	
	public void armPivotBackwards() {
		this.setArmPivotPower(-ARM_BUCKET_ROTATION_SPEED);
	}
	
	public void stopArmPivotRotation() {
		this.setArmPivotPower(0);
	}
	
	private void setArmPivotPower(double pow) {
		this.armPivot.setPower(pow);
	}
	
	public void setArmPivotPositionDefault() {
		while (armPivot.getEncoder().getPosition() < 0) {
			this.setArmPivotPower(ARM_BUCKET_ROTATION_SPEED);
		}
		this.setArmPivotPower(0);
	}
	
	/**
	 * Extends or retracts the arm, based off of its power
	 * @param pow the power to which to set the arm extensor
	 */
	public void moveArmExtensor(double pow) {
		this.armExtensor.setPower(pow);
	}
	
	public void retractExtensor() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!ResQRobot.this.touchPressed()) {
					ResQRobot.this.moveArmExtensor(1);
				}
				ResQRobot.this.moveArmExtensor(0);
			}
		});
		t.start();
	}
	
	public void openLeftBlocker() {
		this.leftBucketBlocker.moveToPosition(LEFT_BUCKET_BLOCKER_OPEN_POSITION);
	}
	
	public void openRightBlocker() {
		this.rightBucketBlocker.moveToPosition(RIGHT_BUCKET_BLOCKER_OPEN_POSITION);
	}
	
	public void closeLeftBlocker() {
		this.leftBucketBlocker.moveToPosition(LEFT_BUCKET_BLOCKER_BLOCKED_POSITION);
	}
	
	public void closeRightBlocker() {
		this.rightBucketBlocker.moveToPosition(RIGHT_BUCKET_BLOCKER_BLOCKED_POSITION);
	}
	
	public void openLeftBooper() {
		this.leftBooper.moveToPosition(LEFT_BOOPER_UP_POSITION);
	}
	
	public void openRightBooper() {
		this.rightBooper.moveToPosition(RIGHT_BOOPER_UP_POSITION);
	}
	
	public void closeLeftBooper() {
		this.leftBooper.moveToPosition(LEFT_BOOPER_DOWN_POSITION);
	}
	
	public void closeRightBooper() {
		this.rightBooper.moveToPosition(RIGHT_BOOPER_DOWN_POSITION);
	}
	
	public void moveLeftClimberArm(int pos) {
		this.leftClimberArm.moveToPosition(pos);
	}
	
	public void moveRightClimberArm(int pos) {
		this.rightClimberArm.moveToPosition(pos);
	}
	
	public boolean touchPressed() {
		return touch.isPressed();
	}
	
	public Alliance getBeaconColor() {
		if (color.red() > color.blue())
			return Alliance.RED;
		return Alliance.BLUE;
	}
}
