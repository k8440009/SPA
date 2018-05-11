import java.util.Random;
import java.util.Scanner;

public class gasStation {
	public static void main(String[] args) {
		/*
		 * 1) ���Ƽ� ������ ��� �� ������, ���Ժ����� ��� �� ������ ���´�. 
		 * 		����� ���� �� ������ ���´�. �߰������� ����� ������ ���´�.
		 * 2-1) ���Ƽ� ������ ����ϸ�  rambda ���� �Է� �޴´�.
		 * 2-2) ���Ժ����� ��� �� ��� mean �� stdev���� �Է� �޴´�.
		 * 3) �ݺ� Ƚ���� ŭ �����ϰ� ť�� ��� ���̸� ����Ѵ�.
		 * 
		 */
		
		// ���Ƽ� ����, ���� ����, �����Լ�, �Է� ������
		Scanner scan = new Scanner(System.in);
		Random r = new Random(System.currentTimeMillis());
		poisson pos = new poisson();
		normal no = new normal();
		
		// �ð��� ���� ����
		double time = 0;
		double tLimit = 100;
		double tStep = 1.0;
		
		// ť�� ���� ����
		double ave = 0; 
		int queue = 0;
		double aveQue = 0;
		int arrive = 0;
		int totQue = 0;
		
		// ���� ����
		int select0 = 1; // ���� Ƚ�� �Է�
		int select1 = 0; // ���Ժ��� ���, ���Ƽ� ���� ��� ����
		int room = 1; // â���� ����
		double tPump []; // â�� �迭 ����
		double mean = 0.0; // ��հ�
		double stdev = 0.0; // ǥ������
		double rambda = 0.0; // ���ٰ�
		double work = 0.0; // 1�д� ���� ����
		
		// �� ����
		int people = 0; // �ð� �� ���� ��
		double prArr = 0.0; // 1/���� ��
		
		// ���� Ƚ�� �Է�
		System.out.print("���� Ƚ�� : ");
		select0 = scan.nextInt();
		
		// ���� ���� �Է�
		System.out.print("â���� ���� : ");
		room = scan.nextInt();
		tPump = new double[room];
		
		// ���Ժ����� ����Ѵٸ� 0, ���Ƽ� ������ ����Ѵٸ� 1
		System.out.println("���Ժ����� ����� ���̸� 0, ���Ƽ� ������ ����� ���̸� 1���� �Է��Ѵ�.");
		select1 = scan.nextInt();

		// ���Ժ��� ���
		if(select1 == 0) {
			
			System.out.print("mean���� �Է��Ͻÿ� : ");
			mean = scan.nextDouble();
			System.out.print("stdev���� �Է��Ͻÿ� : ");
			stdev = scan.nextDouble();
			System.out.print("1�� ���� �����Ǵ� ���� : ");
			work = scan.nextDouble();
			System.out.print("�ѽð� ���� ���� �� �� �ִ� ���� �� : ");
			people = scan.nextInt();
			prArr = (double)(people / 60.0);
			
			System.out.println("â���� ���� : " + room);
			System.out.println("�ð��� ���� : " + tStep + "��");
			System.out.println("��ǥ �ð� : " + tLimit + "��");
			System.out.println("��� : " + mean);
			System.out.println("ǥ������ : " + stdev);
			System.out.println("1�� ���� �����Ǵ� ���� : " + work);
			System.out.println("�ѽð� ���� ���� �� �� �ִ� ���� �� : " + people);
			System.out.print("TIME" + "\t\t" + "ARRIVAL" + "\t\t" + "QUEUE" + "\t\t");
			for (int i = 0; i < tPump.length; i++) {
				System.out.print("tPump["+i+"]"+"\t");
			}
			System.out.println();
			
			// ���� 
			for (int i = 0; i < select0; i++) {
				// �ʱ�ȭ
				time = 0;
				arrive = 0;
				
				for (int k = 0; k < tPump.length; k++) {
					tPump[k] = 0;
				}
				
				queue = 0;
				totQue = 0;
				aveQue = 0;
				int j = 0;

				System.out.println("���� �� : " + (i+1) + "ȸ");
				
				// ���� �ð��� �� ������ �ݺ�
				while (time < tLimit) {
					
					// ���� �ð� ���
					time = time + tStep;
					arrive = 0;
					// �������� prArr���� ������ �մ� �ϳ��� �߻���Ų��.
					if (r.nextDouble() < prArr * tStep) {
						arrive = 1;
						queue = queue + arrive; // ť�� ����ϰ� �ִ� ����� ���� ����

					}
					
					j = 0; // tpump1,2
					while (j < tPump.length) {
						// ���� ���� ����ð��� �����ִ� ���
						if (tPump[j] > 0) {
							tPump[j] = tPump[j] - tStep;

							// ���� ���� ����ð��� ������ �Ǵ� ���� ����� �ϹǷ� 0���� �ʱ�ȭ�Ѵ�.
							if (tPump[j] < 0) 
								tPump[j] = 0;
						}
						// ���� ���� ����ð��� 0�� ���鼭 ť�� ����� ���ִ� ���
						if (tPump[j] == 0 && queue != 0) {
							queue--; // ť�� �ִ� ����� ���ش�.
							// ���Ժ����� ���� �ð� ���
							tPump[j] = 1.0 + (1.0/work) * no.getNormalRandom(mean, stdev); 
						}						
						j++; // ����â�� ������ŭ �ݺ�
						// �������� ��ü ��
						totQue = totQue + queue;
						// ���� ��� ��� �ð�
						aveQue = totQue / (tLimit / tStep);
					}
					// �� â���� �ο��� ���񽺽ð� ���
					System.out.print(time + "\t\t" + arrive + "\t\t" + queue + "\t\t");
					if(room > 1) {
						for (int k = 0; k < tPump.length; k++) {
							System.out.print((int)tPump[k] + "\t\t");
						}
						System.out.println();
					} else {
						System.out.println((int)tPump[0]);
					}
				}
				ave = ave + aveQue;
				System.out.println("���� ��� ���ð� : " + aveQue + "��");
				System.out.println("��ü ����ο��� : " + totQue + "��");
				
			}
			// ��ü Ƚ���� ��� ���ð��� ���Ѵ�.
			System.out.println("��� ��� �ð� : " + ave/select0/room);
			
		} // if ����
		
		// else��(���Ƽ�)
		else {
			System.out.print("rambda���� �Է��Ͻÿ� : ");
			rambda = scan.nextDouble();
			System.out.print("�ѽð� ���� ���� �� �� �ִ� ���� �� : ");
			people = scan.nextInt();
			prArr = (double)(people / 60.0);		
			System.out.println("�ð��� ���� : " + tStep + "��");
			System.out.println("��ǥ �ð� : " + tLimit + "��");
			System.out.println("���� 1�г� ������ Ȯ�� : " + prArr);
			System.out.println("���Ƽۺ��� ��� : " + rambda);
			System.out.println("â���� ���� : " + room);
			System.out.print("TIME" + "\t\t" + "ARRIVAL" + "\t\t" + "QUEUE" + "\t\t");
			for (int i = 0; i < tPump.length; i++) {
				System.out.print("tPump["+i+"]"+"\t");
			}
			System.out.println();
			
			for (int i = 0; i < select0; i++) {
				// �ʱ�ȭ
				time = 0;
				arrive = 0;
				
				for (int k = 0; k < tPump.length; k++) {
					tPump[k] = 0;
				}
				
				queue = 0;
				totQue = 0;
				aveQue = 0;
				int j = 0;

				System.out.println("���� �� : " + (i+1) + "ȸ");
				
				// ���� �ð��� �� ������ �ݺ�
				while (time < tLimit) {
					
					// ���� �ð� ���
					time = time + tStep;
					arrive = 0;
					// �������� prArr���� ������ �մ� �ϳ��� �߻���Ų��.
					if (r.nextDouble() < prArr * tStep) {
						arrive = 1;
						queue = queue + arrive; // ť�� ����ϰ� �ִ� ����� ���� ����

					}
					
					j = 0; // tpump1,2
					while (j < tPump.length) {
						// ���� ���� ����ð��� �����ִ� ���
						if (tPump[j] > 0) {
							tPump[j] = tPump[j] - tStep;

							// ���� ���� ����ð��� ������ �Ǵ� ���� ����� �ϹǷ� 0���� �ʱ�ȭ�Ѵ�.
							if (tPump[j] < 0) 
								tPump[j] = 0;
						}
						// ���� ���� ����ð��� 0�� ���鼭 ť�� ����� ���ִ� ���
						if (tPump[j] == 0 && queue != 0) {
							queue--; // ť�� �ִ� ����� ���ش�.
							// ���Ƽۺ����� ����Ͽ� ���� ���� ���缭�� �ð��� ����Ѵ�.
							tPump[j] = pos.getPoissonRandom(rambda); 
						}						
						j++; // ����â�� ������ŭ �ݺ�
						// �������� ��ü ��
						totQue = totQue + queue;
						// ���� ��� ��� �ð�
						aveQue = totQue / (tLimit / tStep);
					}
					System.out.print(time + "\t\t" + arrive + "\t\t" + queue + "\t\t");
					if(room > 1) {
						for (int k = 0; k < tPump.length; k++) {
							System.out.print(tPump[k] + "\t\t");	
						}
						System.out.println();
					} else {
						System.out.println(tPump[0]);
					}
				}
				ave = ave + aveQue;
				System.out.println("���� ��� ���ð� : " + aveQue + "��");
				System.out.println("��ü ����ο��� : " + totQue + "��");
				
			}
			System.out.println("��� ��� �ð� : " + ave/select0/room);

		} // else ����
			
	}
}// ���� Ŭ���� ����

