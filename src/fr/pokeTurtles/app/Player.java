package fr.pokeTurtles.app;

public class Player {

	private int nbRock;
	private int nbTree;
	
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
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

}
