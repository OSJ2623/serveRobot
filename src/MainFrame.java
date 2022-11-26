//package ServeRobot;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainFrame extends JFrame implements Runnable, ActionListener{
	public static Queueing queue = new Queueing();// add Queueing ++++++++++++++++++++++++
	public static Dijkstra dj = new Dijkstra(); //add Dijkstra ++++++++++++++++++++++++++
	public static MapPane mp =new MapPane();//add MapPane ++++++++++++++++++++++++++
	public static int[][] visit_xy = null;//add visited nodes++++++++++++++++++++++++++
	public static int[] dest = null;//add dest nodes
	private static int[][] transfer=null;//add transfer nodes++++++++++++++++++++
	//private static int[] robot_state;
	public static void main(String args[]) throws Exception {	// out()을 위해 throws Exception 추가
    	
		MainFrame frame = new MainFrame();
		MapPane mapPane = new MapPane();
		JPanel centerPane = new JPanel();

		frame.init();
		centerPane.add(mapPane);
        mapPane.setPreferredSize(new Dimension(600, 580));
        frame.getContentPane().add(centerPane, BorderLayout.CENTER);
        frame.pack();
		
		frame.setVisible(true);

		int[][] position = {
				{400, 80},
				{400, 180},
				{400, 280},
				{400, 380},
				{400, 280}
		};
		//mapPane.setRobot(1, position);
		int[][] position2 = {
				{200, 80},
				{200,180},
				{200,280},
				{200,80},
				{300, 80},
				{400,80}
		};
		//mapPane.setRobot(2, position2);
		
		
		//infinity while, robot operation++++++++++++++++++++++++++++++++++++++++++++++
		while(true)
		{	
			while(true)//check robot state
			{
				if(mp.isFree()!=0&&queue.state()==0)break;
				Thread.sleep(100);
			}
			visit_xy=null;
			transfer=null;
			dest = null;
			int working_robot;
			int[] robot1=null;
			int[] robot2=null;
			int node;
			int[] setting= {200,80};
			int[] serving= {400,80};
			int[] infinity= {10000,10000};
			int[] x = null;
			int[] y = null;
			
			String[] temp_str = queue.out();
			
			node =change_node(Integer.parseInt(temp_str[1]),temp_str[0]);
			working_robot=mp.isFree();
			
			
			robot1=mp.getInfo(1);
			robot2=mp.getInfo(2);
			
			if(temp_str[0].equals("serving")||temp_str[0].equals("setting"))//if "serving" or "settting"
			{	
				if(temp_str[0].equals("setting"))//if "setting"
				{
					
					//error dikstra init robot1, robot2를 각각 1차원 배열에 담아야하는데 각각을 1차원 배열의 1칸에만 값을 저장함
					dj.init(node, robot1, robot2, working_robot);
					visit_xy=dj.list_result();//go to setting bar
					dest=dj.dest_num();
					for(int i=0;i<visit_xy.length;i++)
					{
						x[i]=visit_xy[i][0]; y[i]=visit_xy[i][1];
					}
					if(dj.workRobot()==1)
						dj.init(node, setting, infinity, working_robot);
					else if(dj.workRobot()==2)
						dj.init(node, infinity, setting, working_robot);
					transfer=dj.list_result();//go to end node		
					for(int i=0;i<visit_xy.length;i++)
					{
						x[x.length]=visit_xy[i][0]; y[y.length]=visit_xy[i][1];
					}
					for(int i=0;i<x.length;i++)
					{
						visit_xy[i][0]=x[i];
						visit_xy[i][1]=y[i];
					}
					dest=dj.dest_num();
				}
				else if(temp_str[0].equals("serving"))	//if "serving"
				{
					dj.init(node, robot1, robot2, working_robot);
					visit_xy=dj.list_result();	//go to kitchen
					dest=dj.dest_num();
					for(int i=0;i<visit_xy.length;i++)
					{
						x[i]=visit_xy[i][0]; y[i]=visit_xy[i][1];
					}
					if(dj.workRobot()==1)
						dj.init(node, serving, infinity, working_robot);
					else if(dj.workRobot()==2)
						dj.init(node, infinity, serving, working_robot);

					transfer=dj.list_result();//go to end node
					for(int i=0;i<visit_xy.length;i++)
					{
						x[x.length]=visit_xy[i][0]; y[y.length]=visit_xy[i][1];
					}
					for(int i=0;i<x.length;i++)
					{
						visit_xy[i][0]=x[i]; visit_xy[i][1]=y[i];
					}
					dest=dj.dest_num();
				}
			}
			else if(temp_str[0].equals("refull"))//if refull
			{
					dj.init(node, robot1, robot2, working_robot);
					visit_xy=dj.list_result();//go to setting bar
					dest=dj.dest_num();
			}
			else if(temp_str[0].equals("clean"))//if clean
			{
				dj.init(node, robot1, robot2, working_robot);
				visit_xy=dj.list_result();//go to end node
				dest=dj.dest_num();
			}
			
			
			//don't need node?
			mp.setRobot(dj.workRobot(), visit_xy, dest);
			
		}
    }

	public void init() {
    	// initialize GUI
        
        // 창 최소크기 설정(맵이 안찌그러지도록)
        double magn = 1080 / Toolkit.getDefaultToolkit().getScreenSize().getHeight(); //화면배율
        double minX = 1000*magn;
        double minY = 722*magn;	//(580+40+60)+42
        setMinimumSize(new Dimension((int)minX, (int)minY));
        //setResizable(false);
        //setLocationRelativeTo(null);	//가운데에 창 뜨게
        //System.out.println("Frame Size = " + getSize());
        
        // 전체 화면
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        device.setFullScreenWindow(this);

        setTitle("Serving Robot Simulator");	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //mapPane = new MapPane();
        //centerPane = new JPanel();
        emptyPane1 = new JPanel();
        emptyPane2 = new JPanel();
        
        // 시간 띄우기
        timeTestLabel = new JLabel("00 : 00");
        emptyPane1.add(timeTestLabel);
        new Thread(this).start();
        
        // guest 정보 들어갈 자리 생성
        guest = new Guest[6];
        
        // 손님 입장 버튼
        guestEntranceBtn = new JButton();
        guestEntranceBtn.setText("Accept Guests");
        guestEntranceBtn.addActionListener(this);
        emptyPane2.add(guestEntranceBtn);
        
        //getContentPane().add(centerPane, BorderLayout.CENTER);
        getContentPane().add(emptyPane1, BorderLayout.NORTH);
        getContentPane().add(emptyPane2, BorderLayout.SOUTH);
        
        emptyPane1.setPreferredSize(new Dimension(1000, 40));
        emptyPane2.setPreferredSize(new Dimension(1000, 60));
        
//        centerPane.add(mapPane);
//        mapPane.setPreferredSize(new Dimension(600, 580));
//        
//        pack();
    }
    
      //table number -> node number ++++++++++++++++++++++++++++++++++++++++
    private static int change_node(int table, String operation)
    {
    	if(table==0)
    		return 1;
    	else if(table==1)
    		return 6;
    	else if(operation.equals("setting")&&table==2)
    		return 6;
    	else if(operation.equals("serving")&&table==2)
    		return 7;
    	else if(table==3)
    		return 7;
    	else if(table==4)
    		return 11;
    	else if(operation.equals("setting")&&table==5)
    		return 11;
    	else if(operation.equals("serving")&&table==5)
    		return 12;
    	else if(table==6)
    		return 12;
    	
		return 0;

    }

    
    // Real-time updates
    public void run()
    {
 	   //반복x이면 반복문 밖에
 	   
 	   while(true) {
 		   Calendar time = Calendar.getInstance();
 		   int mm, ss;
 		   mm = time.get(Calendar.MINUTE);
 		   ss = time.get(Calendar.SECOND);
 		   timeTestLabel.setText(mm + ":" + ss);
 		   
 		   try {
 			   Thread.sleep(1000);	// 1 second
 		   } catch(InterruptedException e) {
 			   e.printStackTrace();
 		   }
 	   }
 	   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // TODO Auto-generated method stub
 	  
 	   if (e.getSource() == guestEntranceBtn) {
 		   // 빈 테이블 체크
 		   for (int i = 0; i < 6; i++) { 
 			   if (Queueing.table_state[i] == 0) {
 				   // 이 테이블 넘버를 넘겨주고 guest 생성.  테이블 앉는 자리는 일단 랜덤 아님
 				   guest[i] = new Guest(i);	// guest 생성, 초기화
 				   guest[i].start();	// guest thread 시작
 				  Queueing.table_state[i] = 1;	// 자리 참
 				  break;
 			   }
 			   else {
 				   if (i == 5) {	// 다 찼으면 못넣게. 
 					   JOptionPane.showMessageDialog(null, "The restaurant is full","alert", JOptionPane.WARNING_MESSAGE);
 				   }
 			   }
 		   }
 		   
 		   
 	   }
 	   
    }
    
    
    JPanel emptyPane1;
    JPanel emptyPane2;
    JLabel timeTestLabel;
    JButton guestEntranceBtn;
    
    Guest[] guest;

}