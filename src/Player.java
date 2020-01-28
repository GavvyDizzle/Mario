import java.awt.image.BufferedImage;

public class Player{

	private double x, y;
	private final int hitboxPadding = 0;
	private int imageWidth = 40, imageHeight = 40;
	private int width = imageWidth - hitboxPadding * 2, height = imageHeight - hitboxPadding * 2;
	private double xVelocity = 0, yVelocity = 0;
	private int xGridPos, yGridPos;
	private int pixMoved = 0;
	private String imageType = "little";
	BufferedImage image;
	private boolean isCollidingWithFloor = true;
	private int frame_Falling;
	private int frameCount = 0;
	private boolean isInAir = false;
	
	
	//change hitbox to bottom of feet and add in other images
	
	private int playerJump = 0;
	private double jumpSpeed = 0;
	private int jumpFrame = 0;
	private int enemyBounceFrame = 0;
	private int walkingFrame = 0;
	private int rightFrame = 0;
	private int leftFrame = 0;
	
	public Player()
	{
		x = 50;
		y = 640 - hitboxPadding - LevelConstants.BLOCKSIZE*2 - imageHeight;
		//get image	
	}
	
	public void isColliding(PlatformBlock[][] arr, Goomba[] arrGoomba)
	{
		//checks for collision with blocks that will react when player touches the bottom of it
		for (int r = Math.max(0, yGridPos-6); r < Math.min(arr.length, yGridPos + 6); r++) {
			for (int c = Math.max(0, xGridPos-2); c < Math.min(arr[0].length, ((int) pixMoved / 40) + 16); c++) {
				if (arr[r][c].getFilled()) {
					if (x + width + hitboxPadding > arr[r][c].getX() && x + hitboxPadding < arr[r][c].getX() + LevelConstants.BLOCKSIZE &&
					   (y + height + hitboxPadding > arr[r][c].getY() && y + hitboxPadding < arr[r][c].getY() + LevelConstants.BLOCKSIZE)) {
						if (arr[r][c].getisHittable() && y + hitboxPadding > arr[r][c].getY() + 15 &&
								y + hitboxPadding < arr[r][c].getY() + LevelConstants.BLOCKSIZE && yVelocity < 0) {
							arr[r][c].bigHitBlock();
						}
					}
				}
				if (arr[r][c].getFrame_HitBlock() != 0)
					arr[r][c].bigHitBlock();
			}
		}
		
		//goes through array and moves player if colliding: 1)bottom  2)right  3)left  4)top
		for (int count = 0; count <= 3; count++)
			for (int r = Math.max(0, yGridPos-2); r < Math.min(arr.length, yGridPos + 3); r++)
				for (int c = Math.max(0, xGridPos-2); c < Math.min(arr[0].length, ((int) pixMoved / 40) + 16); c++)
					if (arr[r][c].getFilled())
						if (x + width + hitboxPadding > arr[r][c].getX() && x + hitboxPadding < arr[r][c].getX() + LevelConstants.BLOCKSIZE &&
						   (y + height + hitboxPadding > arr[r][c].getY() && y + hitboxPadding < arr[r][c].getY() + LevelConstants.BLOCKSIZE))
							snapToPos(r,c,count,arr);
		
		//checks for collision with Goomba
		for (int r = 0; r < arrGoomba.length; r++)
			if (arrGoomba[r].getIsAlive())
				if (x + width + hitboxPadding > arrGoomba[r].getX() && x + hitboxPadding < arrGoomba[r].getX() + arrGoomba[r].getWidth() &&
				   (y + height + hitboxPadding > arrGoomba[r].getY() && y + hitboxPadding < arrGoomba[r].getY() + arrGoomba[r].getHeight()))
					arrGoomba[r].collide(this);
	}
	
	private void snapToPos(int r, int c, int count, PlatformBlock[][] arr)
	{
		switch(count) {
		case 0: //bottom
			if ( !arr[Math.min(13, r+1)][c].getFilled() && 
					y + hitboxPadding > arr[r][c].getY() + 15 &&
					y + hitboxPadding < arr[r][c].getY() + LevelConstants.BLOCKSIZE &&
					yVelocity < 0)
			{
				y = arr[r][c].getY() + LevelConstants.BLOCKSIZE + hitboxPadding;
				jumpSpeed = 0;
				jumpFrame = 0;
			}
			break;
		
		case 1: //right
			if ( x + hitboxPadding + width > arr[r][c].getX() && 
					x + hitboxPadding + width < arr[r][c].getX() + 12 &&
					xVelocity > 0 &&
			        y + hitboxPadding + height - 20 > arr[r][c].getY() )
			{
				x = arr[r][c].getX() - width - hitboxPadding;
				xVelocity = 0;
			}
			break;
			
		case 2: //left
			if ( x + hitboxPadding > arr[r][c].getX() + LevelConstants.BLOCKSIZE - 12 &&
					x + hitboxPadding < arr[r][c].getX() + LevelConstants.BLOCKSIZE && 
					xVelocity < 0 &&
					y + hitboxPadding + height - 20 > arr[r][c].getY() ) 
			{
				x = arr[r][c].getX() + LevelConstants.BLOCKSIZE - hitboxPadding;
				xVelocity = 0;
			}
			break;
			
		default: //up
			if ( !arr[r-1][c].getFilled() && 
					y + hitboxPadding + height > arr[r][c].getY() &&
					y + hitboxPadding + height < arr[r][c].getY() + 30 &&
					y < arr[r][c].getY() && jumpFrame == 0)
			{
				y = arr[r][c].getY() - height - hitboxPadding;
				isCollidingWithFloor = true;
				frame_Falling = 0;
			}
		}
	}
	
