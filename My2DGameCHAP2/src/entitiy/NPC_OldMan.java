package entitiy;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	
	}
	
	public void setDialogue() {
		
		dialogues[0] = "반갑군 소년";
		dialogues[1] = "이 섬에 보물을 찾으러 온거냐?";
		dialogues[2] = "난 강한 마법사였지만... \n이제 모험하기엔 늙었군";
		dialogues[3] = "행운을 빈다";
		
	}
	
	public void getImage() {
	
		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1");
		down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1");
		right2 = setup("/npc/oldman_right_2");
	
		}
	

	/* npc의 행동설정*/
	@Override
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1; 
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i>50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction  = "right";
			}
			actionLockCounter = 0;
		}
		
	}
	
}
