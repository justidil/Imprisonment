import java.awt.Polygon;


public class Wall extends GameObject{
	MissingPolygon poli = new MissingPolygon(true);
	double initial_area;

	public Wall() {
		x = 0;
		y = 0;
		poli.addPointExtendingSegment(new Point(0, 0));
		poli.addPointExtendingSegment(new Point(getGAME_WIDTH(), 0));
		poli.addPointExtendingSegment(new Point(getGAME_WIDTH(), getGAME_HEIGHT()));
		poli.addPointExtendingSegment(new Point(0, getGAME_HEIGHT()));
		initial_area = calcArea();
	}

	public boolean isPointOnPerimeter(Point lookup) {
		return poli.isPointOnPerimeter(lookup);
	}

	public double calcArea() {
		Polygon wall_area = this.poli.toPolygon();
		int n = wall_area.npoints;
		double area = 0;
		for (int i = 0; i < n-1; i++) {
			area += wall_area.xpoints[i] 	 * wall_area.ypoints[i+1] -
					wall_area.xpoints[i+1] * wall_area.ypoints[i];
		}
		return area/2;
	}
	
	public int percentageOccupied() {
		return (int) (100 - ((calcArea()/initial_area) * 100));
	}
	
	public boolean isOuter(Point p) {
		return !(this.poli.toPolygon().contains(p.x, p.y) ||
				this.poli.isPointOnPerimeter(p));
	}
}
