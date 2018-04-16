import java.util.Random;

// 포아송 분포
class Poisson {
	public double getPoissonRandom(double mean) {
		// 난수 생성
		Random r = new Random(System.currentTimeMillis());
		// exp : 거듭제곱
		double L = Math.exp(-mean);
		double k = 0;
		double p = 1.0;

		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L);
		return k - 1;
	}
}

public class queueTheory1 {

	public static void main(String[] args) {

		/*
		 * 시간에 따른 분류 
		 * time : 시뮬레이션의 현재 시간 
		 * tLimit : 목표 시간 
		 * tPump : 고객에 대한 봉사시간
		 * 
		 * 큐에 대한 분류 
		 * queue : 줄에서 기다리고 있는 사람의 수 
		 * totQue : 대기행렬 큐의 전체합, 전체 고객의 총 대기시간
		 * aveQueue : 평균 큐의 길이 
		 * arrive : 0 = 고객이 도착하지 않는 경우, 1 = 고객이 도착한 경우
		 * 
		 * 포아송분포에 대한 변수 
		 * prArr : 고객이 1분내 도착할 확률 
		 * mean : 기댓값
		 *
		 */

		// 생성자
		Poisson p1 = new Poisson(); // 포아송분포 생성자
		Random rand = new Random(System.currentTimeMillis()); // 시드값 생성자

		// 시간에 대한 변수
		double time = 0;
		double tLimit = 100;
		double tPump = 0;
		double tStep = 1.0;

		// 큐에 대한 변수
		int queue = 0;
		double aveQue = 0;
		int arrive = 0;
		int totQue = 0;
		int cnt = 0;

		/*
		 * 포아송분포에 대한 변수 double mean = 1.0; 
		 * double mean = 2.0; 
		 * double mean = 4.0; 
		 * double mean = 6.0; 
		 * double mean = 8.0; 
		 * double mean = 10.0;
		 */
		double mean = 9.0;

		/*
		 * 균등분포에 대한 변수 double prArr = 0.17; // 1시간 10명 
		 * double prArr = 0.3; // 1시간 20명
		 * double prArr = 0.5; // 1시간 30명 
		 * double prArr = 0.67; // 1시간 40명 
		 * double prArr = 0.83; // 1시간 50명 
		 * double prArr = 1; // 1시간 60명
		 */
		double prArr = 0.3;

		System.out.println("시간의 간격 : " + tStep + "분");
		System.out.println("목표 시간 : " + tLimit + "분");
		System.out.println("고객이 1분내 도착할 확률 : " + prArr);
		System.out.println("포아송분포 기댓값 : " + mean);
		System.out.println("TIME		ARRIVAL		QUEUE		TPUMP");

		// 5회 반복
		for (int i = 1; i <= 5; i++) {
			// 초기화
			time = 0;
			arrive = 0;
			cnt = 0;
			tPump = 0;
			queue = 0;
			totQue = 0;
			aveQue = 0;

			System.out.println("시행 차 : " + i + "회");
			// 제한 시간이 될 때까지 반복
			while (time < tLimit) {
				// 현재 시간 계산
				time = time + tStep;
				arrive = 0;
				// 난수 발생 저장 변수
				double u = rand.nextDouble();
				// 난수값이 prArr보다 작으면 손님 하나를 발생시킨다.
				if (u < prArr * tStep) {
					arrive = 1;
					queue = queue + arrive; // 큐에 대기하고 있는 사람의 값을 증가
					// 대기인원수가 고르게 분포하는지 확인
					if (arrive == 1) {
						cnt++; // 사람이 도착하였으면 cnt 증가
					}
				}

				// 고객에 대한 봉사시간이 남아있는 경우
				if (tPump > 0) {
					tPump = tPump - tStep;

					// 고객에 대한 봉사시간이 음수가 되는 경우는 없어야 하므로 0으로 초기화한다.
					if (tPump < 0) {
						tPump = 0;
					}

				}

				// 고객에 대한 봉사시간이 0인 경우면서 큐에 사람이 차있는 경우
				if (tPump == 0 && queue != 0) {
					queue = queue - 1; // 큐에 있는 사람을 빼준다.
					tPump = p1.getPoissonRandom(mean); // 포아송분포를 사용하여 고객에 대한 봉사서비스 시간을 계산한다.
				}

				// 대기행렬의 전체 합
				totQue = totQue + queue;
				// 고객의 평균 대기 시간
				aveQue = totQue / (tLimit / tStep);
				System.out.println(time + "\t\t" + arrive + "\t\t" + queue + "\t\t" + tPump);
			}
			System.out.println("손님의 수 : " + cnt);
			System.out.println("고객의 평균 대기시간 : " + aveQue + "분");
			System.out.println("전체 대기인원수 : " + totQue + "명");
			System.out.println();
		}
	}
}
