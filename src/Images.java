import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Images {

	public static BufferedImage  playerSprites, enemySprites,
	groundBlock, brickBlock, questionBlock, emptyBlock, stairBlock,
	bigMarioStand, bigMarioWalk1,bigMarioWalk2, 
	bigMarioWalk3, bigMarioJump, bigMarioDuck,
	bigMarioSlide, littleMarioSlide,
	littleMarioStand, littleMarioWalk1,littleMarioWalk2, 
	littleMarioWalk3, littleMarioJump, littleMarioDeath,
	goomba, goombaReflected, goombaSquashed, castle;
	
	public Images(String type)
	{
		initImages(type);
	}
	
	private void initImages(String player)
	{
		playerSprites = createImage("/res/playerSprites.png");
		enemySprites = createImage("/res/enemySprites.png");
		goomba = enemySprites.getSubimage(0,16,16,16);
		goombaReflected = enemySprites.getSubimage(16,16,16,16);
		goombaSquashed = enemySprites.getSubimage(32,16,16,16);
		castle = createImage("/res/castle.png");
		
		groundBlock = createImage("/res/ground.png"); 
		brickBlock = createImage("/res/brick.png");
		questionBlock = createImage("/res/question_block.png");
		emptyBlock = createImage("/res/empty_block.png");
		stairBlock = createImage("/res/stair_block.png");
		
		if (player.contentEquals("mario")) {
			int c = 80;
			bigMarioStand = playerSprites.getSubimage(c,1,16,32);
			littleMarioStand = playerSprites.getSubimage(c,34,16,16);
			c += 17;
			bigMarioWalk2 = playerSprites.getSubimage(c,1,16,32);
			littleMarioWalk2 = playerSprites.getSubimage(c,34,16,16);
			c += 17;
			bigMarioWalk3 = playerSprites.getSubimage(c,1,16,32);
			littleMarioWalk3 = playerSprites.getSubimage(c,34,16,16);
			c += 17;
			bigMarioWalk1 = playerSprites.getSubimage(c,1,16,32);
			littleMarioWalk1 = playerSprites.getSubimage(c,34,16,16);
			c += 17;
			bigMarioSlide = playerSprites.getSubimage(c,1,16,32);
			littleMarioSlide = playerSprites.getSubimage(c,34,16,16);
			c += 17;
			bigMarioJump = playerSprites.getSubimage(c,1,16,32);
			littleMarioJump = playerSprites.getSubimage(c,34,16,16);
			c += 17;
			bigMarioDuck = playerSprites.getSubimage(c,1,16,32);
			littleMarioDeath = playerSprites.getSubimage(c,34,16,16);
			c += 17;
		}
		else if (player.equals("luigi")) {
			int c = 80;
			bigMarioStand = playerSprites.getSubimage(c,66,16,32);
			littleMarioStand = playerSprites.getSubimage(c,99,16,16);
			c += 17;
			bigMarioWalk2 = playerSprites.getSubimage(c,66,16,32);
			littleMarioWalk2 = playerSprites.getSubimage(c,99,16,16);
			c += 17;
			bigMarioWalk3 = playerSprites.getSubimage(c,66,16,32);
			littleMarioWalk3 = playerSprites.getSubimage(c,99,16,16);
			c += 17;
			bigMarioWalk1 = playerSprites.getSubimage(c,66,16,32);
			littleMarioWalk1 = playerSprites.getSubimage(c,99,16,16);
			c += 17;
			bigMarioSlide = playerSprites.getSubimage(c,66,16,32);
			littleMarioSlide = playerSprites.getSubimage(c,99,16,16);
			c += 17;
			bigMarioJump = playerSprites.getSubimage(c,66,16,32);
			littleMarioJump = playerSprites.getSubimage(c,99,16,16);
			c += 17;
			bigMarioDuck = playerSprites.getSubimage(c,66,16,32);
			littleMarioDeath = playerSprites.getSubimage(c,99,16,16);
			c += 17;
		}
	}
	
	private BufferedImage createImage(String fileName)
	{
		BufferedImage i = null;
		try {
			i = ImageIO.read(this.getClass().getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
}
