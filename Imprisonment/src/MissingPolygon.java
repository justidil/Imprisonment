import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;

class Segment {

	public final Point p2;
	public final Point p1;

	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public boolean isPointOnSegment(Point lookup) {
		// the point we're looking for identical to a margin of the segment
		if (lookup.y == p1.y && lookup.x == p1.x)
			return true;
		if (lookup.y == p2.y && lookup.x == p2.x)
			return true;

		// this is a horizontal segment, and the point we're looking for
		// is between the X coords of the two points
		if (lookup.y == p1.y
				&& lookup.y == p2.y
				&& (lookup.x > p1.x && lookup.x < p2.x || lookup.x < p1.x
						&& lookup.x > p2.x))
			return true;

		// this is a vertical segment, and the point we're looking for
		// is between the Y coords of the two points
		if (lookup.x == p1.x
				&& lookup.x == p2.x
				&& (lookup.y > p1.y && lookup.y < p2.y || lookup.y < p1.y
						&& lookup.y > p2.y))
			return true;

		return false;
	}

	public boolean equals(Segment s) {
		return p1.equals(s.p1) && p2.equals(s.p2)
		|| p2.equals(s.p1) && p1.equals(s.p2);
	}

	public int length() {
		return p1.dist(p2);
	}

	@Override
	public String toString() {
		return "Segment {p1=" + p1 + ", p2=" + p2 + "}";
	}
}

public class MissingPolygon{

	boolean isClosedLine;
	public ArrayList<Point> points = new ArrayList<Point>();

	public MissingPolygon(boolean isClosedLine) {
		this.isClosedLine = isClosedLine;
	}

	public MissingPolygon(boolean isClosedLine, Point[] arr) {
		this.isClosedLine = isClosedLine;
		for (Point p : arr)
			this.addPointExtendingSegment(p);
	}

	public MissingPolygon(boolean isClosedLine, ArrayList<Point> arr) {
		this.isClosedLine = isClosedLine;
		for (Point p : arr)
			this.addPointExtendingSegment(p);
	}
	
	

	public Segment getLinePointIsOn(Point lookup) {
		int n = points.size();

		for (int i = 0; i < n - 1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i == n - 1 ? 0 : i + 1);

			Segment seg = new Segment(p1, p2);
			if (seg.isPointOnSegment(lookup))
				return seg;
		}

		if (!isClosedLine)
			return null;

		// in closed lines there is a line from the last to the first point
		Segment seg = new Segment(points.get(n - 1), points.get(0));
		if (seg.isPointOnSegment(lookup))
			return seg;

