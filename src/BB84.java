import java.util.Random;

import com.aparapi.Kernel;

public class BB84 {

	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		Random rand = new Random();

		/*
		 * n: 鍵長
		 * m: 安全性パラメータ
		 */
		final int n = 10000000;
		final int m = 100;

		// ビット列
		final Boolean[] aliceBits = new Boolean[n + m];
		final Boolean[] bobBits = new Boolean[n + m];
		
		
		Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                while(true) {
                    

                    int aliceMeasurement = rand.nextInt(2);
                    aliceBits[i] = rand.nextBoolean();
                    BraKetVector aliceQbit = new BraKetVector(aliceBits[i], aliceMeasurement);

                    // NetWork
                    BraKetVector getQbit = network(aliceQbit, true, rand);


                    int bobMeasurement = rand.nextInt(2);
                    BraKetVector bobQbit = getQbit.measurement(bobMeasurement, rand);
                    bobBits[i] = bobQbit.toBit();

                    // 系の確認
                    if (aliceMeasurement == bobMeasurement) {
                        break;
                    }
                    
                }
                
            }
		    
		    
		};
		
		
		/*
		// プロトコル
		for (int i = 0; i < n + m;) {
			
			 // Alice
			 // aliceMeasurement: 送信する系
			 // aliceQbit: 送信するqbit
			 
			int aliceMeasurement = rand.nextInt(2);
			aliceBits[i] = rand.nextBoolean();
			BraKetVector aliceQbit = new BraKetVector(aliceBits[i], aliceMeasurement);

			// NetWork
			BraKetVector getQbit = network(aliceQbit, true);

			
			 // Bob
			 // bobMeasurement: 測定する系
			 // bobQbit: 測定後の状態
			 
			int bobMeasurement = rand.nextInt(2);
			BraKetVector bobQbit = getQbit.measurement(bobMeasurement);
			bobBits[i] = bobQbit.toBit();

			// 系の確認
			if (aliceMeasurement == bobMeasurement) {
				i++;
			}
		}
	    */
		
		System.out.println( kernel.getTargetDevice() );
		kernel.execute(n+m);
		System.out.println( "device: "+kernel.getTargetDevice() );


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
			if (wiretap)
				break;
		}

		// 鍵の生成
		Boolean[] key = new Boolean[n];
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

		System.out.println("wiretap: " + wiretap);
		/*
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
		*/
		/*
		System.out.print("key= ");
		if (wiretap) {
			System.out.print("null");
		} else {
			for (int i = 0; i < n; i++) {
				if(key[i] == true){
					System.out.print(0);
				}else{
					System.out.print(1);
				}
				
			}
		}
		*/
		System.out.println();

		System.out.println("time: " + (System.currentTimeMillis() - startTime) + "ms");
	}

	public static BraKetVector network(BraKetVector in, boolean wiretap, boolean info, Random r) {
		if (info) {
			System.out.println("Send");
			System.out.println("wiretap: " + wiretap);
		}

		if (wiretap == false)
			return in;

		Boolean eveMeasurement = r.nextBoolean();
		BraKetVector tmp = in.measurement(eveMeasurement, r);

		if (info) {
			System.out.println("Eve_measurement: " + eveMeasurement);
			tmp.show(true);
			System.out.println();
		}

		return tmp;
	}

	public static BraKetVector network(BraKetVector qbit, boolean wiretap, Random r) {
		return network(qbit, wiretap, false, r);
	}
}
