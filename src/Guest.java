import java.util.Calendar;
import java.util.Random;

public class Guest extends Thread{
	
	int tableNum;		// 몇번 테이블에 앉아있는지 (0~5)
	int[] entryTime = {0, 0};	// 입장 시각
	int settingTimer;	// 세팅 기다리는 타이머 count
	int servingTimer;	// 서빙 기다리는 타이머 count
	int timeToCook;		// 요리하는 시간
	int timeToStay;		// 손님이 머무르는 시간
	int[] endCook = {0, 0};		// 요리 끝나는 시각
	int[] endStay = {0, 0};		// 손님 나가는 시각
	int[] temp = {0, 0};
	int satisfaction;	// 만족도


	public Guest(int num)
	{
		init(num);
	}

	// Set guest(table) Information
	public void init(int tableNum)
	{
		this.tableNum = tableNum+1;	// 이제 1~6
		
		Calendar time = Calendar.getInstance();
		this.entryTime[0] = time.get(Calendar.MINUTE);
		this.entryTime[1] = time.get(Calendar.SECOND);
//		this.entryTime[2] = time.get(Calendar.MILLISECOND);
		
		this.settingTimer = 0;
		this.servingTimer = 0;
		
		Random random = new Random();
		//random.nextInt(max - min) + min; //min ~ max
		
		this.timeToCook = random.nextInt(420 - 300) + 300;	// 5~7분. 초단위로 씀
		this.timeToStay = random.nextInt(720 - 600) + 600;	// 10분 정도 (10~12분으로 일단 설정)
		
		this.timeToCook = timeToCook / 10;	// 10배속
		this.timeToStay = timeToStay / 10;
		
		this.satisfaction = 10;
		System.out.println("손님 "+ String.valueOf(this.tableNum) + "번 테이블에 착석");
		System.out.println(this.tableNum + "번 입장시각-" + this.entryTime[0]+":"+this.entryTime[1]);
	}
	
	public void run() {
		
		
		// 큐에 setting push
		Queueing.init("setting.", tableNum);
		// init 후에 priority 호출. 일단 init() 안에 직접 넣어둠.
		System.out.println("큐에 push: " + String.valueOf(this.tableNum) + "번 테이블 세팅");
		
		// 세팅 기다리기
		settingCountThread t1 = new settingCountThread(this);
		t1.start();
		
		// endCook 계산
		// entryTime에 timeToCook 더하기
		this.endCook[0] = (this.entryTime[0] + (this.timeToCook / 60)) % 60;	//minute
		this.endCook[1] = (this.entryTime[1] + this.timeToCook) % 60;	//second
//		this.endCook[2] = this.entryTime[2];	//millisecond
		System.out.println(this.tableNum + "번 요리완료-" + this.endCook[0]+":"+this.endCook[1]);
		
		// endCook 시간이 되면 큐에 serving push
		while(true) {
			Calendar now = Calendar.getInstance();
			this.temp[0] = now.get(Calendar.MINUTE);
			this.temp[1] = now.get(Calendar.SECOND);
//			this.temp[2] = now.get(Calendar.MILLISECOND);
			
			if(this.temp[0] == this.endCook[0] && this.temp[1] == this.endCook[1]) {
				// 큐에 serving push
				Queueing.init("serving.", this.tableNum);
				System.out.println("큐에 push: " + String.valueOf(this.tableNum) + "번 테이블 서빙");
				break;
			}
		}
		
		// 서빙 기다리기
		servingCountThread t2 = new servingCountThread(this);
		t2.start();
		
		// 서빙 완료 전까지 아무 것도 안하기
		while(true) {
			if(Queueing.isServingDone[this.tableNum-1]) {
				System.out.println("반복문 탈출");
				break;	// 서빙이 끝났다는 신호가 오면 break
			}
		}
		System.out.println("반복문 탈출2");
		// 서빙 완료 후 endStay 계산
		// 현재 시간에 timeToStay 더하기
		Calendar afterServe = Calendar.getInstance();
		this.endStay[0] = (afterServe.get(Calendar.MINUTE) + (this.timeToStay / 60)) % 60;
		this.endStay[1] = (afterServe.get(Calendar.SECOND) + this.timeToStay) % 60;
//		this.endStay[2] = afterServe.get(Calendar.MILLISECOND);
		System.out.println(this.tableNum + "번 퇴장-" + this.endStay[0]+":"+this.endStay[1]);
		
		// endStay 시간이 되면 큐에 clean push
		while(true) {
			Calendar now = Calendar.getInstance();
			this.temp[0] = now.get(Calendar.MINUTE);
			this.temp[1] = now.get(Calendar.SECOND);
//			this.temp[2] = now.get(Calendar.MILLISECOND);
			
			if(this.temp[0] == this.endStay[0] && this.temp[1] == this.endStay[1]) {
				// 큐에 clean push
				Queueing.init("clean.", tableNum);
				System.out.println("큐에 push: " + String.valueOf(this.tableNum) + "번 테이블 클린");
				break;
			}
		}
		
		// 만족도 계산하고 총합만족도로 보내기
		this.settingTimer -= 300;
		this.servingTimer -= 300;
		if(this.settingTimer > 0) {
			this.satisfaction -= this.settingTimer / 10;	// 10초 넘길 때마다 만족도가 깎임
		}
		if(this.servingTimer > 0) {
			this.satisfaction -= this.servingTimer / 10;
		}
		System.out.println(String.valueOf(this.tableNum) + "번 테이블 만족도: " + this.satisfaction);
		
		// clean 이후에 table_state 바꾸고 이런 거는 여기 말고 다른 곳에 넣기
	}
}


