package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

//오브젝트들의 가장상위 클래스
public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48); //object collision 관련
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		/* <랜더링 범위에 맞춰 오브젝트를 그림> */
		 int screenX = worldX - gp.player.worldX + gp.player.screenX; 
		 int screenY = worldY - gp.player.worldY + gp.player.screenY;
		 
		 if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			 
			 g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
		 }
		 /* </ 랜더링 범위에 맞춰 오브젝트를 그림> */
	}
}
