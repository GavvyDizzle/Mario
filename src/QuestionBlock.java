import java.awt.image.BufferedImage;

public class QuestionBlock extends Block {

	private int x, y, width, height;
	private boolean isFilled = true, isHit = false;
	private BufferedImage image;
	private int contains;
	private int frame = 0;
	
	public QuestionBlock()
	{
		super();
	}

	//default: contains 1 coin
	// 1 coin = 0
	// MegaMushroom = 1
	public QuestionBlock(int x, int y, int contains) //only activates once
	{
		this.x = x * Block.SIZE;
		this.y = y * Block.SIZE;
		width = Block.SIZE;
		height = Block.SIZE;
		image = Images.questionBlock;
		//contains() code
		contains = 0;
	}
	
	public void hit() //activates on first frame when hit
	{
		isHit = true;
		if (contains == 0)
			doCoin();
		else if (contains == 1)
			doMegaMushroom();
	}
	
	public void questionBlockHit()
	{
		switch (frame) //frame counter
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
			image = Images.emptyBlock;
			super.playBumpSound();
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
			frame = 0;
		}
		frame++;
	}

	private void doCoin() //show coin collect animation
	{
		
	}
	
	private void doMegaMushroom() //top and side collision
	{

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
	public boolean isHit() {
		return isHit;
	}
	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getContains() {
		return contains;
	}
}
