package org.usfirst.frc.team5554.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import systems.PIDProcessor;
import util.DynamicSource;
import vision.CameraHandler;
import vision.Stream;
import vision.VideoBox;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraThread extends Thread
{		
    public DynamicSource speed;
    public DynamicSource turn;

	public CameraThread(DynamicSource speed, DynamicSource turn)
	{
		this.speed = speed;
		this.turn = turn;
	}

	@Override	
	public void run()
	{		
		/*** Setup ***/
		CameraHandler cameras = new CameraHandler(1, RobotMap.CAMERA_RES_WIDTH, RobotMap.CAMERA_RES_HEIGHT);
        VideoBox screen = new VideoBox("Live Feed" , RobotMap.CAMERA_RES_WIDTH, RobotMap.CAMERA_RES_HEIGHT);
        List<MatOfPoint> contours = new ArrayList<>();
		
		cameras.SetStreamer(0);
				
		/*** Body ***/
		while (!Thread.interrupted()) 
		{	
            int size = 0;
            double offset = 0;
			
            Stream stream = cameras.GetStream();
            Mat frame;
            
            Scalar minThresholdBound = new Scalar(
	            	(int)SmartDashboard.getNumber(RobotMap.MIN_R_KEY, 0),
	            	(int)SmartDashboard.getNumber(RobotMap.MIN_G_KEY, 0),
	                (int)SmartDashboard.getNumber(RobotMap.MIN_B_KEY, 0));
            Scalar maxThresholdBound = new Scalar(
	        		(int)SmartDashboard.getNumber(RobotMap.MAX_R_KEY, 255),
	        		(int)SmartDashboard.getNumber(RobotMap.MAX_G_KEY, 255),
	                (int)SmartDashboard.getNumber(RobotMap.MAX_B_KEY, 255));
            
            if (stream.GetReport() == 0) 
            {
                continue;
            }
            else
            {
                frame = stream.GetImage();

                /*** processes the frame given ***/
                VisionHelper.BGR2HSV(frame, frame);
                VisionHelper.Threshold(frame, minThresholdBound, maxThresholdBound);
                VisionHelper.ErodeAndDilate(frame, RobotMap.KERNEL_SQUARE_LENGTH, RobotMap.KERNEL_SQUARE_LENGTH);
                    
                /*** gets information from processed frame ***/
                contours = VisionHelper.GetContours(frame);
                
                for (MatOfPoint contour : contours)
                {
                    Target t = new Target(contour);

                    if (VisionHelper.IsTargetInSize(t,
                		SmartDashboard.getNumber(RobotMap.MIN_HEIGHT_KEY, 0),
                		SmartDashboard.getNumber(RobotMap.MAX_HEIGHT_KEY, 1000),
                		SmartDashboard.getNumber(RobotMap.MIN_WIDTH_KEY, 0),
                		SmartDashboard.getNumber(RobotMap.MAX_WIDTH_KEY, 1000)))
                    {
                		if (t.GetSize() > size )
                		{
                			size = t.GetSize();
                			offset = t.GetOffsetX(RobotMap.CAMERA_RES_WIDTH);
                		}
                    }
                }

        		System.out.println("Size: " + size);
        		System.out.println("Offset: " + offset);
            	speed.SetValue(size);
            	turn.SetValue(offset);
                
            	screen.stream(frame);
            }
		}
	}
}