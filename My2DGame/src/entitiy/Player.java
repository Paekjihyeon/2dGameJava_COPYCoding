package entitiy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
//	카메라 관련. 플레이어가 화면의 어느 위치에 있는가.
	public final int screenX;
	public final int screenY;
	
		public Player(GamePanel gp, KeyHandler keyH) {
			
			this.gp = gp;
			this.keyH = keyH;
			
			// "점"의 관점에선 중심이지만 "타일"의 관점에선 중앙이 아니어서 타일사이즈/2만큼 빼줌
			screenX = gp.screenWidth/2 - (gp.tileSize/2); 
			screenY = gp.screenHeight/2 - (gp.tileSize/2);
			
			//플레이어 크기에 대비하여 설정해주면됨.
			soildArea = new Rectangle();
			soildArea.x = 8;
			soildArea.y =16;
			soildArea.width = 32;
			soildArea.height = 32;
			
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
			
			//플레이어의 기본 위치
			worldX = gp.tileSize * 23; // 게임 타일 크기 * 월드맵 x축 위치
			worldY = gp.tileSize * 21; 
			speed = 4;
			//기본 방향
			direction = "down";
		}
		
		public void update() {
			
		
			if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {	
				//가만히 있을때는 이미지가 변경되지 않게 한 것임. 키를 누르지 않아도 화면은 업데이트 되므로, 키를 누를때만 동적으로 이미지 변경되게

				if(keyH.upPressed == true) {
					direction = "up";	
				}else if(keyH.downPressed == true) {
					direction = "down";
				}else if(keyH.leftPressed ==true) {
					direction = "left";
				}else if(keyH.rightPressed==true) {
					direction = "right";
				}
				
				
				// tile collision 설정 , 방향에 따라 collistion 식이 달라지므로 방향을 지정하고 해야함
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				//충돌대상 타일이 아닐때만 움직일 수 있도록 
				if(collisionOn == false) {
					switch(direction) {
					case "up" : worldY -= speed; break;
					case "down" : worldY += speed; break;
					case "left" : worldX -= speed; break;
					case "right" : worldX += speed; break;
					}
				 }
				
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
				
				//화면 업데이트 10번당 동적으로 이미지 변경

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
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
			
		}
}
