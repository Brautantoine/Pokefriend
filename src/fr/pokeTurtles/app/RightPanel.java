package fr.pokeTurtles.app;

import java.util.ArrayList;

public class RightPanel {

	private LabelWidget gameInfo;
	private StandardSprite box;
	private GameTable gt;
	
	private ArrayList<ClickableWidget> clicks;
	
	public RightPanel(GameTable gt) {
		clicks = new ArrayList<ClickableWidget>();
		box = new StandardSprite(1024,670,"img/layout/coreGame/frame1.png");
		gameInfo = new LabelWidget(1050, 975, "Joueur : Chartor\n\nNombre de bloc restant :\n\nRocher : 3\nArbre : 2");
		this.gt = gt;
		
		clicks.add(new ClickableWidget(1024,550,93,533,"img/layout/coreGame/bloc.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		clicks.add(new ClickableWidget(1024,450,93,533,"img/layout/coreGame/add.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		clicks.add(new ClickableWidget(1024,350,93,533,"img/layout/coreGame/exec.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		clicks.add(new ClickableWidget(950,60,281,200,"img/cards/redCard.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		//gt.highlightCell();
	}
	
	public void close() {
		gameInfo.close();
		box.close();
		
		for (ClickableWidget c : clicks)
			c.close();
	}

}
