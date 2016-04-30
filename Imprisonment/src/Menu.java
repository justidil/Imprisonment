import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
public class Menu extends JFrame{
	private MainMenu mainmenu = new MainMenu();
	private SettingsMenu settingsmenu = new SettingsMenu();
	public Menu(){
		setTitle("Imprisonment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(2 * 40 + 1000, 2* 40 + 600));
		
		pack();
		setVisible(true);
		mainMenuListener();
		settingsMenuListener();
	}
	public void setScreen(){
		getContentPane().add(mainmenu);
		pack();
	}
	private void showSettings(){
		getContentPane().add(settingsmenu);
		getContentPane().remove(mainmenu);
		pack();
	}
	private void showMain(){
		getContentPane().remove(settingsmenu);
		getContentPane().add(mainmenu);
		pack();
	}
	
	private void mainMenuListener(){
			
		mainmenu.getSettingsButton().addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showSettings();
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
	
	private void settingsMenuListener(){
		settingsmenu.getBackToMain().addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showMain();
				pack();
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
	public void startGame(){
		getContentPane().remove(mainmenu);
		pack();
	}
	public JButton playGame(){
		return mainmenu.getPlayGameButton();
	}
}

