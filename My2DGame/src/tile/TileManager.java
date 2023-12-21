package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10]; //일단 10개로 크기 조정 
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		
		getTileImage();
		loadMap("/map/map01.txt");
	}
	
	//타일 이미지 로드
	 public void getTileImage() {
		 
		 try {
			 
			 tile[0] = new Tile();
			 tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			 tile[1] = new Tile();
			 tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			 tile[2] = new Tile();
			 tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			 
		 }catch(IOException e) {
			 e.printStackTrace();
			 //경우 1. 파일 이름을 잘못 입력했을 경우
		 }
	 }
	 
	 public void loadMap(String map) {
		 try {
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0; 
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				while(col < gp.maxScreenCol) {
					String number[] = line.split(" ");
					int num = Integer.parseInt(number[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
//				if(col==gp.maxScreenCol) {
					col = 0;
					row ++;
//				}

			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	 }
	 
	 
	 
	 public void draw(Graphics2D g2) {
		//이렇게 비효율적으로 코드를 짜는 것이 아님
//		 g2.drawImage(tile[1].image, 0, 0, gp.tileSize, gp.tileSize,null);
//		 g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize,null);
//		 g2.drawImage(tile[1].image, 96, 0, gp.tileSize, gp.tileSize,null);
//		 g2.drawImage(tile[1].image, 144, 0, gp.tileSize, gp.tileSize,null);
//		 g2.drawImage(tile[1].image, 192, 0, gp.tileSize, gp.tileSize,null);
//		 
		 int col = 0;
		 int row = 0;
		 int x = 0;
		 int y = 0;
		 
		 while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			 int tileNum = mapTileNum[col][row];
			 
			 g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize,null);
			 col++;
			 x += gp.tileSize; //48
			 
			 if(col == gp.maxScreenCol) {
				 col = 0;
				 x = 0;
				 row++;
				 y += gp.tileSize;
				 
			 }
		 }
		 
		 
	 }
}