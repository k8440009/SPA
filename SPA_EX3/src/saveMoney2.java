import java.util.Random;
import java.util.Scanner;

// 연봉과 저축액을 받아서 확률 적으로 계산하는 함수
public class saveMoney2 {
	
	double perInc = 0; // 연봉 인상률, 연봉 최고와 최저를 난수를 돌린 다음 구한다.
	double perInt = 0.06; // 복리 이자, 복리 이자는 고정한다.
	double savPer = 0.1; // 연봉에서 저축 할 정도
	
	private static Scanner scan = new Scanner(System.in);
	private static Random rand = new Random(System.currentTimeMillis()); // 시드값을 생성

	public static void main(String[] args) {
		
		// 입력 부분
		System.out.print("처음 연봉, 처음 저축액, 연봉 최고 인상률, 연봉 최하 인상률 입력 : ");
		double salary = scan .nextDouble(); // 처음 연봉
		double savings = scan .nextDouble(); // 처음 저축액
		double perHigh = scan .nextDouble(); // 연봉 최고 인상률
		double perLow = scan .nextDouble(); // 연봉 최하 인상률

		saveMoney2 saveMoney = new saveMoney2(); // 생성자
		saveMoney.calculate(salary, savings, perHigh, perLow); // 함수에 값을 집어 넣음		
	}
	
	// (연봉, 저축액, 연봉 최고 인상률, 연봉 최저 인상률, 복리 이자율)을 메인에서 받아서 계산한다.
	private void calculate(double salary, double savings, double perHigh, double perLow){
		
		// 연봉 증가율, 복리 출력
		System.out.println("SALARY INCREASE RATE IS " + perHigh + " - " + perLow);
		System.out.println("INTEREST RATE ON SAVINGS " + this.perInt);
		System.out.println("YEAR \t SALARY.INC.RATE \t SALARY \t SAVINGS \t 단위 만원");	 
		
		int year;
		
		// 30년동안의 연봉, 저축액 계산
		// 0. 년도를 0부터 30까지 계속 1씩 상승, 년도를 5로 나누었을 때 나머지가 0이면 출력
		// 1. 저축액 계산 : 이전 저축액 + (이전 저축액 * 복리이자 + 연봉 * 0.1%(연봉에서 저축할 비율)) 
		// 2. 연봉 : 연봉 + 연봉 * 연봉 증가율
		// 3. 계산된 저축액과 연봉은 소숫점 4의 자리에서 반올림 한다.
		// 4. 연도가 30년이 될 때 까지 0 부터 3까지 반복
		for(year = 0; year <= 30; year++) {			
			if((year % 5) == 0) {
				System.out.println(year + "\t\t" + (Math.round(perInc*1000)/1000.0) + "\t\t" + 
				(Math.round(salary*1000)/1000.0)  + " \t\t " +  (Math.round(savings*1000)/1000.0));
			}
			
			// 최대 연봉증가율 최소 증가율 난수 계산
			this.perInc = Math.abs(rand.nextDouble() * (perHigh - perLow) + perLow);
			// 연봉 계산
			savings = savings + (savings * perInt) + (salary * savPer);
			salary = salary + (salary * perInc);
		}
	}
}
