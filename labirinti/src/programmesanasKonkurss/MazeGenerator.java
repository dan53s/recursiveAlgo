package programmesanasKonkurss;

import java.util.Scanner;
import java.util.Random;


public class MazeGenerator {
	
	static Random ran = new Random();
	
	static String ceiling = "_";
	static String wall = "|";
	
	static String explored = "*";
	static String path = " ";
	static String start = "S";
	static String finish = "F";
	
	static int startX = 0;
	static int startY = 0;
	static int finishX = 0;
	static int finishY = 0;
	static int currentX = 0;
	static int currentY = 0;
	static int counter = 0;
	static int direction = 0;
	static int count = 0;

	static int[] previousX = new int[10000];
	static int[] previousY = new int[10000];
	
	static String[][] maze = new String[0][0];
	
	public static void main(String[] args) {
		 //make sure that width and length % 2 != 0
		Scanner sc = new Scanner(System.in);
		int width = 0;
		int height = 0;
		System.out.println("Augstumam ieteicams būt 2 reizes lielākam nekā garumam");
		System.out.print("Ierakstiet augstumu: ");
		if(sc.hasNextInt()) {
			 width = sc.nextInt();
		}
		else {
			System.out.println("Kļūda");
			sc.close();
		}
		System.out.println("");
		System.out.print("Ierakstiet garumu: ");
		if(sc.hasNextInt()) {
			 height = sc.nextInt();
		}
		else {
			System.out.println("Kļūda");
			sc.close();
			
		}
		if(width % 2 == 0) {
			 width++;
		 }
		if(height % 2 == 0) {
			 width++;
		 }
		System.out.println("");
		maze = new String[height][width];
		createMaze(width,height);
	}

	
	public static void DisplayMaze(String[][] maze) {
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length;j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static String[][] createPath(String[][] maze){
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				if(maze[i][j] == explored) {
					maze[i][j] = " ";
				}
			}
		}
		return maze;
	}
	
	public static String[][] initMaze(String[][] array){
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				
				if(i % 2 == 0 || i == array.length - 1) {
					if(j % 2 == 0) {array[i][j] = " ";}
					else {array[i][j] = ceiling;}
				}
				else {
					if(j % 2 == 0) {array[i][j] = wall;}
					else {array[i][j] = path;}
				}
			}
		}
		return array;
	}
	
	public static int[] ShuffleArray(int[] array) {
		Random rand = new Random();
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			int temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		return array;
	}
	static void IterationX(int num) {
		currentX+=num;
		maze[currentY][currentX] = " ";
		currentX+=num;
		maze[currentY][currentX] = explored;
		previousX[count] = currentX;
		previousY[count] = currentY;
		count++;
	}
	static void IterationY(int num) {
		currentY+=num;
		maze[currentY][currentX] = " ";
		currentY+=num;
		maze[currentY][currentX] = explored;
		previousX[count] = currentX;
		previousY[count] = currentY;
		count++;
	}
	
	public static void CreateEnd(int width, int height) {
		direction = ran.nextInt(4)+1;
		
		/*	_ _  top 1
		 * |   | right 2
		 * |   | down 3
		 *  _ _  left 4
		 *
		 */
		
		if(direction == 1) {
			while(true) {
				finishY = 0;
				finishX = ran.nextInt(width - 3) + 1;
				if(maze[finishY][finishX] != path && (maze[finishY + 2][finishX] != ceiling && maze[finishY + 1][finishX + 1] != wall && maze[finishY + 1][finishX - 1] != wall)) {
					maze[finishY][finishX] = finish;
					break;
				}
			}
		}
		if(direction == 2) {
			while(true) {
				finishY = ran.nextInt(height - 3) + 1;
				finishX = width;
				if(maze[finishY][finishX] != path && (maze[finishY][finishX - 2] != wall && maze[finishY - 1][finishX + 1] != ceiling && maze[finishY + 1][finishX + 1] != ceiling)) {
					maze[finishY][finishX] = finish;
					break;
				}
			}
		}
		if(direction == 3) {
			while(true) {
				finishY = height;
				finishX = ran.nextInt(width - 3) + 1;
				if(maze[finishY][finishX] != path && (maze[finishY - 2][finishX] != ceiling && maze[finishY - 1][finishX + 1] != wall && maze[finishY - 1][finishX - 1] != wall)) {
					maze[finishY][finishX] = finish;
					break;
				}
			}
		}
		if(direction == 4) {
			while(true) {
				finishY = ran.nextInt(height - 3) + 1;
				finishX = 0;
				if(maze[finishY][finishX] != path && (maze[finishY][finishX + 2] != wall && maze[finishY - 1][finishX + 1] != ceiling && maze[finishY + 1][finishX + 1] != ceiling)) {
					maze[finishY][finishX] = finish;
					break;
				}
			}
		}
	}
	
	public static void createMaze( int width, int height){
		initMaze(maze);
		
		startX = ran.nextInt((width - 2)) + 1;
		startY = ran.nextInt((height - 2)) + 1;
		
		if(startX % 2 == 0) {
			startX--;
		}
		if(startY % 2 == 0) {
			startY--;
		}
		
		maze[startY][startX] = start;
		
		currentY = startY;
		currentX = startX;
		
		int taken = 0;
		
		for(int i = 0; i < ((width*height)*10); i++) {
			
			int[] dir = {1,2,3,4};
			dir = ShuffleArray(dir);
			taken=0;
			
			for(int j=0;j<4; j++) {

			direction = dir[j];
			//up
			if(direction == 1) {
				if(currentY - 2 <=0  || currentY - 1 <= 0) {
					continue;
				}
				 if(maze[currentY - 2][currentX] != explored && maze[currentY - 2][currentX] != start){ 
					 IterationY(-1);
				}
			}
			taken++;
			
			//down
			 if(direction == 2) {
				//check if it isnt out of boundries
				if(currentY + 2 >= maze.length - 1||currentY + 1 >= maze.length - 1) {
					continue;
				}
				//check if the tile hasnt already been explored
				 if(maze[currentY + 2][currentX] != explored && maze[currentY + 2][currentX] != start) {
					 IterationY(1);
				}
			}
			 
			 taken++;
			//left
			 if(direction == 3) {
				if(currentX - 2 <= 0||currentX - 1 <= 0) {
					continue;
				}
				 if(maze[currentY][currentX - 2] != explored && maze[currentY][currentX - 2] != start){
					 IterationX(-1);
				}
			}
			 
			 taken++;
			//right
			 if(direction == 4) {
				if(currentX + 2 >= maze[currentY].length-1||currentY + 1 >= maze[currentY].length-1) {
					continue;
				}
				 if(maze[currentY][currentX + 2] != explored && maze[currentY][currentX + 2] != start){
				
					IterationX(1);
				}
			}
			 
			 taken++;
			 if(count > 0 && taken == 4) {
				 	count-=1;
				 	currentX = previousX[count];
				 	currentY = previousY[count];
			 	}
			}//end of j loop
		}//end of i loop
		createPath(maze);
		//CreateEnd( width, height);
		DisplayMaze(maze);
	}	
}
	
