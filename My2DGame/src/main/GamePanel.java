package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entitiy.Player;
import tile.TileManager;

//게임 스크린으로 동작함.
public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings
	// 16x16  => 아이템, 몬스터, 캐릭터등의 기본 사이즈. 
	final int originalTitleSize = 16; 
	// 16 *3 => 스크린에서 상대적인 크기를 조정하는 상수 (기본사이즈 배수) 
	final int scale = 3;  
	
	public final int tileSize = originalTitleSize * scale;
	
	//게임사이즈를 정할 떄는 가로세로를 둘다 생각해야함.
	public final int maxScreenCol = 16; //타일
	public final int maxScreenRow = 12; //타일
	public final int screenWidth = tileSize * maxScreenCol; //768 pixel
	public final int screenHeight = tileSize * maxScreenRow; //576 pixel
	
	//FPS - 화면 업데이트 주기 설정
	int FPS = 60;
	
	//게임을 종료하기 전에 지속할 수 있게함
	Thread gameThread;
	
	KeyHandler keyH = new KeyHandler();
	
	public Player player = new Player(this, keyH);
	
	TileManager tileM = new TileManager(this);
	
	
	// 월드 세팅	 
		//1.월드 크기
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		//게임 랜더링 성능을 향상
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH); //키보드 움직임을 인지 
		this.setFocusable(true); //입력된 값에 포커스 할 수 있도록 함.
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	};
	
	//게임 루프를 실행
	@Override
	public void run() {
		//루프의 실행간격을 지정해주지 않으면 너무 빠른 움직임으로 인식해버림.
		//게임 업데이트 주기를 설정하는 법1 -이거도 60fps임 
		double drawIntervel = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;
		
		while(gameThread!=null) {
			currentTime = System.nanoTime();
			delta += (currentTime-lastTime)/drawIntervel;
			timer += (currentTime-lastTime);
			lastTime = currentTime;
			
			if(delta >=1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >=1000000000) {
				System.out.println("PPS:" +  drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		
		
		//게임업데이트 주기를 설정하는 법 2
//		double drawIntervel = 1000000000/FPS;  //1분에 60
//		double nextDrawTime = System.nanoTime() + drawIntervel; 
		
		
		
		//게임 실행하는 동안 계속 진행 
//		while(gameThread!=null){
////			System.out.println("Game is running");
//			
//		
//			//1 UPDATE : update information such as character positions -키보드 등으로 이동되는 위치 적용.
//			update();
//			
//			//update된 결과에 따라 게임 스크린을 다시 그림 
//			// 2 DRAW : draw the screen with the update information ->  
//			repaint();
//			
//			
////			업데이트 사이 쉬는 시간 
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				//sleep method가 millisecond를 지원헤서 변환하는 작업 (나노->밀리)
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				remainingTime = remainingTime/1000000; 
//				Thread.sleep((long)remainingTime);
//				
//				nextDrawTime +=drawIntervel;
//				
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
	}
	
	public void update() {

		player.update();
		
	}
	
	
	//게임을 그리는 함수. 플레이어, 타일 등 포함
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  
		
		Graphics2D g2 = (Graphics2D) g;
		
		
		//이때 요소를 그리는 순서 중요 
		// ex. 플레이어를 먼저 그린다면 타일이 플레이어 위로 가있음.
		tileM.draw(g2);
		player.draw(g2);
		
		
		g2.dispose(); //그래픽들이 쌓이는 것을 방지
				
				
	}
		
		
	}
	
