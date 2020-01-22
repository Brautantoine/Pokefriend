package fr.pokeTurtles.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.DropMode;

public class Player {

	private int nbRock;
	private int nbTree;
	
	private ArrayDeque<Card> drawStack;
	private ArrayDeque<Card> dropStack;
	private ArrayDeque<Card> execStak;
	
	private ArrayList<Card> hand;
	
	private static int NB_PLAYER = 0;
	
	private String playerName;
	
	private Boolean gameOver;
	
	public Player() {
		nbRock = 3;
		nbTree = 2;
		
		create();
	}
	
	public void create() {
		NB_PLAYER++;
		switch (NB_PLAYER) {
		case 1:
			playerName = "chartor";
			break;
		case 2:
			playerName = "caratroc";
			
			break;
		case 3:
			playerName = "tortipouss";
			break;
		case 4:
			playerName = "carapuce";
			break;
		default:
			throw new RuntimeException("More than 4 player instanciate");
		}
		
		ArrayList<Card> stock = new ArrayList<>();
		
		for(int i = 0; i<18; i++)
			stock.add(Card.blue);
		for(int i = 0; i<8; i++)
			stock.add(Card.yellow);
		for(int i = 0; i<8; i++)
			stock.add(Card.purple);
		for(int i = 0; i<3; i++)
			stock.add(Card.red);
		
		Collections.shuffle(stock);
		
		drawStack = new ArrayDeque<>(stock);
		dropStack = new ArrayDeque<>();
		execStak = new ArrayDeque<>();
		
		hand = new ArrayList<>();
		
		gameOver = false;
	}
	
	public ArrayList<Card> drawHand() {
		
		int k = 5;
		
		if(drawStack.size() < k-hand.size())
			reload();
		if(drawStack.size() < k-hand.size())
			k = drawStack.size();
		
		for(int i = hand.size();i<k;i++)
			hand.add(drawStack.pop());
		return hand;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	public void addToExec(ArrayList<Card> cards) {
		for(Card c : cards)
			execStak.push(c);
			
	}
	
	private void reload() {
		for(int i=0;i<dropStack.size();i++) {
			drawStack.push(dropStack.pop());
		}
	}
	
	public void addToDrop(ArrayList<Card> cards) {
		for(Card c : cards)
			dropStack.push(c);
	}
	
	public int getRock() { return nbRock; }
	public int getTree() { return nbTree; }
	public int getDrawSize() { return drawStack.size(); }
	public int getExecSize() { return execStak.size(); }
	public int getDropStack() { return dropStack.size(); }
	public ArrayList<Card> getHand() { return hand; }
	public ArrayDeque<Card> getDrop() { return dropStack; }
	public ArrayDeque<Card> getExec() { return execStak; }
	public Boolean isFinish() { return gameOver; }
	
	public void removeTree() { nbTree--; }
	public void removeRock() { nbRock--; }
	public void finish() { gameOver = true; }

}
