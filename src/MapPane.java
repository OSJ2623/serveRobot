import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.TimerTask;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.Point;
import javax.swing.JPanel;

public class MapPane extends JPanel implements ActionListener{
	public static Dijkstra dj = new Dijkstra();
	final int PANEL_WIDTH = 600;
    final int PANEL_HEIGHT = 580;
    
    Image map, robot_front1, robot_back1, robot_front2, robot_back2;
    
    javax.swing.Timer timer;
    java.util.Timer timerUtil;
    TimerTask task1;
    TimerTask task2;
    TimerTask taskStop;
    
    int xVel = 1, yVel = 1;
    
    int x1 = 400, y1 = 80, x2 = 400, y2 = 80;
    
    int robot;
    
    Queue<Point> way1 = new LinkedList<Point>();
    Queue<Point> way2 = new LinkedList<Point>();
    
	Point p1 = null;
	Point p2 = null;
	
    int flag1 = 0, flag2 = 0;
    
    int[] temp1 = {400, 80}, temp2 = {400, 80};
	
    int[] dest1, dest2;
    
    /*
    public void stopTask(int num) {
    	taskStop = new TimerTask() {
    		@Override
    		public void run() {
    	    	timerUtil = new java.util.Timer();
    	    	
    		}
    	};

    	if(num == 1 && way1.isEmpty() == true) {
    		tempTask(1);
    	}
    	else if(num == 2 && way2.isEmpty() == true) {
    		tempTask(2);
    	}
    }
    */
    public void tempTask(int num) {
    	int[] robot1 = {0,0}, robot2 = {0,0};
        robot1[0] = temp1[0];
        robot1[1] = temp1[1];
        robot2[0] = temp2[0];
        robot2[1] = temp2[1];
        task1 = new TimerTask() {
        	@Override
        	public void run() {
        		if(way1.isEmpty() == true) {
	        		dj.init(1, robot1, robot2, 1);
	        		int[][] position = { {0,}};
	        		position = dj.list_result();
	        		for(int i = 0; i < position.length; i++) {
	        			if(position[i][0] != 0 && position[i][1] != 0) {
	        				way1.add(new Point(position[i][0], position[i][1]));
	        				temp1[0] = position[i][0];
	        				temp1[1] = position[i][1];
	        			}
	        		}
	        	}
        	}
        };
        task2 = new TimerTask() {
        	@Override
        	public void run() {
        		if(way2.isEmpty() == true) {
	        		dj.init(1, robot1, robot2, 2);
	        		int[][] position = { {0,}};
	        		position = dj.list_result();
	        		for(int i = 0; i < position.length; i++) {
	        			if(position[i][0] != 0 && position[i][1] != 0) {
	        				way2.add(new Point(position[i][0], position[i][1]));
	        				temp2[0] = position[i][0];
	        				temp2[1] = position[i][1];
	        			}
	        		}
        		}
        	}
        };
    	timerUtil = new java.util.Timer();
    	if(num == 1) {
    		timerUtil.schedule(task1, 3000);
    	}
    	else if(num==2) {
    		timerUtil.schedule(task2, 3000);
    	}
    }
    
    public MapPane() {
        initComponents();
        robot_front1 = new ImageIcon("./images/robot1.png").getImage();
        
        x1 = x1 - robot_front1.getWidth(null)/2;
        y1 = y1 - robot_front1.getHeight(null)/2;
//        System.out.println("dd" + x1 + y1);
        
        robot_back1 = new ImageIcon("./images/robot_back.png").getImage();
        
        robot_front2 = new ImageIcon("./images/robot2.png").getImage();
        
        x2 = x2 - robot_front2.getWidth(null)/2;
        y2 = y2 - robot_front2.getHeight(null)/2;
        
        robot_back1 = new ImageIcon("./images/robot_back.png").getImage();
        
        int[] robot1 = {0,0}, robot2 = {0,0};
        robot1[0] = temp1[0];
        robot1[1] = temp1[1];
        robot2[0] = temp2[0];
        robot2[1] = temp2[1];
        timer = new Timer(10, this);
        timerUtil = new java.util.Timer();
        
        
        timer.start();
    }
    
