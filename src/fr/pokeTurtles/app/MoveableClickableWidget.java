package fr.pokeTurtles.app;

public class MoveableClickableWidget extends ClickableWidget {

	public MoveableClickableWidget(int posX, int posY, int height, int width, String img) {
		super(posX, posY, height, width, img);
		// TODO Auto-generated constructor stub
	}
	
	public void moveY (int dy) {
		this.posY+=dy;
	}
	

}
