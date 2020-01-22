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
	
	private ArrayList<Turtles> turtles;
	private ArrayList<Integer> winners;
	
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
		
		turtles = new ArrayList<>();
		winners = new ArrayList<>();
		
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
			Turtles t = new Turtles(posx+((x)*offset)+20, posy-((y)*offset)+10,Direction.DOWN,x,y);
			turtles.add(t);
			return t;
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
		for(Turtles t : turtles)
			explore(t.getGridX(), t.getGridY());
		
		
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
	
	public int moveTurtles(int currentPlayer) {
		
		int ret = 0;
		
		Turtles t = turtles.get(currentPlayer);
		
		int posx = t.getGridX();
		int posy = t.getGridY();
		
		switch (t.getDirection()) {
		case DOWN:
			try {
				if(elements[posx][posy+1] == null) {
					moveElement(posx, posy, posx, posy+1);
					t.updatePos(posx, posy+1);
				}
				else if(elements[posx][posy+1].getClass() == Cristal.class)
					if(!((Cristal)elements[posx][posy+1]).getReached()) {
						((Cristal)elements[posx][posy+1]).reach();
						winners.add(currentPlayer);
						t.close();
						elements[posx][posy] = null;
						ret = 1;
						
					}
				else if(elements[posx][posy+1].getType() == TableElementType.ROCK) {
					turnTurtleLeft(t);
					turnTurtleLeft(t);
				}
					
					
			}
			catch (Exception e) {
				if(e.getClass() == ArrayIndexOutOfBoundsException.class) {
					t.reset(); // TODO check reset
					elements[posx][posy]=null;
					elements[t.getGridX()][t.getGridY()] = t;
				}
			}
			break;
		case LEFT:
			try {
				if(elements[posx-1][posy] == null) {
					moveElement(posx, posy, posx-1, posy);
					t.updatePos(posx-1, posy);
				}
				else if(elements[posx-1][posy].getClass() == Cristal.class)
					if(!((Cristal)elements[posx-1][posy]).getReached()) {
						((Cristal)elements[posx-1][posy]).reach();
						winners.add(currentPlayer);
						t.close();
						elements[posx][posy] = null;
						ret = 1;
						
					}
				else if(elements[posx][posy+1].getType() == TableElementType.ROCK) {
						turnTurtleLeft(t);
						turnTurtleLeft(t);
					}
			}
			catch (Exception e) {
				if(e.getClass() == ArrayIndexOutOfBoundsException.class) {
					t.reset(); // TODO check reset
					elements[posx][posy]=null;
					elements[t.getGridX()][t.getGridY()] = t;
				}
			}
			break;
		case RIGHT:
			try {
				if(elements[posx+1][posy] == null) {
					moveElement(posx, posy, posx+1, posy);
					t.updatePos(posx+1, posy);
				}
				else if(elements[posx+1][posy].getClass() == Cristal.class)
					if(!((Cristal)elements[posx+1][posy]).getReached()) {
						((Cristal)elements[posx+1][posy]).reach();
						winners.add(currentPlayer);
						t.close();
						elements[posx][posy] = null;
						ret = 1;
						
					}
					else if(elements[posx][posy+1].getType() == TableElementType.ROCK) {
						turnTurtleLeft(t);
						turnTurtleLeft(t);
					}
			}
			catch (Exception e) {
				if(e.getClass() == ArrayIndexOutOfBoundsException.class) {
					t.reset(); // TODO check reset
					elements[posx][posy]=null;
					elements[t.getGridX()][t.getGridY()] = t;
				}
			}
			break;
		case UP:
			try {
				if(elements[posx][posy-1] == null) {
					moveElement(posx, posy, posx, posy-1);
					t.updatePos(posx, posy-1);
				}
				else if(elements[posx][posy-1].getClass() == Cristal.class)
					if(!((Cristal)elements[posx][posy-1]).getReached()) {
						((Cristal)elements[posx][posy-1]).reach();
						winners.add(currentPlayer);
						t.close();
						elements[posx][posy] = null;
						ret = 1;
						
					}
					else if(elements[posx][posy+1].getType() == TableElementType.ROCK) {
						turnTurtleLeft(t);
						turnTurtleLeft(t);
					}
			}
			catch (Exception e) {
				if(e.getClass() == ArrayIndexOutOfBoundsException.class) {
					t.reset(); // TODO check reset
					elements[posx][posy]=null;
					elements[t.getGridX()][t.getGridY()] = t;
				}
			}
			break;
		default:
			break;
		}
		
		return ret;
		
	}
	
	public void turnTurtleLeft (int currentPlayer) {
		
		Turtles t = turtles.get(currentPlayer);
		
		switch (t.getDirection()) {
		case DOWN:
			t.setDirection(Direction.LEFT);
			break;
		case LEFT:
			t.setDirection(Direction.UP);
			break;
		case UP:
			t.setDirection(Direction.RIGHT);
			break;
		case RIGHT:
			t.setDirection(Direction.DOWN);
			break;
		default:
			break;
		}
	}