    public void setRobot(int robot, int[][] position, int[] dest) {
    	//濡쒕큸�쓣 �씠�룞�떆�궡
    	//robot : ��吏곸뿬�빞 �븯�뒗 濡쒕큸
    	//position : �씠�룞 寃쎈줈 (醫뚰몴濡�)
    	//dest : 理쒖쥌 紐⑹쟻吏� �젙蹂� (醫뚰몴濡�)
    	this.robot = robot;
    	
    	if(robot == 1) {
    		//this.flag1 = 1;
    		this.dest1 = dest;
    		for(int i = 0; i < position.length; i++) {
    			if(position[i][0] != 0 && position[i][1] != 0) { 
	    			this.way1.add(new Point(position[i][0], position[i][1]));
	    			this.temp1[0] = position[i][0];
	    			this.temp1[1] = position[i][1];
//	    			System.out.println("mapPane1 : " + position[i][0] +"," + position[i][1]);
//	    			System.out.println("destination1 : " + dest1[0] +","+dest1[1]);
    			}
    		}
    	}
    	
    	if(robot == 2) {
    		//this.flag2 = 1;
    		this.dest2 = dest;
    		for(int i = 0; i < position.length; i++) {
    			if(position[i][0] != 0 && position[i][1] != 0) { 
	    			this.way2.add(new Point(position[i][0], position[i][1]));
	    			this.temp2[0] = position[i][0];
	    			this.temp2[1] = position[i][1];
//	    			System.out.println("mapPane2 : " + position[i][0] +"," + position[i][1]);
//	    			System.out.println("destination2 : " + dest2[0] +","+dest2[1]);
    			}
    			//System.out.println("mapPane : " + temp2[0] +"," + temp2[1]);
    		}
    	}
    }
    
    public int[] getInfo(int robot) {
    	//濡쒕큸 �씠�룞寃쎈줈�쓽 留� �뮮 醫뚰몴 諛섑솚
    	//robot : �뼸怨좎떢�� 濡쒕큸�쓽 踰덊샇
    	int[] coordinate = new int[2];
    	
    	if(robot == 1) {
    		coordinate[0] = temp1[0];
    		coordinate[1] = temp1[1];
    	}
    	
    	if(robot == 2) {
    		coordinate[0] = temp2[0];
    		coordinate[1] = temp2[1];
    	}
    	
		return coordinate;
    }
    
    public int isFree() {
    	//�뼱�뼡 濡쒕큸�씠 �씪 �븞�븯怨� �엳�뒗吏� 諛섑솚
    	if(way1.isEmpty() && !way2.isEmpty()) return 1; //1이 일을 안한다
    	else if(way2.isEmpty() && !way1.isEmpty()) return 2; //2가 일을 안한다
    	else if(way2.isEmpty() && way1.isEmpty()) return 3; //濡쒕큸 �몮�떎 �씪�씠 諛곗젙�릺吏� �븡�븯�쑝硫�
    	else return 0; //�몮�떎 �씪�씠 諛곗젙�릺�뼱 �엳�쑝硫�
    }
    
