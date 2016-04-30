import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MapManager extends JPanel implements  Runnable {
	private final int GRIDS   = 10;
	private final int GRID_X 	   = GRIDS + Dot.SIZE;
	private final int GRID_Y 	   = GRIDS + Dot.SIZE;
	private final int GAME_WIDTH   = 1000;
	private final int GAME_HEIGHT  = 600;
	private final int WIN_PERCENTAGE = 50;
	private final Color LEVEL1_BG = Color.getHSBColor(100, 160, 99);
	private final Color LEVEL_2_BG = Color.getHSBColor(90, 170, 60);
	private Color bg_color = LEVEL1_BG;
	int current_level = 1;
	private Random rand = new Random();
	private int delay, frame;
	private int numbersmallmonster = 2;
	private boolean isStarted = true;
	private Dot dot = new Dot();
	private BigMonster bigmonster;
	private ArrayList<SmallMonster> smallMonsters = new ArrayList<SmallMonster>();
	private Thread threads = new Thread(this);
	private Wall wall;
	private ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	private Bonus bon = new Bonus();
	Image offscreen;
	public int width, height;
	public int state;
	boolean first = true;
	private GameObject obj = new GameObject();
	public MapManager(){
		addKeyListener(new KeyListener(){
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
						state = arg0.getKeyCode();
						dot.key_decide(state);
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}});
		
		setFocusable(true);
		requestFocusInWindow();
		int fps = 50;
		offscreen = createImage(this.getSize().width,this.getSize().height);
		//gra = offscreen.getGraphics();
		delay = fps > 0 ? 1000 / fps : 100;
		createObjects();
		threads.start();
	}
	public void createObjects() {
		wall = new Wall();
		bigmonster = new BigMonster(rand.nextInt(700 - 100 + 1) + 100, rand.nextInt(400 - 100 + 1) + 100);
		for (int i = 0; i < numbersmallmonster; i++)
			smallMonsters.add(new SmallMonster(rand.nextInt(700 - 100 + 1) + 100, rand.nextInt(400 - 100 + 1) + 100));
		bonuses.add(new Bonus(1, GAME_HEIGHT-bon.getSIZE(), bon.getINCREASE_SPEED()));
		bonuses.add(new Bonus(GAME_WIDTH-bon.getSIZE(), GAME_HEIGHT-bon.getSIZE(), bon.getDECREASE_SPEED()));
		bonuses.add(new Bonus(1, 1, bon.getSTOP_TIME()));
		obj.setWall(wall);
		obj.setBigmonster(bigmonster);
		obj.setBonuses(bonuses);
		obj.setDot(dot);
		obj.setGAME_HEIGHT(GAME_HEIGHT);
		obj.setGAME_WIDTH(GAME_WIDTH);
		obj.setGRID_X(GRID_X);
		obj.setGRID_Y(GRID_X);
		obj.setNumbersmallmonster(numbersmallmonster);
		obj.setSmallMonsters(smallMonsters);
	}
	
	public static void waits (int n){
		long t0, t1;
		t0 =  System.currentTimeMillis();
		do{
			t1 = System.currentTimeMillis();
		}
		while ((t1 - t0) < (n * 1000));
	}
	
	public void next_level() {
		numbersmallmonster++;
		bg_color = LEVEL_2_BG;
		isStarted = true;
		dot.x = GAME_WIDTH/2;
		dot.y = GAME_HEIGHT;
		bonuses.clear();
		smallMonsters.clear();
	}
	@SuppressWarnings("deprecation")
	public void stop() {
		threads.stop();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Remember the starting time
		long tm = System.currentTimeMillis();
		while (true) {
			// Display the next frame of animation.
			repaint();
			// Delay depending on how far we are behind.
			try {
				tm += delay;
				Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
			} catch (InterruptedException e) {break;}
			// Advance the frame
			frame++;
		}
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void paint(Graphics g) {
		
		width  = this.getSize().width;
		height = this.getSize().height;
		
		if(first){
			createObjects();
			first = false;
		}
		g.setColor(bg_color);
		g.fillRect(0, 0, width, height);
		if (wall.percentageOccupied() >= WIN_PERCENTAGE && current_level == 1) {
			g.drawString("YOU WON!", GAME_WIDTH/2, GAME_HEIGHT/2);
			next_level();
			current_level++;
			g.drawImage(offscreen,0, 0, this);
			createObjects();
		}
		if ((wall.percentageOccupied() >= WIN_PERCENTAGE && current_level == 2)) {
			g.drawString("YOU WON!", GAME_WIDTH/2, GAME_HEIGHT/2);
			next_level();
			current_level++;
			g.drawImage(offscreen,0, 0, this);
			createObjects();
		}
				
	

		g.setColor(Color.black);
		wall.poli.draw(g, GRID_X, GRID_Y);

		bigmonster.draw(g);
		for (int i = 0; i < numbersmallmonster; i++)
		if (!smallMonsters.get(i).isDead())
			smallMonsters.get(i).draw(g);
		else g.drawString("Dead!", smallMonsters.get(i).x, smallMonsters.get(i).y);
		
		
		for (int i = 0; i < bonuses.size(); i++)
			bonuses.get(i).draw(g);
		
		dot.draw(g);
		
		g.setColor(Color.black);
		int life_x = 10, life_y = 10;
		g.drawString("Lives", life_x, life_y);
		life_x += 20;
		life_y -= 10;
		
		if (dot.getLives() == 0) {
			g.drawString("GAME OVER!", GAME_WIDTH/2, GAME_HEIGHT/2);
			stop();
		}
		for (int i = 0; i < Dot.lives; i++) {
			life_x += 15;
			g.fillOval(life_x, life_y, 11, 11);
		}
		if(isStarted) {
			g.drawString("Level " + current_level, GAME_WIDTH/2, GAME_HEIGHT/2);
			isStarted = false;
		}
		if (wall.percentageOccupied() >= WIN_PERCENTAGE && current_level == 3) {
			g.drawString("YOU WON!", GAME_WIDTH/2, GAME_HEIGHT/2);
			g.drawImage(offscreen,0, 0, this);
			threads.stop();
		}
		g.drawImage(offscreen,0, 0, this);
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
