import java.util.Random;
import java.util.Scanner;

public class gasStation {
	public static void main(String[] args) {
		/*
		 * 1) 포아송 분포를 사용 할 것인지, 정규분포를 사용 할 것인지 묻는다. 
		 * 		몇번을 실행 할 것인지 묻는다. 중간과정을 출력할 것인지 묻는다.
		 * 2-1) 포아송 분포를 사용하면  rambda 값을 입력 받는다.
		 * 2-2) 정규분포를 사용 할 경우 mean 과 stdev값을 입력 받는다.
		 * 3) 반복 횟수만 큼 실행하고 큐의 평균 길이를 출력한다.
		 * 
		 */
		
		// 포아송 분포, 정규 분포, 랜덤함수, 입력 생성자
		Scanner scan = new Scanner(System.in);
		Random r = new Random(System.currentTimeMillis());
		poisson pos = new poisson();
		normal no = new normal();
		
		// 시간에 대한 변수
		double time = 0;
		double tLimit = 100;
		double tStep = 1.0;
		
		// 큐에 대한 변수
		double ave = 0; 
		int queue = 0;
		double aveQue = 0;
		int arrive = 0;
		int totQue = 0;
		
		// 실험 변수
		int select0 = 1; // 실험 횟수 입력
		int select1 = 0; // 정규분포 계산, 포아송 분포 계산 결정
		int room = 1; // 창구의 갯수
		double tPump []; // 창구 배열 선언
		double mean = 0.0; // 평균값
		double stdev = 0.0; // 표준편차
		double rambda = 0.0; // 람다값
		double work = 0.0; // 1분당 넣을 리터
		
		// 고객 변수
		int people = 0; // 시간 당 고객의 수
		double prArr = 0.0; // 1/고객의 수
		
		// 실험 횟수 입력
		System.out.print("실험 횟수 : ");
		select0 = scan.nextInt();
		
		// 서버 갯수 입력
		System.out.print("창구의 갯수 : ");
		room = scan.nextInt();
		tPump = new double[room];
		
		// 정규분포를 사용한다면 0, 포아송 분포를 사용한다면 1
		System.out.println("정규분포를 사용할 것이면 0, 포아송 분포를 사용할 것이면 1번을 입력한다.");
		select1 = scan.nextInt();

		// 정규분포 계산
		if(select1 == 0) {
			
			System.out.print("mean값을 입력하시오 : ");
			mean = scan.nextDouble();
			System.out.print("stdev값을 입력하시오 : ");
			stdev = scan.nextDouble();
			System.out.print("1분 동안 주유되는 리터 : ");
			work = scan.nextDouble();
			System.out.print("한시간 동안 도착 할 수 있는 고객의 수 : ");
			people = scan.nextInt();
			prArr = (double)(people / 60.0);
			
			System.out.println("창구의 갯수 : " + room);
			System.out.println("시간의 간격 : " + tStep + "분");
			System.out.println("목표 시간 : " + tLimit + "분");
			System.out.println("기댓값 : " + mean);
			System.out.println("표준편차 : " + stdev);
			System.out.println("1분 동안 주유되는 리터 : " + work);
			System.out.println("한시간 동안 도착 할 수 있는 고객의 수 : " + people);
			System.out.print("TIME" + "\t\t" + "ARRIVAL" + "\t\t" + "QUEUE" + "\t\t");
			for (int i = 0; i < tPump.length; i++) {
				System.out.print("tPump["+i+"]"+"\t");
			}
			System.out.println();
			
			// 연산 
			for (int i = 0; i < select0; i++) {
				// 초기화
				time = 0;
				arrive = 0;
				
				for (int k = 0; k < tPump.length; k++) {
					tPump[k] = 0;
				}
				
				queue = 0;
				totQue = 0;
				aveQue = 0;
				int j = 0;

				System.out.println("시행 차 : " + (i+1) + "회");
				
				// 제한 시간이 될 때까지 반복
				while (time < tLimit) {
					
					// 현재 시간 계산
					time = time + tStep;
					arrive = 0;
					// 난수값이 prArr보다 작으면 손님 하나를 발생시킨다.
					if (r.nextDouble() < prArr * tStep) {
						arrive = 1;
						queue = queue + arrive; // 큐에 대기하고 있는 사람의 값을 증가

					}
					
					j = 0; // tpump1,2
					while (j < tPump.length) {
						// 고객에 대한 봉사시간이 남아있는 경우
						if (tPump[j] > 0) {
							tPump[j] = tPump[j] - tStep;

							// 고객에 대한 봉사시간이 음수가 되는 경우는 없어야 하므로 0으로 초기화한다.
							if (tPump[j] < 0) 
								tPump[j] = 0;
						}
						// 고객에 대한 봉사시간이 0인 경우면서 큐에 사람이 차있는 경우
						if (tPump[j] == 0 && queue != 0) {
							queue--; // 큐에 있는 사람을 빼준다.
							// 정규분포로 서비스 시간 계산
							tPump[j] = 1.0 + (1.0/work) * no.getNormalRandom(mean, stdev); 
						}						
						j++; // 복수창구 갯수만큼 반복
						// 대기행렬의 전체 합
						totQue = totQue + queue;
						// 고객의 평균 대기 시간
						aveQue = totQue / (tLimit / tStep);
					}
					// 각 창구에 부여된 서비스시간 출력
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
				System.out.println("고객의 평균 대기시간 : " + aveQue + "분");
				System.out.println("전체 대기인원수 : " + totQue + "명");
				
			}
			// 전체 횟수의 평균 대기시간을 구한다.
			System.out.println("평균 대기 시간 : " + ave/select0/room);
			
		} // if 문끝
		
		// else문(포아송)
		else {
			System.out.print("rambda값을 입력하시오 : ");
			rambda = scan.nextDouble();
			System.out.print("한시간 동안 도착 할 수 있는 고객의 수 : ");
			people = scan.nextInt();
			prArr = (double)(people / 60.0);		
			System.out.println("시간의 간격 : " + tStep + "분");
			System.out.println("목표 시간 : " + tLimit + "분");
			System.out.println("고객이 1분내 도착할 확률 : " + prArr);
			System.out.println("포아송분포 기댓값 : " + rambda);
			System.out.println("창구의 갯수 : " + room);
			System.out.print("TIME" + "\t\t" + "ARRIVAL" + "\t\t" + "QUEUE" + "\t\t");
			for (int i = 0; i < tPump.length; i++) {
				System.out.print("tPump["+i+"]"+"\t");
			}
			System.out.println();
			
			for (int i = 0; i < select0; i++) {
				// 초기화
				time = 0;
				arrive = 0;
				
				for (int k = 0; k < tPump.length; k++) {
					tPump[k] = 0;
				}
				
				queue = 0;
				totQue = 0;
				aveQue = 0;
				int j = 0;

				System.out.println("시행 차 : " + (i+1) + "회");
				
				// 제한 시간이 될 때까지 반복
				while (time < tLimit) {
					
					// 현재 시간 계산
					time = time + tStep;
					arrive = 0;
					// 난수값이 prArr보다 작으면 손님 하나를 발생시킨다.
					if (r.nextDouble() < prArr * tStep) {
						arrive = 1;
						queue = queue + arrive; // 큐에 대기하고 있는 사람의 값을 증가

					}
					
					j = 0; // tpump1,2
					while (j < tPump.length) {
						// 고객에 대한 봉사시간이 남아있는 경우
						if (tPump[j] > 0) {
							tPump[j] = tPump[j] - tStep;

							// 고객에 대한 봉사시간이 음수가 되는 경우는 없어야 하므로 0으로 초기화한다.
							if (tPump[j] < 0) 
								tPump[j] = 0;
						}
						// 고객에 대한 봉사시간이 0인 경우면서 큐에 사람이 차있는 경우
						if (tPump[j] == 0 && queue != 0) {
							queue--; // 큐에 있는 사람을 빼준다.
							// 포아송분포를 사용하여 고객에 대한 봉사서비스 시간을 계산한다.
							tPump[j] = pos.getPoissonRandom(rambda); 
						}						
						j++; // 복수창구 갯수만큼 반복
						// 대기행렬의 전체 합
						totQue = totQue + queue;
						// 고객의 평균 대기 시간
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
				System.out.println("고객의 평균 대기시간 : " + aveQue + "분");
				System.out.println("전체 대기인원수 : " + totQue + "명");
				
			}
			System.out.println("평균 대기 시간 : " + ave/select0/room);

		} // else 종료
			
	}
}// 메인 클래스 종료

// 포아송 분포(평균값)
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
} // 포아송 클래스 종료

// 정규분포(표준 편차, 평균값)
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
} // normal 클래스 종료