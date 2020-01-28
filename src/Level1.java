import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Level1 extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;

	private BufferedImage image_marioStand, image_marioWalk1,image_marioWalk2, 
	image_marioWalk3, image_marioJump, image_marioDuck,
	image_marioSlide;
	
	Timer timer;
	Player player = new Player();
	private boolean isLeft, isRight, isDown, isLookingLeft = false;
	private boolean isColliding = false, isPaused = false;
	public static boolean isDead = false;
	
	private final int deathScreenLength = LevelConstants.FPS * 2; //number of frames
	private int frame_DeadScreen = 0;
	
	int test;
	long milliStart = System.currentTimeMillis();

	private PlatformBlock[][] arr = new PlatformBlock[16][220];
	private Goomba[] arrGoomba;
	
	public Level1()
	{
		initPlatform();
		
		arrGoomba = new Goomba[10];
		for (int r = 0; r < arrGoomba.length; r++)
			arrGoomba[r] = new Goomba();
		arrGoomba[0] = new Goomba(22,13);
		arrGoomba[1] = new Goomba(25,13);
		arrGoomba[2] = new Goomba(28,13);
		
		/*
		Block[][] b = new Block[10][10];
		b[0][0] = new Block();
		b[1][0] = new QuestionBlock();
		*/
		
		new musicStuff();
		
		setFocusable(true);
		setBackground(new Color(93,148,255));
		
		timer = new Timer(1000/LevelConstants.FPS, new ActionListener() { //do this every frame
		    public void actionPerformed(ActionEvent ae) {
		    	//if (player.getFrameCount() == 1)
		    		//musicStuff.theme.start();
		    	player.setFrameCount(1);
		    	if (TopClass.lives < 1)
		    		TopClass.isGameOver = true;
		    	checkForDeath();
		    	if (frame_DeadScreen == 1)
		    		resetLevel();
		    	player.keepOnScreen();
		    	if (player.getXVelocity() != 0 || player.getRightFrame() > 0 || player.getLeftFrame() > 0)
		    		player.setWalkingFrame(player.getWalkingFrame()+1);
		    	else
		    		player.setWalkingFrame(0);
		        repaint();
		        screenMove();
		    }
		});
	}
	
	private void initPlatform() {
		for (int r = 0; r < arr.length; r++) {
			for (int c = 0; c < arr[0].length; c++) {	
				arr[r][c] = new PlatformBlock();
			}
		}
		
		for (int r = 14; r <= 15; r++) { //ground
			for (int c = 0; c < arr[0].length; c++) {	
				if (c != 70 && c != 71 && c != 87 && c != 88 && c != 89 && c != 154 && c != 155)
				arr[r][c] = new PlatformBlock(c,r,Images.groundBlock,false);
			}
		}
		arr[10][17] = new PlatformBlock(17,10,Images.questionBlock,true);
		arr[10][21] = new PlatformBlock(21,10,Images.brickBlock,true);
		arr[10][22] = new PlatformBlock(22,10,Images.questionBlock,true);
		arr[10][23] = new PlatformBlock(23,10,Images.brickBlock,true);
		arr[10][24] = new PlatformBlock(24,10,Images.questionBlock,true);
		arr[10][25] = new PlatformBlock(25,10,Images.brickBlock,true);
		arr[6][23] = new PlatformBlock(23,6,Images.questionBlock,true);
		for (int r = 12; r <= 13; r++) { //pipe 1
			for (int c = 29; c <= 30; c++) {	
				arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		for (int r = 11; r <= 13; r++) { //pipe 2
			for (int c = 39; c <= 40; c++) {	
				arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		for (int r = 10; r <= 13; r++) { //pipe 3
			for (int c = 47; c <= 48; c++) {	
				arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		for (int r = 10; r <= 13; r++) { //pipe 4(a_in)
			for (int c = 58; c <= 59; c++) {	
				arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		arr[9][65] = new PlatformBlock(65,9,null,true);//1up
		arr[10][78] = new PlatformBlock(78,10,Images.brickBlock,true);
		arr[10][79] = new PlatformBlock(79,10,Images.questionBlock,true);//mega
		arr[10][80] = new PlatformBlock(80,10,Images.brickBlock,true);
		for (int c = 81; c <= 94; c++) {	
			if (c != 89 && c != 90 && c != 91)
				arr[6][c] = new PlatformBlock(c,6,Images.brickBlock,false);
		}
		arr[6][95] = new PlatformBlock(95,6,Images.questionBlock,true);
		arr[10][95] = new PlatformBlock(95,10,Images.brickBlock,true);//8 coins to emptyBlock
		arr[10][101] = new PlatformBlock(101,10,Images.brickBlock,true);
		arr[10][102] = new PlatformBlock(102,10,Images.brickBlock,true);//STAR (add in?)
		arr[10][107] = new PlatformBlock(107,10,Images.questionBlock,true);
		arr[6][110] = new PlatformBlock(110,6,Images.questionBlock,true);//mega
		arr[10][110] = new PlatformBlock(110,10,Images.questionBlock,true);
		arr[10][113] = new PlatformBlock(113,10,Images.questionBlock,true);
		arr[10][119] = new PlatformBlock(119,10,Images.brickBlock,true);
		arr[6][122] = new PlatformBlock(122,6,Images.brickBlock,true);
		arr[6][123] = new PlatformBlock(123,6,Images.brickBlock,true);
		arr[6][124] = new PlatformBlock(124,6,Images.brickBlock,true);
		arr[6][129] = new PlatformBlock(129,6,Images.brickBlock,true);
		arr[10][130] = new PlatformBlock(130,10,Images.brickBlock,true);
		arr[10][131] = new PlatformBlock(131,10,Images.brickBlock,true);
		arr[6][130] = new PlatformBlock(130,6,Images.questionBlock,true);
		arr[6][131] = new PlatformBlock(131,6,Images.questionBlock,true);
		arr[6][132] = new PlatformBlock(132,6,Images.brickBlock,true);
		//135-138, 141-144
		for (int c = 138; c <= 141; c++) {	
			if (c != 139 && c != 140)
				arr[10][c] = new PlatformBlock(c,10,Images.stairBlock,false);
		}
		for (int c = 137; c <= 142; c++) {	
			if (c != 139 && c != 140)
				arr[11][c] = new PlatformBlock(c,11,Images.stairBlock,false);
		}
		for (int c = 136; c <= 143; c++) {	
			if (c != 139 && c != 140)
				arr[12][c] = new PlatformBlock(c,12,Images.stairBlock,false);
		}
		for (int c = 135; c <= 144; c++) {	
			if (c != 139 && c != 140)
				arr[13][c] = new PlatformBlock(c,13,Images.stairBlock,false);
		}
		
		//149-153, 156-159
		for (int r = 10; r <= 10; r++) {
			for (int c = 152; c <= 156; c++) {	
				if (c != 154 && c != 155)
					arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		for (int r = 11; r <= 11; r++) {
			for (int c = 151; c <= 157; c++) {	
				if (c != 154 && c != 155)
					arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		for (int r = 12; r <= 12; r++) {
			for (int c = 150; c <= 158; c++) {	
				if (c != 154 && c != 155)
					arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		for (int r = 13; r <= 13; r++) {
			for (int c = 149; c <= 159; c++) {	
				if (c != 154 && c != 155)
					arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		
		for (int r = 12; r <= 13; r++) { //pipe 4(a_out)
			for (int c = 164; c <= 165; c++) {	
				arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		arr[10][169] = new PlatformBlock(169,10,Images.brickBlock,true);
		arr[10][170] = new PlatformBlock(170,10,Images.brickBlock,true);
		arr[10][171] = new PlatformBlock(171,10,Images.questionBlock,true);
		arr[10][172] = new PlatformBlock(172,10,Images.brickBlock,true);
		for (int r = 12; r <= 13; r++) {
			for (int c = 180; c <= 181; c++) {	
				arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
		}
		
		int col = 182;
		for (int r = 13; r >= 6; r--) {
			for (int c = col; c <= 190; c++) {	
					arr[r][c] = new PlatformBlock(c,r,Images.stairBlock,false);
			}
			col++;
		}
		
		//for (int r = 3; r <= 12; r++) {
		//	arr[r][199] = new PlatformBlock(199,r,Images.questionBlock,false);
		//}
		arr[13][199] = new PlatformBlock(199,13,Images.stairBlock,true);
		
		arr[8][203] = new PlatformBlock(203,8,Images.castle,false);
		
	}

	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		
		if (TopClass.isGameOver) {
			musicStuff.gameOver.start();
			setBackground(Color.BLACK);
			g.setColor(Color.RED);
			g.drawString("GAME OVER", 300, 300);
		}
		else if (handleDeath()) {//work here
			setBackground(Color.BLACK);
			g.setColor(Color.RED);
			g.drawString("Lives: " + TopClass.lives, 300, 300);
		}
		else 
		{
			setBackground(new Color(93,148,255));
			updatePlayer();
			updateEnemies();
			player.setIsCollidingWithFloor(false);
			
			/*if (isDown)
				player.setHeight(40);
			else
				player.setHeight(80);*/
			
			player.isColliding(arr, arrGoomba);
		
			//g.drawRect((int)player.getX()+player.getPaddingSize(), (int)player.getY()+player.getPaddingSize(), player.getWidth(), player.getHeight());
			for (int r = 0; r < arr.length; r++) {
				for (int c = 0; c < arr[0].length; c++) {	
					if (arr[r][c].getFilled()) {
						g.drawImage(arr[r][c].getImage(), (int)arr[r][c].getX(), (int)arr[r][c].getY(), LevelConstants.BLOCKSIZE, LevelConstants.BLOCKSIZE, null);
					}
				}
			}
			for (int r = 0; r < arrGoomba.length; r++)
				if (arrGoomba[r].isShow())
					g.drawImage(arrGoomba[r].getImage(), (int)arrGoomba[r].getX(), (int)arrGoomba[r].getY(), arrGoomba[r].getWidth(), (int)arrGoomba[r].getHeight(), null);
			
			g.drawImage(getPlayerImage(isColliding), (int)player.getX(), (int)player.getY(), player.getImageWidth(), player.getImageHeight(), null);
			
			//if (player.isInAir()) {
			//	g.setColor(Color.YELLOW);
			//	g.drawString("Level " + test, 100, 100);
			//	g.drawRect((int)player.getX()+player.getPaddingSize(), (int)player.getY()+player.getPaddingSize(), player.getWidth(), player.getHeight());
			//}
			
			//g.setColor(new Color(100,100,0,127));
			//g.fillRect(playerXGridPos * 40, playerYGridPos * 40, 40, 40);
			
			//for (int r = Math.max(0, player.getYGridPos()-1); r < Math.min(16, player.getYGridPos() + 3); r++)
				//for (int c = Math.max(0, player.getXGridPos()-1); c < player.getXGridPos()+2; c++)
					//g.fillRect(c * 40, r * 40, 40, 40);
		}
		g2d.dispose();
	}
	
	private void updatePlayer()
	{
		if (player.getIsCollidingWithFloor() || player.getYVelocity() < 0)
			player.setInAir(false);
		else
			player.setInAir(true);
		
		if (isRight) {
 			if (isRight) {
 				isLookingLeft = false;
 			}
 		}
 		if (isLeft) {
 			if (!isRight && isLeft) {
 				isLookingLeft = true;
 			}
 		}
		
		updatePlayerGridPos();
		
		double xRightAcc = 0, xLeftAcc = 0;
		
		if (player.getJumpFrame() != 0)
			player.doJump();
		if (player.getEnemyBounceFrame() != 0)
			player.enemyBounce();
		
		if(!isDown)
		{
			//right and left movement
			if (isRight) {
				player.setRightFrame(player.getRightFrame()+1);
				xRightAcc = doWalkRight(player.getRightFrame());
			}
			else {
				player.setRightFrame(0);
			}
			
			if (isLeft && !isRight) {
				player.setLeftFrame(player.getLeftFrame()+1);
				xLeftAcc = doWalkLeft(player.getLeftFrame());
			}
			else {
				player.setLeftFrame(0);
			}
		}
		
		player.updatePlayer(xRightAcc, xLeftAcc, isDown);
	}
	
	private void updateEnemies()
	{
		for (int r = 0; r < arrGoomba.length; r++) {
			arrGoomba[r].updateGoomba();
			
			if (arrGoomba[r].getSquashedFrame() > Goomba.squashedFrameLength)
				arrGoomba[r].delete();
			else if (arrGoomba[r].getSquashedFrame() > 0)
				arrGoomba[r].setSquashedFrame(arrGoomba[r].getSquashedFrame()+1);
		}
		
	}
	
	private double doWalkRight(int frame)
	{
		return Math.max(0, 3.698 * (frame/60.0) - .229);
	}
	private double doWalkLeft(int frame)
	{
		return Math.min(0, -1*(3.698 * (frame/60.0) - .229));
	}
	
	public void updatePlayerGridPos()
	{
		player.setXGridPos((int) ((player.getX() + 24) / LevelConstants.BLOCKSIZE));
		player.setYGridPos((int) ((player.getY() + 24) / LevelConstants.BLOCKSIZE));
	}
	
	public void screenMove()
	{
		int align = 0;
		if (player.getX() > 280) {
			align = (int) (player.getXVelocity());
			player.setPixMoved(player.getPixMoved() + align);
			player.setNX(280);
			
			for (int r = 0; r < arr.length; r++)
				for (int c = 0; c < arr[0].length; c++) {
					arr[r][c].setX(-1*Math.round(align));
				}
			for (int r = 0; r < arrGoomba.length; r++)
					arrGoomba[r].setX(-1*Math.round(align));
		}
	}
	
	private BufferedImage getPlayerImage(Boolean collide)
	{
		setPlayerImageType();
		
		BufferedImage i = null;
		int rem = player.getWalkingFrame() % 18; // 0-5 6-11 12-17
		if (!player.getIsCollidingWithFloor()) {
			i = image_marioJump;
		}
		else if (isDown && player.getIsCollidingWithFloor())
		{
			i = image_marioDuck;
		}
		else if (player.getWalkingFrame() <= 2 || Math.abs(player.getXVelocity()) < .25) {
			i = image_marioStand;
		}
		else if ( (isRight && player.getXVelocity() < 0) || (isLeft && player.getXVelocity() > 0) ) {
			i = image_marioSlide;
		}
		else if (rem >= 6 && rem <= 11) {
			i = image_marioWalk1;
		}
		else if (rem >= 12) {
			i = image_marioWalk2;
		}
		else {
			i = image_marioWalk3;
		}
		
		if (isLookingLeft)
		{
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		    tx.translate(-i.getWidth(null), 0);
		    AffineTransformOp op = new AffineTransformOp(tx,
		        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    i = op.filter(i, null);
		}
	    
	    return i;
	}
	
	private void setPlayerImageType()
	{
		if (player.getImageType().equals("little"))
		{
			player.setImageHeight(40);
			image_marioStand = Images.littleMarioStand;
			image_marioWalk1 = Images.littleMarioWalk1;
			image_marioWalk2 = Images.littleMarioWalk2;
			image_marioWalk3 = Images.littleMarioWalk3;
			image_marioJump = Images.littleMarioJump;
			image_marioDuck = Images.littleMarioStand;
			image_marioSlide = Images.littleMarioSlide;
		}
		else if (player.getImageType().equals("big"))
		{
			player.setImageHeight(80);
			image_marioStand = Images.bigMarioStand;
			image_marioWalk1 = Images.bigMarioWalk1;
			image_marioWalk2 = Images.bigMarioWalk2;
			image_marioWalk3 = Images.bigMarioWalk3;
			image_marioJump = Images.bigMarioJump;
			image_marioDuck = Images.bigMarioStand;
			image_marioSlide = Images.bigMarioSlide;
		}
	}
	
	private void resetLevel()
	{
		TopClass.lives--;
		initPlatform();
		player = new Player();
		arrGoomba[0] = new Goomba(22,13);
		arrGoomba[1] = new Goomba(25,13);
		arrGoomba[2] = new Goomba(28,13);
	}
	
	public void checkForDeath() {
		if (player.getY() + player.getHeight() > TopClass.WINDOW_SIZE)
		{
			isDead = true;
		}
	}
	
	private boolean handleDeath()
	{
		if (isDead && frame_DeadScreen == 0) //first frame of death
		{
			musicStuff.theme.stop();
			if (TopClass.lives > 1) {
				musicStuff.death.setFramePosition(0);
				musicStuff.death.start();
			}
			frame_DeadScreen++;
			return false;
		}
		else if (frame_DeadScreen > deathScreenLength) //last frame of death
		{
			if (TopClass.lives > 0) {
				musicStuff.theme.setFramePosition(0);
				musicStuff.theme.start();
			}
			frame_DeadScreen = 0;
			isDead = false;
			return false;
		}
		else if (frame_DeadScreen != 0)
		{
			frame_DeadScreen++;
			return true;
		}
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ( (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && player.getIsCollidingWithFloor())
			player.doJump();
 		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
 			isRight = true;
 		}
 		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
 			isLeft = true;
 		}
 		if ( (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && player.getIsCollidingWithFloor())
 			isDown = true;
 		if (e.getKeyCode() == KeyEvent.VK_P && !isDead && !TopClass.isGameOver) {
 			isPaused = !isPaused;
 			if (isPaused) {
 				timer.stop();
 				musicStuff.theme.stop();
 				musicStuff.pause.setFramePosition(0);
 				musicStuff.pause.start();
 			}
 			else {
 				musicStuff.theme.start();
 				timer.start();
 			}
 				
 		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
 		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
 			isRight = false;
 		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
 			isLeft = false;
 		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
 			isDown = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	
	//public int getFrameCount() { return frameCount; }
	public int getFrame_DeadScreen() { return frame_DeadScreen; }
	public void setFrame_DeadScreen(int n) { frame_DeadScreen = n; }
}
