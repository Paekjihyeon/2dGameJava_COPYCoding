package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		 //타일안에 scaled이미지를 넣음. 바로 이미지를 화면에 그림
		 BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		 Graphics2D g2 = scaledImage.createGraphics(); //buffered된 곳에 이미지를 그릴 수 있음.
		 g2.drawImage(original,0,0, width, height,null);
		 g2.dispose();
		 
		 return scaledImage;
	}
}
