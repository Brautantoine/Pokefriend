package fr.pokeTurtles.app;

import java.util.ArrayList;

public class GameTable {

	private int posx;
	private int posy;
	
	private int offset;
	
	private ToggleableClickableWidget[][] table;
	private boolean[][] clickMask;
	//private boolean[][] validateElements;
	private TableElement[][] elements;
	
	private volatile boolean waiting;
	
	private volatile int chosenX;
	private volatile int chosenY;
	
	public GameTable(int posx, int posy) {
		
		this.posx = posx;
		this.posy = posy;
		
		this.waiting = false;
		
		this.chosenX = -1;
		this.chosenY = -1;
		
		table = new ToggleableClickableWidget[8][8];
		clickMask = new boolean[8][8];
		//validateElements = new boolean[8][8];
		elements = new TableElement[8][8];
		
		offset = 104;
		
		for(int i=0; i<8; i++)
			for(int k=0; k<8; k++) {
				clickMask[i][k] = false;
				//validateElements[i][k] = false;
			}
		

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
				//clickMask[i][k] = true;
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
		if(clickMask[i][k] == true) {
			chosenX = i;
			chosenY = k;
			
			waiting = false;
		}
			
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
			return new Cristal(posx+((x)*offset)+20, posy-((y)*offset)+10,x,y);
		default:
			return null;
		}
	}
	
	public void addTree() {
		
		waiting = true;
		chosenX = -1;
		chosenY = -1;
		
		validCells();
		highlightValidCells();
		
		while(waiting);
		
		addElement(chosenX, chosenY, TableElementType.BUSH);
		
		highlightValidCells();
		
		for(int i = 0; i<8; i++)
			for(int k =0; k<8;k++)
				clickMask[i][k] = false;
	}
	
	public void addRock() {
		
		waiting = true;
		chosenX = -1;
		chosenY = -1;
		
		unvalidAndExplore();
		
		highlightValidCells();
		
		while(waiting);
		
		addElement(chosenX, chosenY, TableElementType.ROCK);
		
		highlightValidCells();
		
		for(int i = 0; i<8; i++)
			for(int k =0; k<8;k++)
				clickMask[i][k] = false;
		
		
		
	}
	
	private void unvalidAndExplore() {
		
		ArrayList<Cristal> cristals = new ArrayList<>();
		
		for(int i = 0; i<8; i++)
			for(int k =0; k<8;k++) {
				if(elements[i][k] == null)
					clickMask[i][k] = true;
				else if(elements[i][k].getClass() == Cristal.class)
					cristals.add((Cristal)elements[i][k]);
			}
		for(Cristal c : cristals)
			explore(c.getPosI(),c.getPosK());
		/*for(Cristal c : cristals)
			validateExplore(c.getPosI(), c.getPosK());*/
		
		
	}
	
	private void explore (int I, int K) {
		
		int i = -1;
		int k = -1;
		
		clickMask[I][K] = false;
		
		int openPath = 4;
		
		try {
			if(clickMask[I-1][K] == false)
				openPath--;
			else {
				i = I-1;
				k = K;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		try {
			if(clickMask[I+1][K] == false)
				openPath--;
			else {
				i = I+1;
				k = K;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		try {
			if(clickMask[I][K-1] == false)
				openPath--;
			else {
				i = I;
				k = K-1;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		try {
			if(clickMask[I][K+1] == false)
				openPath--;
			else {
				i = I;
				k = K+1;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		if(openPath == 1) {
			explore(i, k);
		}
		
		
	}
	
	/** HOWTO 
	 * 1. visit all the reachable cell
	 * 2. Block a case
	 * 3. Visit again all the reachable cell
	 * 4. Compare
	 */
	
	/*private void validateExplore(int I, int K) {
		
		int i = -1;
		int k = -1;
		
		int openPath = 4;
		
		try {
			if(clickMask[I-1][K] == false) {
				if(elements[I-1][K] == null)
					validateExplore(I-1, K);
				else {
					openPath--;
				}
			}
			else {
				i = I-1;
				k = K;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		try {
			if(clickMask[I+1][K] == false) {
				if(elements[I+1][K] == null)
					validateExplore(I+1, K);
				else {
					openPath--;
				}
			}
			else {
				i = I+1;
				k = K;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		try {
			if(clickMask[I][K-1] == false) {
				if(elements[I][K-1] == null)
					validateExplore(I, K-1);
				else {
					openPath--;
				}
			}
			else {
				i = I;
				k = K-1;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		try {
			if(clickMask[I][K+1] == false) {
				if(elements[I][K+1] == null)
					validateExplore(I, K+1);
				else {
					openPath--;
				}
			}
			else {
				i = I;
				k = K+1;
			}
		}
		catch (Exception e) {
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				openPath--;
			else
				throw e;
		}
		
		System.err.println("case : "+I+" "+K+" : openPath : "+openPath+ " i: "+i+" k: "+k);
		if(openPath == 1)
			explore(i, k);
	}*/
	
	private void validCells () {
		for(int i = 0; i<8; i++)
			for(int k =0; k<8;k++)
				if(elements[i][k] == null)
					clickMask[i][k] = true;
	}
	
	private void highlightValidCells() {
		for(int i = 0; i<8; i++)
			for(int k =0; k<8;k++)
				if(clickMask[i][k] == true)
					table[i][k].toggleSprite();
	}
	
	
	public void moveElement(int x, int y, int nx, int ny) {
		elements[nx][ny] = elements[x][y];
		elements[x][y] = null;
		
		elements[nx][ny].move(posx+((nx)*offset)+20, posy-((ny)*offset)+10);
	}
	
	

}
