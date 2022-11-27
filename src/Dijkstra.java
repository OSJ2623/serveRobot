public class Dijkstra {
	private int n = 12; //node 수
	private int[][] Graph; //어느 노드끼리 이웃해있는지
	private int[] visitList; //방문 순서(노드번호)
	private int[] distance; //거리 저장
	private boolean[] visited; //노드를 방문했는지 확인해주는 리스트
	private int[][] visit_xy; //방문 순서(노드좌표)
	private int end; //종착점
	private int[] robots; //로봇들의 현재 위치
	private int r; //움직일 로봇
	private int robot_status; //움직일 수 있는 로봇
	private int [][] node_list = {
			{200, 80},
			{300, 80},
			{400, 80},
			{200, 180},
			{400, 180},
			{200, 280},
			{400, 280},
			{200, 380},
			{300, 380},
			{400, 380},
			{200, 480},
			{400, 480}
	};
	
	public Dijkstra() {
		super();
	}
	
	//초기화
	public void init(
			int end, //목적지
			int[] robot1, //로봇 현재 위치
			int[] robot2, 
			int robot_status) {
		Graph = new int[n][n];
		visitList = new int[n];
		distance = new int[n];
		visited = new boolean[n];
		this.end = end;
		robots = new int[2];
		robots[0] = change_Num(robot1);
		robots[1] = change_Num(robot2);
		this.robot_status = robot_status;
		//초기화 한 후 바로 실행
		Do_Dijkstra();
	}
	
	//방문 순서 받아오는 함수
	public int[][] list_result(){
		return visit_xy;
	}
	
	//움직여야 하는 로봇 번호 받아오는 함수
	public int workRobot(){
		return r;
	}
	
	//목적지 노드 좌표 받아오는 함수
	public int[] dest_num(){
		
		int[] node_xy = new int[2];
		node_xy[0] = node_list[end-1][0];
		node_xy[1] = node_list[end-1][1];
		
		return node_xy;
	}
	
	public int change_Num(int[] xy) {
		
		int nodeNum = 0;
		
		for(int i = 0; i<n; i++) {
			if(node_list[i][0]==xy[0] && node_list[i][1]==xy[1]) {
				nodeNum = i;
			}
		}
		
		return nodeNum;
	}

	
	//연결된 노드들을 저장해주는 함수
	public void get_Graph(int a, int b) {
		a--;
		b--;
		Graph[a][b] = Graph[a][b] = 1;
	}
	
	//거리를 가장 큰 정수로 미리 초기화해주는 함수
	private void set_distance() {
		for(int i = 0; i < n ; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
	}
	
	//이웃한 노드들의 거리를 저장해주는 함수
	private void neighbor_dist(int start) {
		
		for(int i = 0; i<n; i++) {
			
			if(!visited[i] && Graph[start][i]!=0) {
				distance[i] = 1;
				visitList[i] = start;
			}
		}
	}
	
	//더 효율적인 루트가 있을 시 반영해주는 함수
	private void update_dist(int start) {
		
		
		for(int i = 0; i<n-1 ; i++) {
			
			int min = Integer.MAX_VALUE;
			int minNode = -1;
			
			for(int j = 0; j<n; j++) {
				
				if(!visited[j] && distance[j]<Integer.MAX_VALUE) {
					if(min>distance[j]) {
						min = distance[j];
						minNode = j;
					}
				}
			}
<<<<<<< HEAD
			System.out.println("minNode ::::: " + minNode);
			//visit that node
=======
			
>>>>>>> parent of dae8b68 (다익스트라 주석 수정)
			visited[minNode] = true;
			
			for(int k = 0; k<n; k++) {
				
				if(!visited[k] && Graph[minNode][k]!=0) {
					
					if(distance[k] > distance[minNode]+Graph[minNode][k]) {
						distance[k] = distance[minNode]+Graph[minNode][k];
						visitList[k] = minNode;
					}
				}
			}
		}
	}
	
	//어느 로봇이 더 가까이 있는지 반환해주는 함수
	private int robot_num(int robot1, int robot2, int robot_status) {
		
		if(robot_status == 1 || robot_status == 2) {
			return robot_status-1;
		}
		
		if(distance[robot1]> distance[robot2]) {
    		return 1;
    	}
    	else
    		return 0;
	}
	
	//노드번호로 저장된 리스트를 좌표값으로 바꿔주는 함수
	private int[][] get_location_list(int[] list, int length){
		
		int [][] location_list = new int[length+1][2];
		
		for(int i = 0; i<length ; i++) {
			location_list[i][0] = node_list[list[i]][0];
			location_list[i][1] = node_list[list[i]][1];
		} 
		
		return location_list;
	}
	
	//현재 map을 그대로 저장해주는 함수
	private void set_Graph() {
		get_Graph(1,2);
		get_Graph(1,4);
		get_Graph(2,1);
		get_Graph(2,3);
		get_Graph(3,2);
		get_Graph(3,5);
		get_Graph(4,1);
		get_Graph(4,6);
		get_Graph(5,3);
		get_Graph(5,7);
		get_Graph(6,4);
		get_Graph(6,8);
		get_Graph(7,5);
		get_Graph(7,10);
		get_Graph(8,6);
		get_Graph(8,9);
		get_Graph(8,11);
		get_Graph(9,8);
		get_Graph(9,10);
		get_Graph(10,7);
		get_Graph(10,9);
		get_Graph(10,12);
		get_Graph(11,8);
		get_Graph(12,10);
	}
	
	//함수들을 필요한대로 실행시켜주는 함수
	public void Do_Dijkstra() {
		
		int start = end;
		int robot1 = robots[0];
		int robot2 = robots[1];
		
		start--;
		
		set_Graph();
		set_distance();
		
		distance[start] = 0;
		visited[start] = true;
		visitList[start] = start;
		
		neighbor_dist(start);
		
		update_dist(start);
		
		int Robot = robot_num(robot1, robot2, robot_status);
		r = Robot+1;
		
		//종착점에서 로봇위치까지 거꾸로 list에 저장해주는 함수
		for(int i=0; i<n; i++) {
			int[] route = new int[n];
			for(int k = 0 ; k<n ;k++) {
				route[k] = 0;
			}
			int index = i;
			int j = 0;
			while(true) {
				if(visitList[index] == index) break; 
				route[j]= visitList[index];
				index = visitList[index]; 
				j++;
			}
			
			//종착점에서 로봇위치까지의 결과 저장하고 종료
			if(i == robots[Robot]) {
				
				int[][] location_list = new int[j][2];
				
				location_list = get_location_list(route, j);
				
				visit_xy = location_list;
				
				break;
			}
		}
		
	}
	
}
