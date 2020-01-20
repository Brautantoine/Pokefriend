package fr.pokeTurtles.app;

public class GameTable {

	private int posx;
	private int posy;
	
	private int offset;
	
	private ToggleableClickableWidget[][] table;
	private boolean[][] clickMask;
	private TableElement[][] elements;
	
	public GameTable(int posx, int posy) {
		
		this.posx = posx;
		this.posy = posy;
		
		table = new ToggleableClickableWidget[8][8];
		clickMask = new boolean[8][8];
		elements = new TableElement[8][8];
		
		offset = 104;

		for(int i=0;i<8;i++)
			for(int k=0;k<8;k++) {	// 50 //925
				final int iRef = i;
				final int kRef = k;
				table[i][k] = new ToggleableClickableWidget(posx+(i*offset), posy-(k*offset), offset, offset, "img/layout/coreGame/cellSprite.png", 1) {
					@Override
					public void onClick(int x, int y) {
						if(contains(x, y)) {
							//toggleSprite();
							validateClick(iRef, kRef);
						}
					}
				};
				clickMask[i][k] = true;
				//new StupidSprite("img/turtles/turtwig.png",600-(i*20),450+(k*20),-2,10);
			}
	}
	
	public void close() {
		for(int i=0;i<8;i++)
			for(int k=0;k<8;k++) {
				table[i][k].close();
				if(elements[i][k] != null)
					elements[i][k].close();
			}
	}
	
	public void highlightCell() {
		for(int i=0;i<8;i++)
			for(int k=0;k<8;k++)
				table[i][k].toggleSprite();
	}
	
	private void validateClick (int i,int k) {
		if(clickMask[i][k] == true)
			table[i][k].toggleSprite();
	}
	
	public void addElement(int x, int y, TableElementType type) {
		elements[x][y] = makeElement(x, y, type);
	}
	
	public void removeElement(int x, int y) {
		elements[x][y].close();
	}
	
	private TableElement makeElement(int x, int y, TableElementType type) {
		switch (type) {
		case BUSH:
		case ROCK:
			return new TableElement(type, posx+((x)*offset)+20, posy-((y)*offset)+10);
		case TURTLES:
			return new Turtles(posx+((x)*offset)+20, posy-((y)*offset)+10,Direction.DOWN,x,y);
		case PKCTR:
			return new Cristal(posx+((x)*offset)+20, posy-((y)*offset)+10);
		default:
			return null;
		}
	}
	
	public void moveElement(int x, int y, int nx, int ny) {
		elements[nx][ny] = elements[x][y];
		elements[x][y] = null;
		
		elements[nx][ny].move(posx+((nx)*offset)+20, posy-((ny)*offset)+10);
	}
	
	

}
