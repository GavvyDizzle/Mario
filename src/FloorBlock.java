import java.awt.image.BufferedImage;

public class FloorBlock extends Block {

	private int x, y, width, height;
	private boolean isFilled;
	private BufferedImage image;
	
	public FloorBlock(int x, int y) {
		this.x = x * LevelConstants.BLOCKSIZE;
		this.y = y * LevelConstants.BLOCKSIZE;
		isFilled = true;
		image = Images.groundBlock;
	}
	
	public void checkTopCollide(Player p) { //only top collision
		super.checkTopCollide(p);
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

	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