	public void keepOnScreen()
	{
		if (x < 0)
			x = 0;
	}
	
	public void updatePlayer(double rAcc, double lAcc, boolean isDown)
	{
		if (jumpSpeed != 0)
			yVelocity = jumpSpeed * -1;
		else if (!isCollidingWithFloor && jumpFrame == 0) {
			if (frame_Falling == 0)
				frame_Falling = frameCount;
			doFalling();
		}
		else
			yVelocity = LevelConstants.GRAVITY;
		
		//x
		if (isDown && isCollidingWithFloor) {}//down is not pressed and colliding with floor
		else
			xVelocity += rAcc + lAcc;
		
		xVelocity *= LevelConstants.FRICTION;
		
		//keeps in constraints
		if (Math.abs(xVelocity) < .25)
			xVelocity = 0;
		
		if (xVelocity < -6)
			xVelocity = -6;
		
		if (xVelocity > 6)
			xVelocity = 6;
		
		x += xVelocity;
		y += yVelocity;
	}
	
	public void doJump() //handles jump action
	{
		if (jumpFrame <= 34)
		{
			if (jumpFrame == 2) {
				musicStuff.littleJump.setFramePosition(0);
				musicStuff.littleJump.start();
			}
			jumpSpeed = ((-33.91*((jumpFrame/60.0)*(jumpFrame/60.0)))
					  + (17.361*(jumpFrame/60.0)) - .018) * 4;
			jumpFrame++;
		}
		else {
			jumpFrame = 0;
			jumpSpeed = 0;
		}
	}
	
	public void enemyBounce()
	{
		if (enemyBounceFrame <= 14) {
			
			switch(enemyBounceFrame) {
			case 1:
				musicStuff.stomp.setFramePosition(0);
				musicStuff.stomp.start();
				jumpSpeed = 1;
				break;
			case 2:
				jumpSpeed = 2;
				break;
			case 3:
				jumpSpeed = 2;
				break;
			case 4:
				jumpSpeed = 3;
				break;
			case 5:
				jumpSpeed = 3;
				break;
			case 6:
				jumpSpeed = 4;
				break;
			case 7:
				jumpSpeed = 3;
				break;
			case 8:
				jumpSpeed = 2;
				break;
			case 9:
				jumpSpeed = 1;
				break;
			case 10:
				jumpSpeed = -1;
				break;
			case 11:
				jumpSpeed = -2;
				break;
			case 12:
				jumpSpeed = -3;
				break;
			case 13:
				jumpSpeed = -3;
				break;
			default:
				jumpSpeed = -4;
				musicStuff.stomp.stop();
			}
			enemyBounceFrame++;
			System.out.println(jumpSpeed);
		}
		else {
			enemyBounceFrame = 0;
			jumpSpeed = 0;
		}
	}
	
	private void doFalling()
	{
		double time = (frameCount - frame_Falling)*1.0 / LevelConstants.FPS;
		yVelocity = -0.3*( (-27.94 * time * time) - (-1.057 * time) + 2.477 ) + LevelConstants.GRAVITY - 2;
	}
	
	
	public double getX() { return x; }
	public double getY() { return y; }
	public int getPaddingSize() { return hitboxPadding; }
	public int getImageWidth() { return imageWidth; }
	public int getImageHeight() { return imageHeight; }
	public void setX(double n) { x += n; }
	public void setNX(double n) { x = n; }
	public void setY(double n) { y += n; }
	
	public int getFrameCount() { return frameCount; }
	public void setFrameCount(int n) { frameCount += n; }
	
	
	public void setImageHeight(int n) { height = n; }
	public void setHeight(int n) { height = n; }
	
	public double getXVelocity() { return xVelocity; }
	public double getYVelocity() { return yVelocity; }
	public void setXVelocity(double n) { xVelocity = n; }
	public void setYVelocity(double n) { yVelocity = n; }
	
	public int getXGridPos() { return xGridPos; }
	public int getYGridPos() { return yGridPos; }
	public void setXGridPos(int n) { xGridPos = n; }
	public void setYGridPos(int n) { yGridPos = n; }
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public int getPixMoved() { return pixMoved; }
	public void setPixMoved(int n) { pixMoved = n; }
	
	public boolean getIsCollidingWithFloor() { return isCollidingWithFloor; }
	public void setIsCollidingWithFloor(boolean b) { isCollidingWithFloor = b; }
	

	public int getPlayerJump() {
		return playerJump;
	}

	public void setPlayerJump(int playerJump) {
		this.playerJump = playerJump;
	}

	public double getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public int getEnemyBounceFrame() {
		return enemyBounceFrame;
	}
	
	public void setEnemyBounceFrame(int n) {
		enemyBounceFrame = n;
	}
	
	public int getJumpFrame() {
		return jumpFrame;
	}

	public void setJumpFrame(int jumpFrame) {
		this.jumpFrame = jumpFrame;
	}

	public int getWalkingFrame() {
		return walkingFrame;
	}

	public void setWalkingFrame(int walkingFrame) {
		this.walkingFrame = walkingFrame;
	}

	public int getRightFrame() {
		return rightFrame;
	}

	public void setRightFrame(int rightFrame) {
		this.rightFrame = rightFrame;
	}

	public int getLeftFrame() {
		return leftFrame;
	}

	public void setLeftFrame(int leftFrame) {
		this.leftFrame = leftFrame;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public void setFrameFalling(int i) {
		frame_Falling = i;
	}

	public boolean isInAir() {
		return isInAir;
	}

	public void setInAir(boolean isInAir) {
		this.isInAir = isInAir;
	}
	
}
