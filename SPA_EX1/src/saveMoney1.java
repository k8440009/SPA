import java.util.Scanner;

// ������ ������� �޾Ƽ� ����ϴ� �Լ�
public class saveMoney1 {
	
	public static void main(String[] args) {
		// �Է� ���� ������ 
		Scanner scan = new Scanner(System.in);	
		// ó�� ����
		double salary = 0;
		// ó�� �����
		double savings = 0;
		// ���� �λ��
		double perInc = 0;
		// ���� ����
		double perInt = 0;	
		// �������� ������ ����
		double savPer = 0.1;
		
		// ������ �Է� ����
		// ����, �����, ���� �λ��, ���� ���� 
		// ���� -> 1200, 0, 0.09, 0.06
		salary = scan.nextDouble();
		savings = scan.nextDouble();
		perInc = scan.nextDouble();
		perInt = scan.nextDouble();
		
		// ������
		saveMoney1 saveMoney = new saveMoney1();
		saveMoney.calculate(salary, savings, perInc, perInt, savPer);		
	}
	
	// (����, �����, ���� �λ��, ���� ����, �������� ���� �� ����)�� ���ο��� �޾Ƽ� ����Ѵ�.
	private void calculate(double salary, double savings, double perInc, double perInt, double savPer){
		
		// ���� ������, ���� ���
		System.out.println("SALARY INCREASE RATE IS " + perInc);
		System.out.println("INTEREST RATE ON SAVINGS " + perInt + "\n\n");
		System.out.println("YEAR \t\t SALARY \t\t SAVINGS \t\t ���� 10��");	 
		
		int year;
		
		// 30�⵿���� ����, ����� ���
		// 0. �⵵�� 0���� 30���� ��� 1�� ���, �⵵�� 5�� �������� �� �������� 0�̸� ���
		// 1. ����� ��� : ���� ����� + (���� ����� * �������� + ���� * 0.1%(�������� ������ ����)) 
		// 2. ���� : ���� + ���� * ���� ������
		// 3. ���� ����װ� ������ �Ҽ��� 1�� �ڸ����� �ݿø� �Ѵ�.
		// 4. ������ 30���� �� �� ���� 0 ���� 3���� �ݺ�
		for(year = 0; year <= 30; year++) {			
			if((year % 5) == 0) {
				System.out.println(year + " \t\t " + (Math.round(salary*100.0) /100.0)*1000 + " \t\t " +  (Math.round(savings*100.0) /100.0)*1000);	
			}
			savings = savings + (savings * perInt + salary * savPer);
			salary = salary + salary * perInc;
		}
	}
}