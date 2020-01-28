import java.awt.image.BufferedImage;

public class PlatformBlock {

	private int x, y;
	BufferedImage image;
	private boolean isFilled, isHittable; //isBottomHit;
	private int frame_HitBlock = 0;
	//int coins = 0;
	
	
	public PlatformBlock(int x, int y, BufferedImage image, Boolean isHittable)
	{
		this.x = x * LevelConstants.BLOCKSIZE;
		this.y = y * LevelConstants.BLOCKSIZE;
		this.image = image;
		isFilled = true;
		this.isHittable = isHittable;
	}

	public PlatformBlock() {
		x = -42;
		y = -42;
		image = null;
		isFilled = false;
		isHittable = false;
	}
	
	public void bigHitBlock() // 1 -> loop#     .2 seconds = 12 frames
	{
		frame_HitBlock++;
		isHittable = false;
		
		switch (frame_HitBlock)
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
			this.image = Images.emptyBlock;
			musicStuff.bump.setFramePosition(0);
			musicStuff.bump.start();
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
			frame_HitBlock = 0;
		}
	}
	
	public void littleHitBlock() // 1 -> loop#     .2 seconds = 12 frames
	{
		frame_HitBlock++;
		
		switch (frame_HitBlock)
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
			musicStuff.bump.setFramePosition(0);
			musicStuff.bump.start();
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
			frame_HitBlock = 0;
		}
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean getFilled() { return isFilled; }
	public BufferedImage getImage() { return image; }
	public void setImage(BufferedImage i) { image = i; }
	public Boolean getisHittable() { return isHittable; }
	public void setIsHittable(Boolean b) { isHittable = b; }
	public void setX(int n) { x += n; }
	public void setY(int n) { y += n; }
	
	
	public String toString()
	{
		return "x:" + x + " y:" + y;
	}

	public void setFrame_hitBlock(int i) {
		frame_HitBlock = i;
	}

	public int getFrame_HitBlock() {
		return frame_HitBlock;
	}
}
