import java.util.Random;
import java.util.Scanner;

public class diceSimulation {
	
	static int rangeMax = 6; // 주사위 숫자 최대
	static int rangeMin = 1; // 주사위 숫자 최소
	
	private static Scanner scan =  new Scanner(System.in);
	private static Random rand = new Random(System.currentTimeMillis()); 
	
	public static void main(String[] args) {
		
		// 시드값을 생성
		// 시드값이 정해진 경우 난수 패턴이 같이 때문에 
		// 현재 시간을 seed값으로 하여 난수를 생성한다.
		
		
		// 시행 횟수 입력
		System.out.print("시행 횟수 : ");
		int nRolls = scan.nextInt();
	
		// 각 숫자의 출현 횟수를 세기 위한 배열 (0~5) 
		// 주사위 숫자 (난수로 돌린 주사위 숫자 값 저장 변수) 
		
		int [] side = new int [6]; // 나온 주사위 눈을 저장할 배열
		int temp = 0; // 난수를 통해 계산된 주사위 눈을 저장 할 변수
		int count = 0;
		
		// 주사위 눈 시행 갯수 배열 초기화
		for(int i = 0; i<side.length; i++) {
			side[i] = 0;
		}
	
		// 난수를 돌리고 temp값에 값을 저장, temp와 배열 크기를 비교하여 값을 저장	
		for(count=0; count < nRolls; count++) {
			// 0~5 사이 난수값 생성
			// 주사위 1의 count를 증가시켜줄 배열의 변수는 0
			// 주사위 6의 count를 증가시켜줄 배열의 변수는 5
			// Math.abs(rand.nextInt(rangeMax)) : 0이상 6미만의 숫자
			temp = (int)(Math.abs(rand.nextDouble()*rangeMax) + rangeMin) - 1; 
			side[temp]++;
		}
	
		// 선택된 주사위 눈 갯수 출력
		for (int j = 0; j < side.length; j++) {
			System.out.println( "주사위 눈 " + (j+1) + " 선택 횟수 : " + side[j]);
		}
		
		
	}		
}