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
	
    public Target(Point center, int height, int width) // for general targets (ex. LimeLight vision targets)
	{
        this.width = width;
        this.height = height;

        centerPoint = center;
	}
        
    public Target(double center_x, double center_y, int height, int width) // for general targets (ex. LimeLight vision targets)
	{
        this.width = width;
        this.height = height;

        centerPoint = new Point(center_x, center_y);
	}
        
	public Target(MatOfPoint points) // for OpenCV vision targets
	{
        Rect br = Imgproc.boundingRect(points);

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
	
	public void SetWidth(int width) // set the width of the target
	{
        this.width = width;
	}
	
	public void SetHeight(int height) // set the height of the target
	{
        this.height = height;
	}
	
    public Point GetCenter()
    {
        return this.centerPoint;
    }
    
    public int GetWidth()
    {
        return this.width;
    }
    
    public int GetHeight()
    {
        return this.height;
    }
    
    public double GetOffsetX(int image_pixel_width)
    {
        return this.centerPoint.x - (image_pixel_width / 2 - 0.5); // calculates x offset from center (+ right - left)
    }
    
    public double GetOffsetY(int image_pixel_height)
    {
        return -(this.centerPoint.y - (image_pixel_height / 2 - 0.5)); // calculates y offset from center (+ up - down)
    }
    
    public int GetSize()
    {
    	return this.height * this.width;
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
}