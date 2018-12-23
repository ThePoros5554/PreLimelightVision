package org.usfirst.frc.team5554.robot;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class Target
{
    private Point centerPoint;
	private int width;
	private int height;
	
    public Target(Point center, int height, int width)
	{
        this.width = width;
        this.height = height;

        centerPoint = center;
	}
        
    public Target(double center_x, double center_y, int height, int width)
	{
        this.width = width;
        this.height = height;

        centerPoint = new Point(center_x, center_y);
	}
        
	public Target(MatOfPoint target)
	{
        Rect br = Imgproc.boundingRect(target);

        this.width = br.width;
        this.height = br.height;

        centerPoint = new Point(br.x + (br.width / 2.0), br.y + (br.height / 2.0));
	}
    
	public void SetCenter(double center_x, double center_y) // set the center point of the target *relative to the resolution of the Mat*
	{
        this.centerPoint.x = center_x;
        this.centerPoint.y = center_y;
	}
        
    public void SetCenter(Point center) // set the center point of the target *relative to the resolution of the Mat*
	{
        this.centerPoint = center;
	}
	
	public void SetSize(int w, int h) // set the width and height of the target
	{
        this.width = w;
        this.height = h;
	}
    
    public double GetAngle(double fovAngle, int pixelNum) // calculates the angle between the target and the camera (+ right, - left)
    {
        double center = pixelNum / 2 - 0.5;
        double focal_length = (0.5 * pixelNum) / Math.tan(fovAngle / 2.0);
        double angle = Math.toDegrees(Math.atan((this.centerPoint.x - center) / focal_length));
        
        return angle;
    }
	
    public Point GetCenter()
    {
        return this.centerPoint;
    }

    public Point GetUpperLeft()
    {
        return new Point(this.centerPoint.x - (this.width / 2.0), this.centerPoint.y - (this.height / 2.0));
    }
    
    public Point GetUpperRight()
    {
        return new Point(this.centerPoint.x + (this.width / 2.0), this.centerPoint.y - (this.height / 2.0));
    }
    
    public Point GetBottomLeft()
    {
        return new Point(this.centerPoint.x - (this.width / 2.0), this.centerPoint.y + (this.height / 2.0));
    }
    
    public Point GetBottomRight()
    {
        return new Point(this.centerPoint.x + (this.width / 2.0), this.centerPoint.y + (this.height / 2.0));
    }
    
    public int GetWidth()
    {
        return this.width;
    }
    
    public int GetHeight()
    {
        return this.height;
    }
}