    public void paint(Graphics g){
    	super.paint(g);
//    	System.out.println(way1.isEmpty());
//    	System.out.println(way2.isEmpty());
    	//mapPane 크占쏙옙
    	//g.setColor(Color.LIGHT_GRAY);
    	//g.drawRect(0, 0, getWidth(), getHeight());
    	
    	//占싱뱄옙占쏙옙 占쏙옙載� 占쏙옙치 확占쏙옙
//    	g.setColor(Color.WHITE);
//    	g.fillRect(0, 0, 600, 580);
//    	g.setColor(Color.BLACK);
//    	g.drawRect(0, 0, 600, 579);
    	
    	//占쏙옙 占싱뱄옙占쏙옙 (600*580)
//    	java.awt.Image map = Toolkit.getDefaultToolkit().getImage("./images/map.png");
//    	g.drawImage(map, 0, 0, 600, 580, this);
    	
    	Graphics2D g2D = (Graphics2D) g;
    	g2D.drawImage(robot_front1, x1, y1, null);
    	
    	g2D.drawImage(robot_front2, x2, y2, null);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
    	int[] robot1 = {0,0}, robot2 = {0,0};
        robot1[0] = temp1[0];
        robot1[1] = temp1[1];
        robot2[0] = temp2[0];
        robot2[1] = temp2[1];
    	/*
    	if(x > 600-robot_front1.getWidth(null) || x<0) {
    		
    	}
		x1 = x1+xVel;
		
		if(y > 580-robot_front1.getHeight(null) || y<0) {
    		
    	}
		y = y+yVel;
		*/
//    	System.out.println("占쏙옙占쏙옙占쏙옙: "+ temp1[0] + "," + temp1[1]);
    	/*
    	System.out.println("flag1 :" + flag1 +"flag2 : " + flag2);
    	System.out.println("temp1 :" + temp1[0] + "," + temp1[1]);
    	System.out.println("temp2 :" + temp2[0] + "," + temp2[1]);
    	*/
    	if(flag1 == 0) {
        	p1 = way1.poll();
    	}
    	
    	if(flag2 == 0) {
    		p2 = way2.poll();
    	}
    	
//    	System.out.println(p1);
    	
		if(p1 != null) {
//			System.out.println("占쏙옙占쏙옙x: " + p1.x + ", 占쏙옙占쏙옙x: " + (x1+robot_front1.getWidth(null)/2)
//					+ ",占쏙옙占쏙옙 y: " + p1.y +", 占쏙옙占쏙옙 y: " +(y1+robot_front1.getHeight(null)/2));
			
			if(p1.x != (x1+robot_front1.getWidth(null)/2)) { //占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙치占쏙옙 x占쏙옙표占쏙옙 占쏙옙占쏙옙 占십다몌옙
				flag1 = 1;
				
				if((y1+robot_front1.getHeight(null)/2) == 80 ||
						(y1+robot_front1.getHeight(null)/2) == 380) {
					if(p1.x > (x1+robot_front1.getWidth(null)/2)) {
						x1=x1+xVel;
					}
					
					else if(p1.x < (x1+robot_front1.getWidth(null)/2)) {
						x1=x1-xVel;
					}
				}
			}
			if(p1.y != (y1+robot_front1.getHeight(null)/2)) {
				flag1 = 1;
				
				if(p1.y > (y1+robot_front1.getHeight(null)/2))
					y1 = y1+yVel;
				
				else if(p1.y < (y1+robot_front1.getHeight(null)/2))
					y1 = y1-yVel;
			}
			if(p1.x == (x1+robot_front1.getWidth(null)/2) && p1.y == (y1+robot_front1.getHeight(null)/2)) {
				flag1 = 0;
//				System.out.println("flag1 변경 :" + flag1);
			}
			if(dest1[0] == (x1+robot_front1.getWidth(null)/2) && dest1[1] == (y1+robot_front1.getHeight(null)/2)) {
				// robot1 arrived to destination.
				System.out.println("도착");
				// what is done?
				if (!(dest1[0] == 200 && dest1[1] == 80) && MainFrame.robot_doing[0].equals("setting")) {	// destination is not settingBar
					MainFrame.isSettingDone[MainFrame.robot_table[0] - 1] = true;	// setting done
					MapPane.table[MainFrame.robot_table[0] - 1].repaint();	// repaint the table
					MainFrame.robot_doing[0] = "";	// reset
					MainFrame.robot_table[0] = -1;
					System.out.println("setting done");
				}
				else if (!(dest1[0] == 400 && dest1[1] == 80) && MainFrame.robot_doing[0].equals("serving")) {	// destination is not kitchen
					MainFrame.isServingDone[MainFrame.robot_table[0] - 1] = true;	// serving done
					MapPane.table[MainFrame.robot_table[0] - 1].repaint();	// repaint the table
					MainFrame.robot_doing[0] = "";	// reset
					MainFrame.robot_table[0] = -1;
					System.out.println("serving done");
				}
				else if (MainFrame.robot_doing[0].equals("clean")) {
					MainFrame.table_state[MainFrame.robot_table[0] - 1] = 0;	// guest can come
					MainFrame.haveToClean[MainFrame.robot_table[0] - 1] = false;	// reset
					MapPane.table[MainFrame.robot_table[0] - 1].repaint();	// repaint the table
					MapPane.state[MainFrame.robot_table[0] - 1].setText("");	// reset
					MainFrame.isSettingDone[MainFrame.robot_table[0] - 1] = false;
					MainFrame.isServingDone[MainFrame.robot_table[0] - 1] = false;
					MainFrame.robot_doing[0] = "";
					MainFrame.robot_table[0] = -1;
					System.out.println("clean done");
				}	
				else if (!(dest1[0] == 400 && dest1[1] == 80) && MainFrame.robot_doing[0].equals("refull")) {		// destination is not kitchen
					Queueing.dish = 5;	//	refull
					MainFrame.robot_doing[0] = "";	// reset
					MainFrame.robot_table[0] = -1;
					System.out.println("refull done");
				}
				
				tempTask(1);
			}
		}
		else {
			
		}
		
		if(p2 != null) {
			
//			System.out.println("占쏙옙占쏙옙x: " + p2.x + ", 占쏙옙占쏙옙x: " + (x2+robot_front1.getWidth(null)/2)
//					+ ",占쏙옙占쏙옙 y: " + p2.y +", 占쏙옙占쏙옙 y: " +(y2+robot_front1.getHeight(null)/2));
			
			if(p2.x != (x2+robot_front1.getWidth(null)/2)) { //占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙치占쏙옙 x占쏙옙표占쏙옙 占쏙옙占쏙옙 占십다몌옙
				flag2 = 1;
				
				if((y2+robot_front1.getHeight(null)/2) == 80 ||
						(y2+robot_front1.getHeight(null)/2) == 380) {
					if(p2.x > (x2+robot_front1.getWidth(null)/2)) {
						x2=x2+xVel;
					}
					
					else if(p2.x < (x2+robot_front1.getWidth(null)/2)) {
						x2=x2-xVel;
					}
				}
			}
			if(p2.y != (y2+robot_front1.getHeight(null)/2)) {
				flag2 = 1;
				if(p2.y > (y2+robot_front1.getHeight(null)/2))
					y2 = y2+yVel;
				
				else if(p2.y < (y2+robot_front1.getHeight(null)/2))
					y2 = y2-yVel;
			}
			if(p2.x == (x2+robot_front1.getWidth(null)/2) && p2.y == (y2+robot_front1.getHeight(null)/2)) {
//				System.out.println("flag2 변경" + flag2);
				flag2 = 0;
			}
			if(dest2[0] == (x2+robot_front1.getWidth(null)/2) && dest2[1] == (y2+robot_front1.getHeight(null)/2)) {
				// robot2 arrived to destination.
				
				// what is done?
				if (!(dest2[0] == 200 && dest2[1] == 80) && MainFrame.robot_doing[1].equals("setting")) {	// destination is not settingBar
					MainFrame.isSettingDone[MainFrame.robot_table[1] - 1] = true;	// setting done
					MapPane.table[MainFrame.robot_table[1] - 1].repaint();	// repaint the table
					MainFrame.robot_doing[1] = "";	// reset
					MainFrame.robot_table[1] = -1;
				}
				else if (!(dest2[0] == 400 && dest2[1] == 80) && MainFrame.robot_doing[1].equals("serving")) {	// destination is not kitchen
					MainFrame.isServingDone[MainFrame.robot_table[1] - 1] = true;	// serving done
					MapPane.table[MainFrame.robot_table[1] - 1].repaint();	// repaint the table
					MainFrame.robot_doing[1] = "";	// reset
					MainFrame.robot_table[1] = -1;
				}
				else if (MainFrame.robot_doing[1].equals("clean")) {
					MainFrame.table_state[MainFrame.robot_table[1] - 1] = 0;	// guest can come
					MainFrame.haveToClean[MainFrame.robot_table[1] - 1] = false;	// reset
					MapPane.table[MainFrame.robot_table[1] - 1].repaint();	// repaint the table
					MapPane.state[MainFrame.robot_table[1] - 1].setText("");	// reset
					MainFrame.isSettingDone[MainFrame.robot_table[1] - 1] = false;
					MainFrame.isServingDone[MainFrame.robot_table[1] - 1] = false;
					MainFrame.robot_doing[1] = "";
					MainFrame.robot_table[1] = -1;
				}else if (!(dest2[0] == 400 && dest2[1] == 80) && MainFrame.robot_doing[1].equals("refull")) {		// destination is not kitchen
					Queueing.dish = 5;	//	refull
					MainFrame.robot_doing[1] = "";	// reset
					MainFrame.robot_table[1] = -1;
				}
				
				
				tempTask(2);
			}
		}
		repaint();
	}
    
