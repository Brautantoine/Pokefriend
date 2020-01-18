package fr.pokeTurtles.app;

import java.util.ArrayList;

public class CoreGame {

	private ArrayList<Drawable> screenElements;
	private ArrayList<Clickable> clicks;
	private volatile boolean core;
	
	private AudioMaster audioMaster;
	
	private GameTable gt;
	
	private int nbPlayer;
	private ArrayList<Player> players;
	
	public CoreGame(int nbPlayer) {
		screenElements = new ArrayList<Drawable>();
		clicks = new ArrayList<Clickable>();
		core = true;
		this.nbPlayer = nbPlayer;
		players = new ArrayList<Player>();
		
		audioMaster = AudioMaster.getInstance();
		
	}
	
	public void loadGame() {
		//setOldValues();
	}
	
	public void newGame() {
		startStage();
	}
	
	private void startStage() {
		audioMaster.startMusic("stage1");
		new Backgroung("img/background/stage1G.png");
		/*for(int i=0;i<8;i++)
			for(int k=0;k<8;k++){	
				new ToggleableClickableWidget(50+(i*104), 925-(k*104), 104, 104, "img/layout/coreGame/cellSprite.png", 1) {
					@Override
					public void onClick(int x, int y) {
						if(contains(x, y)) {
							toggleSprite();
						}
					}
				};
				//new StupidSprite("img/turtles/turtwig.png",600-(i*20),450+(k*20),-2,10);
			}*/
		gt = new GameTable(50, 925);
		gt.addElement(1, 1, TableElementType.BUSH);
		gt.addElement(2, 2, TableElementType.BUSH);
		gt.addElement(5, 3, TableElementType.ROCK);
		gt.addElement(4, 7, TableElementType.TURTLES);
		gt.addElement(1, 3, TableElementType.TURTLES);
		gt.addElement(1, 6, TableElementType.TURTLES);
		gt.addElement(7, 7, TableElementType.TURTLES);
		gt.addElement(3, 3, TableElementType.PKCTR);
		gt.addElement(3, 4, TableElementType.PKCTR);
		gt.addElement(3, 5, TableElementType.PKCTR);
		gt.addElement(3, 6, TableElementType.PKCTR);
		gt.moveElement(4, 7, 1, 2);
		
		clicks.add(new ClickableWidget(60,45,150,400,"img/layout/retour.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					System.out.println("return to menu");
					audioMaster.playSound("click");
					core = false;
				}	
			}
		});
		while(core);
		wipeScreenElements();
	}
	
	private void wipeScreenElements () {
		for(Drawable e : screenElements)
			e.close();
		for(Clickable e : clicks)
			e.close();
		screenElements.clear();
		clicks.clear();
		
		gt.close();
	}

}
