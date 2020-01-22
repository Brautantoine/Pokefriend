package fr.pokeTurtles.app;

import java.util.ArrayDeque;
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
		screenElements.add(new Backgroung("img/background/stage1G.png"));
		
		gt = new GameTable(50, 925);
		
		switch (nbPlayer) {
		case 2:
			gt.addElement(2, 0, TableElementType.TURTLES);
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

		default:
			break;
		}

		panel = new RightPanel(gt);
		
		int remainingWinners = 0;
		
		switch (nbPlayer) {
		case 2:
			remainingWinners = 1;
			break;
		case 3:
			remainingWinners = 3;
			break;
		case 4:
			remainingWinners = 2;
			break;
		default:
			break;
		}
		
		Player player;
		int currentPlayer = 0;
		while(core) {
			
			player = players.get(currentPlayer);
			
			if(player.isFinish()) {
				currentPlayer++;
				if(currentPlayer >= nbPlayer)
					currentPlayer=0;
				continue;
			}
			
			if(remainingWinners == 0) {
				core = false;
				continue;
			}
			
			System.err.println(player.getPlayerName());
			//panel.printPlayerInfo(player);
			PlayerChoice playerChoice = panel.getPlayerChoice(player.drawHand(), player,currentPlayer);
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
			case EXEC:
				remainingWinners-=executePlayerStack(currentPlayer);
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
		
		core = true;
		screenElements.add(new Backgroung("img/background/stage1G.png"));
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
		String winText = new String("Les gagnants sont :\n");
		for(Integer i : gt.getWinners()) {
			
			winText+="- "+players.get(i).getPlayerName()+"\n";
			
		}
		screenElements.add(new LabelWidget(500, 800, winText));
		while(core);
		
		wipeScreenElements();
	}
	
	private int executePlayerStack(int currentPlayer) {
		
		int ret = 0;
		
		Player player = players.get(currentPlayer);
		
		ArrayDeque<Card> execStack = player.getExec();
		ArrayDeque<Card> dropStack = player.getDrop();
		
		final int s = execStack.size();
		
		for(int i =0; i<s; i++) {
			
			Card c = execStack.pop();
			
			switch (c) {
			case blue:
				ret=gt.moveTurtles(currentPlayer);
				break;
			case purple:
				gt.turnTurtleLeft(currentPlayer);
				break;
			case red:
				gt.fireDatLaserKABOOOM(currentPlayer);
				break;
			case yellow:
				gt.turnTurtleRight(currentPlayer);
				break;
			default:
				break;
			}
			
			dropStack.push(c);
			
			if(ret == 1) {
				player.finish();
				break;
			}
		}
		
		return ret;
		
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
