import java.awt.*;
import java.util.*;

public class SmallMonster extends GameObject{
	private  int MONSTER_SIZE = 40;
	private  int MOVEMENT_PACE = 2;
	private  int ROTATION_PACE = 1;
	private String name = "";
	private int keep_direction = 0;
	private int direction;
	private int time_to_wait = 0;
	
	private int NORTH = 1;
	private int EAST  = 2;
	private int SOUTH = 3;
	private int WEST  = 4;
	private int NEAST = 5;
	private int NWEST = 6;
	private int SEAST = 7;
	private int SWEST = 8;
	
	Random rand = new Random();
	boolean growing = true;
	double angle = 45;
	boolean dead = false;
	public SmallMonster(int init_x, int init_y, String name) {
		this(init_x, init_y);
		this.name = name;
	}

	public SmallMonster(int init_x, int init_y) {
		this.x = init_x;
		this.y = init_y;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillPolygon(this.getPaintable());
		g.drawString(name, x, y);
	}
	
	
	public void draw(Graphics g_main){
		if (Bonus.time_stopped && time_to_wait < 300) {
			time_to_wait++;
			this.paint(g_main);
			return;
		}
		else {
			time_to_wait = 0;
			Bonus.time_stopped = false;
		}
		animate();
		this.paint(g_main);
	}
	
	public Polygon getPolygon() {
		Polygon p = new Polygon();
		
		Point pnt = new Point(-MONSTER_SIZE/2, -MONSTER_SIZE/2);
		pnt.rotatePoint(angle);
		p.addPoint(pnt.x, pnt.y);
		
		pnt = new Point(-MONSTER_SIZE/2, MONSTER_SIZE/2);
		pnt.rotatePoint(angle);
		p.addPoint(pnt.x, pnt.y);
		
		pnt = new Point(MONSTER_SIZE/2, MONSTER_SIZE/2);
		pnt.rotatePoint(angle);
		p.addPoint(pnt.x, pnt.y);
		
		pnt = new Point(MONSTER_SIZE/2, -MONSTER_SIZE/2);
		pnt.rotatePoint(angle);
		p.addPoint(pnt.x, pnt.y);
		
		if (angle == 90)
			angle = 0;
		angle += ROTATION_PACE;
		return p;
	}

	public boolean isDead() {
		if (dead)
			return true;
		
		if (!getWall().poli.toPolygon().contains(this.x, this.y)) {
			dead = true;
			return true;
		}
		return false;
	}
	
	public Polygon getPaintable() {
		Polygon ret = getPolygon();
		ret.translate(x + getGRID_X(), y + getGRID_Y());
		return ret;
	}
	
	public Polygon getTranslatedPolygon() {
		Polygon cp_poly = getPolygon();
		cp_poly.translate(x, y);
		return cp_poly;
	}
	
	
	public void generateDirection() {
		direction = rand.nextInt(SWEST - NORTH + 1) + NORTH;
	}
	
	
	public boolean isOuter(Point nex_ship_pos) {
		Polygon cp_poly = getPolygon();
		int n = cp_poly.npoints;
		for (int i = 0; i < n; i++)
			if (getWall().isOuter(new Point(cp_poly.xpoints[i] + nex_ship_pos.x, cp_poly.ypoints[i] + nex_ship_pos.y)))
				return true;
		for (int i = 0; i < getBonuses().size(); i++) {
			Polygon next_critter = getPolygon();
			next_critter.translate(nex_ship_pos.x, nex_ship_pos.y);
			if (next_critter.intersects(getBonuses().get(i).getTranslatedPolygon().getBounds2D()))
				return true;
		}
		return false;
	}
	
	public void animate() {
		if (keep_direction == 400) {
			generateDirection();
			keep_direction = 0;
		}
		else if (keep_direction == 0)
			generateDirection();
		Point curr_ship_pos = new Point(this.x, this.y);
		Point nex_ship_pos = curr_ship_pos.getBigMonsterNewPosition(direction, MOVEMENT_PACE);
		
		if(isOuter(nex_ship_pos))
			generateDirection();
		else {
			this.x = nex_ship_pos.x;
			this.y = nex_ship_pos.y;
		}
			
		keep_direction++;
		
	}
}

