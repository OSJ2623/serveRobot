import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

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

	final int PANEL_WIDTH = 600;
    final int PANEL_HEIGHT = 580;
    
    Image map, robot_front1, robot_back1, robot_front2, robot_back2;
    
    Timer timer;
    
    int xVel = 1, yVel = 1;
    
    int x1 = 200, y1 = 80, x2 = 400, y2 = 80;
    
    int robot;
    
    Queue<Point> way1 = new LinkedList<Point>();
    Queue<Point> way2 = new LinkedList<Point>();
    
	Point p1 = null;
	Point p2 = null;
	
    int flag1 = 0, flag2 = 0;
    
    int[] temp1 = {0, 0}, temp2 = {0, 0};
	
    int[] dest1, dest2;
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
        
        timer = new Timer(10, this);
        timer.start();
    }
    
    public void setRobot(int robot, int[][] position, int[] dest) { //������ ����
    	this.robot = robot;
    	
    	if(robot == 1) {
    		//this.flag1 = 1;
    		this.dest1 = dest;
    		for(int i = 0; i < position.length; i++) {
    			this.way1.add(new Point(position[i][0], position[i][1]));
    			this.temp1[0] = position[i][0];
    			this.temp1[1] = position[i][1];
    		}
    	}
    	
    	if(robot == 2) {
    		//this.flag2 = 1;
    		this.dest2 = dest;
    		for(int i = 0; i < position.length; i++) {
    			this.way2.add(new Point(position[i][0], position[i][1]));
    			this.temp2[0] = position[i][0];
    			this.temp2[1] = position[i][1];
    		}
    	}
    }
    
    public int[] getInfo(int robot) {
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
   
    public void paint(Graphics g){
    	super.paint(g);
    	
    	//mapPane ũ��
    	//g.setColor(Color.LIGHT_GRAY);
    	//g.drawRect(0, 0, getWidth(), getHeight());
    	
    	//�̹��� �� ��ġ Ȯ��
//    	g.setColor(Color.WHITE);
//    	g.fillRect(0, 0, 600, 580);
//    	g.setColor(Color.BLACK);
//    	g.drawRect(0, 0, 600, 579);
    	
    	//�� �̹��� (600*580)
//    	java.awt.Image map = Toolkit.getDefaultToolkit().getImage("./images/map.png");
//    	g.drawImage(map, 0, 0, 600, 580, this);
    	
    	Graphics2D g2D = (Graphics2D) g;
    	g2D.drawImage(robot_front1, x1, y1, null);
    	
    	g2D.drawImage(robot_front2, x2, y2, null);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

    	/*
    	if(x > 600-robot_front1.getWidth(null) || x<0) {
    		
    	}
		x1 = x1+xVel;
		
		if(y > 580-robot_front1.getHeight(null) || y<0) {
    		
    	}
		y = y+yVel;
		*/
//    	System.out.println("������: "+ temp1[0] + "," + temp1[1]);
    	
    	if(flag1 == 0) {
        	p1 = way1.poll();
    	}
    	
    	if(flag2 == 0) {
    		p2 = way2.poll();
    	}
    	
//    	System.out.println(p1);
    	
		if(p1 != null) {
//			System.out.println("����x: " + p1.x + ", ����x: " + (x1+robot_front1.getWidth(null)/2)
//					+ ",���� y: " + p1.y +", ���� y: " +(y1+robot_front1.getHeight(null)/2));
			
			if(p1.x != (x1+robot_front1.getWidth(null)/2)) { //���� ���� ��ġ�� x��ǥ�� ���� �ʴٸ�
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
			}
			if(dest1[0] == (x1+robot_front1.getWidth(null)/2) && dest1[1] == (y1+robot_front1.getHeight(null)/2)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if(p2 != null) {
			
//			System.out.println("����x: " + p2.x + ", ����x: " + (x2+robot_front1.getWidth(null)/2)
//					+ ",���� y: " + p2.y +", ���� y: " +(y2+robot_front1.getHeight(null)/2));
			
			if(p2.x != (x2+robot_front1.getWidth(null)/2)) { //���� ���� ��ġ�� x��ǥ�� ���� �ʴٸ�
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
				flag2 = 0;
			}
			if(dest2[0] == (x2+robot_front1.getWidth(null)/2) && dest2[1] == (y2+robot_front1.getHeight(null)/2)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		repaint();
	}
    
    private void initComponents() {
    	// �Ʒ��� �������ּ���... ���� �г��� �Ǿ� ���ƿðԿ�...

    	serveBar = new JPanel();
    	kitchen = new JPanel();
    	table1 = new JPanel();
    	table2 = new JPanel();
    	table3 = new JPanel();
    	table4 = new JPanel();
    	table5 = new JPanel();
    	table6 = new JPanel();
    	serveBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    	serveBar.setBackground(Color.WHITE);
    	kitchen.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    	kitchen.setBackground(Color.WHITE);
    	table1.setBackground(Color.CYAN);
    	table2.setBackground(Color.BLACK);
    	table3.setBackground(Color.CYAN);
    	table4.setBackground(Color.BLACK);
    	table5.setBackground(Color.CYAN);
    	table6.setBackground(Color.BLACK);

    	setBackground(Color.WHITE);
    	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
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
    	    					.addComponent(table1, 120, 120, 120)
    	    					.addComponent(table4, 120, 120, 120))
    	    			.addGap(80, 80, 80)
    	    			.addGroup(layout.createParallelGroup()
    	    					.addComponent(table2, 120, 120, 120)
    	    					.addComponent(table5, 120, 120, 120))
    	    			.addGap(80, 80, 80)
    	    			.addGroup(layout.createParallelGroup()
    	    					.addComponent(table3, 120, 120, 120)
    	    					.addComponent(table6, 120, 120, 120))
    					)
    			);
        layout.setVerticalGroup(
        		layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup()
    					.addComponent(serveBar, 40, 40, 40)
    					.addComponent(kitchen, 40, 40, 40))
        		.addGap(180, 180, 180)
    			.addGroup(layout.createParallelGroup()
    					.addComponent(table1, 120, 120, 120)
    					.addComponent(table2, 120, 120, 120)
    					.addComponent(table3, 120, 120, 120))
    			.addGap(80, 80, 80)
    			.addGroup(layout.createParallelGroup()
    					.addComponent(table4, 120, 120, 120)
    					.addComponent(table5, 120, 120, 120)
    					.addComponent(table6, 120, 120, 120))
        );
    }
    
    
    JPanel table1;
    JPanel table2;
    JPanel table3;
    JPanel table4;
    JPanel table5;
    JPanel table6;
    JPanel serveBar;
    JPanel kitchen;
    
}
