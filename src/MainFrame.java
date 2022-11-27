
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

public class MainFrame extends JFrame implements Runnable, ActionListener {
	public static Queueing queue = new Queueing();// add Queueing ++++++++++++++++++++++++
	public static Dijkstra dj = new Dijkstra(); // add Dijkstra ++++++++++++++++++++++++++
	public static MapPane mp = new MapPane();// add MapPane ++++++++++++++++++++++++++
	public static int[][] visit_xy = null;// add visited nodes++++++++++++++++++++++++++
	public static int[] dest = null;// add dest nodes
	private static int[][] transfer = null;// add transfer nodes++++++++++++++++++++

	public static int[] table_state = new int[6];
	public static boolean[] isSettingDone = new boolean[6];
	public static boolean[] isServingDone = new boolean[6];
	public static boolean[] haveToClean = new boolean[6];
	public static String[] robot_doing = new String[] { "", "" };
	public static int[] robot_table = new int[2];

	public static void main(String args[]) throws Exception { // out()�뜝�럩諭� �뜝�럩留꾢뜝�럥�돵 throws Exception 占쎈퉲�겫�룞�삕

		MainFrame frame = new MainFrame();
		// MapPane mapPane = new MapPane();
		JPanel centerPane = new JPanel();

		frame.init();
		centerPane.add(mp);
		mp.setPreferredSize(new Dimension(600, 580));
		frame.getContentPane().add(centerPane, BorderLayout.CENTER);
		frame.pack();

		frame.setVisible(true);

		int[][] position = { { 400, 80 }, { 400, 180 }, { 400, 280 }, { 400, 380 }, { 400, 280 } };
		// mapPane.setRobot(1, position);
		int[][] position2 = { { 200, 80 }, { 200, 180 }, { 200, 280 }, { 200, 80 }, { 300, 80 }, { 400, 80 } };
		// mapPane.setRobot(2, position2);

		while (true) {
			while (true)// check robot state
			{
				System.out.println(queue.dish);
				if (mp.isFree() != 0 && queue.state() == 0)
					break;
				if (mp.isFree() != 0 && queue.state() == 0)
					break;
				Thread.sleep(1000);
//				System.out.println();
			}

			visit_xy = null;
			transfer = null;
			dest = null;
			int working_robot;
			int[] robot1 = null;
			int[] robot2 = null;

			int node;
			int[] setting = { 200, 80 };
			int[] serving = { 400, 80 };

			String[] temp_str = queue.out();
			node = change_node(Integer.parseInt(temp_str[1]), temp_str[0]);
			working_robot = mp.isFree();

			robot1 = mp.getInfo(1);
			robot2 = mp.getInfo(2);

			robot1 = mp.getInfo(1);
			robot2 = mp.getInfo(2);

			if (temp_str[0].equals("serving") || temp_str[0].equals("setting"))// if "serving" or "settting"
			{
				if (temp_str[0].equals("setting"))// if "setting"
				{
					// �몢 濡쒕큸 以� �늻媛� �븷 吏�
					dj.init(1, robot1, robot2, working_robot);

					dest = dj.dest_num();
					/*
					 * for(int i = 0; i < dj.list_result().length; i++)
					 * System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
					 * System.out.println(dest); System.out.println(dj.workRobot());
					 */
					// System.out.println(dj.list_result()[0]);()
					visit_xy = dj.list_result();
					mp.setRobot(dj.workRobot(), visit_xy, dest);

					if (dj.workRobot() == 1)
						dj.init(node, setting, robot2, 1);
					else if (dj.workRobot() == 2)
						dj.init(node, robot1, setting, 2);

					while (true) {

						if (mp.getInfo(dj.workRobot())[0] == setting[0] && mp.getInfo(dj.workRobot())[1] == setting[1])
							break;
						System.out.println(mp.getInfo(dj.workRobot())[0]);
					}
					visit_xy = null;
					visit_xy = dj.list_result();
					dest = dj.dest_num();
					mp.setRobot(dj.workRobot(), visit_xy, dest);

				} else if (temp_str[0].equals("serving")) // if "serving"
				{
					// �몢 濡쒕큸 以� �늻媛� �븷 吏�
					dj.init(3, robot1, robot2, working_robot);

					dest = dj.dest_num();
					/*
					 * for(int i = 0; i < dj.list_result().length; i++)
					 * System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
					 * System.out.println(dest); System.out.println(dj.workRobot());
					 */
					// System.out.println(dj.list_result()[0]);()
					visit_xy = dj.list_result();
					mp.setRobot(dj.workRobot(), visit_xy, dest);

					if (dj.workRobot() == 1)
						dj.init(node, serving, robot2, 1);
					else if (dj.workRobot() == 2)
						dj.init(node, robot1, serving, 2);

					while (true) {

						if (mp.getInfo(dj.workRobot())[0] == serving[0] && mp.getInfo(dj.workRobot())[1] == serving[1])
							break;
						System.out.println(mp.getInfo(dj.workRobot())[0]);
					}

					visit_xy = null;
					visit_xy = dj.list_result();
					dest = dj.dest_num();
					mp.setRobot(dj.workRobot(), visit_xy, dest);
				} else if (temp_str[0].equals("refull"))// if refull
				{
					// �몢 濡쒕큸 以� �늻媛� �븷 吏�
					dj.init(3, robot1, robot2, working_robot);

					dest = dj.dest_num();
					/*
					 * for(int i = 0; i < dj.list_result().length; i++)
					 * System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
					 * System.out.println(dest); System.out.println(dj.workRobot());
					 */
					// System.out.println(dj.list_result()[0]);()
					visit_xy = dj.list_result();
					mp.setRobot(dj.workRobot(), visit_xy, dest);

					if (dj.workRobot() == 1)
						dj.init(1, serving, robot2, 1);
					else if (dj.workRobot() == 2)
						dj.init(1, robot1, serving, 2);

					while (true) {

						if (mp.getInfo(dj.workRobot())[0] == serving[0] && mp.getInfo(dj.workRobot())[1] == serving[1])
							break;
						System.out.println(mp.getInfo(dj.workRobot())[0]);
					}

					visit_xy = null;
					visit_xy = dj.list_result();
					dest = dj.dest_num();
					mp.setRobot(dj.workRobot(), visit_xy, dest);
					queue.dish = 10;
				} else if (temp_str[0].equals("clean"))// if clean
				{
					dj.init(node, robot1, robot2, working_robot);

					dest = dj.dest_num();
					// don't need node?
					visit_xy = dj.list_result();
					if (temp_str[0].equals("serving") || temp_str[0].equals("setting"))// if "serving" or "settting"
					{
						if (temp_str[0].equals("setting"))// if "setting"
						{
							// �몢 濡쒕큸 以� �늻媛� �븷 吏�
							dj.init(1, robot1, robot2, working_robot);

							dest = dj.dest_num();
							/*
							 * for(int i = 0; i < dj.list_result().length; i++)
							 * System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
							 * System.out.println(dest); System.out.println(dj.workRobot());
							 */
							// System.out.println(dj.list_result()[0]);()
							visit_xy = dj.list_result();
							mp.setRobot(dj.workRobot(), visit_xy, dest);

							if (dj.workRobot() == 1) {
								dj.init(node, setting, robot2, 1);
								robot_doing[0] = temp_str[0];
								robot_table[0] = Integer.parseInt(temp_str[1]);
							} else if (dj.workRobot() == 2) {
								dj.init(node, robot1, setting, 2);
								robot_doing[1] = temp_str[0];
								robot_table[1] = Integer.parseInt(temp_str[1]);
							}

							while (true) {
								Queueing.priority();
								if (mp.getInfo(dj.workRobot())[0] == setting[0]
										&& mp.getInfo(dj.workRobot())[1] == setting[1])
									break;
								System.out.println(mp.getInfo(dj.workRobot())[0]);
							}
							visit_xy = null;
							visit_xy = dj.list_result();
							dest = dj.dest_num();
							mp.setRobot(dj.workRobot(), visit_xy, dest);

						} else if (temp_str[0].equals("serving")) // if "serving"
						{
							// �몢 濡쒕큸 以� �늻媛� �븷 吏�
							dj.init(3, robot1, robot2, working_robot);

							dest = dj.dest_num();
							/*
							 * for(int i = 0; i < dj.list_result().length; i++)
							 * System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
							 * System.out.println(dest); System.out.println(dj.workRobot());
							 */
							// System.out.println(dj.list_result()[0]);()
							visit_xy = dj.list_result();
							mp.setRobot(dj.workRobot(), visit_xy, dest);

							if (dj.workRobot() == 1) {
								dj.init(node, serving, robot2, 1);
								robot_doing[0] = temp_str[0];
								robot_table[0] = Integer.parseInt(temp_str[1]);
							} else if (dj.workRobot() == 2) {
								dj.init(node, robot1, serving, 2);
								robot_doing[1] = temp_str[0];
								robot_table[1] = Integer.parseInt(temp_str[1]);
							}

							while (true) {

								if (mp.getInfo(dj.workRobot())[0] == serving[0]
										&& mp.getInfo(dj.workRobot())[1] == serving[1])
									break;
								System.out.println(mp.getInfo(dj.workRobot())[0]);
							}

							visit_xy = null;
							visit_xy = dj.list_result();
							dest = dj.dest_num();
							mp.setRobot(dj.workRobot(), visit_xy, dest);
						}

					} else if (temp_str[0].equals("refull"))// if refull
					{
						// �몢 濡쒕큸 以� �늻媛� �븷 吏�
						dj.init(3, robot1, robot2, working_robot);

						dest = dj.dest_num();
						/*
						 * for(int i = 0; i < dj.list_result().length; i++)
						 * System.out.println(dj.list_result()[i][0] + "," + dj.list_result()[i][1]);
						 * System.out.println(dest); System.out.println(dj.workRobot());
						 */
						// System.out.println(dj.list_result()[0]);()
						visit_xy = dj.list_result();
						mp.setRobot(dj.workRobot(), visit_xy, dest);

						if (dj.workRobot() == 1) {
							dj.init(1, serving, robot2, 1);
							robot_doing[0] = temp_str[0];
							robot_table[0] = Integer.parseInt(temp_str[1]);
						} else if (dj.workRobot() == 2) {
							dj.init(1, robot1, serving, 2);
							robot_doing[1] = temp_str[0];
							robot_table[1] = Integer.parseInt(temp_str[1]);
						}

						while (true) {

							if (mp.getInfo(dj.workRobot())[0] == serving[0]
									&& mp.getInfo(dj.workRobot())[1] == serving[1])
								break;
							System.out.println(mp.getInfo(dj.workRobot())[0]);
						}

						visit_xy = null;
						visit_xy = dj.list_result();
						dest = dj.dest_num();
						mp.setRobot(dj.workRobot(), visit_xy, dest);
//				queue.dish = 5;	// mapPane�뿉 濡쒕큸�씠 �씪�걹�굹硫� �븯�뒗 �룞�옉 履쎌쑝濡� �씠�룞.
					} else if (temp_str[0].equals("clean"))// if clean
					{
						dj.init(node, robot1, robot2, working_robot);
						if (dj.workRobot() == 1) {
							robot_doing[0] = temp_str[0];
							robot_table[0] = Integer.parseInt(temp_str[1]);
						} else if (dj.workRobot() == 2) {
							robot_doing[1] = temp_str[0];
							robot_table[1] = Integer.parseInt(temp_str[1]);
						}
						dest = dj.dest_num();
						// don't need node?
						visit_xy = dj.list_result();
						mp.setRobot(dj.workRobot(), visit_xy, dest);

					}
				}
			}
		}
	}

