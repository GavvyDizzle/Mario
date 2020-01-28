import java.awt.image.BufferedImage;

public class Block {

	public static int SIZE = 40;
	
	private int x, y, width, height;
	private boolean isFilled, isHittable;
	private BufferedImage image;
	private int frameHit = 0;
	
	public Block()
	{
		setX(0);
		setY(0);
		width = 0;
		height = 0;
		setFilled(false);
		setHittable(false);
		setImage(null);
	}
	
	
	public void playBumpSound()
	{
		musicStuff.bump.setFramePosition(0);
		musicStuff.bump.start();
	}

	public void blockBump() //hit without changing block properties
	{
		switch (frameHit)
		{
		case 1:
			y -= 1;
			break;
		case 2:
			y -= 2;
			break;
		case 3:
			y -= 3;
			break;
		case 4:
			y -= 6;
			break;
		case 5:
			y -= 8;
			break;
		case 6:
			y -= 11;
			playBumpSound();
			break;
		case 7:
			y += 1;
			break;
		case 8:
			y += 2;
			break;
		case 9:
			y += 3;
			break;
		case 10:
			y += 6;
			break;
		case 11:
			y += 8;
			break;
		case 12:
			y += 11;
			break;
		default:
			frameHit = 0;
		}
	}
	
	public void checkBottomCollide(Player p) { //updates in this order: 1.bottom -> 2.right -> 3.left -> 4.top
		if ( //!arr[Math.min(13, r+1)][c].getFilled() &&
				p.getY() + p.getPaddingSize() > y + 15 &&
				p.getY() + p.getPaddingSize() < y + LevelConstants.BLOCKSIZE &&
				p.getYVelocity() < 0)
		{
			p.setY(y + LevelConstants.BLOCKSIZE + p.getPaddingSize());
			p.setJumpSpeed(0);
			p.setJumpFrame(0);
		}
	}
	public void checkRightCollide(Player p) {
		if ( p.getX() + p.getPaddingSize() + LevelConstants.BLOCKSIZE > x && 
				p.getX() + p.getPaddingSize() + LevelConstants.BLOCKSIZE < x + 12 &&
				p.getXVelocity() > 0 &&
		        y + p.getPaddingSize() + LevelConstants.BLOCKSIZE - 20 > y )
		{
			p.setX(x - LevelConstants.BLOCKSIZE - p.getPaddingSize());
			p.setXVelocity(0);
		}
	}
	public void checkLeftCollide(Player p) {
		if ( p.getX() + p.getPaddingSize() > x + LevelConstants.BLOCKSIZE - 12 &&
				p.getX() + p.getPaddingSize() < x + LevelConstants.BLOCKSIZE && 
				p.getXVelocity() < 0 &&
				y + p.getPaddingSize() + LevelConstants.BLOCKSIZE - 20 > y ) 
		{
			p.setX(x + LevelConstants.BLOCKSIZE - p.getPaddingSize());
			p.setXVelocity(0);
		}
	}
	public void checkTopCollide(Player p) {
		if ( //!arr[r-1][c].getFilled() && 
				p.getY() + p.getPaddingSize() + LevelConstants.BLOCKSIZE > y &&
				p.getY() + p.getPaddingSize() + LevelConstants.BLOCKSIZE < y + 30 &&
				p.getY() < y && p.getJumpFrame() == 0)
		{
			p.setY(y - LevelConstants.BLOCKSIZE - p.getPaddingSize());
			p.setIsCollidingWithFloor(true);
			p.setFrameFalling(0);
		}
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean isFilled() {
		return isFilled;
	}
	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
	public boolean isHittable() {
		return isHittable;
	}
	public void setHittable(boolean isHittable) {
		this.isHittable = isHittable;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
