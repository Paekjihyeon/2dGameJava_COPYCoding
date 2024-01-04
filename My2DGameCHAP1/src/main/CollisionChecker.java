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
		int entitiyLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY  = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY  = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
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
	
	//플레이어가 해당 오브젝트와 hit했는지 체크
	public int checkObject(Entity entity,boolean player) {
		
		int index = 999;
		
		for(int i=0;i<gp.obj.length;i++) {
			if(gp.obj[i] != null) {
				
				//entity의 solid area position 구하기 (x, y만)
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				//object의 solid area position 구하기
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				//intersects()함수를 사용하여 entity , object의 Rectangle이 교차하는지 판단 
				switch(entity.direction) {
				case "up": 
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("upInter");
						if(gp.obj[i].collision ==true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i; //플레이어가 아닌 캐릭터들은 오브젝트와 상호작용할 수 없음.
						}
					}
					break;
				case "down": 
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("downInter");
						if(gp.obj[i].collision ==true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i; 
						}
					}
					break;
				case "left": 
					entity.solidArea.x -= entity.speed; 
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("leftInter");
						if(gp.obj[i].collision ==true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i; 
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						System.out.println("rightInter");
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i; 
						}
					}
					break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		
			
		}
		
		return index;
	}

}
