import java.awt.*;
import java.util.*;

public class BigMonster extends GameObject{
	private int SIZE = 80;
	private int MOVEMENT_PACE = 5;

	public final static int NORTH = 1;
	public final static int EAST  = 2;
	public final static int SOUTH = 3;
	public final static int WEST  = 4;
	public final static int NEAST = 5;
	public final static int NWEST = 6;
	public final static int SEAST = 7;
	public final static int SWEST = 8;
	int size = SIZE;

	int keep_direction = 0;
	int direction = NORTH;
	int time_to_wait = 0;


	Random rand = new Random();

	public BigMonster(int init_x, int init_y) {
		this.x = init_x;
		this.y = init_y;
	}

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillPolygon(this.getPaintable());
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
	
	
	private Polygon getPolygon() {
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(0, size);
		p.addPoint(size, size);
		p.addPoint(size, 0);
		return p;
	}

	

	public Polygon getPaintable() {
		Polygon poli = getPolygon();
		poli.translate(x + getGRID_X(), y + getGRID_Y());
		return poli;
	}

	public Polygon getTranslatedPolygon() {
		Polygon poli = getPolygon();
		poli.translate(x, y);
		return poli;
	}

	public int getPosition(MissingPolygon[] polys) {
		if (polys[0].toPolygon().contains(this.x, this.y))
			return 0;
		if (polys[1].toPolygon().contains(this.x, this.y))
			return 1;
		return 1;
	}


	public void generateDirection() {
		direction = rand.nextInt(SWEST - NORTH + 1) + NORTH;
	}


	public boolean isOuter(Point nex_mons_pos) {
		Polygon poli = getPolygon();
		int n = poli.npoints;
		for (int i = 0; i < n; i++){
			if (getWall().isOuter(new Point(poli.xpoints[i] + nex_mons_pos.x, poli.ypoints[i] + nex_mons_pos.y)))
				return true;
		}
		for (int i = 0; i < getBonuses().size(); i++) {
			Polygon next_mons = getPolygon();
			next_mons.translate(nex_mons_pos.x, nex_mons_pos.y);
			if (next_mons.intersects(getBonuses().get(i).getTranslatedPolygon().getBounds2D()))
				return true;
		}
		return false;
	}

	public void animate() {
		if (keep_direction == 400) {
			generateDirection();
			keep_direction = 0;
		}
		Point curr_mons_pos = new Point(this.x, this.y);
		Point nex_mons_pos = curr_mons_pos.getBigMonsterNewPosition(direction, MOVEMENT_PACE);
		
		if(isOuter(nex_mons_pos))
			generateDirection();
		else {
			this.x = nex_mons_pos.x;
			this.y = nex_mons_pos.y;
		}
		keep_direction++;

	}
}
