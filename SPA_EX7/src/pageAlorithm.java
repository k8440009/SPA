import java.util.Random;
import java.util.Scanner;

import javax.xml.ws.FaultAction;

public class pageAlorithm {

	// 메모리 크기
	int memorySize = 5;
	// 참조 스트링 길이
	int stringSize = 1000;
	// 숫자의 범위
	static int Min = 1;
	static int Max = 1000;

	// 평균
	static int randAvg = 0;
	static int fifoAvg = 0;
	static int secondAvg = 0;
	static int lruAvg = 0;
	static int lfuAvg = 0;
	static int time = 100;
	// 참조 변수 배열 생성
	int randomString[] = new int[stringSize];
	// 내가 만든 배열
	// int refString [] = {2,3,2,1,5,2,4,5,3,2,5,2};
	// int refString [] = {1,2,6,1,4,5,1,2,1,4,5,6,4,5};
	// 기회
	int chance[] = new int[memorySize];
	// 카운트

	// 페이지 폴트
	int pageFault = 0;
	static int select = 0;

	public static void main(String[] args) {
		// 생성자
		Random r = new Random(System.currentTimeMillis());
		pageAlorithm pa = new pageAlorithm();
		Scanner scan = new Scanner(System.in);
		FIFO fifo = new FIFO();
		LRU lru = new LRU();
		Rand rand = new Rand();
		LFU lfu = new LFU();

		// 랜덤 배열 생성

		// 입력 부분
		// 숫자 범위
		// System.out.print("최대 숫자를 입력하시오 : ");
		// pa.Max = scan.nextInt();
		// 메모리 크기
		System.out.print("시행  횟수를 입력하시오 : ");
		pa.time = scan.nextInt();
		System.out.print("프레임 크기를 입력하시오 : ");
		pa.memorySize = scan.nextInt();
		System.out.println();
		
		for (int i = 0; i < pa.randomString.length; i++) {
			pa.randomString[i] = (int) (r.nextDouble() * Max) + Min;
		}
		
		// 선택 할 페이지 알고리즘
		System.out.println("1. Random 2. FIFO 3. LRU 4. LFU 5. TOTAL");
		System.out.print("입력 하시오 : ");
		pa.select = scan.nextInt();

		switch (select) {
		case 1:
			for (int i = 0; i < pa.time; i++) {
				rand.getRandom(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("평균 Random 페이지 fault 횟수 : " + (pa.randAvg / pa.time));
			break;
		case 2:
			for (int i = 0; i < pa.time; i++) {
				fifo.getFifo(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("평균 FIFO 페이지 fault 횟수 : " + (pa.fifoAvg / pa.time));
			break;
		case 3:
			for (int i = 0; i < pa.time; i++) {
				lru.getLRU(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("평균 LRU 페이지 fault 횟수 : " + (pa.lruAvg / pa.time));
			break;
		case 4:
			for (int i = 0; i < pa.time; i++) {
				lfu.getLFU(pa.randomString, pa.memorySize, pa.stringSize);
			}
			System.out.println("평균 LFU 페이지 fault 횟수 : " + (pa.lfuAvg / pa.time));
			break;
		case 5:
			for (int i = 0; i < pa.time; i++) {
				rand.getRandom(pa.randomString, pa.memorySize, pa.stringSize);
				fifo.getFifo(pa.randomString, pa.memorySize, pa.stringSize);
				lfu.getLFU(pa.randomString, pa.memorySize, pa.stringSize);
				lru.getLRU(pa.randomString, pa.memorySize, pa.stringSize);			
			}
			System.out.println("평균 Random 페이지 fault 횟수 : " + (pa.randAvg / pa.time));
			System.out.println("평균 FIFO 페이지 fault 횟수 : " + (pa.fifoAvg / pa.time));
			System.out.println("평균 LFU 페이지 fault 횟수 : " + (pa.lfuAvg / time));
			System.out.println("평균 LRU 페이지 fault 횟수 : " + (pa.lruAvg / time));
			break;

		}
	}
}

// LRU 알고리즘
class LRU {
	public void getLRU(int randomString[], int memorySize, int stringSize) {

		// 생성자 및 변수 선언
		pageAlorithm pa = new pageAlorithm();
		int lru[] = new int[memorySize];
		// 페이지 fault 변수 초기화
		pa.pageFault = 0;

		// 초기화
		// LRU 배열 초기화
		for (int i = 0; i < lru.length; i++) {
			lru[i] = 0;
		}
		// 랜덤 배열 출력
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();

		// 초기 배열 출력
		System.out.print("초기 배열 값 : ");
		for (int i = 0; i < lru.length; i++) {
			System.out.print(lru[i] + " ");
		}
		System.out.println();

		// 연산부
		System.out.println("LRU");
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "회"); // 회차 출력
			System.out.println("요구된 페이지 : " + randomString[i]);

			for (int j = 0; j < lru.length; j++) {
				// 찾았을 때, 페이지 fault 없음
				if (lru[j % memorySize] == randomString[i]) {
					for (int k = j; k >= 1; k--) {
						lru[k] = lru[k - 1];
					}
					lru[0] = randomString[i];
					break;
				}

				// 찾지 못했을 때
				if (j == (lru.length - 1)) {
					for (int k = (memorySize - 1); k >= 1; k--) {
						lru[k] = lru[k - 1];
					}
					lru[0] = randomString[i]; // 먼저 입력된 페이지 교체
					pa.pageFault++; // 페이지 fault 수 증가
				}
			}

			// 변경된 LRU 배열 및 페이지 fault 수 출력
			/*
			 for (int j = 0; j < lru.length; j++) {
				System.out.print(lru[j] + " ");
			}
			System.out.println();
			 System.out.println("페이지 falut : " + pa.pageFault + "\n");
			 */

		}
		// LRU 알고리즘 평균 페이지 fault 수 계산
		pa.lruAvg = pa.lruAvg + pa.pageFault;
		System.out.println();

	}
}

// FIFO 방식
class FIFO {
	public void getFifo(int randomString[], int memorySize, int stringSize) {
		// 생성자 및 변수 선언
		pageAlorithm pa = new pageAlorithm();
		int fifo[] = new int[memorySize];
		// 페이지 fault 변수 초기화
		pa.pageFault = 0;

		// 초기화
		// FIFO 배열 초기화
		for (int i = 0; i < fifo.length; i++) {
			fifo[i] = 0;
		}

		// 랜덤 배열 출력
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();

		// 초기 배열 출력
		System.out.print("초기 배열 값 : ");
		for (int i = 0; i < fifo.length; i++) {
			System.out.print(fifo[i] + " ");
		}
		System.out.println();

		// 연산부
		int k = 0;
		System.out.println("FIFO");
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "회"); // 회차 출력
			System.out.println("요구된 페이지 : " + randomString[i]);

			for (int j = 0; j < fifo.length; j++) {
				// 찾았을 떄
				if (fifo[j % memorySize] == randomString[i]) {
					break; // 페이지 fault 없음
				}

				// 찾지 못했을 때
				if (j == (fifo.length - 1)) {
					fifo[k % memorySize] = randomString[i];
					k++;
					pa.pageFault++;
				}
			}

		/*	// 변경된 FIFO 배열 및 페이지 fault 수 출력
			for (int j = 0; j < fifo.length; j++) {
				System.out.print(fifo[j] + " " + "");
			}
			System.out.println();
			System.out.println("페이지 falut : " + pa.pageFault + "\n");
			*/
		 }
		// fifo 알고리즘 평균 페이지 fault 수 계산
		pa.fifoAvg = pa.fifoAvg + pa.pageFault;
		System.out.println();
	}

}

class Rand {
	public void getRandom(int randomString[], int memorySize, int stringSize) {
		// 생성자 및 변수 선언
		pageAlorithm pa = new pageAlorithm();
		int rand[] = new int[memorySize];
		Random r = new Random(System.currentTimeMillis());

		// 초기화
		// rand 배열 초기화
		for (int i = 0; i < rand.length; i++) {
			rand[i] = 0;
		}
		// 랜덤 배열 출력
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();
		// 초기 배열 출력
		System.out.print("초기 배열 값 : ");
		for (int i = 0; i < rand.length; i++) {
			System.out.print(rand[i] + " ");
		}
		System.out.println();
		// 페이지 fault 변수 초기화
		pa.pageFault = 0;
		// 연산부
		System.out.println("Random");
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "회"); // 회차 출력
			System.out.println("요구된 페이지 : " + randomString[i]);
			for (int j = 0; j < rand.length; j++) {
				// 찾았을 떄
				if (rand[j % memorySize] == randomString[i]) {
					break; // 페이지 fault 없음
				}
				// 못 찾았을 때
				if (j == (rand.length - 1)) {
					rand[(int) (r.nextDouble() * (memorySize))] = randomString[i]; // 먼저 입력된 페이지 교체
					pa.pageFault++; // 페이지 fault 수 증가
				}
			}
			// 변경된 Rand 배열 및 페이지 fault 수 출력
			/*
			for (int j = 0; j < rand.length; j++) {
				System.out.print(rand[j] + " ");
			}
			System.out.println();
			System.out.println("페이지 falut : " + pa.pageFault + "\n");
			*/
		}
		// Rand 알고리즘 평균 페이지 fault 수 계산
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

		// 랜덤 배열 출력
		for (int i = 0; i < stringSize; i++) {
			System.out.print(randomString[i] + " ");
		}
		System.out.println();

		System.out.print("초기 배열 값 : ");
		for (int i = 0; i < lfu.length; i++) {
			System.out.print(lfu[i] + " ");
		}
		System.out.println();
		// 페이지 fault 변수 초기화
		pa.pageFault = 0;
		int k = 0;
		for (int i = 0; i < stringSize; i++) {
			System.out.println((i + 1) + "회"); // 회차 출력
			System.out.println("요구된 페이지 : " + randomString[i]);

			for (int j = 0; j < lfu.length; j++) {
				// 찾았을 떄
				if (lfu[j % memorySize] == randomString[i]) {
					count[j%memorySize]++;
					break; // 페이지 fault 없음	
				}

				// 찾지 못했을 때
				if (j == (lfu.length - 1)) {
					min = count[j];
					// 최소 값을 구한다.
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
						// 최소 카운트를 가진 j2를 찾는다.
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
			// 변경된 FIFO 배열 및 페이지 fault 수 출력
			for (int j = 0; j < lfu.length; j++) {
				System.out.print(lfu[j] + " " + "");
			}
			System.out.println();
			
			
			for (int j = 0; j < count.length; j++) {
				System.out.print(count[j] + " " + "");
			}
			System.out.println();
			System.out.println("페이지 falut : " + pa.pageFault + "\n");
			*/
		}
		pa.lfuAvg = pa.lfuAvg + pa.pageFault;
		System.out.println();
	}
}
