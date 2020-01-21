package fr.pokeTurtles.app;

import java.util.ArrayList;

public class RightPanel {

	private LabelWidget gameInfo;
	private StandardSprite box;
	private GameTable gt;
	
	private ArrayList<ClickableWidget> clicks;
	private ArrayList<Integer> selectedOrder;
	
	private volatile boolean waiting = false;
	private volatile int cardSelected = 0;
	
	private PlayerChoice playerChoice;
	
	private int selected[]; // This one is hard cause it's also tell the move to apply onClick, just remember -10:selected, 10:not
	
	public RightPanel(GameTable gt) {
		clicks = new ArrayList<ClickableWidget>();
		selectedOrder = new ArrayList<>();
		box = new StandardSprite(1024,670,"img/layout/coreGame/frame1.png");
		gameInfo = new LabelWidget(1050, 1000, "Joueur : Chartor\n\nNombre de blocs restants\n\nRocher : 3\nArbre : 2");
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
					playerChoice = PlayerChoice.BUILDPROG;
					waiting = false;
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
		clicks.add(new ClickableWidget(2000,2000,93,533,"img/layout/coreGame/keep.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					waiting = false;
					playerChoice = PlayerChoice.KEEP;
				}	
			}
		});
		clicks.add(new ClickableWidget(2000,2000,93,533,"img/layout/coreGame/drop.png") {
			@Override
			public void onClick(int x, int y) {
				
				if(contains(x, y)) {	
					waiting = false;
					playerChoice = PlayerChoice.DROP;
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
	
	public PlayerChoice getPlayerChoice (ArrayList<Card> playerHand,Player player) {
		
		for(int i = 3; i<5 ; i++)
			clicks.get(i).move(2000, 2000);
		
		for(int i = 0; i<3 ; i++)
			clicks.get(i).move(1024, 550-(i*100));
		
		// First unselect all
		for(int i = 0;i<5;i++)
			selected[i]=10;
		
		if(player.getRock() == 0 && player.getTree() == 0)
			clicks.get(0).move(2000, 2000);
		
		waiting = true;
		
		int i = 0;
		int k = 0;
		
		printPlayerInfo(player);
		
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
							
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
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
	
	public PlayerChoice getBlockChoice (Player player) {
		
		for(int i = 0; i<3 ; i++)
			clicks.get(i).move(2000, 2000);
		
		waiting = true;
		
		LabelWidget label = new LabelWidget(1024, 600, "Quel type de block ?");
		
		if(player.getTree() > 0)
			clicks.get(3).move(1024, 450);
		if(player.getRock() > 0)
			clicks.get(4).move(1024, 350);
		
		while(waiting);
		
		label.close();
		
		return playerChoice;
		
	}
	
	public PlayerChoice getDropChoice (ArrayList<Card> playerHand) {
		
		ArrayList<MoveableClickableWidget> card = new ArrayList<>();
		
		for(int i = 0; i<5 ; i++)
			clicks.get(i).move(2000, 2000);
		
		clicks.get(5).move(1024, 450);
		
		// First unselect all
				for(int i = 0;i<5;i++)
					selected[i]=10;
		
		LabelWidget label = new LabelWidget(924, 600, "Quel faire des cartes restantes ?");
		
		waiting = true;
		
		int i = 0;
		int k = 0;
		
		cardSelected = 0;
		
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
							
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
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
							if(selectedOrder.contains(Integer.valueOf(kRef)))
								selectedOrder.remove(Integer.valueOf(kRef));
							else
								selectedOrder.add(Integer.valueOf(kRef));
							
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
		
		if(playerHand.size() == 0) {
			waiting = false;
			playerChoice = PlayerChoice.KEEP;
		}
			
		
		while(waiting) {
			if (cardSelected > 0)
				clicks.get(6).move(1024, 350);
			else
				clicks.get(6).move(2000, 2000);
		}
		
		for(MoveableClickableWidget m : card)
			m.close();
		
		label.close();
		
		for(i=5;i<7;i++)
			clicks.get(i).move(2000, 2000);
		
		return playerChoice;
		
	}
	
	public void printPlayerInfo(Player player) {
		gameInfo.setText("Joueur : "+player.getPlayerName()+"\n\nNombre de blocs restants\n\nRocher : "+player.getRock()+"\nArbre : "+player.getTree()+"\nPioche : "+player.getDrawSize()+"\nExecution :"+player.getExecSize()+"\nDefausse : "+player.getDropStack());
	}
	
	// Add list of selected index
	public ArrayList<Card> getSelected(ArrayList<Card> hands) {
		
		ArrayList<Card> cards = new ArrayList<>();
		
		int k = 0;
		
		/*for(int i=0;i<5;i++) {
			if() {
				cards.add(hands.get(k));
				hands.remove(k);
			}
			else
				k++;
		}*/
		
		for(Integer i : selectedOrder) {
			cards.add(hands.get(i));
			//hands.remove(Integer.valueOf(i));
		}
		for(int i=4; i>=0; i--)
			if(selectedOrder.contains(Integer.valueOf(i)))
				hands.remove(i);
			
		selectedOrder.clear();
		cardSelected=0;
		return cards;
	}
	
	public void close() {
		gameInfo.close();
		box.close();
		
		for (ClickableWidget c : clicks)
			c.close();
	}

}
