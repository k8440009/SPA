import java.util.Random;
import java.util.Scanner;

// ������ ������� �޾Ƽ� Ȯ�� ������ ����ϴ� �Լ�
public class saveMoney2 {
	
	double perInc = 0; // ���� �λ��, ���� �ְ�� ������ ������ ���� ���� ���Ѵ�.
	double perInt = 0.06; // ���� ����, ���� ���ڴ� �����Ѵ�.
	double savPer = 0.1; // �������� ���� �� ����
	
	private static Scanner scan = new Scanner(System.in);
	private static Random rand = new Random(System.currentTimeMillis()); // �õ尪�� ����

	public static void main(String[] args) {
		
		// �Է� �κ�
		System.out.print("ó�� ����, ó�� �����, ���� �ְ� �λ��, ���� ���� �λ�� �Է� : ");
		double salary = scan .nextDouble(); // ó�� ����
		double savings = scan .nextDouble(); // ó�� �����
		double perHigh = scan .nextDouble(); // ���� �ְ� �λ��
		double perLow = scan .nextDouble(); // ���� ���� �λ��

		saveMoney2 saveMoney = new saveMoney2(); // ������
		saveMoney.calculate(salary, savings, perHigh, perLow); // �Լ��� ���� ���� ����		
	}
	
	// (����, �����, ���� �ְ� �λ��, ���� ���� �λ��, ���� ������)�� ���ο��� �޾Ƽ� ����Ѵ�.
	private void calculate(double salary, double savings, double perHigh, double perLow){
		
		// ���� ������, ���� ���
		System.out.println("SALARY INCREASE RATE IS " + perHigh + " - " + perLow);
		System.out.println("INTEREST RATE ON SAVINGS " + this.perInt);
		System.out.println("YEAR \t SALARY.INC.RATE \t SALARY \t SAVINGS \t ���� ����");	 
		
		int year;
		
		// 30�⵿���� ����, ����� ���
		// 0. �⵵�� 0���� 30���� ��� 1�� ���, �⵵�� 5�� �������� �� �������� 0�̸� ���
		// 1. ����� ��� : ���� ����� + (���� ����� * �������� + ���� * 0.1%(�������� ������ ����)) 
		// 2. ���� : ���� + ���� * ���� ������
		// 3. ���� ����װ� ������ �Ҽ��� 4�� �ڸ����� �ݿø� �Ѵ�.
		// 4. ������ 30���� �� �� ���� 0 ���� 3���� �ݺ�
		for(year = 0; year <= 30; year++) {			
			if((year % 5) == 0) {
				System.out.println(year + "\t\t" + (Math.round(perInc*1000)/1000.0) + "\t\t" + 
				(Math.round(salary*1000)/1000.0)  + " \t\t " +  (Math.round(savings*1000)/1000.0));
			}
			
			// �ִ� ���������� �ּ� ������ ���� ���
			this.perInc = Math.abs(rand.nextDouble() * (perHigh - perLow) + perLow);
			// ���� ���
			savings = savings + (savings * perInt) + (salary * savPer);
			salary = salary + (salary * perInc);
		}
	}
}