class settingCountThread extends Thread{
	
	Guest myG;
	
	public settingCountThread(Guest g)
	{
		myG = g;
	}
	
	public void run() {
		while(true) {
			
			if(Queueing.isSettingDone[myG.tableNum-1]) {
				System.out.println("- " + String.valueOf(myG.tableNum) + "번 세팅 카운터: " + myG.settingTimer + "초");
				break;	// 세팅이 끝났다는 신호가 오면 break
			}
		
			// 세팅 카운터 ++
			myG.settingTimer = myG.settingTimer + 1;
			//System.out.println("- " + String.valueOf(myG.tableNum) + "번 세팅 카운터: " + myG.settingTimer + "초");
			
			if(myG.settingTimer == 300) {
				// 세팅이 5분 넘게 안가고 있으면 queue에 알려주기	(제한시간말고 일단 이렇게)
				Queueing.message = "setting." + String.valueOf(myG.tableNum);
				Queueing.priority();
			}
			
			try {
				Thread.sleep(100);	// 0.1 second (10배속)
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class servingCountThread extends Thread{
	
	Guest myG;
	
	public servingCountThread(Guest g)
	{
		myG = g;
	}
	
	public void run() {
		while(true) {
			
			if(Queueing.isServingDone[myG.tableNum-1]) {
				System.out.println("- " + String.valueOf(myG.tableNum) + "번 서빙 카운터: " + myG.servingTimer + "초");
				break;	// 서빙이 끝났다는 신호가 오면 break
			}
		
			// 서빙 카운터 ++
			myG.servingTimer = myG.servingTimer + 1;
			//System.out.println("- " + String.valueOf(myG.tableNum) + "번 서빙 카운터: " + myG.servingTimer + "초");
			
			if(myG.servingTimer == 300) {
				// 서빙이 5분 넘게 안가고 있으면 queue에 알려주기	(제한시간말고 일단 이렇게)
				Queueing.message = "serving." + String.valueOf(myG.tableNum);
				Queueing.priority();
			}
			
			try {
				Thread.sleep(100);	// 0.1 second (10배속)
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


