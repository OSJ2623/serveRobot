import java.util.LinkedList;
import java.util.Queue;


//main method is placed main class
//this class has 4method operating in the main class
//init(String, int) method is add queue
//out() method is return next operation to main class 
//priority() is  rearrange queues
//state() is check queues state

public class Queueing {

	public static String message = null;
<<<<<<< HEAD
	public static int dish = 5;
=======
	public static int dish=10;
>>>>>>> parent of b85621c (Í±∞Ïùò Îã§ ÏôîÏñ¥Ïöî...)

	private static Queue<String> Queue = new LinkedList<>(); // third priority Queue
	private static Queue<String> clean_Queue = new LinkedList<>();// second priority Queue
	private static Queue<String> priority_Queue = new LinkedList<>();// first priority Queue

	private static Queue<String> temp_Queue = new LinkedList<>();// temporary stored Queue

	// kind of priority
	// '.' is Delimiter to robot go to where table
	// ex) 'setting.5' is robot should go table number5 to do 'setting' at table 5

	public static int state()// find the queque us empty if empty return 1, if not empty return 0
	{
		if (Queue.isEmpty() && clean_Queue.isEmpty() && priority_Queue.isEmpty())
			return 1;
		else
			return 0;
	}

<<<<<<< HEAD
	// rearrange the Queues according to priority.
	public static void priority() {

		// if all queue is empty so this operation is the lowest priority
		if (dish <= 2 && state() == 1)//dish is 
			Queue.add("refull.0");
		// the highest priority "refull", if all dish is zero
		if (dish == 0 && !priority_Queue.isEmpty() && priority_Queue.element() != "refull.0")// dish is zero then do 1st
=======
		//1st priority "refull" setting and lowest priority "refull" setting
		if(dish<=5&&state()==1)//Queue is empty then do lowest priority "refull" 	
			Queue.add("refull.0");
		if(dish==0&&!priority_Queue.isEmpty()&&priority_Queue.element()!="refull.0")//dish is zero then do 1st priority "refull"
		{
			if(priority_Queue.contains("refull.0"))
				priority_Queue.remove("refull.0");
			if(Queue.contains("refull.0"))
				Queue.remove("refull.0");
			priorityQueue();
			priority_Queue.add("refull.0");
			repriorityQueue();
		}
		else if(dish==0&&priority_Queue.isEmpty())
		{
			if(Queue.contains("refull.0"))
				Queue.remove("refull.0");
			priority_Queue.add("refull.0");
		}
			
		//setting the 2nd, 3rd priority "setting" or "serving"
		//In one table, Necessarily "setting" > "serving"
		//At different tables,"setting" >= "serving"
		if(!(message==null))
>>>>>>> parent of b85621c (Í±∞Ïùò Îã§ ÏôîÏñ¥Ïöî...)
		{
			if (priority_Queue.contains("refull.0"))
				priority_Queue.remove("refull.0");
			// if refull is in priority but not first (exceptional case)
			if (dish == 0 && !priority_Queue.isEmpty() && priority_Queue.contains("refull.0")
					&& priority_Queue.element() != "refull.0") {
				if (Queue.contains("refull.0"))
					Queue.remove("refull.0");
				priority_Queue.remove("refull.0");
				priorityQueue();
				priority_Queue.add("refull.0");
				repriorityQueue();
			}
			// if refull is not in priority
			else if (dish == 0 && priority_Queue.isEmpty()) {
				if (Queue.contains("refull.0"))
					Queue.remove("refull.0");
				priority_Queue.add("refull.0");
			}
<<<<<<< HEAD

			// setting the 2nd, 3rd priority "setting" or "serving"
			// In one table, Necessarily "setting" > "serving"
			// At different tables,"setting" >= "serving"
			if (!(message == null)) {
				if (Queue.contains(message))
					Queue.remove(message);
				if (priority_Queue.contains(message))
					return;
				else {
					priority_Queue.add(message);
					message = null;// main.message = null;
				}
			}
		}
=======
				
				
			
		}
		
			
		//Thread.sleep(100); this page or main page in infinity loop
			
			
>>>>>>> parent of b85621c (Í±∞Ïùò Îã§ ÏôîÏñ¥Ïöî...)
	}

