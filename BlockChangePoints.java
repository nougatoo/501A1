import java.awt.Point;


public class BlockChangePoints {
	
	private Point[] points = new Point[5]; //Number can change depending on how many we want 
	
	public BlockChangePoints(int mult1, int b1,
							int mult2, int b2,
							int mult3, int b3, 
							int mult4, int b4, 
							int mult5, int b5)
	{
		points[0] = new Point(b1, mult1);
		points[1] = new Point(b2, mult2);
		points[2] = new Point(b3, mult3);
		points[3] = new Point(b4, mult4);
		points[4] = new Point(b5, mult5);
	}
	
	public Point[] getPoints()
	{
		return points;
	}
	
	public void setPoint(int index, int x, int y)
	{
		points[index].x=x;
		points[index].y=y;
	}
	

}
