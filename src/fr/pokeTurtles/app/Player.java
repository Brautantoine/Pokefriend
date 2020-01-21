package fr.pokeTurtles.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Player {

	private int nbRock;
	private int nbTree;
	
	private ArrayDeque<Card> drawStack;
	private ArrayDeque<Card> dropStack;
	private ArrayDeque<Card> execStak;
	
	private ArrayList<Card> hand;
	
	private static int NB_PLAYER = 0;
	
	private String playerName;
	
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
			playerName = "tortipouss";
			break;
		case 3:
			playerName = "caratroc";
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
	}
	
	public ArrayList<Card> getHand() {
		for(int i = hand.size();i<5;i++)
			hand.add(drawStack.pop());
		return hand;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	public int getRock() { return nbRock; }
	public int getTree() { return nbTree; }

}
