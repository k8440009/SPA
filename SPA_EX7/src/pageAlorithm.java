import java.util.Random;
import java.util.Scanner;

import javax.xml.ws.FaultAction;

public class pageAlorithm {

	// �޸� ũ��
	int memorySize = 5;
	// ���� ��Ʈ�� ����
	int stringSize = 1000;
	// ������ ����
	static int Min = 1;
	static int Max = 1000;

	// ���
	static int randAvg = 0;
	static int fifoAvg = 0;
	static int secondAvg = 0;
	static int lruAvg = 0;
	static int lfuAvg = 0;
	static int time = 100;
	// ���� ���� �迭 ����
	int randomString[] = new int[stringSize];
	// ���� ���� �迭
	// int refString [] = {2,3,2,1,5,2,4,5,3,2,5,2};
	// int refString [] = {1,2,6,1,4,5,1,2,1,4,5,6,4,5};
	// ��ȸ
	int chance[] = new int[memorySize];
	// ī��Ʈ

	// ������ ��Ʈ
	int pageFault = 0;
	static int select = 0;

	public static void main(String[] args) {
		// ������
		Random r = new Random(System.currentTimeMillis());
		pageAlorithm pa = new pageAlorithm();
		Scanner scan = new Scanner(System.in);
		FIFO fifo = new FIFO();
		LRU lru = new LRU();
		Rand rand = new Rand();
		LFU lfu = new LFU();

		// ���� �迭 ����

		// �Է� �κ�
		// ���� ����
		// System.out.print("�ִ� ���ڸ� �Է��Ͻÿ� : ");
		// pa.Max = scan.nextInt();
		// �޸� ũ��
		System.out.print("����  Ƚ���� �Է��Ͻÿ� : ");
		pa.time = scan.nextInt();
		System.out.print("������ ũ�⸦ �Է��Ͻÿ� : ");
		pa.memorySize = scan.nextInt();
		System.out.println();
		
		for (int i = 0; i < pa.randomString.length; i++) {
			pa.randomString[i] = (int) (r.nextDouble() * Max) + Min;
		}
		
		// ���� �� ������ �˰���
		System.out.println("1. Random 2. FIFO 3. LRU 4. LFU 5. TOTAL");
		System.out.print("�Է� �Ͻÿ� : ");
		pa.select = scan.nextInt();

		switch (select) {
		case 1:
			for (int i = 0; i < pa.time; i++) {
				rand.getRandom(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("��� Random ������ fault Ƚ�� : " + (pa.randAvg / pa.time));
			break;
		case 2:
			for (int i = 0; i < pa.time; i++) {
				fifo.getFifo(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("��� FIFO ������ fault Ƚ�� : " + (pa.fifoAvg / pa.time));
			break;
		case 3:
			for (int i = 0; i < pa.time; i++) {
				lru.getLRU(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("��� LRU ������ fault Ƚ�� : " + (pa.lruAvg / pa.time));
			break;
		case 4:
			for (int i = 0; i < pa.time; i++) {
				lfu.getLFU(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("��� LFU ������ fault Ƚ�� : " + (pa.lfuAvg / pa.time));
			break;
		case 5:
			for (int i = 0; i < pa.time; i++) {
				rand.getRandom(pa.randomString, pa.memorySize, pa.stringSize);
				fifo.getFifo(pa.randomString, pa.memorySize, pa.stringSize);
				lfu.getLFU(pa.randomString, pa.memorySize, pa.stringSize);
				lru.getLRU(pa.randomString, pa.memorySize, pa.stringSize);			
			}
			System.out.println("��� Random ������ fault Ƚ�� : " + (pa.randAvg / pa.time));
			System.out.println("��� FIFO ������ fault Ƚ�� : " + (pa.fifoAvg / pa.time));
			System.out.println("��� LFU ������ fault Ƚ�� : " + (pa.lfuAvg / time));
			System.out.println("��� LRU ������ fault Ƚ�� : " + (pa.lruAvg / time));
			break;

		}
	}
}

// LRU �˰���
class LRU {
	public void getLRU(int randomString[], int memorySize, int stringSize) {

		// ������ �� ���� ����
		pageAlorithm pa = new pageAlorithm();
		int lru[] = new int[memorySize];
		// ������ fault ���� �ʱ�ȭ
		pa.pageFault = 0;

		// �ʱ�ȭ
		// LRU �迭 �ʱ�ȭ
		for (int i = 0; i < lru.length; i++) {
			lru[i] = 0;
		}
		// ���� �迭 ���
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();

		// �ʱ� �迭 ���
		System.out.print("�ʱ� �迭 �� : ");
		for (int i = 0; i < lru.length; i++) {
			System.out.print(lru[i] + " ");
		}
		System.out.println();

		// �����
		System.out.println("LRU");
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "ȸ"); // ȸ�� ���
			System.out.println("�䱸�� ������ : " + randomString[i]);

			for (int j = 0; j < lru.length; j++) {
				// ã���� ��, ������ fault ����
				if (lru[j % memorySize] == randomString[i]) {
					for (int k = j; k >= 1; k--) {
						lru[k] = lru[k - 1];
					}
					lru[0] = randomString[i];
					break;
				}

				// ã�� ������ ��
				if (j == (lru.length - 1)) {
					for (int k = (memorySize - 1); k >= 1; k--) {
						lru[k] = lru[k - 1];
					}
					lru[0] = randomString[i]; // ���� �Էµ� ������ ��ü
					pa.pageFault++; // ������ fault �� ����
				}
			}

			// ����� LRU �迭 �� ������ fault �� ���
			/*
			 for (int j = 0; j < lru.length; j++) {
				System.out.print(lru[j] + " ");
			}
			System.out.println();
			 System.out.println("������ falut : " + pa.pageFault + "\n");
			 */

		}
		// LRU �˰��� ��� ������ fault �� ���
		pa.lruAvg = pa.lruAvg + pa.pageFault;
		System.out.println();

	}
}

// FIFO ���
class FIFO {
	public void getFifo(int randomString[], int memorySize, int stringSize) {
		// ������ �� ���� ����
		pageAlorithm pa = new pageAlorithm();
		int fifo[] = new int[memorySize];
		// ������ fault ���� �ʱ�ȭ
		pa.pageFault = 0;

		// �ʱ�ȭ
		// FIFO �迭 �ʱ�ȭ
		for (int i = 0; i < fifo.length; i++) {
			fifo[i] = 0;
		}

		// ���� �迭 ���
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();

		// �ʱ� �迭 ���
		System.out.print("�ʱ� �迭 �� : ");
		for (int i = 0; i < fifo.length; i++) {
			System.out.print(fifo[i] + " ");
		}
		System.out.println();

		// �����
		int k = 0;
		System.out.println("FIFO");
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "ȸ"); // ȸ�� ���
			System.out.println("�䱸�� ������ : " + randomString[i]);

			for (int j = 0; j < fifo.length; j++) {
				// ã���� ��
				if (fifo[j % memorySize] == randomString[i]) {
					break; // ������ fault ����
				}

				// ã�� ������ ��
				if (j == (fifo.length - 1)) {
					fifo[k % memorySize] = randomString[i];
					k++;
					pa.pageFault++;
				}
			}

		/*	// ����� FIFO �迭 �� ������ fault �� ���
			for (int j = 0; j < fifo.length; j++) {
				System.out.print(fifo[j] + " " + "");
			}
			System.out.println();
			System.out.println("������ falut : " + pa.pageFault + "\n");
			*/
		 }
		// fifo �˰��� ��� ������ fault �� ���
		pa.fifoAvg = pa.fifoAvg + pa.pageFault;
		System.out.println();
	}

}

class Rand {
	public void getRandom(int randomString[], int memorySize, int stringSize) {
		// ������ �� ���� ����
		pageAlorithm pa = new pageAlorithm();
		int rand[] = new int[memorySize];
		Random r = new Random(System.currentTimeMillis());

		// �ʱ�ȭ
		// rand �迭 �ʱ�ȭ
		for (int i = 0; i < rand.length; i++) {
			rand[i] = 0;
		}
		// ���� �迭 ���
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();
		// �ʱ� �迭 ���
		System.out.print("�ʱ� �迭 �� : ");
		for (int i = 0; i < rand.length; i++) {
			System.out.print(rand[i] + " ");
		}
		System.out.println();
		// ������ fault ���� �ʱ�ȭ
		pa.pageFault = 0;
		// �����
		System.out.println("Random");
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "ȸ"); // ȸ�� ���
			System.out.println("�䱸�� ������ : " + randomString[i]);
			for (int j = 0; j < rand.length; j++) {
				// ã���� ��
				if (rand[j % memorySize] == randomString[i]) {
					break; // ������ fault ����
				}
				// �� ã���� ��
				if (j == (rand.length - 1)) {
					rand[(int) (r.nextDouble() * (memorySize))] = randomString[i]; // ���� �Էµ� ������ ��ü
					pa.pageFault++; // ������ fault �� ����
				}
			}
			// ����� Rand �迭 �� ������ fault �� ���
			/*
			for (int j = 0; j < rand.length; j++) {
				System.out.print(rand[j] + " ");
			}
			System.out.println();
			System.out.println("������ falut : " + pa.pageFault + "\n");
			*/
		}
		// Rand �˰��� ��� ������ fault �� ���
		pa.randAvg = pa.randAvg + pa.pageFault;
		System.out.println();
	}
}

class LFU {
	public void getLFU(int randomString[], int memorySize, int stringSize) {
		pageAlorithm pa = new pageAlorithm();
		int lfu[] = new int[memorySize];
		int min = 0;
		int count [] = new int [memorySize];
		
		for (int i = 0; i < lfu.length; i++) {
			lfu[i] = 0;
		}
		
		for (int i = 0; i < count.length; i++) {
			count[i] = 0;
		}

		// ���� �迭 ���
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();

		System.out.print("�ʱ� �迭 �� : ");
		for (int i = 0; i < lfu.length; i++) {
			System.out.print(lfu[i] + " ");
		}
		System.out.println();
		// ������ fault ���� �ʱ�ȭ
		pa.pageFault = 0;
		int k = 0;
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "ȸ"); // ȸ�� ���
			System.out.println("�䱸�� ������ : " + randomString[i]);

			for (int j = 0; j < lfu.length; j++) {
				// ã���� ��
				if (lfu[j % memorySize] == randomString[i]) {
					count[j%memorySize]++;
					break; // ������ fault ����	
				}

				// ã�� ������ ��
				if (j == (lfu.length - 1)) {
					min = count[j];
					// �ּ� ���� ���Ѵ�.
					for (int j2 = 0; j2 < count.length; j2++) {
						if(count[j2] < min) {
							min = count[j2];
						}
					}
					
					for (int j2 = 0; j2 < count.length; j2++) {
						if(lfu[j2] == 0) {
							lfu[j2] = randomString[i];
							count[j2]=1;
							pa.pageFault++;
							break;
						}
						// �ּ� ī��Ʈ�� ���� j2�� ã�´�.
						if(count[j2] == min) {
							lfu[j2] = randomString[i];
							count[j2]=1;
							pa.pageFault++;
							break;
						}
						
					}
					

				}
			}

			/*
			// ����� FIFO �迭 �� ������ fault �� ���
			for (int j = 0; j < lfu.length; j++) {
				System.out.print(lfu[j] + " " + "");
			}
			System.out.println();
			
			
			for (int j = 0; j < count.length; j++) {
				System.out.print(count[j] + " " + "");
			}
			System.out.println();
			System.out.println("������ falut : " + pa.pageFault + "\n");
			*/
		}
		pa.lfuAvg = pa.lfuAvg + pa.pageFault;
		System.out.println();
	}
}
