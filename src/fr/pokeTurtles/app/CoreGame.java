package fr.pokeTurtles.app;

import java.awt.Panel;
import java.util.ArrayList;

public class CoreGame {

	private ArrayList<Drawable> screenElements;
	private ArrayList<Clickable> clicks;
	private volatile boolean core;
	
	private AudioMaster audioMaster;
	
	private GameTable gt;
	
	private int nbPlayer;
	private ArrayList<Player> players;
	
	private RightPanel panel;
	
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
		
		for(int i=0;i<nbPlayer;i++)
			players.add(new Player());
		
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
		
		switch (nbPlayer) {
		case 2:
			gt.addElement(1, 0, TableElementType.TURTLES);
			gt.addElement(5, 0, TableElementType.TURTLES);
			gt.addElement(3, 7, TableElementType.PKCTR);
			for(int i=0;i<8;i++)
				gt.addElement(7, i, TableElementType.ROCK);
			break;
		case 3:
			gt.addElement(0, 0, TableElementType.TURTLES);
			gt.addElement(3, 0, TableElementType.TURTLES);
			gt.addElement(6, 0, TableElementType.TURTLES);
			gt.addElement(0, 7, TableElementType.PKCTR);
			gt.addElement(3, 7, TableElementType.PKCTR);
			gt.addElement(6, 7, TableElementType.PKCTR);
			for(int i=0;i<8;i++)
				gt.addElement(7, i, TableElementType.ROCK);
			break;
		case 4:
			gt.addElement(0, 0, TableElementType.TURTLES);
			gt.addElement(2, 0, TableElementType.TURTLES);
			gt.addElement(5, 0, TableElementType.TURTLES);
			gt.addElement(7, 0, TableElementType.TURTLES);
			gt.addElement(1, 7, TableElementType.PKCTR);
			gt.addElement(6, 7, TableElementType.PKCTR);
			//gt.addElement(7, 7, TableElementType.BUSH);

		default:
			break;
		}
		/*gt.addElement(1, 1, TableElementType.BUSH);
		gt.addElement(2, 2, TableElementType.BUSH);
		gt.addElement(5, 3, TableElementType.ROCK);
		gt.addElement(4, 7, TableElementType.TURTLES);
		gt.addElement(1, 3, TableElementType.TURTLES);
		gt.addElement(1, 6, TableElementType.TURTLES);
		gt.addElement(7, 7, TableElementType.TURTLES);
		gt.addElement(3, 3, TableElementType.PKCTR);
		gt.addElement(3, 4, TableElementType.PKCTR);
		gt.addElement(3, 5, TableElementType.PKCTR);
		gt.addElement(3, 6, TableElementType.PKCTR);*/
		//gt.addElement(4, 6, TableElementType.PKCTR);
		//gt.moveElement(4, 7, 1, 2);
		
		//screenElements.add(new LabelWidget(1050, 975, "Joueur : Chartor\n\nNombre de bloc restant :\n\nRocher : 3\nArbre : 2"));
		panel = new RightPanel(gt);
		/*clicks.add(new ClickableWidget(60,45,150,400,"img/layout/retour.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					System.out.println("return to menu");
					audioMaster.playSound("click");
					core = false;
				}	
			}
		});*/
		
		Player player;
		int currentPlayer = 0;
		while(core) {
			player = players.get(currentPlayer);
			System.err.println(player.getPlayerName());
			//panel.printPlayerInfo(player);
			PlayerChoice playerChoice = panel.getPlayerChoice(player.drawHand(), player);
			System.err.println("playerChoice : "+playerChoice);
			
			switch (playerChoice) {
			case BLOCK:
				playerChoice = panel.getBlockChoice(player);
				System.err.println(playerChoice);
				switch (playerChoice) {
				case TREE:
					gt.addTree();
					player.removeTree();
					break;
				case ROCK:
					gt.addRock();
					player.removeRock();
					break;
				default:
					break;
				}
				break;
			case BUILDPROG:
				player.addToExec(panel.getSelected(player.getHand()));
				break;
			default:
				break;
			}
			
			currentPlayer++;
			if(currentPlayer >= nbPlayer)
				currentPlayer=0;
			
			playerChoice = panel.getDropChoice(player.getHand());
			
			if(playerChoice == PlayerChoice.DROP)
				player.addToDrop(panel.getSelected(player.getHand()));
		}
		wipeScreenElements();
	}
	
	private void wipeScreenElements () {
		for(Drawable e : screenElements)
			e.close();
		for(Clickable e : clicks)
			e.close();
		screenElements.clear();
		clicks.clear();
		
		panel.close();
		gt.close();
	}

}