public void turnTurtleLeft (Turtles t) {
		
		//Turtles t = turtles.get(currentPlayer);
		
		switch (t.getDirection()) {
		case DOWN:
			t.setDirection(Direction.LEFT);
			break;
		case LEFT:
			t.setDirection(Direction.UP);
			break;
		case UP:
			t.setDirection(Direction.RIGHT);
			break;
		case RIGHT:
			t.setDirection(Direction.DOWN);
			break;
		default:
			break;
		}
	}
	
public void turnTurtleRight (int currentPlayer) {
		
		Turtles t = turtles.get(currentPlayer);
		
		switch (t.getDirection()) {
		case DOWN:
			t.setDirection(Direction.RIGHT);
			break;
		case LEFT:
			t.setDirection(Direction.DOWN);
			break;
		case UP:
			t.setDirection(Direction.LEFT);
			break;
		case RIGHT:
			t.setDirection(Direction.UP);
			break;
		default:
			break;
		}
	}

	public void fireDatLaserKABOOOM (int currentPlayer) {
		
		Turtles t = turtles.get(currentPlayer);
		
		if (EXPLOSIOOOOOOON(t.getGridX(), t.getGridY(), t.getDirection()) == 1) {
			elements[t.getGridX()][t.getGridY()]=null;
			t.reset(); // TODO check reset
			elements[t.getGridX()][t.getGridY()] = t;
		}
	}

	public int EXPLOSIOOOOOOON (int x, int y, Direction dir) {
		
		TableElement cell;
		
		try {
			switch (dir) {
			case DOWN:
				cell = elements[x][y+1];
				
				if(cell != null) {
					switch (cell.getType()) {
					case BUSH:
						cell.close();
						elements[x][y+1] = null;
						break;
					case TURTLES:
						((Turtles)cell).reset(); // TODO check reset
						elements[x][y+1]=null;
						elements[((Turtles)cell).getGridX()][((Turtles)cell).getGridY()] = ((Turtles)cell);
						return 0;
					case PKCTR:
						return 1;
					default:
						return 0;
					}
				}
				else
					return EXPLOSIOOOOOOON(x, y+1, dir);
				break;
			case LEFT:
				cell = elements[x-1][y];
				
				if(cell != null) {
					switch (cell.getType()) {
					case BUSH:
						cell.close();
						elements[x-1][y] = null;
						break;
					case TURTLES:
						((Turtles)cell).reset(); // TODO check reset
						elements[x-1][y]=null;
						elements[((Turtles)cell).getGridX()][((Turtles)cell).getGridY()] = ((Turtles)cell);
						return 0;
					case PKCTR:
						return 1;
					default:
						return 0;
					}
				}
				else
					return EXPLOSIOOOOOOON(x-1, y, dir);
				break;
			case RIGHT:
				cell = elements[x+1][y];
				
				if(cell != null) {
					switch (cell.getType()) {
					case BUSH:
						cell.close();
						elements[x+1][y] = null;
						break;
					case TURTLES:
						((Turtles)cell).reset(); // TODO check reset
						elements[x+1][y]=null;
						elements[((Turtles)cell).getGridX()][((Turtles)cell).getGridY()] = ((Turtles)cell);
						return 0;
					case PKCTR:
						return 1;
					default:
						return 0;
					}
				}
				else
					return EXPLOSIOOOOOOON(x+1, y, dir);
				break;
			case UP:
				cell = elements[x][y-1];
				
				if(cell != null) {
					switch (cell.getType()) {
					case BUSH:
						cell.close();
						elements[x][y-1] = null;
						break;
					case TURTLES:
						((Turtles)cell).reset(); // TODO check reset
						elements[x][y-1]=null;
						elements[((Turtles)cell).getGridX()][((Turtles)cell).getGridY()] = ((Turtles)cell);
						return 0;
					case PKCTR:
						return 1;
					default:
						return 0;
					}
				}
				else
					return EXPLOSIOOOOOOON(x, y-1, dir);
			default:
				break;
			}
		}
		catch (Exception e) {
			
			if(e.getClass() == ArrayIndexOutOfBoundsException.class)
				return 0;
			else
				throw e;

		}
		
		return 0;
	}
	
	public Direction getTurtlesDirection(int currentPlayer) { return turtles.get(currentPlayer).getDirection(); }
	public ArrayList<Integer> getWinners() { return winners; }
	

}
