/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5554.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	/*** Ports ***/
	// motors
	public static final int REAR_RIGHT_MOTOR_PORT = 0;
	public static final int REAR_LEFT_MOTOR_PORT = 2;
	public static final int FRONT_RIGHT_MOTOR_PORT = 1;
	public static final int FRONT_LEFT_MOTOR_PORT = 3;
	//joystick
	public static final int DRIVER_JOYSTICK_PORT = 0;

	/*** Vision ***/
	public static final int CAMERA_RES_HEIGHT = 320;
	public static final int CAMERA_RES_WIDTH = 240;
	public static final int KERNEL_SQUARE_LENGTH = 5;
	
	/*** PID ***/
	public static final double ALIGN_SPEED_P = 0.0001;
	public static final double ALIGN_SPEED_I = 0;
	public static final double ALIGN_SPEED_D = 0;
	public static final double ALIGN_TURN_P = 0.004;
	public static final double ALIGN_TURN_I = 0;
	public static final double ALIGN_TURN_D = 0;
	public static final double OFFSET_SETPOINT = 0;
	public static final double AREA_SETPOINT = 6500;
	
	/*** Dashoard ***/
	public static final String MIN_R_KEY = "min_R";
	public static final String MIN_G_KEY = "min_G";
	public static final String MIN_B_KEY = "min_B";
	public static final String MAX_R_KEY = "max_R";
	public static final String MAX_G_KEY = "max_G";
	public static final String MAX_B_KEY = "max_B";
	public static final String MIN_HEIGHT_KEY = "min_h";
	public static final String MIN_WIDTH_KEY = "min_w";
	public static final String MAX_HEIGHT_KEY = "max_h";
	public static final String MAX_WIDTH_KEY = "max_w";
}
