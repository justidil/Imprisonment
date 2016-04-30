import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
public class SettingsMenu extends JPanel {
	private JButton bg1, bg2, bg3, defaultS, backToMain;
	public SettingsMenu(){
		bg1 = new JButton("Background 1");
		bg2 = new JButton("Background 2");
		bg3 = new JButton("Background 3");
		defaultS = new JButton("Default");
		backToMain = new JButton("BackToMain");
		setLayout(new GridLayout(5,30,10,30));
		add(bg1);
		add(bg2);
		add(bg3);
		add(defaultS);
		add(backToMain);
		bg1.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setBackground(Color.BLACK);  
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
		bg2.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setBackground(Color.CYAN);
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
		bg3.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setBackground(Color.GRAY);
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
		
		defaultS.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setDefaultBackground();
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
	private void setDefaultBackground(){
		setBackground(Color.LIGHT_GRAY);
	}
	public JButton getBackToMain(){
		return backToMain;
	}
	
}
