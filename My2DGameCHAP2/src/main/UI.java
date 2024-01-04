package main;

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
	//DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.PLAIN, 80);
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
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80)); //deriveFont : 기존 폰트 객체에 새로운 옵션을 덮어씌우는거임.
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXforCenteredText(String text) {
		/*텍스트의 길이를 구함*/
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
}
