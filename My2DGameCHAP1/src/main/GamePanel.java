package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entitiy.Player;
import object.SuperObject;
import tile.TileManager;

//게임 스크린으로 동작함.
public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings
	// 16x16  => 아이템, 몬스터, 캐릭터등의 기본 사이즈. 
	final int originalTitleSize = 16; 
	// 16 *3 => 스크린에서 상대적인 크기를 조정하는 상수 (기본사이즈 배수) 
	final int scale = 3;  
	
	public final int tileSize = originalTitleSize * scale;
	
	/*WORLD SETTING*/
	//게임사이즈를 정할 떄는 가로세로를 둘다 생각해야함.
	public final int maxScreenCol = 16; //타일
	public final int maxScreenRow = 12; //타일
	public final int screenWidth = tileSize * maxScreenCol; //768 pixel
	public final int screenHeight = tileSize * maxScreenRow; //576 pixel	 
	//월드 크기
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	
	
	//FPS - 화면 업데이트 주기 설정
	int FPS = 60;
	
	/*SYSTEM*/
	//게임을 종료하기 전에 지속할 수 있게함
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Sound music = new Sound(); // 배경음 
	Sound se = new Sound(); //효과음
	public UI ui = new UI(this); 
	Thread gameThread;
	
	
	
	/*ENTITY AND OBJECT*/
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; 
	
	// GAME STATE
	public int gameState;
	public final int playState = 1; 	// "게임플레이 상태" 상태값 상수로 표현
	public final int pauseState = 2;	// "일시정지 상태" 상태값을 상수로 표현

	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		//게임 랜더링 성능을 향상
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH); //키보드 움직임을 인지 
		this.setFocusable(true); //입력된 값에 포커스 할 수 있도록 함.
	}
	
	//게임 사전준비 메소드
	public void setupGame() {
		aSetter.setObject(); // 오브젝트 설정 
		playMusic(0);
		stopMusic();
		
		gameState = playState;
		
		
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
		
		if(gameState == playState) {
			player.update();	
		}
		if(gameState == pauseState) {
			
		}
		
	}
	
	
	//게임을 그리는 함수. 플레이어, 타일 등 포함
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  
		
		Graphics2D g2 = (Graphics2D) g;
		
		
		//DEBUG 
		long drawStart = 0;
		if(keyH.checkDrawTime==true) {
			drawStart = System.nanoTime();
		}
		
		
		//이때 요소를 그리는 순서 중요 
		// ex. 플레이어를 먼저 그린다면 타일이 플레이어 위로 가있음.
		tileM.draw(g2);
		
		for(int i=0;i<obj.length;i++) {
			if(obj[i]!=null) {
				obj[i].draw(g2, this);
			}
		}
		
		player.draw(g2);
		
		// UI가 가장 상단에 위치하도록 가장 마지막에 draw
		ui.draw(g2);
		
		
		//DEBUG
		
		if(keyH.checkDrawTime==true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw time : " + passed, 10, 400); //게임을 그리는데 걸리는 시간 (게임 성능  측정)
			
			System.out.println("Draw time : " + passed);
		}
		
		g2.dispose(); //그래픽들이 쌓이는 것을 방지
				
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
		
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
		
	}
	
