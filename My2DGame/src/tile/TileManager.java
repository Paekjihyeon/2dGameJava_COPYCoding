package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10]; //일단 10개로 크기 조정 
		 mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/map/world01.txt");
	}
	
	//타일 이미지 로드
	 public void getTileImage() {
		 
			 /* 타일 draw 작업 */
			 setup(0,"grass", false);
			 setup(1,"wall", true);
			 setup(2,"water", true);
			 setup(3,"earth", false);
			 setup(4,"tree", true);
			 setup(5,"sand", false);
			 
			 /* 계속 scale배수 작업을 해줘야하기 때문에 위의 방법이 낫다.
			 tile[0] = new Tile();
			 tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			 
			 tile[1] = new Tile();
			 tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			 tile[1].collision= true; //진입불가
			 
			 tile[2] = new Tile();
			 tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			 tile[2].collision= true;
			 
			 tile[3] = new Tile();
			 tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			 
			 tile[4] = new Tile();
			 tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			 tile[4].collision= true;
			 
			 tile[5] = new Tile();
			 tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			 */
			 
	 }
	 
	 public void setup(int index, String imagePath, boolean collision) {
		 
		 /*타일을 그려주는 작업 */
		 UtilityTool uTool = new UtilityTool();
		 try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imagePath +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize); 
			tile[index].collision = collision;
			
		} catch (Exception e) {
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
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					String number[] = line.split(" ");
					int num = Integer.parseInt(number[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if(col==gp.maxWorldCol) {
					col = 0;
					row ++;
				}

			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	 }
	 
	 
	 
	 public void draw(Graphics2D g2) {
	 
		 int worldCol = 0;
		 int worldRow = 0;
		 
		 while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			 int tileNum = mapTileNum[worldCol][worldRow];
			 
			 int worldX = worldCol * gp.tileSize;
			 int worldY = worldRow * gp.tileSize;
			 int screenX = worldX - gp.player.worldX + gp.player.screenX; 
			 int screenY = worldY - gp.player.worldY + gp.player.screenY;
			 
			 //화면에 보이지 않는 맵의 영역까지 한번에 그리는 것은 좋은 랜더링 방식이 아니기 때문에 조건
			 if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				 
				 g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				
			 }
			 
			 worldCol++;
			 
			 if(worldCol == gp.maxWorldCol) {
				 worldCol = 0;
				 worldRow++;
				 
			 }
		 }
		 
		 
	 }
}
