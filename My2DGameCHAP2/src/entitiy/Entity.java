package entitiy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

//플레이어, NPC의 가장 부모 클래스
public class Entity {
	GamePanel gp;
	
	public int worldX, worldY; //전체 월드맵에 대한 위치 (스크린에 대한 위치 아님)
	public int speed;
	
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter =0 ;
	public int spriteNum = 1;
	
	//캐릭터의 tile collision을 자정할 영역 (x, y, width, height)
	public Rectangle solidArea = new Rectangle(0,0,48,48); //기본값 설정
	public boolean collisionOn = false;
	
	//오브젝트 collision 관련
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	//action을 좀더 자연스럽게 하기 위함 
	public int actionLockCounter = 0;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	//이미지를 설정해주는 작업 
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
	try {
		image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
		image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return image;
	}
	
	/* 이미지를 그려주는 작업*/
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		 int screenX = worldX - gp.player.worldX + gp.player.screenX; 
		 int screenY = worldY - gp.player.worldY + gp.player.screenY;
		 
		 if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			 
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

	public void setAction() {}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		// tile collision 설정 , 방향에 따라 collision 식이 달라지므로 방향을 지정하고 해야함
		collisionOn = false;
		gp.cChecker.checkTile(this);  //collisonON값 달라짐
		gp.cChecker.checkObject(this , false);
		gp.cChecker.checkPlayer(this);
		
		/* check object collision
		int objIndex = gp.cChecker.checkObject(this, true); //collisonON값 달라질 수 있음
		pickUpObject(objIndex); */
		
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

