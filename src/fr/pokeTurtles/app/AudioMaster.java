package fr.pokeTurtles.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * <b>AudioMaster</b> - singleton <br>
 * 
 * Singleton class that wrap all the I/O for the audio content
 */
public class AudioMaster {

	
	private static AudioMaster instance;
	
	private Music bgm;
	//private ArrayList<Sound> soundList;
	
	private float musicVolume;
	//private float oldMusicVolume;
	private boolean musicMuted;
	private float soundVolume;
	//private float oldSoundVolume;
	private boolean soundMuted;
	
	private HashMap<String, String> lib;
	private HashMap<String, Sound> loadedSound;
	
	/**
	 * <b>AudioMaster</b> <br>
	 * 
	 * private constructor, call loadConfig to restore the previous config
	 */
	private AudioMaster() {
		
		lib = new HashMap<>();
		loadedSound = new HashMap<>();
		
		loadConfig();
	}
	
	/**
	 * <b>getInstance</b> <br>
	 * 
	 * @return AudioMaster instance
	 * 
	 * create and return the singleton instance
	 */
	static public AudioMaster getInstance() {
    	if (instance == null) 
            instance = new AudioMaster(); 
  
        return instance; 
    }
	/**
	 * <b>loadConfig</b> <br>
	 * 
	 * restore the previous config from the config files
	 */
	private void loadConfig() {
		
		//load old config
		if(!readOldConfig()) {
			
			System.err.println("Error : Something went wrong while loading the old config, use default param instead");
			
			musicVolume = 0.4f;
			musicMuted = false;
			soundVolume = 0.8f;
			soundMuted = false;
		}
		
		
		
		//add default audio to the set
		lib.put("mainMenu", "audio/bgm/mainMenu.mp3");
		lib.put("click", "audio/sound/click.wav");
		
	}
	public void registerAudioFile(String key, String fileName)
	{
		if(lib.containsKey(key))
			throw new RuntimeException("Error : this key is already use");
		
		lib.put(key, fileName);
	}
	public void unregisterAudioFile(String key, String fileName) {
		if(!lib.containsKey(key))
			System.err.println("Registering error : unknow key name");
		else {
			lib.remove(key);
		}
	}
	/**
	 * <b>startMusic</b> <br>
	 * 
	 * 
	 * @param musicName the key reference in the lib to play
	 * 
	 * instanciate the music stream with the right file and play it with the current setting
	 */
	public void startMusic(String musicName) {
		
		if (bgm != null) {
			bgm.stop();
			bgm.dispose();
		}
				
		if(!lib.containsKey(musicName))
			throw new RuntimeException("The music "+musicName+" is unknow, please check spelling or register it before use");
		
		bgm = Gdx.audio.newMusic(new FileHandle(lib.get(musicName)));
		
		bgm.setLooping(true);
		if(musicMuted == false)
			bgm.setVolume(musicVolume);
		else
			bgm.setVolume(0);
		bgm.play();
	}
	/**
	 * <b>pauseMusic</b> <br>
	 * 
	 * pause the current music
	 */
	public void pauseMusic() {
		if (bgm != null)
			bgm.pause();
	}
	/**
	 * <b>resumeMusic</b> <br>
	 * 
	 * resume the current music
	 */
	public void resumeMusic() {
		if (bgm != null)
			bgm.play();
	}
	/**
	 * <b>setMusicPos</b> <br>
	 * 
	 * @param pos the new music pos in second
	 * 
	 * set the current music position in second
	 */
	public void setMusicPos(float pos) {
		if (bgm != null)
			bgm.setPosition(pos);
	}
	public void increaseMusicVolume() {
		musicVolume+=0.1;
		
		if(musicVolume > 1)
			musicVolume=1;
		
		if(musicMuted == false)
			bgm.setVolume(musicVolume);
	}
	public void decreaseMusicVolume() {
		musicVolume-=0.1;
		
		if(musicVolume < 0)
			musicVolume=0;
		
		if(musicMuted == false)
			bgm.setVolume(musicVolume);
	}
	/**
	 * <b>toggleMute</b> <br>
	 * 
	 * mute or unmute the current music
	 */
	public void toggleMuteMusic() {
		musicMuted = !musicMuted;
		
		if(musicMuted == false)
			bgm.setVolume(musicVolume);
		else
			bgm.setVolume(0);
	}
	/**
	 * <b>isMuted</b> <br>
	 * 
	 * @return
	 */
	public boolean isMusicMuted() {
		return musicMuted;
	}
	/**
	 * <b>loadSound</b> <br>
	 * 
	 * @param soundName the key of the sound to play
	 * 
	 * load the sound in memory, had to be call before playing it
	 */
	public void loadSound(String soundName) {
		if(!lib.containsKey(soundName))
			throw new RuntimeException("The sound "+soundName+" is unknow, please check spelling or register it before use");
		
		loadedSound.put(soundName, Gdx.audio.newSound(new FileHandle(lib.get(soundName))));
	}
	/**
	 * <b>playSound</b> <br>
	 * 
	 * @param soundName the key of the sound to play
	 * 
	 * play a loaded sound
	 */
	public void playSound(String soundName) {
		if(!loadedSound.containsKey(soundName))
			throw new RuntimeException("The sound "+soundName+" is not loaded");
		if(soundMuted == false)
			loadedSound.get(soundName).play(soundVolume);
		else
			loadedSound.get(soundName).play(0);
	}
	public void toggleMuteSound() {
		soundMuted = !soundMuted;
		
		//bgm.setVolume(musicVolume);
	}
	public void increaseSoundVolume() {
		soundVolume+=0.1;
		
		if(soundVolume > 1)
			soundVolume=1;
	}
	public void decreaseSoundVolume() {
		soundVolume-=0.1;
		
		if(soundVolume < 0)
			soundVolume=0;
	}
	public boolean isSoundMuted() {
		return soundMuted;
	}
	/**
	 * <b>disposeAllSound</b> <br>
	 * 
	 * free all the sound loaded in memory
	 */
	public void disposeAllSound() {
		for (String s : loadedSound.keySet())
			loadedSound.get(s).dispose();
		loadedSound.clear();
	}
	
	public void unloadSound(String key) {
		if(!lib.containsKey(key))
			System.err.println("Registering error : unknow key name");
		else {
			loadedSound.get(key).dispose();
			loadedSound.remove(key);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void saveConfig() throws FileNotFoundException {
		JSONObject currentConfig = new JSONObject();
		
		currentConfig.put("musicVolume", musicVolume);
		currentConfig.put("soundVolume", soundVolume);
		currentConfig.put("musicMuted", musicMuted);
		currentConfig.put("soundMuted", soundMuted);
		
		PrintWriter pw = new PrintWriter("config/audio/config.ini"); 
        pw.write(currentConfig.toJSONString()); 
          
        pw.flush(); 
        pw.close(); 
	}
	
	private boolean readOldConfig() {
		
		boolean ret = true;
		
		try {
		JSONObject reader = (JSONObject) new JSONParser().parse(new FileReader("config/audio/config.ini"));
		
		musicVolume = ((Double)(reader.get("musicVolume"))).floatValue();
		musicMuted = (boolean)reader.get("musicMuted");
		
		soundVolume = ((Double)(reader.get("soundVolume"))).floatValue();
		soundMuted = (boolean)reader.get("soundMuted");
		
		}
		catch(Exception e) {
				e.printStackTrace();
				ret = false;
		}
		
		return ret;
		
		
	}
}
