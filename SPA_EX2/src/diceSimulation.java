import java.util.Random;
import java.util.Scanner;

public class diceSimulation {
	
	static int rangeMax = 6; // �ֻ��� ���� �ִ�
	static int rangeMin = 1; // �ֻ��� ���� �ּ�
	
	private static Scanner scan =  new Scanner(System.in);
	private static Random rand = new Random(System.currentTimeMillis()); 
	
	public static void main(String[] args) {
		
		// �õ尪�� ����
		// �õ尪�� ������ ��� ���� ������ ���� ������ 
		// ���� �ð��� seed������ �Ͽ� ������ �����Ѵ�.
		
		
		// ���� Ƚ�� �Է�
		System.out.print("���� Ƚ�� : ");
		int nRolls = scan.nextInt();
	
		// �� ������ ���� Ƚ���� ���� ���� �迭 (0~5) 
		// �ֻ��� ���� (������ ���� �ֻ��� ���� �� ���� ����) 
		
		int [] side = new int [6]; // ���� �ֻ��� ���� ������ �迭
		int temp = 0; // ������ ���� ���� �ֻ��� ���� ���� �� ����
		int count = 0;
		
		// �ֻ��� �� ���� ���� �迭 �ʱ�ȭ
		for(int i = 0; i<side.length; i++) {
			side[i] = 0;
		}
	
		// ������ ������ temp���� ���� ����, temp�� �迭 ũ�⸦ ���Ͽ� ���� ����	
		for(count=0; count < nRolls; count++) {
			// 0~5 ���� ������ ����
			// �ֻ��� 1�� count�� ���������� �迭�� ������ 0
			// �ֻ��� 6�� count�� ���������� �迭�� ������ 5
			// Math.abs(rand.nextInt(rangeMax)) : 0�̻� 6�̸��� ����
			temp = (int)(Math.abs(rand.nextDouble()*rangeMax) + rangeMin) - 1; 
			side[temp]++;
		}
	
		// ���õ� �ֻ��� �� ���� ���
		for (int j = 0; j < side.length; j++) {
			System.out.println( "�ֻ��� �� " + (j+1) + " ���� Ƚ�� : " + side[j]);
		}
		
		
	}		
}