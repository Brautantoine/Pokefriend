package fr.pokeTurtles.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * <b>Main</b> <br>
 * 
 * Init libgdx and launch the MainMenu
 */
public class Main {

	/**
	 * <b>main</b> <br>
	 * 
	 * @param args
	 * 
	 * what should I add seriously ...
	 */
	public static void main(String[] args) {
		
		// Let's create an application config
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	      cfg.title = "Pokemon : Turtle friends [unofficial]";
	      cfg.useGL30 = false;
	      cfg.width = 1920;
	      cfg.height = 1080;
			
	    // And now we start the application
		LwjglApplication app = new LwjglApplication(Engine.getInstance(), cfg);
	      
	      System.out.println("start of main");
	      
	      // we wait till the engine is ready to go
	      while(!ready)
	    	  ready = Engine.getInstance().isReady();
	      
	      System.out.println("ready ...");
	      
	      // old debug
	      /*//new Backgroung("img/background/turtlefriend.jpg");
	      new StupidSprite("img/turtles/chartor.png",200,200);
	      new StupidSprite("img/turtles/turtwig.png",600,450,-2,10);
	      new StupidSprite("img/turtles/caratroc.png", 800,800);
	      new StupidSprite("img/turtles/squirttle.jpeg", 500, 250,10,20);
	      
	      test();
	      	
	      //Engine.getInstance().addDrawable(null);*/
	      
	      new MainMenu();
	      
	      System.out.println("end of main menu");
	      app.exit();
	      System.out.println("gracefully Exit");
	}
	
	/** We can't use the Engine before it is ready, and the while won't retest the conditions unless it's volatile **/
	static volatile private boolean ready = false;
	
	/**
	 * <b>test</b> <br>
	 * 
	 * Debug purpose ... don't mind it
	 */
	private static void test () {
		StupidSprite sprite = new StupidSprite("img/turtles/caratroc.png", 0,0);
		sprite.close();
	}

}