    private void initComponents() {
    	// for GUI

    	serveBar = new JPanel();
    	kitchen = new JPanel();
    	tableArea = new JPanel[6];
    	for (int i = 0; i < 6; i++) {
    		tableArea[i] = new JPanel();
    		tableArea[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    		tableArea[i].setBackground(Color.WHITE);
    	}
    	serveBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    	kitchen.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    	serveBar.setBackground(Color.WHITE);
    	kitchen.setBackground(Color.WHITE);
    	
    	table = new TablePanel[6];
    	name = new JLabel[6];
    	state = new JLabel[6];
    	for (int i = 0; i < 6; i++) {
    		table[i] = new TablePanel(i);
    		table[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
    		table[i].setBackground(Color.WHITE);
    		name[i] = new JLabel(" table " + String.valueOf(i+1));
    		state[i] = new JLabel("");
    	}
    	
    	GroupLayout[] t = new GroupLayout[6];
    	for (int i = 0; i < 6; i++) {
    		t[i] = new GroupLayout(tableArea[i]);
    		tableArea[i].setLayout(t[i]);
    		t[i].setHorizontalGroup(
    				t[i].createParallelGroup()
        			.addComponent(name[i], 120, 120, 120)
        			.addComponent(table[i], 118, 118, 118)
        			.addComponent(state[i], 120, 120, 120)
        	);
    		t[i].setVerticalGroup(
    				t[i].createSequentialGroup()
        			.addComponent(name[i], 30, 30, 30)
        			.addComponent(table[i], 60, 60, 60)
        			.addComponent(state[i], 30, 30, 30)
            );
    	}
    	
    	
    	
    	setBackground(Color.WHITE);
    	GroupLayout layout = new GroupLayout(this);
    	this.setLayout(layout);
    	layout.setHorizontalGroup(
    			layout.createParallelGroup()
    			.addGroup(layout.createSequentialGroup()
    					.addGap(140, 140, 140)
    					.addComponent(serveBar, 120, 120, 120)
    					.addGap(80, 80, 80)
    					.addComponent(kitchen, 120, 120, 120))
    			.addGroup(layout.createSequentialGroup()
    	    			.addGap(40, 40, 40)
    	    			.addGroup(layout.createParallelGroup()
    	    					.addComponent(tableArea[0], 120, 120, 120)
    	    					.addComponent(tableArea[3], 120, 120, 120))
    	    			.addGap(80, 80, 80)
    	    			.addGroup(layout.createParallelGroup()
    	    					.addComponent(tableArea[1], 120, 120, 120)
    	    					.addComponent(tableArea[4], 120, 120, 120))
    	    			.addGap(80, 80, 80)
    	    			.addGroup(layout.createParallelGroup()
    	    					.addComponent(tableArea[2], 120, 120, 120)
    	    					.addComponent(tableArea[5], 120, 120, 120))
    					)
    			);
        layout.setVerticalGroup(
        		layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup()
    					.addComponent(serveBar, 40, 40, 40)
    					.addComponent(kitchen, 40, 40, 40))
        		.addGap(180, 180, 180)
    			.addGroup(layout.createParallelGroup()
    					.addComponent(tableArea[0], 120, 120, 120)
    					.addComponent(tableArea[1], 120, 120, 120)
    					.addComponent(tableArea[2], 120, 120, 120))
    			.addGap(80, 80, 80)
    			.addGroup(layout.createParallelGroup()
    					.addComponent(tableArea[3], 120, 120, 120)
    					.addComponent(tableArea[4], 120, 120, 120)
    					.addComponent(tableArea[5], 120, 120, 120))
        );
    }
    
    JPanel serveBar;
    JPanel kitchen;
    public static JPanel tableArea[];
    public static TablePanel table[];
    public static JLabel name[];
    public static JLabel state[]; 
    
}