import java.awt.image.BufferedImage;

public class BrickBlock extends Block {

	private int x, y, width, height;
	private boolean isFilled = true, isHit = false;
	private BufferedImage image;
	private int contains;
	private int frame = 0;
}
