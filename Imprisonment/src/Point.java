import java.awt.event.KeyEvent;


public class Point  {

	int x;
	int y;

	public Point() {}
	public Point(int p_x, int p_y) {
		this.x = p_x;
		this.y = p_y;
	}
	
	public void rotatePoint(double angle) {
		int old_x = x;
		int old_y = y;
		x = (int) (old_x * Math.cos(Math.toRadians(angle)) - old_y * Math.sin(Math.toRadians(angle))); 
		y = (int) (old_x * Math.sin(Math.toRadians(angle)) + old_y * Math.cos(Math.toRadians(angle)));
	}



	/*
	 * Returns the new position considering that the player would move
	 * according to the key pressed.
	 */
	public Point getNewPosition(int keyCode, int speed,Dot dot)
	{
		int diff_x = 0;
		int diff_y = 0;

		switch (keyCode) {
		case KeyEvent.VK_UP:
			diff_x = 0;
			diff_y = -speed;
			dot.orientation = dot.UP;
			break;
		case KeyEvent.VK_DOWN:
			diff_x = 0;
			diff_y = speed;
			dot.orientation = dot.DOWN;
			break;
		case KeyEvent.VK_LEFT:
			diff_x = -speed;
			diff_y = 0;
			dot.orientation = dot.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			diff_x = speed;
			diff_y = 0;
			dot.orientation = dot.RIGHT;
			break;
		default:
			/* ignore random key presses: the new position is the same as the current one */
			break;
		}
		return new Point(this.x + diff_x, this.y + diff_y);
	}
	

	public Point getBigMonsterNewPosition(int direction, int speed)
	{
		int diff_x = 0;
		int diff_y = 0;

		switch (direction) {
		case BigMonster.NORTH:
			diff_x = 0;
			diff_y = -speed;
			break;
		case BigMonster.SOUTH:
			diff_x = 0;
			diff_y = speed;
			break;
		case BigMonster.WEST:
			diff_x = -speed;
			diff_y = 0;
			break;
		case BigMonster.EAST:
			diff_x = speed;
			diff_y = 0;
			break;
		case BigMonster.NEAST:
			diff_x = speed;
			diff_y = -speed;
			break;
		case BigMonster.NWEST:
			diff_x = -speed;
			diff_y = -speed;
			break;
		case BigMonster.SEAST:
			diff_x = speed;
			diff_y = speed;
			break;
		case BigMonster.SWEST:
			diff_x = -speed;
			diff_y = speed;
			break;
			
		default:
			/* ignore random key presses: the new position is the same as the current one */
			break;
		}
		return new Point(this.x + diff_x, this.y + diff_y);
	}
	
	public boolean equals(Point p)
	{
		return p.x == x && p.y == y;
	}

	public int dist(Point p)
	{
		return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);

	}
}
