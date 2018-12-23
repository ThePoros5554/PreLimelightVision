package org.usfirst.frc.team5554.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class VisionHelper
{
	
    public VisionHelper()
    {
    }

    /*** Static: ***/

    public static void BGR2HSV(Mat frame, Mat dst) // parses frame from BGR to HSV
    {
        Imgproc.cvtColor(frame, dst, Imgproc.COLOR_BGR2HSV);
    }

    public static void Threshold(Mat frame, Scalar minBound, Scalar maxBound) // distinguishes pixels with values that fit the bounds from the other pixels
    {
        Core.inRange(frame, minBound, maxBound, frame);
    }

    public static List<MatOfPoint> GetContours(Mat frame) // gets a list of all shape outlines
    {
        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.findContours(frame, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        return contours;
    }

    private static MatOfPoint ConvexHull2Points(MatOfInt convexHull, MatOfPoint contour) // helping method to convert a MatOfInt convex to MatOfPoint
    {
        List<Integer> indexes = convexHull.toList();
        List<Point> points = new ArrayList<>();
        MatOfPoint matOfPoint = new MatOfPoint();

        for (Integer index : indexes)
        {
            points.add(contour.toList().get(index));
        }

        matOfPoint.fromList(points);

        return matOfPoint;
    }

    public static MatOfPoint GetConvexHull(MatOfPoint contour) // gets the convex of a shape
    {
        MatOfInt convexHullInt = new MatOfInt();
        MatOfPoint convexHullPoint = new MatOfPoint();

        Imgproc.convexHull(contour, convexHullInt);

        convexHullPoint = ConvexHull2Points(convexHullInt, convexHullPoint);

        return convexHullPoint;
    }

    public static boolean IsContourConvex(MatOfPoint contour)
    {
        return Imgproc.isContourConvex(contour);
    }

    public static void ErodeAndDilate(Mat frame, int kernel_x, int kernel_y) // eliminates small chunks of pixels in a frame
    {
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(kernel_x, kernel_y));

        Imgproc.erode(frame, frame, kernel); // makes bigger chunks of white pixels
        Imgproc.dilate(frame, frame, kernel); // normalizes the white chunks into a shape
    }

    public static boolean IsTargetInSize(Target t, double min_h, double max_h, double min_w, double max_w) // returns if the target is in the desired size bounds
    {
        double h = t.GetHeight();
        double w = t.GetWidth();

        if (h <= max_h && h >= min_h && w <= max_w && w >= min_w)
            return true;
        else
            return false;
    }

    public static boolean TargetWideness(Target t, double min_wr, double max_wr) // returns if the target has the desired wideness ratio bounds
    {
        double wideness = t.GetWidth() / t.GetHeight();

        if (wideness <= max_wr && wideness >= min_wr)
            return true;
        else
            return false;
    }

    /******/
}
