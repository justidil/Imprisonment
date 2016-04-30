import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
public class GameManager {
	private Menu menu;
	public GameManager(){
		menu = new Menu();
		menu.setScreen();
		menu.playGame().addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				menu.startGame();
				initializeGame();
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
	public void initializeGame(){
		MapManager m = new MapManager();
		menu.getContentPane().add(m);
		menu.pack();
	}
}
