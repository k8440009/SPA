import java.util.Scanner;

// 연봉과 저축액을 받아서 계산하는 함수
public class saveMoney1 {
	
	public static void main(String[] args) {
		// 입력 받을 생성자 
		Scanner scan = new Scanner(System.in);	
		// 처음 연봉
		double salary = 0;
		// 처음 저축액
		double savings = 0;
		// 연봉 인상률
		double perInc = 0;
		// 복리 이자
		double perInt = 0;	
		// 연봉에서 저축할 비율
		double savPer = 0.1;
		
		// 데이터 입력 순서
		// 연봉, 저축액, 연봉 인상률, 복리 이자 
		// 예시 -> 1200, 0, 0.09, 0.06
		salary = scan.nextDouble();
		savings = scan.nextDouble();
		perInc = scan.nextDouble();
		perInt = scan.nextDouble();
		
		// 생성자
		saveMoney1 saveMoney = new saveMoney1();
		saveMoney.calculate(salary, savings, perInc, perInt, savPer);		
	}
	
	// (연봉, 저축액, 연봉 인상률, 복리 이자, 연봉에서 저축 할 비율)을 메인에서 받아서 계산한다.
	private void calculate(double salary, double savings, double perInc, double perInt, double savPer){
		
		// 연봉 증가율, 복리 출력
		System.out.println("SALARY INCREASE RATE IS " + perInc);
		System.out.println("INTEREST RATE ON SAVINGS " + perInt + "\n\n");
		System.out.println("YEAR \t\t SALARY \t\t SAVINGS \t\t 단위 10원");	 
		
		int year;
		
		// 30년동안의 연봉, 저축액 계산
		// 0. 년도를 0부터 30까지 계속 1씩 상승, 년도를 5로 나누었을 때 나머지가 0이면 출력
		// 1. 저축액 계산 : 이전 저축액 + (이전 저축액 * 복리이자 + 연봉 * 0.1%(연봉에서 저축할 비율)) 
		// 2. 연봉 : 연봉 + 연봉 * 연봉 증가율
		// 3. 계산된 저축액과 연봉은 소숫점 1의 자리에서 반올림 한다.
		// 4. 연도가 30년이 될 때 까지 0 부터 3까지 반복
		for(year = 0; year <= 30; year++) {			
			if((year % 5) == 0) {
				System.out.println(year + " \t\t " + (Math.round(salary*100.0) /100.0)*1000 + " \t\t " +  (Math.round(savings*100.0) /100.0)*1000);	
			}
			savings = savings + (savings * perInt + salary * savPer);
			salary = salary + salary * perInc;
		}
	}
}