package fr.pokeTurtles.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

/**
 * <b>MainMenu</b> <br>
 * 
 * class of the game main menu
 *
 */
public class MainMenu {

	/** All the elements that appear on screen, will be used for gracefully close them all **/
	private ArrayList<Drawable> screenElements;
	private ArrayList<Clickable> clicks;
	private volatile boolean core;
	
	private volatile boolean switchAsked;
	private volatile MainMenuContextSwitch mainMenuContextSwitch;
	
	private AudioMaster audioMaster; 
	
	/**
	 * <b>MainMenu</b> <br>
	 * 
	 * constructor ... I don't have many thing to say about it
	 */
	public MainMenu () {
		core = true;
		screenElements = new ArrayList<>();
		clicks = new ArrayList<Clickable>();
		
		switchAsked = false;
		mainMenuContextSwitch = MainMenuContextSwitch.NONE;
		
		audioMaster = AudioMaster.getInstance();
		try {
			audioMaster.startMusic("mainMenu");
			audioMaster.loadSound("click");
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		
		/*bgm = Gdx.audio.newMusic(new FileHandle("audio/bgm/quietSong.mp3"));
	    bgm.play();*/
		
		createMainMenu();
		
		System.out.println("start of menu");
		while(core) {
			if(switchAsked) {
				switchAsked = false;
				switch (mainMenuContextSwitch) {
				case OPTION:
					wipeScreenElements();
					createOptionMenu();
					break;
				case MAIN:
					wipeScreenElements();
					createMainMenu();
					break;
				case CREDIT:
					wipeScreenElements();
					createCreditPage();
					break;
				case PLAY:
					wipeScreenElements();
					CoreGame cg = new CoreGame(4);
					cg.newGame();
					createMainMenu();
					audioMaster.startMusic("mainMenu");
				default:
					break;
				}
				mainMenuContextSwitch = MainMenuContextSwitch.NONE;
			}
		};
		wipeScreenElements();
	}
	/**
	 * 
	 * @param newContext
	 */
	private void askForSwitch(MainMenuContextSwitch newContext) {
		switchAsked = true;
		mainMenuContextSwitch = newContext;
	}
	
	/**
	 * <b>createMainMenu()</b> <br>
	 * 
	 * create the main menu elements 
	 */
	private void createMainMenu() {
		screenElements.add(new Backgroung("img/background/back1.png"));
		clicks.add(new ClickableWidget(562,143,108,782,"img/layout/quitter.png") {
			@Override
			public void onClick(int x, int y) {
				System.out.println("overrided click");
				if(contains(x, y)) {	
					audioMaster.playSound("click");
					closeMenu();
				}	
			}
		});
		clicks.add(new ClickableWidget(562, 366, 108, 782, "img/layout/credit.png"){
			@Override
			public void onClick(int x, int y) {
				if (contains(x, y)) {
					audioMaster.playSound("click");
					System.out.println("credit");
					askForSwitch(MainMenuContextSwitch.CREDIT);
				}
			}
		});
		clicks.add(new ClickableWidget(562, 481, 108, 782, "img/layout/option.png"){
			@Override
			public void onClick(int x, int y) {
				if (contains(x, y)) {
					System.out.println("option");
					audioMaster.playSound("click");
					askForSwitch(MainMenuContextSwitch.OPTION);
				}
					
			}
		});
		clicks.add(new ClickableWidget(562, 597, 108, 782, "img/layout/jouer.png"){
			@Override
			public void onClick(int x, int y) {
				if (contains(x, y)) {
					audioMaster.playSound("click");
					System.out.println("jouer");
					askForSwitch(MainMenuContextSwitch.PLAY);
				}
					
			}
		});
		clicks.add(new ClickableWidget(627, 734, 294, 666, "img/layout/title.png"));
		
	}
	/**
	 * 
	 */
	private void createOptionMenu() {
		screenElements.add(new Backgroung("img/background/back1.png"));
		
		clicks.add(new ClickableWidget(60,45,150,400,"img/layout/retour.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					System.out.println("return to menu");
					audioMaster.playSound("click");
					try {
						audioMaster.saveConfig();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					askForSwitch(MainMenuContextSwitch.MAIN);
				}	
			}
		});
		screenElements.add(new LabelWidget(210, 850,"Volume de la musique :",Color.YELLOW)); // Guess what ? The font is monochrome so change the color doesn't do anything
		LoadingBarWidget lb = new LoadingBarWidget(530, 705, 18, 94, 10, audioMaster.getMusicVolume(), 65, "img/layout/soundBarSprite.png");
		screenElements.add(lb);
		clicks.add(new ToggleableClickableWidget(210, 700, 93, 103, "img/layout/soundSpriteSheet.png", 1, (audioMaster.isMusicMuted() == true ? 1 : 0)) {
			@Override
			public void onClick(int x, int y) {
				if(contains(x, y)) {
					audioMaster.playSound("click");
					audioMaster.toggleMuteMusic();
					toggleSprite();
				}
			}
		});
		
		clicks.add(new ClickableWidget(350, 745, 40, 120, "img/layout/minus.png") {
			@Override
			public void onClick(int x, int y) {
				if(contains(x, y)) {
					System.err.println("dec Volume");
					audioMaster.playSound("click");
					audioMaster.decreaseMusicVolume();
					lb.unfill(1);
				}
			}
		});
		
		clicks.add(new ClickableWidget(1200, 705, 100, 120, "img/layout/plusSprite.png") {
			@Override
			public void onClick(int x, int y) {
				if(contains(x, y)) {
					System.err.println("inc Volume");
					audioMaster.playSound("click");
					audioMaster.increaseMusicVolume();
					lb.fill(1);
				}
			}
		});
		screenElements.add(new LabelWidget(210, 600, "Volume des Bruitages :"));
		LoadingBarWidget lb2 = new LoadingBarWidget(530, 450, 18, 94, 10, audioMaster.getSoundVolume(), 65, "img/layout/soundBarSprite.png");
		screenElements.add(lb2);
		clicks.add(new ToggleableClickableWidget(210, 450, 93, 103, "img/layout/soundSpriteSheet.png", 1, (audioMaster.isSoundMuted() == true ? 1 : 0)) {
			@Override
			public void onClick(int x, int y) {
				if(contains(x, y)) {
					audioMaster.playSound("click");
					audioMaster.toggleMuteSound();
					toggleSprite();
					
					
				}
			}
		});
		
		clicks.add(new ClickableWidget(350, 485, 40, 120, "img/layout/minus.png") {
		@Override
		public void onClick(int x, int y) {
			if(contains(x, y)) {
				System.err.println("dec Volume");
				audioMaster.playSound("click");
				audioMaster.decreaseSoundVolume();
				lb2.unfill(1);
				}
			}
		});
		
		clicks.add(new ClickableWidget(1200, 465, 100, 120, "img/layout/plusSprite.png") {
			@Override
			public void onClick(int x, int y) {
				if(contains(x, y)) {
					System.err.println("inc Volume");
					audioMaster.playSound("click");
					audioMaster.increaseSoundVolume();
					lb2.fill(1);
				}
			}
		});
	}
	
	private void createCreditPage() {
		screenElements.add(new Backgroung("img/background/turtlefriend.jpg"));
		
		screenElements.add(new LabelWidget(500, 500, "C'est les crédits :"));
		clicks.add(new ClickableWidget(60,45,150,400,"img/layout/retour.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					System.out.println("return to menu");
					audioMaster.playSound("click");
					askForSwitch(MainMenuContextSwitch.MAIN);
				}	
			}
		});
	}
	
	/**
	 * <b>wipeScreenElements</b> <br>
	 * 
	 * close all the current Drawable
	 */
	// TODO change for an object list
	@SuppressWarnings("unused")
	private void wipeScreenElements () {
		for(Drawable e : screenElements)
			e.close();
		for(Clickable e : clicks)
			e.close();
		screenElements.clear();
		clicks.clear();
	}
	/**
	 * 
	 */
	private void closeMenu() {
		System.out.println("close");
		core = false;
	}
}
