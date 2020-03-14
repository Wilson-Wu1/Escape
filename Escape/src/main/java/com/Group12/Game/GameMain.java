package com.Group12.Game;
import java.io.*;
import java.util.Scanner; 

/**
 * Handles the game's logic.
 * @author Nicholas, Daniel, Wilson, Yuxi
 *
 */
public class GameMain {
	
	// Attributes
	
	private static GameMain gameMain;
	
	private Enemy[] enemies;
	private Collectible[] collectibles;
	private MainCharacter[] mainChar;
	private Board board;
	
	private int objectivesRemaining;
	private StateType gameState;
	private int score;
	private TickTimer tick;
	
	// Methods
	
	private GameMain() {
		enemies = new Enemy[];
		collectibles = new Collectible[];
		mainChar = new MainCharacter;
		board = new Board;
		
		objectivesRemaining = 4;
		//gameState = Menu;		//TODO: once the enumeration is finalized
		score = 0;
		tick = new TickTimer;
	}
	
	/**
	 * This method returns the single instance of GameMain
	 * if it exists, otherwise it creates a new instance.
	 * @return GameMain	This returns the instance of GameMain.
	 */
	public static GameMain getInstance() {
		if(gameMain == null) {
			gameMain = new GameMain();
		}
		return gameMain;
	}
	
	public void startGame() {
		makeBoard("map1");		//TODO: don't hardcode + decide on proper file location
	}
	
	public void update() {
		
	}
	
	private void updatePlayer() {
		
	}
	
	private void updateEnemies() {
		
	}
	
	private void updateDisplay() {
		
	}
	
	/**
	 * This method reads from a file and uses it to initialize
	 * board, enemies, collectibles, and mainChar.
	 * @param x This is the name of the file that makeBoard reads from.
	 */
	private void makeBoard(String x) {
		// file format: xDim yDim
		// remaining yDim lines of xDim characters: board layout & entity placement
		File file = new File(x);
		Scanner sc = new Scanner(file);
		int xSize, ySize;
		xSize = sc.nextInt();
		ySize = sc.nextInt();
		board.setXSize(xSize);		//TODO: tell Daniel board either must be dynamic or accept dims in constructor
		board.setYSize(ySize);
		
		/* characters in file:
		 * 	. = Open
		 * 	# = Barrier	TODO: tell Daniel celltype is Barrier in the UML Diagram
		 * all other characters also on Open tiles
		 * 	@ = MainCharacter
		 * 	G = Goal
		 * 	E = Enemy
		 * 	P = Punishment
		 * 	O = ObjectiveReward
		 * 	W = WeaponCollectible
		 */
		
		
		
		sc.close();
	}


}
