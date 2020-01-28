import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HomeScreen extends JPanel{
	private static final long serialVersionUID = 1L;

	private JButton mario, luigi;
	
	public HomeScreen()
	{
		mario = new JButton("mario");
		luigi = new JButton("luigi");
		
		mario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Images("mario");
			}
		});
		luigi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Images("luigi");
			}
		});
		
		add(mario);
		add(luigi);
	}
	
	public void paintComponent(Graphics g)
	{
		g.fillRect(0, 0, 640, 640);
		g.setColor(Color.RED);
		g.drawRect(0, 0, 640, 640);
		g.setColor(Color.YELLOW);
		//g.drawString("" + GameScreen.frameCount, 100, 100);
	}
}
