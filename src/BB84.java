import java.util.Random;

public class BB84 {

	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */
	
	public static void main(String[] args) {
		Random rand = new Random();
		
		int n = 128;
		int m = 64;
		
		
		int[] aliceBits = new int[n+m];
		int[] bobBits = new int[n+m];
		
		
		//送信
		for(int i=0; i<n+m;) {
			//Alice
			int aliceMeasurement = rand.nextInt(2);
			aliceBits[i] = rand.nextInt(2);
			BraKetVector aliceQbit = new BraKetVector(aliceBits[i], aliceMeasurement); 
			
			/*
			//Show Alice data
			System.out.println("Show Alice data");
			aliceQbit.show();
			System.out.println();
			*/
			
			
			//Send
			BraKetVector getData = network(aliceQbit, true);
			
			
			//Bob
			int bobMeasurement = rand.nextInt(2);
			BraKetVector bobQbit = getData.measurement(bobMeasurement);
			bobBits[i] = bobQbit.toBit();
			/*
			//Show Bob data
			System.out.println("Show Bob data");
			bobQbit.show();
			System.out.println();
			*/
			
			if(aliceMeasurement == bobMeasurement) {
				i++;
			}
		}
		System.out.println("送信終了");
		System.out.println();
		
		//盗聴確認
		int start = rand.nextInt(n);
		boolean wiretap = false;
		
		for(int i=start; i<start+m; i++) {
			wiretap = wiretap || (aliceBits[i]!=bobBits[i]);
		}
		
		//print
		
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
