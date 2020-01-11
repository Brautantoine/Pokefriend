package fr.pokeTurtles.app;

/**
 * <b>Clickable</b> <br>
 * 
 * Interface to specify callback used by the Engine to process touchDown event
 *
 */
public interface Clickable {
	abstract public void onClick(int x, int y);
	/* ** DEBUG ** abstract public void onKeyDown();*/
	abstract boolean close();
}
