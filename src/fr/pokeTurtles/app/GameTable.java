package fr.pokeTurtles.app;

public class GameTable {

	private int posx;
	private int posy;
	
	private int offset;
	
	private ToggleableSprite[][] table;
	
	public GameTable(int posx, int posy) {
		
		this.posx = posx;
		this.posy = posy;
		
		table = new ToggleableSprite[8][8];
		
		offset = 104;

		for(int i=0;i<8;i++)
			for(int k=0;k<8;k++) {	// 50 //925
				table[i][k] = new ToggleableSprite(posx+(i*offset), posy-(k*offset), offset, offset, "img/layout/coreGame/cellSprite.png", 1);
				//new StupidSprite("img/turtles/turtwig.png",600-(i*20),450+(k*20),-2,10);
			}
	}
	
	public void close() {
		for(int i=0;i<8;i++)
			for(int k=0;k<8;k++)
				table[i][k].close();
	}
	
	public void highlightCell() {
		for(int i=0;i<8;i++)
			for(int k=0;k<8;k++)
				table[i][k].toggleSprite();
	}
	
	

}