	public void init() {
		// initialize GUI

		// 嶺뚢댙�삕 嶺뚣끉裕뉛옙爰뽩뜝�럡苡욜뼨�먯삕 �뜝�럡�맟�뜝�럩�젧(嶺뚮씭踰딉옙逾�
		// �뜝�럥�닱嶺뚣�볦뒦占쎌쟽�뜝�럩�몠嶺뚯쉻�삕�뜝�럥利꿨슖�댙�삕)
		double magn = 1080 / Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // �뜝�럩�꼨嶺뚮∥占썲첎�닂�삕占쎈첎
		double minX = 1000 * magn;
		double minY = 722 * magn; // (580+40+60)+42
		setMinimumSize(new Dimension((int) minX, (int) minY));
		// setResizable(false);
		// setLocationRelativeTo(null); //�뤆�룊�삕�뜝�럩�뮧�뜝�럥�몥�뜝�럥�뱺 嶺뚢댙�삕 �뜝�럩紐쏃뇦猿볦삕
		// System.out.println("Frame Size = " + getSize());

		// �뜝�럩�쓧嶺뚳퐦�삕 �뜝�럩�꼨嶺뚮〕�삕
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = graphics.getDefaultScreenDevice();
		device.setFullScreenWindow(this);

		setTitle("Serving Robot Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// mapPane = new MapPane();
		// centerPane = new JPanel();
		emptyPane1 = new JPanel();
		emptyPane2 = new JPanel();

		// �뜝�럥六삥뤆�룊�삕 �뜝�럩諭썲뜝�럩�뮡�뼨�먯삕
		timeTestLabel = new JLabel("00 : 00");
		emptyPane1.add(timeTestLabel);
		new Thread(this).start();

		// guest �뜝�럩�젧�솻洹⑥삕 �뜝�럥援뜹뜝�럥�꽑�뤆�룊�삕 �뜝�럩�겱占쎈뎨�뜝占� �뜝�럡臾멨뜝�럡�뎽
		guest = new Guest[6];

		// �뜝�럥占썲뜝�럥六� �뜝�럩肉��뜝�럩�궋 �뵓怨뚯뫓占쎈괏
		guestEntranceBtn = new JButton();
		guestEntranceBtn.setText("Accept Guests");
		guestEntranceBtn.addActionListener(this);
		emptyPane2.add(guestEntranceBtn);

		// getContentPane().add(centerPane, BorderLayout.CENTER);
		getContentPane().add(emptyPane1, BorderLayout.NORTH);
		getContentPane().add(emptyPane2, BorderLayout.SOUTH);

		emptyPane1.setPreferredSize(new Dimension(1000, 40));
		emptyPane2.setPreferredSize(new Dimension(1000, 60));

		// centerPane.add(mapPane);
		// mapPane.setPreferredSize(new Dimension(600, 580));
		//
		// pack();
	}

	// table number -> node number ++++++++++++++++++++++++++++++++++++++++
	private static int change_node(int table, String operation) {
		if (table == 0)
			return 1;
		else if (table == 1)
			return 6;
		else if (operation.equals("setting") && table == 2)
			return 6;
		else if (operation.equals("serving") && table == 2)
			return 7;
		else if (operation.equals("clean") && table == 2) {
			if (mp.getInfo(dj.workRobot())[0] < 300)
				return 6;
			else
				return 7;
		} else if (table == 3)
			return 7;
		else if (table == 4)
			return 11;
		else if (operation.equals("setting") && table == 5)
			return 11;
		else if (operation.equals("serving") && table == 5)
			return 12;
		else if (operation.equals("clean") && table == 5) {
			if (mp.getInfo(dj.workRobot())[0] < 300)
				return 11;
			else
				return 12;
		} else if (table == 6)
			return 12;
		return 0;
	}

	// Real-time updates
	public void run() {
		// �뛾�룇瑗띰옙沅뾶�뜝�럩逾좂춯濡녹삕 �뛾�룇瑗띰옙沅쀯옙�닱�뜝占� �뛾�룆�돹�굢占�

		while (true) {
			Calendar time = Calendar.getInstance();
			int mm, ss;
			mm = time.get(Calendar.MINUTE);
			ss = time.get(Calendar.SECOND);
			timeTestLabel.setText(mm + ":" + ss);

   
			try {
				Thread.sleep(1000); // 1 second
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == guestEntranceBtn) {
			// 占쎈쑏�뜝占� �뜝�럥占쎈���삕占쎈턄占쎈눀�뜝占� 嶺뚳퐢�샑野껓옙
			for (int i = 0; i < 6; i++) {
				if (table_state[i] == 0) {
					// �뜝�럩逾� �뜝�럥占쎈���삕占쎈턄占쎈눀�뜝占� �뜝�럡�맂�뵓怨뚯뫊�뜝�룞�삕 �뜝�럡�맂�뇦爰용쳛塋딆뮀占썩뫅�삕 guest
					// �뜝�럡臾멨뜝�럡�뎽. �뜝�럥占쎈���삕占쎈턄占쎈눀�뜝占� �뜝�럥�닶�뜝�럥裕�
					// �뜝�럩�겱占쎈뎨占쎈맧裕� �뜝�럩逾у뜝�럥堉� �뜝�럩�굩�뜝�럥�몓 �뜝�럥�닡�뜝�럥六�
					guest[i] = new Guest(i); // guest �뜝�럡臾멨뜝�럡�뎽, 占쎈／占쎈쐝�뵳怨ㅼ삕占쎈꼨
					guest[i].start(); // guest thread �뜝�럥六삣뜝�럩�굚
					table_state[i] = 1; // �뜝�럩�겱占쎈뎨�뜝占� 嶺뚢댙�삕
					MapPane.table[i].setBackground(Color.LIGHT_GRAY); // 占쎌쁽�뵳占� 筌≪눖�뼄占쎈뮉 占쎈ご占쎈뻻 gui
					break;
				} else {
					if (i == 5) { // �뜝�럥堉� 嶺뚢돦�닔占쎈さ嶺뚮〕�삕 嶺뚮쪇沅좄땻遺룹물�뜝占�.
						JOptionPane.showMessageDialog(null, "The restaurant is full", "alert",
								JOptionPane.WARNING_MESSAGE);
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