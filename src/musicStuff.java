import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class musicStuff {
	
	public static Clip theme, gameOver, oneDown, oneUp, death, pause, pipeWarp, powerUp, start,
				    stomp, powerUpAppears, kick, bigJump, littleJump, fireball, flagpole, coin, bump, breakBlock,
					homeScreenTheme, bowserGalaxyGenerator, underworld, athleticBGM;
	
    public musicStuff() {
        initAudio();
    }
    
    private void initAudio()
    {
    	theme = createClip("/res/SMBTheme.wav");
    	gameOver = createClip("/res/gameOver.wav");
    	oneDown = createClip("/res/1-Down.wav"); 
    	oneUp = createClip("/res/1-UP.wav");
    	death = createClip("/res/death.wav"); 
    	pause = createClip("/res/pause.wav"); 
    	pipeWarp = createClip("/res/pipeWarp.wav");
    	powerUp = createClip("/res/powerUp.wav"); 
    	start = createClip("/res/start.wav");
    	stomp = createClip("/res/smb_stomp.wav");
    	powerUpAppears = createClip("/res/smb_powerup_appears.wav");
    	kick = createClip("/res/smb_kick.wav");
    	bigJump = createClip("/res/smb_jump-super.wav");
    	littleJump = createClip("/res/smb_jump-small.wav");
    	fireball = createClip("/res/smb_fireball.wav"); 
    	flagpole = createClip("/res/smb_flagpole.wav"); 
    	coin = createClip("/res/smb_coin.wav"); 
    	bump = createClip("/res/smb_bump.wav"); 
    	breakBlock = createClip("/res/smb_breakblock.wav");
    	homeScreenTheme = createClip("/res/homeScreenTheme.wav"); 
    	bowserGalaxyGenerator = createClip("/res/bowserGalaxyGenerator.wav"); 
    	underworld = createClip("/res/underworld.wav");
    	athleticBGM = createClip("/res/athleticBGM.wav");
    }
    
    private Clip createClip(String fileName)
    {
    	try { 
        	URL defaultSound = this.getClass().getResource(fileName); 
        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound); 
        	Clip clip = AudioSystem.getClip(); 
        	clip.open(audioInputStream); 
        	return clip;
        } 
        catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        }
    }
    
    
}