import java.util.ArrayList;


public class GameObject {
	int x, y;
	private static Wall wall;
	private static Dot dot;
	private static BigMonster bigmonster;
	private static ArrayList<SmallMonster> smallMonsters;
	private  static int GRID_X;
	private  static int GRID_Y;
	private  static int GAME_WIDTH;
	private  static int GAME_HEIGHT;
	private static int numbersmallmonster;
	private static ArrayList<Bonus> bonuses;
	public void setWall(Wall wall){
		this.wall = wall;
	}
	public Wall getWall(){
		return wall;
	}
	public BigMonster getBigmonster() {
		return bigmonster;
	}
	public ArrayList<SmallMonster> getSmallMonsters() {
		return smallMonsters;
	}
	public int getGRID_X() {
		return GRID_X;
	}
	public int getGRID_Y() {
		return GRID_Y;
	}
	public int getGAME_WIDTH() {
		return GAME_WIDTH;
	}
	public int getGAME_HEIGHT() {
		return GAME_HEIGHT;
	}
	public int getNumbersmallmonster() {
		return numbersmallmonster;
	}
	public void setBigmonster(BigMonster bigmonster) {
		this.bigmonster = bigmonster;
	}
	public void setSmallMonsters(ArrayList<SmallMonster> smallMonsters) {
		this.smallMonsters = smallMonsters;
	}
	public void setGRID_X(int gRID_X) {
		GRID_X = gRID_X;
	}
	public void setGRID_Y(int gRID_Y) {
		GRID_Y = gRID_Y;
	}
	public void setGAME_WIDTH(int gAME_WIDTH) {
		GAME_WIDTH = gAME_WIDTH;
	}
	public void setGAME_HEIGHT(int gAME_HEIGHT) {
		GAME_HEIGHT = gAME_HEIGHT;
	}
	public void setNumbersmallmonster(int numbersmallmonster) {
		this.numbersmallmonster = numbersmallmonster;
	}
	public ArrayList<Bonus> getBonuses() {
		return bonuses;
	}
	public void setBonuses(ArrayList<Bonus> bonuses) {
		this.bonuses = bonuses;
	}
	public Dot getDot() {
		return dot;
	}
	public void setDot(Dot dot) {
		this.dot = dot;
	}
}
