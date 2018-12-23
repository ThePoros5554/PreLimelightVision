/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5554.robot;

import commands.ArcadeDrive;
import commands.DriveMechanum;
import commands.auto.PIDArcadeDrive;
import commands.auto.PIDMechDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.DiffDriveTrain;
import subsystems.MechDriveTrain;
import subsystems.MechDriveTrain.MechDrivingDirection;
import subsystems.MechSys;
import systems.PIDProcessor;
import systems.PIDProcessor.ToleranceType;
import triggers.SmartJoystick;
import util.DynamicSource;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static OI oi;

	public static MechDriveTrain dt;
	
	public Victor rearRightMotor;
	public Victor frontRightMotor;
	public Victor rearLeftMotor;
	public Victor frontLeftMotor;
	
	public static SmartJoystick joy;
	
	private PIDMechDrive align;
	private PIDProcessor speed;
	private PIDProcessor turn;
	private DynamicSource s;
	private DynamicSource t;
	
	private CameraThread thread;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		frontRightMotor = new Victor(RobotMap.FRONT_RIGHT_MOTOR_PORT);
		rearRightMotor = new Victor(RobotMap.REAR_RIGHT_MOTOR_PORT);
		frontLeftMotor = new Victor(RobotMap.FRONT_LEFT_MOTOR_PORT);
		rearLeftMotor = new Victor(RobotMap.REAR_LEFT_MOTOR_PORT);
		
		//feederAxis = new MechSys(RobotMap.FEEDER_AXIS_PORT);
		
		joy = new SmartJoystick(0);

		
		dt = new MechDriveTrain(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		dt.SetIsRanged(true);
		dt.setDefaultCommand(new DriveMechanum(dt, joy));
		
		s = new DynamicSource(0.0);
		t = new DynamicSource(0.0);
		
	    SmartDashboard.putNumber(RobotMap.MIN_HEIGHT_KEY, 0);
	    SmartDashboard.putNumber(RobotMap.MAX_HEIGHT_KEY, 200);
	    SmartDashboard.putNumber(RobotMap.MIN_WIDTH_KEY, 0);
	    SmartDashboard.putNumber(RobotMap.MAX_WIDTH_KEY, 200);
	    SmartDashboard.putNumber(RobotMap.MIN_R_KEY, 20);
	    SmartDashboard.putNumber(RobotMap.MIN_G_KEY, 90);
	    SmartDashboard.putNumber(RobotMap.MIN_B_KEY, 0);
	    SmartDashboard.putNumber(RobotMap.MAX_R_KEY, 70);
	    SmartDashboard.putNumber(RobotMap.MAX_G_KEY, 180);
	    SmartDashboard.putNumber(RobotMap.MAX_B_KEY, 255);
		
		thread = new CameraThread(s, t);
		thread.setDaemon(true);
		thread.start();
		
		speed = new PIDProcessor(0.0001, 0, 0, s, true);
		turn = new PIDProcessor(0.004, 0, 0, t, true);
		
		
		align = new PIDMechDrive(dt, MechDrivingDirection.Forward, speed, 6000, null, 0, turn, 0);
		
		oi = new OI();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		align.cancel();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit()
	{
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		align.start();
	}

	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
