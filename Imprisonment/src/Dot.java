import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;


public class Dot extends GameObject{
	static final int SIZE = 20; // the triangle's side size
	int pace = 10;
	final  int RIGHT = 0;
	final  int DOWN  = 1;
	final  int LEFT  = 2;
	final  int UP    = 3;
	boolean first_time = true;
	boolean dead = false;
	boolean isAttacking = false;
	boolean speed_changed = false;
	int time_to_normal = 0;
	double triangle_rotation_angle = 0;
	boolean hasSmallMonsterBombss = false;
	boolean hasShipBombss = false;
	int orientation = RIGHT;
	static int lives = 3;
	MissingPolygon trail;
	// initial positions
	public Dot(){
		x = getGAME_WIDTH()/2;
		y = getGAME_HEIGHT();
		trail = new MissingPolygon(false); // an open broken line of points
	}

	
	public Polygon getPolygon() {
		Polygon p = new Polygon();
		int n = 3; // triangle has 5 points.
		double angle_between_poligon_points = 2 * Math.PI / n;
		int r = SIZE;
		
		for(int i=0; i < n; i++) {
			double v = i * angle_between_poligon_points + triangle_rotation_angle;
			int x, y;
			x = (int) Math.round(r * Math.cos(v));
			y = (int) Math.round(r * Math.sin(v));
			p.addPoint(x, y);
		}
		return p;
	}

	public void draw(Graphics g) {
		if (!isDead()) {
			if (speed_changed && time_to_normal == 600) {
				setSpeed(10);
				speed_changed = false;
				time_to_normal = 0;
			}
			else
				time_to_normal++;
			g.setColor(Color.blue);
			trail.draw(g, 30, 30);
			g.setColor(Color.CYAN);
			g.fillPolygon(getPaintable());
		}
		else  {
			g.drawString("You Are Dead!", x + 30, y + 30);
			manageDeath();
			lives--;
		}
	}

	
	public void setSpeed(int new_speed) {
		this.pace = new_speed;
		speed_changed = true;
	}

	public Polygon getPaintable() {
		Polygon ret = getPolygon();
		ret.translate(x + 30, y + 30);
		return ret;
	}
	
	public Polygon getTranslatedPolygon() {
		Polygon cp_poly = getPolygon();
		cp_poly.translate(x, y);
		return cp_poly;
	}
	
	public boolean isMovingOnPerimeter(Point curr_player_pos, Point next_player_pos) {
		return getWall().isPointOnPerimeter(curr_player_pos)
		&& getWall().isPointOnPerimeter(next_player_pos);
	}
	
	public int getLives() {
		return lives;
	}
	
	public static void waiting (int n){
		long t0, t1;
		t0 =  System.currentTimeMillis();
		do{
			t1 = System.currentTimeMillis();
		}
		while ((t1 - t0) < (n * 1000));
	}

	public void manageDeath() {
		this.x = trail.points.get(0).x;
		this.y = trail.points.get(0).y;
		isAttacking = false;
		first_time = true;
		trail.points.clear();
	}
	
	public void attack(Point curr_player_pos, Point next_player_pos) {

		if (first_time) {
			trail.addPointExtendingSegment(curr_player_pos);
			first_time = false;
		}
		this.trail.addPointExtendingSegment(next_player_pos);

		if (getWall().isPointOnPerimeter(next_player_pos)) {
			// finalize attack
			isAttacking = false;
			first_time = true; // reset first_time
			MissingPolygon polys[] = getWall().poli.cutTerrain(trail);
			int monsterPosition = getBigmonster().getPosition(polys);
			trail = new MissingPolygon(false);
			getWall().poli = polys[monsterPosition];
		}
	}

	public boolean hasMoreLives() {
		if (lives > 0)
			return true;
		return false;
	}
	
	public boolean isTrailOnPoly(Polygon enemy) {
		int n = enemy.npoints;
		for (int i = 0; i < n; i++)
			if (trail.isPointOnPerimeter(new Point(enemy.xpoints[i], enemy.ypoints[i])))
				return true;
		return false;
	}

	public boolean isDead() {
		Polygon cp_ship     = getBigmonster().getTranslatedPolygon();
		Polygon cp_player   = getTranslatedPolygon();
		
		if (isAttacking && (cp_player.intersects(cp_ship.getBounds()))) 
			return true;
		
		for (int i = 0; i < getNumbersmallmonster(); i++) {
			getSmallMonsters().get(i);
			if (isAttacking && cp_player.intersects(getSmallMonsters().get(i).getPolygon().getBounds()))
				return true;
			
			if (isAttacking && isTrailOnPoly(getSmallMonsters().get(i).getTranslatedPolygon()))
				return true;
		}
		
		if (isAttacking && (isTrailOnPoly(cp_ship)))
			return true;
		
		return false;
	}
	
	public boolean isBonus(Point next_player_pos) {
		for (int i = 0; i < getBonuses().size(); i++) {
			Polygon next_player = getPolygon();
			next_player.translate(next_player_pos.x, next_player_pos.y);
			if (next_player.intersects(getBonuses().get(i).getTranslatedPolygon().getBounds2D()))
				return true;
		}
		return false;
	}
	
	private void updatePlayerRotationAngle(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:;
			triangle_rotation_angle = 3 * Math.PI / 2;
			break;
		case KeyEvent.VK_DOWN:
			triangle_rotation_angle = Math.PI / 2;
			break;
		case KeyEvent.VK_LEFT:
			triangle_rotation_angle = Math.PI;
			break;
		case KeyEvent.VK_RIGHT:
			triangle_rotation_angle = 0;
			break;
		default:
			break;
		}
	}

	
	public void key_decide(int keyCode) {
		updatePlayerRotationAngle(keyCode);
		
		Point curr_player_pos = new Point(this.x, this.y);

		Point next_player_pos = null;

		int p = pace;
		while (p > 0)
		{
			next_player_pos = curr_player_pos.getNewPosition(keyCode, p, this);
			if (!getWall().isOuter(next_player_pos))
				break;
			// when nearing an edge we might not have enough space to do a full pace
			// in that case retry with a smaller pace.
			p--;
		}

		if (p == 0) {
			// if moving a single pixel from the current position in the direction
			// indicated by keyCode will get us out of the terrain, then we
			// surely cannot move in that direction.
			return;
		}

		if (isMovingOnPerimeter(curr_player_pos, next_player_pos)) {
			this.x = next_player_pos.x;
			this.y = next_player_pos.y;
		}
		else if (!isBonus(next_player_pos)) {
			isAttacking = true;
			attack(curr_player_pos, next_player_pos);
			this.x = next_player_pos.x;
			this.y = next_player_pos.y;
		}

		
	}
}