		return null;
	}

	public boolean isPointOnPerimeter(Point lookup) {
		// if there exists a segment on which this point resides then
		// the point is on the perimeter of the broken line
		return getLinePointIsOn(lookup) != null;
	}

	public void draw(Graphics g, int off_x, int off_y) {
		int n = points.size();

		g.setColor(Color.GRAY);
		Polygon walls = toPolygon();
		walls.translate(off_x+1, off_y+1); 
		g.fillPolygon(walls);
		g.setColor(Color.black);
		for (int i = 0; i < n - 1; i++)
			g.drawLine(off_x + points.get(i).x, off_y + points.get(i).y,
					off_x + points.get(i + 1).x, off_y + points.get(i + 1).y);

		if (isClosedLine)
			g.drawLine(off_x + points.get(n - 1).x, off_y + points.get(n - 1).y,
					off_x + points.get(0).x, off_y + points.get(0).y);
		
		
	}

	public void addPointExtendingSegment(Point p) {
		int prev_pos = this.points.size() - 1;
		int pre_prev_pos = this.points.size() - 2;

		// there is no segment to extend
		if (pre_prev_pos < 0) {
			this.points.add(p);
			return;
		}

		Point prev = this.points.get(prev_pos);
		Point pre_prev = this.points.get(pre_prev_pos);
		if (prev.x == pre_prev.x && prev.x == p.x || prev.y == pre_prev.y
				&& prev.y == p.y)
			this.points.set(prev_pos, p); // overwrite last point with the new
		// one
		else
			this.points.add(p);
	}

	public Polygon toPolygon() {
		int n = this.points.size();
		int x[] = new int[n];
		int y[] = new int[n];
		for (int i = 0; i < n; i++) {
			Point p = this.points.get(i);
			x[i] = p.x;
			y[i] = p.y;
		}
		return new Polygon(x, y, n);
	}

	public int getPerimeterLength() {
		int len = 0;
		int n = points.size();

		for (int i = 0; i < n - 1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i == n - 1 ? 0 : i + 1);
			Segment seg = new Segment(p1, p2);
			len += seg.length();
		}

		if (isClosedLine) {
			// in closed lines there is a line from the last to the first point
			Segment seg = new Segment(points.get(n - 1), points.get(0));
			len += seg.length();
		}

		return len;
	}

	/*
	 * Returns the two MissingPolygon representing two polygons that result from
	 * cutting this MissingPolygon by the given trail.
	 */
	public MissingPolygon[] cutTerrain(MissingPolygon trail) {
		int n = this.points.size();

		// make a copy of the point list to not mess up with the original.
		@SuppressWarnings("unchecked")
		ArrayList<Point> trail_points = (ArrayList<Point>) trail.points.clone();

		int trail_size    = trail_points.size();
		Point trail_start = trail_points.get(0);
		Point trail_stop  = trail_points.get(trail_size - 1);

		if (trail_start.equals(trail_stop)) {
			// if we move up attacking into the terrain and then go down to the perimeter 
			// the start and stop of the trail will be the same point.
			// in this case there's not cutting to be done.
			// Just return the same broken line to the caller in both cuts.
			return new MissingPolygon[] {this, this};
		}
		
		Segment start_seg = this.getLinePointIsOn(trail_start);
		Segment end_seg   = this.getLinePointIsOn(trail_stop);


		// decide whether we need to reverse the trail points before adding it
		// to the new borders.
		boolean reverse_tail_points = false;

		if (start_seg.equals(end_seg)) {
			if (start_seg.p1.dist(trail_start) > start_seg.p1.dist(trail_stop))
				reverse_tail_points = true;
		} else {
			if (points.indexOf(start_seg.p1) > points.indexOf(end_seg.p1))
				reverse_tail_points = true;
		}

		if (reverse_tail_points) {
			Collections.reverse(trail_points);
			Segment aux = start_seg;
			start_seg = end_seg;
			end_seg = aux;
		}

		int i1 = points.indexOf(start_seg.p1);
		int i2 = points.indexOf(end_seg.p2);

		MissingPolygon be = new MissingPolygon(true);
		MissingPolygon bi = new MissingPolygon(true);


		int off_e = i1 < i2 ? n : 0;
		for (int i = i2; i != i1 + 1 + off_e; i++)
		{
			be.addPointExtendingSegment(this.points.get(i % n));
		}

		for(int i = 0; i < trail_size; i++) {
			be.addPointExtendingSegment(trail_points.get(i));
			bi.addPointExtendingSegment(trail_points.get(trail_size - 1 - i));
		}
		int off_i = n - off_e;
		for (int i = i1 + 1; i != i2 + off_i; i++)
		{
			bi.addPointExtendingSegment(this.points.get(i % n));
		}

		return new MissingPolygon[] {be.rotate(), bi.rotate()};
	}

	private MissingPolygon rotate() {
		int n = points.size();
		if (points.get(0).y == points.get(1).y &&
				points.get(0).x <  points.get(1).x)
			return this;
		Point last = points.remove(n-1);
		points.add(0, last);
		return this.rotate();
	}

	public void addPointsExtendingSegment(ArrayList<Point> pts) {
		for (Point p : pts)
			this.addPointExtendingSegment(p);
	}

}
