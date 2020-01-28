import java.awt.image.BufferedImage;

public class StairBlock extends Block {

	private int x, y;
	private boolean isFilled = true, isHit = false;
	private BufferedImage image;
	
	public StairBlock(int x, int y) {
		this.x = x * LevelConstants.BLOCKSIZE;
		this.y = y * LevelConstants.BLOCKSIZE;
		isFilled = true;
		image = Images.stairBlock;
	}
	
	/*public void checkCollision(Player p) { //collision on all 4 sides
		
		//bottom
		if ( //!arr[Math.min(13, r+1)][c].getFilled() &&
				p.getY() + p.getPaddingSize() > y + 15 &&
				p.getY() + p.getPaddingSize() < y + LevelConstants.BLOCKSIZE &&
				p.getYVelocity() < 0)
		{
			p.setY(y + LevelConstants.BLOCKSIZE + p.getPaddingSize());
			p.setJumpSpeed(0);
			p.setJumpFrame(0);
		}
		
		//right
		if ( p.getX() + p.getPaddingSize() + LevelConstants.BLOCKSIZE > x && 
				p.getX() + p.getPaddingSize() + LevelConstants.BLOCKSIZE < x + 12 &&
				p.getXVelocity() > 0 &&
		        y + p.getPaddingSize() + LevelConstants.BLOCKSIZE - 20 > y )
		{
			p.setX(x - LevelConstants.BLOCKSIZE - p.getPaddingSize());
			p.setXVelocity(0);
		}
		
		//left
		if ( p.getX() + p.getPaddingSize() > x + LevelConstants.BLOCKSIZE - 12 &&
				p.getX() + p.getPaddingSize() < x + LevelConstants.BLOCKSIZE && 
				p.getXVelocity() < 0 &&
				y + p.getPaddingSize() + LevelConstants.BLOCKSIZE - 20 > y ) 
		{
			p.setX(x + LevelConstants.BLOCKSIZE - p.getPaddingSize());
			p.setXVelocity(0);
		}

		//top
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
	*/
}
