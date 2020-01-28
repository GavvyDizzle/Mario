import java.awt.image.BufferedImage;

public class Goomba {

	private double x, y;
	private double xVelocity = -1; 
	private int width, height;
	BufferedImage image;
	private boolean isAlive, isShow;
	private int squashedFrame = 0;
	public static int squashedFrameLength = (int) (LevelConstants.FPS * .75);
	
	//add in gravity and allow collisions with PlatformBlocks
	
	public Goomba(int x, int y)
	{
		width = 32;
		height = 32;
		this.x = x * LevelConstants.BLOCKSIZE;
		this.y = y * LevelConstants.BLOCKSIZE + Math.abs(LevelConstants.BLOCKSIZE - height);
		image = Images.goomba;
		isAlive = true;
		setShow(true);
		//arrGoomba.add(this);
	}
	
	public Goomba() {}
	
	public void updateGoomba()
	{
		if (x + width > 0 && x < TopClass.WINDOW_SIZE && isAlive) {
			x += xVelocity;
			
			if (x % 15 <= 7)
				image = Images.goomba;
			else
				image = Images.goombaReflected;
		}
	}
	
	public void delete()
	{
		image = null;
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}
	
	public void collide(Player p)
	{
		if (isAlive && p.isInAir()) {			
			squash();
			p.setEnemyBounceFrame(1);
		}
		else 
			Level1.isDead = true;
	}
	
	private void squash()
	{
		squashedFrame++;
		isAlive = false;
		image = Images.goombaSquashed;
	}
	
	public boolean getIsAlive()
	{
		return isAlive;
	}
	public void setX(double n)
	{
		x += n;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public BufferedImage getImage() { return image; }

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public int getSquashedFrame() {
		return squashedFrame;
	}

	public void setSquashedFrame(int squashedFrame) {
		this.squashedFrame = squashedFrame;
	}
	
}
