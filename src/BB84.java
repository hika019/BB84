import java.util.Random;

public class BB84 {
    
	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */
	
    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
		Random rand = new Random();
		
		/*
		 * n: 鍵長
		 * m: 安全性パラメータ
		 */
		int n = 1000;
		int m = 100;
		
		
		//ビット列
		int[] aliceBits = new int[n+m];
		int[] bobBits = new int[n+m];
		
		
		//プロトコル
		for(int i=0; i<n+m;) {
			
			/* Alice
			 * aliceMeasurement: 送信する系
			 * aliceQbit: 送信するqbit
			 */
			int aliceMeasurement = rand.nextInt(2);
			aliceBits[i] = rand.nextInt(2);
			BraKetVector aliceQbit = new BraKetVector(aliceBits[i], aliceMeasurement); 
			
			
			//NetWork
			BraKetVector getQbit = network(aliceQbit, false);
			
			
			/* Bob
			 * bobMeasurement: 測定する系
			 * bobQbit: 測定後の状態
			 */
			int bobMeasurement = rand.nextInt(2);
			BraKetVector bobQbit = getQbit.measurement(bobMeasurement);
			bobBits[i] = bobQbit.toBit();
			
			
			//系の確認
			if(aliceMeasurement == bobMeasurement) {
				i++;
			}
		}
		System.out.println("送信終了");
		System.out.println();
		
		
		/* パラメータ
		 * start: 盗聴の確認を行う際の開始地点
		 * wiretap: 盗聴の有無
		 */
		int start = rand.nextInt(n);
		boolean wiretap = false;
		
		//盗聴の確認
		for(int i=start; i<start+m; i++) {
			wiretap = wiretap || (aliceBits[i]!=bobBits[i]);
		}
		
		
		//鍵の生成
		int[] key = new int[n];
		if(wiretap == false) {
			int keyIndex = 0;
			for(int i=0; i<start; i++, keyIndex++) {
				key[keyIndex] = bobBits[i];
			}
			for(int i= start+m; i<n+m; i++, keyIndex++) {
				key[keyIndex] = bobBits[i];
			}
		}
		
		
		
		
		//結果の出力
		System.out.println("wiretap: "+wiretap);
		System.out.print("Alice:\n");
		for(int i=0; i<n+m; i++) {
			System.out.print(aliceBits[i]+ ",");
		}
		System.out.println();
		
		System.out.print("Bob:\n");
		for(int i=0; i<n+m; i++) {
			System.out.print(bobBits[i]+ ",");
		}
		System.out.println();
		
		System.out.print("key= ");
		if(wiretap) {
			System.out.print("null");
		}else {
			for(int i=0; i<n; i++) {
				System.out.print(key[i]);
			}
		}
		System.out.println();
		
		System.out.println("time: " + (System.currentTimeMillis()-startTime) +"ms");
		
	}
	
	
	public static BraKetVector network(BraKetVector in, boolean wiretap, boolean info) {
		if(info) {
			System.out.println("Send");
			System.out.println("wiretap: "+wiretap);
		}
		
		if(wiretap == false) return in;
		
		Random rand = new Random();
		
		int eveMeasurement = rand.nextInt(2);
		BraKetVector tmp = in.measurement(eveMeasurement);
		
		if(info) {
			System.out.println("Eve_measurement: "+eveMeasurement);
			tmp.show(true);
			System.out.println();
		}
		
		return tmp;
	}
	
	public static BraKetVector network(BraKetVector qbit, boolean wiretap) {
		return network(qbit, wiretap, false);
	}
}
