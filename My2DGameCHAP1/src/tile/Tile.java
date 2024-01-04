package tile;

import java.awt.image.BufferedImage;

//타일들의 대표 클래스
public class Tile {
	public BufferedImage image;
	
	//플레이어가 지나갈 수 있는 타일 지정 
	public boolean collision = false;
}