// ���Ƽ� ����(��հ�)
class poisson {
	public double getPoissonRandom(double rambda) {
		Random r = new Random(System.currentTimeMillis());
		double prod = 1;
		double u;
		double b;
		int p = 0;
		b = Math.exp(-rambda);
		u = r.nextDouble();
		prod *= u;
		while(prod >= b) {
			u = r.nextDouble();
			prod *= u;
			p++;
		}
		return p;
	}
} // ���Ƽ� Ŭ���� ����

// ���Ժ���(ǥ�� ����, ��հ�)
class normal {
	public double getNormalRandom(double mean, double stdev) {
		Random r = new Random(System.currentTimeMillis());
		double r1, r2, z1, z2, result, dt, num;
		int i;
		result = 0;
		dt = 1.0/100.0;
		
		for (i = 0; i < 100; i++) {
			r1 = r.nextDouble();
			r2 = r.nextDouble();
			z1 = Math.sqrt(-2 * Math.log(r1)) * Math.cos(2 * Math.PI * r2);
			z2 = Math.sqrt(-2 * Math.log(r1)) * Math.sin(2 * Math.PI * r2);
			num = (mean * dt) + (stdev*z1 * Math.sqrt(dt));
			result += num;
		}
		
		return result;
		
	}
} // normal Ŭ���� ����