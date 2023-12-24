package main;

import entitiy.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	//플레이어 뿐 아닌 다른 오브젝트들도 적용하기 위해 Entity로 적용
	public void checkTile(Entity entity) {
		//충돌이 일어났는지 체크하기 위해 왼X, 오X , 아래Y, 위Y체크
		int entitiyLeftWorldX = entity.worldX + entity.soildArea.x;
		int entityRightWorldX = entity.worldX + entity.soildArea.x + entity.soildArea.width;
		int entityTopWorldY  = entity.worldY + entity.soildArea.y;
		int entityBottomWorldY  = entity.worldY + entity.soildArea.y + entity.soildArea.height;
		
		int entityLeftCol = entitiyLeftWorldX/gp.tileSize;  //왼쪽 타일
		int entityRightCol = entityRightWorldX/gp.tileSize; //오른쪽 타일
		int entityTopRow = entityTopWorldY/gp.tileSize; 	//위 타일
		int entityBottomRow = entityBottomWorldY/gp.tileSize; //아래 타일
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up" :
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; //양 쪽 타일 검사 
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true) {
				entity.collisionOn = true;
			}
			
			break;
		case "down" : 
			entityBottomRow = (entityBottomWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; //양 쪽 타일 검사 
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true) {
				entity.collisionOn = true;
			}
			break;
		case "left" :
			entityLeftCol = (entitiyLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; //양 쪽 타일 검사 
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true) {
				entity.collisionOn = true;
			}
			break;
		case "right" :
			entityRightCol = (entityRightWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; //양 쪽 타일 검사 
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true) {
				entity.collisionOn = true;
			}
			break;
		}
	}

}
