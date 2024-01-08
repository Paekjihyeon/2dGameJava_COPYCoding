package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

//스크린 UI 상위 클래스 
public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40,arial_80B;
	public boolean messageOn = false;
	public String message = "";
	int messageCount = 0;
	public boolean gameFinished = false;
	
	double playTime;
	
	public String currentDialoue = "";
	
	
	
	/* font 객체를 미리 만들어 놓는것이 성능면에서 좋음 */
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("DungGeunMo", Font.PLAIN, 40);
		arial_80B = new Font("DungGeunMo", Font.PLAIN, 80);
	}
	
	public void showMessage(String text) {
	
		message = text;
		messageOn = true;
		
	}
	
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.playState) {
			//Do playState stuff later
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80)); //deriveFont : 기존 폰트 객체에 새로운 옵션을 덮어씌우는거임.
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		//WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialoue.split("\n")) {
			g2.drawString(line, x, y);
			y +=40;
		}
	}
	
	//다이아로그 창 만들기
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,220); // rgb
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // 테두리의 width를 정의함
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	
	public int getXforCenteredText(String text) {
		/*텍스트의 길이를 구함*/
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
}
