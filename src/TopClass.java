import java.awt.*; 
import java.awt.event.*; 
import javax.swing.JFrame; 
import javax.swing.*; 

public class TopClass extends JFrame{ 
	private static final long serialVersionUID = 1L;
	
	public static int WINDOW_SIZE = 640;
	private CardLayout card = new CardLayout();
	private final JPanel cardPane = new JPanel();
	private JButton l1ToHome, homeToLevel1;
	private JButton l2ToHome, homeToLevel2;
	
	public static boolean isGameOver = false;
	public static int lives = 3;
	
	public Level1 level1;
	public Level2 level2;

	/*
	 * GOALS:
	 * Make blocks not changeable by little mario
	 * let big mario destroy the blocks
	 * Add in items that come from question blocks
	 * add different enemies (new class or classes)
	 * allow little mario and big mario
	 * add coins and score?
	 */
	
	
	TopClass() {
		
		new Images("mario");
		
		HomeScreen homeScreen = new HomeScreen();
		level1 = new Level1();
		level2 = new Level2();
		
		cardPane.setLayout(card);
		
		l1ToHome = new JButton("Home");
		l2ToHome = new JButton("Home");
		homeToLevel1 = new JButton("Level 1");
		homeToLevel2 = new JButton("Level 2");
		homeScreen.add(homeToLevel1);
		homeScreen.add(homeToLevel2);
		level1.add(l1ToHome);
		level2.add(l2ToHome);
		
		l1ToHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				card.show(cardPane, "h");
				level1.timer.stop();
				musicStuff.homeScreenTheme.setFramePosition(0);
				musicStuff.homeScreenTheme.start();
				musicStuff.theme.stop();
			}
		});
		homeToLevel1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				card.show(cardPane, "l1");
				level1.timer.start();
				musicStuff.theme.setFramePosition(0);
				musicStuff.theme.start();
				musicStuff.homeScreenTheme.stop();
			}
		});
		l2ToHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				card.show(cardPane, "h");
				level2.timer.stop();
				musicStuff.homeScreenTheme.start();
				musicStuff.underworld.stop();
			}
		});
		homeToLevel2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				card.show(cardPane, "l2");
				level2.timer.start();
				musicStuff.underworld.start();
				musicStuff.homeScreenTheme.stop();
			}
		});
		
		cardPane.addKeyListener(level1);
		cardPane.addKeyListener(level2);
        cardPane.setFocusable(true);
		
        cardPane.add("h", homeScreen);  
        cardPane.add("l1", level1);
        cardPane.add("l2", level2);
		
        musicStuff.homeScreenTheme.start();
        
		add(cardPane);
		cardPane.setPreferredSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE));
		setMinimumSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
	} 
	

	public static void main(String[] args) 
	{  
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TopClass();
			}
		});
		
	}
	

} 