<<<<<<< HEAD
	// input operation in queues
	public static void init(String operation, int table) {

		//init() method can put at clean_Queue and Queue
		//pirority_Queue's rearrange or input is priority() method
		if (operation.equals("clean.")) {
			clean_Queue.add(operation + table);
		} else
			Queue.add(operation + table);
=======
		if(operation=="clean.")
		{
			clean_Queue.add(operation+table);
		}
		else
			Queue.add(operation+table);
		
		
>>>>>>> parent of b85621c (Í±∞Ïùò Îã§ ÏôîÏñ¥Ïöî...)
	}

	
	public String[] out() throws Exception {

		String str1;
		String str2;
		String opr = null;

		//Find the following operation in order of priority
		while (true) {
			Thread.sleep(10);// if queue is empty other robot get operation then opr == null
							//opr == null, loop (exceptional case)
			if (!priority_Queue.isEmpty()) {
				opr = priority_Queue.poll();
				break;
			} else if (!clean_Queue.isEmpty()) {
				opr = clean_Queue.poll();
				break;
			} else if (!Queue.isEmpty()) {
				opr = Queue.poll();
				break;
			} else
				continue;
		}
		str1 = opr.substring(0, opr.indexOf("."));
		str2 = opr.substring(opr.indexOf(".") + 1);
		String[] str = { str1, str2 };

		
		//we return operation and table number to other class of same project
//		System.out.println(opr);		

<<<<<<< HEAD
		
=======
		str1 =opr.substring(0, opr.indexOf("."));
		str2 = opr.substring(opr.indexOf(".")+1);
		String[] str = {str1, str2};
		int tbN = Integer.parseInt(str2)-1;
		
		if(str1.equals("setting")) {	//equals Ω·¡÷ººø‰...§–
			dish -= 1;	
//			table_state[Integer.parseInt(str2)-1]=1;	// Guest ª˝º∫«“ ∂ß πŸ≤„º≠ øÏº± æ¯æ÷∫√Ω¿¥œ¥Ÿ
			isSettingDone[tbN] = true;	// ∑Œ∫ø¿Ã ¿œ¿ª ≥°≥Ω »ƒø° πŸ≤∏æﬂ «œ¡ˆ∏∏ øÏº± ø©±‚ø°...
			MapPane.table[tbN].repaint();	// ≈◊¿Ã∫Ì ±◊∏≤ πŸ≤Ÿ±‚
		}
		if(str1.equals("serving")) {
			isServingDone[tbN] = true;	// ∑Œ∫ø¿Ã ¿œ¿ª ≥°≥Ω »ƒø° πŸ≤∏æﬂ «œ¡ˆ∏∏ øÏº± ø©±‚ø°...
			MapPane.table[tbN].repaint();	// ≈◊¿Ã∫Ì ±◊∏≤ πŸ≤Ÿ±‚
		}
		if(str1.equals("clean")) {
			table_state[tbN]=0;	// ∑Œ∫ø¿Ã ¿œ¿ª ≥°≥Ω »ƒø° πŸ≤∏æﬂ «œ¡ˆ∏∏ øÏº± ø©±‚ø°...
//			MapPane.table[tbN].setBackground(Color.WHITE);	// ¿⁄∏Æ ¥ŸΩ√ ∫Òæ˙¥Ÿ¥¬ «•Ω√ gui
			MapPane.table[tbN].repaint();	// ≈◊¿Ã∫Ì ±◊∏≤ πŸ≤Ÿ±‚
			MapPane.state[tbN].setText("");	// table ªÛ≈¬ ∏ﬁΩ√¡ˆ √ ±‚»≠
			isSettingDone[tbN] = false;
			isServingDone[tbN] = false;
		}
		
		
 		if(opr=="refull.0")// temporary if 
 			dish=10;
 		
>>>>>>> parent of b85621c (Í±∞Ïùò Îã§ ÏôîÏñ¥Ïöî...)
		return str;
	}

	// priority_Queue value -> temp_Queue
	private static void priorityQueue() {
		for (int i = 0; i < priority_Queue.size(); i++)
			temp_Queue.add(priority_Queue.poll());

	}

	// temp_Queue -> priority_Queue value
	private static void repriorityQueue() {
		for (int i = 0; i < temp_Queue.size(); i++)
			priority_Queue.add(temp_Queue.poll());
	}
<<<<<<< HEAD

=======
	
	
	//Queue value -> temp_Queue 
		private static void tempQueue()
		{
			for(int i=0;i<Queue.size();i++)
				temp_Queue.add(Queue.poll());
		}
		
		//temp_Queue -> priority_Queue value 
		private static void retempQueue()
		{
			for(int i=0;i<temp_Queue.size();i++)
				Queue.add(temp_Queue.poll());
		}
	
	private static String zeroDishPriority()
	{
		String str=null;
		while(true)
		{
			if(!priority_Queue.isEmpty())
			{
				if(priority_Queue.element().substring(0, priority_Queue.element().indexOf("."))=="serving")
					temp_Queue.add(priority_Queue.poll());
				else if(priority_Queue.element().substring(0, priority_Queue.element().indexOf("."))=="setting"
						&& table_state[Integer.parseInt(priority_Queue.element().substring(priority_Queue.element().indexOf("0")+1))-1]==0)
						temp_Queue.add(priority_Queue.poll());
				else if(priority_Queue.element().substring(0, priority_Queue.element().indexOf("."))=="setting"
						&& table_state[Integer.parseInt(priority_Queue.element().substring(priority_Queue.element().indexOf("0")+1))-1]==1)
						
						{
							str = priority_Queue.poll();
							priorityQueue();
							repriorityQueue();
							break;
						}
				else
					temp_Queue.add(priority_Queue.poll());
			}
			else 
			{
				if(!temp_Queue.isEmpty())
					repriorityQueue();
				return null;
			}
		}
		return str;
	}
	
	private static String zeroDishQueue()
	{
		String str=null;
		while(true)
		{
			if(!Queue.isEmpty())
			{
				if(Queue.element().substring(0, Queue.element().indexOf("."))=="serving")
					temp_Queue.add(Queue.poll());
				else if(Queue.element().substring(0, Queue.element().indexOf("."))=="setting"
						&& table_state[Integer.parseInt(Queue.element().substring(Queue.element().indexOf(".")+1))-1]==0)
						temp_Queue.add(Queue.poll());
				else if(Queue.element().substring(0, Queue.element().indexOf("."))=="setting"
						&& table_state[Integer.parseInt(Queue.element().substring(Queue.element().indexOf(".")+1))-1]==1)
						{
							str = Queue.poll();
							tempQueue();
							retempQueue();
							break;
						}
				else
					temp_Queue.add(priority_Queue.poll());
			}
			else 
			{
				if(!temp_Queue.isEmpty())
					retempQueue();
				return null;
			}
		}
		return str;
	}
	
	
	private static String zeroDishClean()
	{
		String str=null;
		if(clean_Queue.isEmpty())
			return null;
		else
			str=clean_Queue.poll();
		
		return str;
	}
	
>>>>>>> parent of b85621c (Í±∞Ïùò Îã§ ÏôîÏñ¥Ïöî...)
}