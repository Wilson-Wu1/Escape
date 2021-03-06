package com.group12.game;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.group12.board.*;
import com.group12.board.Cell.cellType;
import com.group12.board_entity.*;
import com.group12.game.GameMain.GameState;
import com.group12.game.GameMain.shootDirection;

import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Handles graphical display.
 * @author Daniel, Wilson, Yuxi Hu
 *
 */
@SuppressWarnings("serial")
public class DisplayManager extends JPanel{
	
	// Attributes
	JFrame gameWindow;
	
	//Game Items
	private int sizeX;
	private int sizeY;
	private int cellSize;
	private int score;
	private int HP;
	
	private int goalX;
	private int goalY;
	private float timePercentage;
	private Board board;
	private MainCharacter mainChar;
	private ArrayList<Enemy> enemies;
	private ArrayList<Punishment> punishments;
	private ArrayList<BonusReward> bonusRewards;
	private ArrayList<ObjectiveReward> objectiveRewards;
	
	private ImageData imgData;
	
	
	//Enum to hold current state. Used when updating display
	GameMain.GameState currentState;
	GameMain.shootDirection direction;
	// are these constructors private or public or what?
	DisplayManager(){
	
		this(500, 500);
	}
	
	DisplayManager(int sizeX, int sizeY) {
		gameWindow = new JFrame("Escape");
		
		gameWindow.setResizable(false);
		this.setPreferredSize(new Dimension(sizeX + sizeX/3, sizeY));
		gameWindow.add(this);
		gameWindow.pack();
		gameWindow.setVisible(true);
	    gameWindow.setLocationRelativeTo(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		imgData = new ImageData();
		this.direction = shootDirection.NOTHING;
		this.currentState = GameState.MENU;
		this.repaint();
	}

	public void addKeyListener(KeyListener kl) {
		gameWindow.addKeyListener(kl);
	}
	
	private void dispGame(Graphics2D g2d) {
		dispBoard(g2d);
		dispBonusRewards(g2d);
		dispPunishments(g2d);
		dispObjectiveRewards(g2d);
		dispEnemies(g2d);
		dispMainChar(g2d);
		dispHUD(g2d);
	} 
	
	private void dispHUD(Graphics2D g2d) {
		g2d.setColor(new Color(0.3f, 0.3f, 0.3f, 1.0f));
		g2d.fillRect(sizeX, 0, sizeX/3, sizeY);
		g2d.setColor(new Color(0.8f, 0.2f, 0.2f, 1.0f));
		g2d.fillRect(sizeX + sizeX/20, sizeY/3, sizeX/3 - sizeX/10, sizeY/20);
		g2d.setColor(new Color(0.2f, 0.8f, 0.2f, 1.0f));
		g2d.fillRect(sizeX + sizeX/20, sizeY/3, (int)((sizeX/3 - sizeX/10) * timePercentage), sizeY/20);
		
		g2d.setColor(Color.white);
		Font fnt0 = new Font("arial", Font.BOLD, 20);
		g2d.setFont(fnt0);
		g2d.drawString("Score:  " + Integer.toString(score) ,sizeX + sizeX/20, sizeY/8);
		g2d.drawString("HP:  " + Integer.toString(HP) ,sizeX + sizeX/20, sizeY/6);
	}

	private int left(int x, int y) {
		
		int barrierHere = 0;
	
		for(int i = x; i>0;i--) {
			
			if((board.inBounds(i, y) && board.getCellType(i, y) != cellType.BARRIER)==false){
				
				barrierHere = i;
				
				return barrierHere;
			}
		}
		return barrierHere;	
	}

	private int up(int x, int y) {
		
		int barrierHere = 0;

		for(int i = y; i>0;i--) {
			
			if((board.inBounds(x, i) && board.getCellType(x, i) != cellType.BARRIER)==false){
				
				barrierHere = i;
				
				return barrierHere;
			}
		}
		return barrierHere;	
	}

	private int right(int x, int y) {
		int boardX = this.board.getXSize();
		int barrierHere = boardX;
		
		for(int i = x; i<boardX;i++) {
			
			if((board.inBounds(i, y) && board.getCellType(i, y) != cellType.BARRIER)==false){
				
				barrierHere = i;
			
				return barrierHere;
			}
		}
		return barrierHere;	
	}

	private int down(int x, int y) {
		int boardY = this.board.getYSize();
		int barrierHere = boardY ;

		for(int i = y; i<boardY;i++) {
			
			if((board.inBounds(x, i) && board.getCellType(x, i) != cellType.BARRIER)==false){
				
				barrierHere = i;
				
				return barrierHere;
			}
		}
		return barrierHere;	
	}

	private void dispBoard(Graphics2D g2d) {
		int boardX = this.board.getXSize();
		int boardY = this.board.getYSize();
		this.cellSize = this.board.getCellSize();
		BufferedImage boardImg = imgData.getBoardImg();
		BufferedImage XAxisShoot = imgData.getXAxisImg();
		BufferedImage YAxisShoot = imgData.getYAxisImg();

		for(int y = 0; y < boardY; y++) {
			for(int x = 0; x < boardX; x++) {
				if(this.board.getCellType(x, y) == cellType.OPEN) {
					if(direction == GameMain.shootDirection.LEFT) {
						
							g2d.drawImage(boardImg, x*cellSize, y*cellSize, cellSize, cellSize,null);
							if((mainChar.getYPos()==y)&&(mainChar.getXPos()>x)&&(x > left(mainChar.getXPos(),y))) {
									g2d.drawImage(XAxisShoot, x*cellSize, y*cellSize, cellSize, cellSize,null);
							}
					}
						
					else if(direction == GameMain.shootDirection.UP) {
						g2d.drawImage(boardImg, x*cellSize, y*cellSize, cellSize, cellSize,null);
						if((mainChar.getXPos()==x)&&(mainChar.getYPos()>y)&&(y>up(x,mainChar.getYPos()))) {
							
							
								g2d.drawImage(YAxisShoot, x*cellSize, y*cellSize, cellSize, cellSize,null);
							}
					}
					else if(direction == GameMain.shootDirection.RIGHT) {
						g2d.drawImage(boardImg, x*cellSize, y*cellSize, cellSize, cellSize,null);
						if((mainChar.getYPos()==y)&&(mainChar.getXPos()<x)&&(x < right(mainChar.getXPos(),y))) {
							
							
								g2d.drawImage(XAxisShoot, x*cellSize, y*cellSize, cellSize, cellSize,null);
							}
						

					
						
					}
					else if(direction == GameMain.shootDirection.DOWN) {
						g2d.drawImage(boardImg, x*cellSize, y*cellSize, cellSize, cellSize,null);
						if((mainChar.getXPos()==x)&&(mainChar.getYPos()<y)&&(y<down(x,mainChar.getYPos()))) {
							
							
								g2d.drawImage(YAxisShoot, x*cellSize, y*cellSize, cellSize, cellSize,null);
							}
						
					}
					
				
					else {

						g2d.drawImage(boardImg, x*cellSize, y*cellSize, cellSize, cellSize,null);
					}
					



					
				}
				else 
				{
					BufferedImage cellImg = imgData.getCellImg(); 
					g2d.drawImage(cellImg, x*cellSize, y*cellSize, cellSize, cellSize,null);
				}
				
			}
		}
	}
	
	private void dispBonusRewards(Graphics2D g2d) {
		BufferedImage bonusImg = imgData.getBonusImg(); 
		for(int i = 0; i < bonusRewards.size(); i ++) {
			g2d.drawImage(bonusImg, bonusRewards.get(i).getXPos()*cellSize, bonusRewards.get(i).getYPos() * cellSize, cellSize, cellSize,null);
		}
	}
	
	private void dispPunishments(Graphics2D g2d) {
		g2d.setColor(new Color(1.0f, 0.5f, 0.0f, 1.0f));
		BufferedImage img = imgData.getPunishmentImg();
		for(int i = 0; i < punishments.size(); i ++) {
			g2d.drawImage(img, punishments.get(i).getXPos()*cellSize, punishments.get(i).getYPos() * cellSize, cellSize, cellSize, null);
		}
	}
	
	private void dispObjectiveRewards(Graphics2D g2d) {
		BufferedImage objImg = imgData.getObjectImg(); 
		for(int i = 0; i < objectiveRewards.size(); i ++) {
			g2d.drawImage(objImg, objectiveRewards.get(i).getXPos()*cellSize, objectiveRewards.get(i).getYPos() * cellSize, cellSize, cellSize, null);
		}
		BufferedImage goalImg = imgData.getGoalImg(); 
		g2d.drawImage(goalImg, goalX * cellSize, goalY * cellSize, cellSize, cellSize,null);
	}
	
	private void dispEnemies(Graphics2D g2d) {
		BufferedImage enemyImage = imgData.getEnemyImg(); 
		for(int i = 0; i < enemies.size(); i ++) {
			g2d.drawImage(enemyImage, enemies.get(i).getXPos()*cellSize, enemies.get(i).getYPos() * cellSize, cellSize, cellSize, null);
		}
	}
	
	private void dispMainChar(Graphics2D g2d) {
		g2d.drawImage(imgData.getPlayerImg(), mainChar.getXPos()*cellSize, mainChar.getYPos() * cellSize, cellSize, cellSize, null);
	}
	
	//Graphics to Display the Menu Screen
	private void dispMenu(Graphics menu) {
		menu.setColor(Color.black);
		menu.fillRect(0, 0, sizeX + sizeX/3, sizeY);
		Font fnt0 = new Font("arial", Font.BOLD, 100);
		menu.setFont(fnt0);
		menu.setColor(Color.red);
		menu.drawString("ESCAPE",100,100);
		Font fnt1 = new Font("arial", Font.BOLD,45);
		menu.setFont(fnt1);
		menu.setColor(Color.blue);
		menu.drawString("Play",100,200);
		menu.drawString("Settings",100,300);
		menu.drawString("Help",100,400);
		menu.setColor(Color.white);
		menu.drawString("(press ENTER to play)",100,600);

	}
	//Graphics to Display the Lose Screen
	private void dispLose(Graphics lose) {
		lose.setColor(Color.black);
		lose.fillRect(0,0,sizeX + sizeX/3,sizeY);
		Font fnt0 = new Font("arial", Font.BOLD, 100);
		lose.setFont(fnt0);
		lose.setColor(Color.red);
		lose.drawString("YOU LOSE",100,200);
		
	}
	//Graphics to Display the Win Screen
	private void dispWin(Graphics win) {
		win.setColor(Color.white);
		win.fillRect(0,0,sizeX + sizeX/3,sizeY);
		Font fnt0 = new Font("arial", Font.BOLD, 100);
		win.setFont(fnt0);
		win.setColor(Color.red);
		win.drawString("YOU WIN",100,200);
		
	}

	/**
	 * Changes the current state of game. Called by GameMain.
	 * @param currentState the State to be changed to
	 */
	public void stateChange(GameMain.GameState currentState) {
		this.currentState = currentState;
	}
	
	public void stateChangeDirection(GameMain.shootDirection currentDirection) {
		this.direction = currentDirection;
		
	}
	
	
	/**
	 * Displays a Board onto the game window.
	 * @param board the board object
	 * @param mainChar the main character object
	 * @param enemies the list of enemy objects
	 * @param objectiveRewards the list of objective reward objects
	 * @param punishments the list of punishment objects
	 * @param bonusRewards the list of bonus reward objects
	 * @param goalX the x coordinate of the goal
	 * @param goalY the y coordinate of the goal
	 * @param timePercentage a float representing how much of the "time remaining" bar should be filled
	 * @param score the score
	 */
	public void display(Board board, MainCharacter mainChar, ArrayList<Enemy> enemies, 
						ArrayList<ObjectiveReward> objectiveRewards, ArrayList<Punishment> punishments,
						ArrayList<BonusReward> bonusRewards, int goalX, int goalY, float timePercentage, int score) {
		
		this.cellSize = board.getCellSize();
		
		this.board = board;
		this.bonusRewards = bonusRewards;
		this.punishments = punishments;
		this.objectiveRewards = objectiveRewards;
		this.enemies = enemies;
		this.mainChar = mainChar;
		this.goalX = goalX; this.goalY = goalY;
		this.timePercentage = timePercentage;
		this.score = score;
		this.HP = mainChar.getHealth();
		
		gameWindow.add(this);
		repaint();
	}
	
	// TODO: add more methods for displaying other objects
	
	/**
	 * does something idk
	 */

	@Override
	public void paint(Graphics g) {
		Graphics menu = (Graphics2D) g;
		Graphics2D g2d = (Graphics2D) g;
		Graphics win = (Graphics2D) g;
		Graphics2D lose = (Graphics2D) g;

		if(currentState ==  GameMain.GameState.GAME) {

			dispGame(g2d);
		}
		else if(currentState == GameMain.GameState.MENU) {
			dispMenu(menu);
		}
		else if(currentState == GameMain.GameState.WIN) {
			dispWin(win);
		}
		else if(currentState == GameMain.GameState.LOSE) {
			dispLose(lose);
		}
		
        //call more disp funcs here
	}
	/*
	public static void main(String[] args) {
		
		Board b = new Board();
		MainCharacter mc = new MainCharacter(4, 3);
		ArrayList<Enemy> es = new ArrayList<Enemy>();
		ArrayList<ObjectiveReward> or = new ArrayList<ObjectiveReward>();
		ArrayList<Punishment> pn = new ArrayList<Punishment>();
		ArrayList<BonusReward> br = new ArrayList<BonusReward>();
		
		es.add(new Enemy(3, 12));
		es.add(new Enemy(17, 15));
		es.add(new Enemy(11, 14));

		or.add(new ObjectiveReward(20, 5, 50));
		or.add(new ObjectiveReward(3, 15, 50));
		
		pn.add(new Punishment(9, 8, 100));
		pn.add(new Punishment(12, 17, 100));
		
		br.add(new BonusReward(22, 17, 15, 50));
		br.add(new BonusReward(14, 1, 15, 50));
		
		for(int i = 0; i < b.getXSize(); i++) {
			b.setCellType(i, i%5, cellType.BARRIER);
		}
		
		//Sample board
		for(int x = 0; x < b.getXSize(); x ++) {
			b.setCellType(x, 0, cellType.BARRIER);
			b.setCellType(x, b.getYSize()-1, cellType.BARRIER);
			b.setCellType(x, (int)b.getYSize()/2, cellType.BARRIER);
		}
		for(int y = 0; y < b.getYSize(); y ++) {
			b.setCellType(0, y, cellType.BARRIER);
			b.setCellType(b.getXSize()-1, y, cellType.BARRIER);
		}
		b.setCellType(b.getXSize()/2, b.getYSize()/2, cellType.OPEN);
		b.setCellType(24, 11, cellType.OPEN);
		
		
		DisplayManager d = new DisplayManager(b.getXSize() * b.getCellSize(), b.getYSize() * b.getCellSize());
		d.stateChange(GameMain.GameState.GAME);
    	d.display(b, mc, es, or, pn, br, 24, 11, 0.76f, 5000);

	}
	*/
}
