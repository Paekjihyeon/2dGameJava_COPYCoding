package entitiy;

import java.awt.image.BufferedImage;

//플레이어, NPC의 가장 부모 클래스
public class Entity {
	
	public int worldX, worldY; //전체 월드맵에 대한 위치 (스크린에 대한 위치 아님)
	public int speed;
	
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter =0 ;
	public int spriteNum = 1;
}
