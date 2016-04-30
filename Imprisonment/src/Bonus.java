import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Bonus extends GameObject{
	private int SIZE = 50;
	private int INCREASE_SPEED = 0;
	private int DECREASE_SPEED = 1;
	private int STOP_TIME = 2;
	int trait;
	boolean taken = false;
	static boolean time_stopped = false;
	String message = "";
	boolean acted = false;
	Image image;
	ImageIcon imageicon;
	public Bonus(){}
	
	public Bonus(int init_x, int init_y, int trait) {
		x = init_x;
		y = init_y;
		this.trait = trait;
	}
	
	public void paint(Graphics g) {
		if (!checkIfTaken()) {
			g.setColor(Color.YELLOW);
			g.fillPolygon(this.getPaintable());
		}
		else {
			if (!acted)
				actOnMonster(getDot());
			g.setColor(Color.green);
			g.fillOval(x + getGRID_X(), y + getGRID_Y(), 50, 50);
			g.setColor(Color.black);
			g.drawString(message, x + 2*(getGRID_X()-4), y + 2*getGRID_Y());
			acted = true;
		}
	}


	public void draw(Graphics g_main){
		this.paint(g_main);
	}
	
	public void actOnMonster(Dot dot) {
		switch (trait) {
		case 0:
			dot.setSpeed(20);
			message = "S";
			break;
		case 1:
			dot.setSpeed(5);
			message = "D";
			break;
		case 2:
			message = "T";
			time_stopped = true;
			break;
		}
	}

	public boolean checkIfTaken() {
		if (!getWall().poli.toPolygon().contains(this.getTranslatedPolygon().getBounds2D()))
			return true;
		return false;
	}
	
	public Polygon getPolygon() {
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(0, SIZE);
		p.addPoint(SIZE, SIZE);
		p.addPoint(SIZE, 0);
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
	
	public int getSIZE() {
		return SIZE;
	}

	public int getINCREASE_SPEED() {
		return INCREASE_SPEED;
	}

	public int getDECREASE_SPEED() {
		return DECREASE_SPEED;
	}

	public int getSTOP_TIME() {
		return STOP_TIME;
	}

}
