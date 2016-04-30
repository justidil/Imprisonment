import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JPanel{
	private JButton playGame, settings, quit;
	public MainMenu(){
		playGame = new JButton("Play Game");
		settings = new JButton("Settings");
		quit = new JButton("Quit");
		ImageIcon imp = new ImageIcon("Imprisonment.png");
		JLabel label = new JLabel();
		label.setIcon(imp);
		setLayout(new GridLayout(5,30,10,30));
		add(label);
		add(playGame);
		add(settings);
		add(quit);
		
		quit.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				exitGame();
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
	private void exitGame(){
		System.exit(0);
	}
	
	public JButton getSettingsButton(){
		return settings;
	}
	public JButton getPlayGameButton(){
		return playGame;
	}
}
