package com.Group12.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageData {

	private BufferedImage playerImg;
	private BufferedImage enemyImg;
	private BufferedImage punishmentImg;
	private BufferedImage objectImg;
	private BufferedImage goalImg;
	private BufferedImage bonusImg;
	
	public ImageData() {
		try {
			System.out.println(System.getProperty("user.dir"));
            playerImg = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/player.png"));
            enemyImg = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/enemy.png"));
            objectImg = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/key.png"));
            punishmentImg = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/pul.png"));
            goalImg = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/goal.png"));
            bonusImg = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/bonus.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getPlayerImg() {
		return this.playerImg;
	}
	
	public BufferedImage getEnemyImg() {
		return this.enemyImg;
	}
	
	public BufferedImage getPunishmentImg() {
		return this.punishmentImg;
	}
	
	public BufferedImage getObjectImg() {
		return this.objectImg;
	}
	
	public BufferedImage getGoalImg() {
		return this.goalImg;
	}
	
	public BufferedImage getBonusImg() {
		return this.bonusImg;
	}
}
