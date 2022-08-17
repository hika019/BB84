import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BB84 {

	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		Random rand = new Random();
		ExecutorService es = Executors.newWorkStealingPool(10);


		/*
		 * n: 鍵長
		 * m: 安全性パラメータ
		 */
		final int n = 10000000;
		final int m = 100;

		// ビット列
		int[] aliceBits = new int[n + m];
		int[] bobBits = new int[n + m];


		final int parallel = (n+m)/25;

		// プロトコル
		//25に分割＆並列処理
		try{
			es.execute(() -> mainRoutine(0, parallel, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel, parallel*2, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*2, parallel*3, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*3, parallel*4, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*4, parallel*5, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*5, parallel*6, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*6, parallel*7, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*7, parallel*8, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*8, parallel*9, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*9, parallel*10, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*10, parallel*11, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*11, parallel*12, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*12, parallel*13, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*13, parallel*14, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*14, parallel*15, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*15, parallel*16, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*16, parallel*17, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*17, parallel*18, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*18, parallel*19, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*19, parallel*20, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*20, parallel*21, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*21, parallel*22, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*22, parallel*23, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*23, parallel*24, aliceBits, bobBits));
			es.execute(() -> mainRoutine(parallel*24, parallel*25, aliceBits, bobBits));
		}finally{
			es.shutdown();
			es.awaitTermination(1, TimeUnit.MINUTES);
		}
		System.out.println("送信終了");
		System.out.println();

		/*
		 * パラメータ
		 * start: 盗聴の確認を行う際の開始地点
		 * wiretap: 盗聴の有無
		 */
		int start = rand.nextInt(n);
		boolean wiretap = false;

		// 盗聴の確認
		for (int i = start; i < start + m; i++) {
			wiretap = wiretap || (aliceBits[i] != bobBits[i]);
		}

		// 鍵の生成
		int[] key = new int[n];
		if (wiretap == false) {
			int keyIndex = 0;
			for (int i = 0; i < start; i++, keyIndex++) {
				key[keyIndex] = bobBits[i];
			}
			for (int i = start + m; i < n + m; i++, keyIndex++) {
				key[keyIndex] = bobBits[i];
			}
		}

		// 結果の出力
		/*
		System.out.println("wiretap: " + wiretap);
		System.out.print("Alice:\n");
		for (int i = 0; i < n + m; i++) {
			System.out.print(aliceBits[i] + ",");
		}
		System.out.println();

		System.out.print("Bob:\n");
		for (int i = 0; i < n + m; i++) {
			System.out.print(bobBits[i] + ",");
		}
		System.out.println();
		
		System.out.print("key= ");
		if (wiretap) {
			System.out.print("null");
		} else {
			for (int i = 0; i < n; i++) {
				System.out.print(key[i]);
			}
		}
		System.out.println();
		*/
		System.out.println("time: " + (System.currentTimeMillis() - startTime) + "ms");

	}

	public static void mainRoutine(int start, int end, int[] aliceBits, int[] bobBits) {
		Random rand = new Random();
		//System.out.println(Thread.currentThread().getId());
		// プロトコル
		for (int i = start; i < end;) {

			/*
			 * Alice
			 * aliceMeasurement: 送信する系
			 * aliceQbit: 送信するqbit
			 */
			int aliceMeasurement = rand.nextInt(2);
			aliceBits[i] = rand.nextInt(2);
			BraKetVector aliceQbit = new BraKetVector(aliceBits[i], aliceMeasurement);

			// NetWork
			BraKetVector getQbit = network(aliceQbit, false);

			/*
			 * Bob
			 * bobMeasurement: 測定する系
			 * bobQbit: 測定後の状態
			 */
			int bobMeasurement = rand.nextInt(2);
			BraKetVector bobQbit = getQbit.measurement(bobMeasurement);
			bobBits[i] = bobQbit.toBit();

			// 系の確認
			if (aliceMeasurement == bobMeasurement) {
				i++;
			}
		}
	}

	public static BraKetVector network(BraKetVector in, boolean wiretap, boolean info) {
		if (info) {
			System.out.println("Send");
			System.out.println("wiretap: " + wiretap);
		}

		if (wiretap == false)
			return in;

		Random rand = new Random();

		int eveMeasurement = rand.nextInt(2);
		BraKetVector tmp = in.measurement(eveMeasurement);

		if (info) {
			System.out.println("Eve_measurement: " + eveMeasurement);
			tmp.show(true);
			System.out.println();
		}

		return tmp;
	}

	public static BraKetVector network(BraKetVector qbit, boolean wiretap) {
		return network(qbit, wiretap, false);
	}
}
