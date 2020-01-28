import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Level2 extends JPanel implements KeyListener{
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

	private Block[][] b = new Block[16][16];
	private Goomba[] arrGoomba = new Goomba[1];
	
	public Level2()
	{
		for (int i = 0; i < b.length; i++)
			for (int j = 0; j < b[0].length; j++)
				b[i][j] = new Block();
		b[1][14] = new FloorBlock(1,14);
		b[1][15] = new FloorBlock(1,15);
		b[2][14] = new FloorBlock(2,14);
		b[2][15] = new FloorBlock(2,15);
		b[3][14] = new FloorBlock(3,14);
		b[3][15] = new FloorBlock(3,15);
		//b[0][0] = new Block();
		//b[1][0] = new QuestionBlock();
		
		new musicStuff();
		
		setFocusable(true);
		setBackground(new Color(93,148,255));
		
		timer = new Timer(1000/LevelConstants.FPS, new ActionListener() { //do this every frame
		    public void actionPerformed(ActionEvent ae) {
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
		        //screenMove();
		    }
		});
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
			player.setIsCollidingWithFloor(false);
			
			/*if (isDown)
				player.setHeight(40);
			else
				player.setHeight(80);*/
			
			//player.isColliding(b, arrGoomba);
		
			//g.drawRect((int)player.getX()+player.getPaddingSize(), (int)player.getY()+player.getPaddingSize(), player.getWidth(), player.getHeight());
			for (int r = 0; r < b.length; r++) {
				for (int c = 0; c < b[0].length; c++) {	
					if (b[r][c].isFilled()) {
						g.drawImage(b[r][c].getImage(), (int)b[r][c].getX(), (int)b[r][c].getY(), LevelConstants.BLOCKSIZE, LevelConstants.BLOCKSIZE, null);
					}
				}
			}

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
	
	/*public void screenMove()
	{
		int align = 0;
		if (player.getX() > 280) {
			align = (int) (player.getXVelocity());
			player.setPixMoved(player.getPixMoved() + align);
			player.setNX(280);
		}
			
			for (int r = 0; r < b.length; r++)
				for (int c = 0; c < b[0].length; c++)
					b[r][c].setX(-1*Math.round(align));
	}
	*/
	
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
		//initPlatform();
		player = new Player();
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
