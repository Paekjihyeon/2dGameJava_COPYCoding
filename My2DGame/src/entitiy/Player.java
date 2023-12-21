package entitiy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
		public Player(GamePanel gp, KeyHandler keyH) {
			
			this.gp = gp;
			this.keyH = keyH;
			
			setDefaultValues();
			getPlayerImage();
		}
		
		public void getPlayerImage() {
			try {
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_back_1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_back_2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_front_1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_front_2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_stand.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_walk.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_stand.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_walk.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void setDefaultValues() {
			
			x = 100;
			y = 100;
			speed = 4;
			//기본 방향
			direction = "down";
		}
		
		public void update() {
			
		
			if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {	
				//가만히 있을때는 이미지가 변경되지 않게 한 것임. 키를 누르지 않아도 화면은 업데이트 되므로, 키를 누를때만 동적으로 이미지 변경되게
				
				
				if(keyH.upPressed == true) {
					direction = "up";
					y -= speed;
				}else if(keyH.downPressed == true) {
					direction = "down";
					y += speed;
				}else if(keyH.leftPressed ==true) {
					direction = "left";
					x -= speed;
				}else if(keyH.rightPressed==true) {
					direction = "right";
					x += speed;
				}
				
				//화면 업데이트 10번당 동적으로 이미지 변경
				spriteCounter ++;
				if(spriteCounter>10) {
					if(spriteNum==1) {
						spriteNum =2;
					}else if(spriteNum==2) {
						spriteNum = 1;
					}
					
					spriteCounter = 0;
				}
			}
		}
		
		public void draw(Graphics2D g2){
			
//			g2.setColor(Color.white);
//			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			
			BufferedImage image = null;
			
			switch(direction) {
			case "up":
				if(spriteNum == 1 ) {
					image = up1;
				}
				if(spriteNum == 2 ) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1 ) {
					image = down1;
				}
				if(spriteNum == 2 ) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1 ) {
					image = left1;
				}
				if(spriteNum == 2 ) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1 ) {
					image = right1;
				}
				if(spriteNum == 2 ) {
					image = right2;
				}
				break;
			}
			
			g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
			
		}
}