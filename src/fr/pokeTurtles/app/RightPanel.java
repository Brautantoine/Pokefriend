package fr.pokeTurtles.app;

import java.util.ArrayList;

public class RightPanel {

	private LabelWidget gameInfo;
	private StandardSprite box;
	private GameTable gt;
	
	private ArrayList<ClickableWidget> clicks;
	
	private volatile boolean waiting = false;
	private volatile int cardSelected = 0;
	
	private PlayerChoice playerChoice;
	
	private int selected[]; // This one is hard cause it's also tell the move to apply onClick, just remember -10:selected, 10:not
	
	public RightPanel(GameTable gt) {
		clicks = new ArrayList<ClickableWidget>();
		box = new StandardSprite(1024,670,"img/layout/coreGame/frame1.png");
		gameInfo = new LabelWidget(1050, 975, "Joueur : Chartor\n\nNombre de blocs restants\n\nRocher : 3\nArbre : 2");
		this.gt = gt;
		
		clicks.add(new ClickableWidget(1024,550,93,533,"img/layout/coreGame/bloc.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					playerChoice = PlayerChoice.BLOCK;
					waiting = false;
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
		clicks.add(new ClickableWidget(2000,2000,93,533,"img/layout/coreGame/tree.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					waiting = false;
					playerChoice = PlayerChoice.TREE;
				}	
			}
		});
		clicks.add(new ClickableWidget(2000,2000,93,533,"img/layout/coreGame/rock.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					waiting = false;
					playerChoice = PlayerChoice.ROCK;
				}	
			}
		});
		
		/*clicks.add(new ClickableWidget(900,60,281,200,"img/cards/redCard.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		
		clicks.add(new ClickableWidget(1310,60,281,200,"img/cards/purpleCard.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		clicks.add(new ClickableWidget(1515,60,281,200,"img/cards/yellowCard.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});
		clicks.add(new ClickableWidget(1720,60,281,200,"img/cards/redCard.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					gt.highlightCell();
				}	
			}
		});*/
		//gt.highlightCell();
		
		selected = new int[5];
		
		for(int i = 0;i<5;i++)
			selected[i]=10;
	}
	
	public PlayerChoice getPlayerChoice (ArrayList<Card> playerHand) {
		
		for(int i = 3; i<5 ; i++)
			clicks.get(i).move(2000, 2000);
		
		for(int i = 0; i<3 ; i++)
			clicks.get(i).move(1024, 550-(i*100));
		
		// First unselect all
		for(int i = 0;i<5;i++)
			selected[i]=10;
		
		waiting = true;
		
		int i = 0;
		int k = 0;
		
		ArrayList<MoveableClickableWidget> card = new ArrayList<>();
		
		for (Card c : playerHand) {
			final int kRef = k;
			switch (c) {
			case blue:
				card.add(new MoveableClickableWidget(900+i,60,281,200,"img/cards/blueCard.png") {
					@Override
					public void onClick(int x, int y) {
						if(contains(x, y)) {	
							moveY(selected[kRef]);
							cardSelected+=selected[kRef];
							selected[kRef] = -selected[kRef];
							
						}	
					}
				});
				break;
			case purple:
				card.add(new MoveableClickableWidget(900+i,60,281,200,"img/cards/purpleCard.png") {
					@Override
					public void onClick(int x, int y) {
						if(contains(x, y)) {	
							moveY(selected[kRef]);
							cardSelected+=selected[kRef];
							selected[kRef] = -selected[kRef];
							
						}	
					}
				});
				break;
			case red:
				card.add(new MoveableClickableWidget(900+i,60,281,200,"img/cards/redCard.png") {
					@Override
					public void onClick(int x, int y) {
						if(contains(x, y)) {	
							moveY(selected[kRef]);
							cardSelected+=selected[kRef];
							selected[kRef] = -selected[kRef];
							
						}	
					}
				});
				break;
			case yellow:
				card.add(new MoveableClickableWidget(900+i,60,281,200,"img/cards/yellowCard.png") {
					@Override
					public void onClick(int x, int y) {
						if(contains(x, y)) {	
							moveY(selected[kRef]);
							cardSelected+=selected[kRef];
							selected[kRef] = -selected[kRef];
							
						}	
					}
				});
				break;
			default:
				break;
			}
			i+= 205;
			k++;
		}
		
		while(waiting) {
			if (cardSelected > 0)
				clicks.get(1).move(1024, 450);
			else
				clicks.get(1).move(2000, 2000);
		}
		
		for(MoveableClickableWidget m : card)
			m.close();
		
		return playerChoice;
	}
	
	public PlayerChoice getBlockChoice () {
		
		for(int i = 0; i<3 ; i++)
			clicks.get(i).move(2000, 2000);
		
		waiting = true;
		
		LabelWidget label = new LabelWidget(1024, 600, "Quel type de block ?");
		
		clicks.get(3).move(1024, 450);
		clicks.get(4).move(1024, 350);
		
		while(waiting);
		
		label.close();
		
		return playerChoice;
		
	}
	
	public void printPlayerInfo(Player player) {
		gameInfo.setText("Joueur : "+player.getPlayerName()+"\n\nNombre de blocs restants\n\nRocher : "+player.getRock()+"\nArbre : "+player.getTree());
	}
	
	public void close() {
		gameInfo.close();
		box.close();
		
		for (ClickableWidget c : clicks)
			c.close();
	}

}
