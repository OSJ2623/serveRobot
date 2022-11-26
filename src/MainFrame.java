
import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
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
	public static void main(String args[]) throws Exception {	// out()占쎌뱽 占쎌맄占쎈퉸 throws Exception �빊遺쏙옙
    	
		MainFrame frame = new MainFrame();
		//MapPane mapPane = new MapPane();
		JPanel centerPane = new JPanel();

		frame.init();
		centerPane.add(mp);
		mp.setPreferredSize(new Dimension(600, 580));
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
		
		while(true)
		{	
			while(true)//check robot state
			{
					System.out.println(queue.dish);
					if(mp.isFree()!=0&&queue.state()==0)break;
				Thread.sleep(1000);
				System.out.println();
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

			
			String[] temp_str = queue.out();
			

			node =change_node(Integer.parseInt(temp_str[1]),temp_str[0]);
			working_robot=mp.isFree();

			robot1=mp.getInfo(1);
			robot2=mp.getInfo(2);
			
			if(temp_str[0].equals("serving")||temp_str[0].equals("setting"))//if "serving" or "settting"
			{	
				if(temp_str[0].equals("setting"))//if "setting"
				{
					//두 로봇 중 누가 할 지
					dj.init(1, robot1, robot2, working_robot);
				
					dest=dj.dest_num();
					/*
					for(int i = 0; i < dj.list_result().length; i++)
						System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
					System.out.println(dest);
					System.out.println(dj.workRobot());
					*/
					//System.out.println(dj.list_result()[0]);()
					visit_xy=dj.list_result();
					mp.setRobot(dj.workRobot(),visit_xy, dest);

					if(dj.workRobot()==1)
						dj.init(node, setting, robot2, 1);
					else if(dj.workRobot()==2)
						dj.init(node, robot1, setting, 2);
					
					while(true)
					{

						if(mp.getInfo(dj.workRobot())[0]==setting[0]&&
								mp.getInfo(dj.workRobot())[1]==setting[1])
							break;
						System.out.println(mp.getInfo(dj.workRobot())[0]);
					}
					visit_xy=null;		
					visit_xy=dj.list_result();
					dest=dj.dest_num();
					mp.setRobot(dj.workRobot(), visit_xy, dest);
					
				}
				else if(temp_str[0].equals("serving"))	//if "serving"
				{
					//두 로봇 중 누가 할 지
					dj.init(3, robot1, robot2, working_robot);
				
					dest=dj.dest_num();
					/*
					for(int i = 0; i < dj.list_result().length; i++)
						System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
					System.out.println(dest);
					System.out.println(dj.workRobot());
					*/
					//System.out.println(dj.list_result()[0]);()
					visit_xy=dj.list_result();	
					mp.setRobot(dj.workRobot(), visit_xy, dest);

					if(dj.workRobot()==1)
						dj.init(node, serving, robot2, 1);
					else if(dj.workRobot()==2)
						dj.init(node, robot1, serving, 2);
					
					
					while(true)
					{
						
						if(mp.getInfo(dj.workRobot())[0]==serving[0]&&
								mp.getInfo(dj.workRobot())[1]==serving[1])
							break;
						System.out.println(mp.getInfo(dj.workRobot())[0]);
					}
						
					visit_xy=null;		
					visit_xy=dj.list_result();			
					dest=dj.dest_num();
					mp.setRobot(dj.workRobot(), visit_xy, dest);
				}
			else if(temp_str[0].equals("refull"))//if refull
			{
				//두 로봇 중 누가 할 지
				dj.init(3, robot1, robot2, working_robot);
			
				dest=dj.dest_num();
				/*
				for(int i = 0; i < dj.list_result().length; i++)
					System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
				System.out.println(dest);
				System.out.println(dj.workRobot());
				*/
				//System.out.println(dj.list_result()[0]);()
				visit_xy=dj.list_result();	
				mp.setRobot(dj.workRobot(), visit_xy, dest);

				if(dj.workRobot()==1)
					dj.init(1, serving, robot2, 1);
				else if(dj.workRobot()==2)
					dj.init(1, robot1, serving, 2);
				
				
				while(true)
				{
					
					if(mp.getInfo(dj.workRobot())[0]==serving[0]&&
							mp.getInfo(dj.workRobot())[1]==serving[1])
						break;
					System.out.println(mp.getInfo(dj.workRobot())[0]);
				}
					
				visit_xy=null;		
				visit_xy=dj.list_result();			
				dest=dj.dest_num();
				mp.setRobot(dj.workRobot(), visit_xy, dest);
				queue.dish = 10;
			}
			else if(temp_str[0].equals("clean"))//if clean
			{
				dj.init(node, robot1, robot2, working_robot);
				
				dest=dj.dest_num();
				//don't need node?
				visit_xy=dj.list_result();
				mp.setRobot(dj.workRobot(), visit_xy, dest);
				
			}
			
			
			
			
		}
		}
    }

	public void init() {
    	// initialize GUI
        
        // 筌∽옙 筌ㅼ뮇�꺖占쎄쾿疫뀐옙 占쎄퐬占쎌젟(筌띾벊�뵠 占쎈툧筌〓슡�젃占쎌쑎筌욑옙占쎈즲嚥∽옙)
        double magn = 1080 / Toolkit.getDefaultToolkit().getScreenSize().getHeight(); //占쎌넅筌롫�媛숋옙�몛
        double minX = 1000*magn;
        double minY = 722*magn;	//(580+40+60)+42
        setMinimumSize(new Dimension((int)minX, (int)minY));
        //setResizable(false);
        //setLocationRelativeTo(null);	//揶쏉옙占쎌뒲占쎈쑓占쎈퓠 筌∽옙 占쎌몛野껓옙
        //System.out.println("Frame Size = " + getSize());
        
        // 占쎌읈筌ｏ옙 占쎌넅筌롳옙
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        device.setFullScreenWindow(this);

        setTitle("Serving Robot Simulator");	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //mapPane = new MapPane();
        //centerPane = new JPanel();
        emptyPane1 = new JPanel();
        emptyPane2 = new JPanel();
        
        // 占쎈뻻揶쏉옙 占쎌뱽占쎌뒭疫뀐옙
        timeTestLabel = new JLabel("00 : 00");
        emptyPane1.add(timeTestLabel);
        new Thread(this).start();
        
        // guest 占쎌젟癰귨옙 占쎈굶占쎈선揶쏉옙 占쎌쁽�뵳占� 占쎄문占쎄쉐
        guest = new Guest[6];
        
        // 占쎈�占쎈뻷 占쎌뿯占쎌삢 甕곌쑵�뱣
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
 	   //獄쏆꼶�궗x占쎌뵠筌롳옙 獄쏆꼶�궗�눧占� 獄쏅쉼肉�
 	   
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
 		   // �뜮占� 占쎈�믭옙�뵠�뇡占� 筌ｋ똾寃�
 		   for (int i = 0; i < 6; i++) { 
 			   if (Queueing.table_state[i] == 0) {
 				   // 占쎌뵠 占쎈�믭옙�뵠�뇡占� 占쎄퐜甕곌쑬占쏙옙 占쎄퐜野꺿뫁竊쒏�⑨옙 guest 占쎄문占쎄쉐.  占쎈�믭옙�뵠�뇡占� 占쎈툩占쎈뮉 占쎌쁽�뵳�됰뮉 占쎌뵬占쎈뼊 占쎌삏占쎈쑁 占쎈툡占쎈뻷
 				   guest[i] = new Guest(i);	// guest 占쎄문占쎄쉐, �룯�뜃由곤옙�넅
 				   guest[i].start();	// guest thread 占쎈뻻占쎌삂
 				  Queueing.table_state[i] = 1;	// 占쎌쁽�뵳占� 筌∽옙
 				 MapPane.table[i].setBackground(Color.LIGHT_GRAY);	// �옄由� 李쇰떎�뒗 �몴�떆 gui
 				  break;
 			   }
 			   else {
 				   if (i == 5) {	// 占쎈뼄 筌≪눘�몵筌롳옙 筌륁궠苑붷칰占�. 
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