import java.util.Random;

public class BB84 {

	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */
	
	public static void main(String[] args) {
		Random rand = new Random();
		
		int bitLen = 4;
		
		int[] aliceBits = new int[bitLen];
		int[] bobBits = new int[bitLen];
		
		
		for(int i=0; i<bitLen;) {
			//Alice
			int aliceMeasurement = rand.nextInt(2);
			aliceBits[i] = rand.nextInt(2);
			BraKetVector aliceQbit = new BraKetVector(aliceBits[i], aliceMeasurement); 
			
			//Show Alice data
			System.out.println("Show Alice data");
			aliceQbit.show();
			System.out.println();
			
			
			//Send
			BraKetVector getData = network(aliceQbit, false);
			
			
			//Bob
			int bobMeasurement = rand.nextInt(2);
			BraKetVector bobQbit = getData.measurement(bobMeasurement);
			bobBits[i] = bobQbit.toBit();
			
			//Show Bob data
			System.out.println("Show Bob data");
			bobQbit.show();
			System.out.println();

			
			if(aliceMeasurement == bobMeasurement) {
				i++;
			}
		}
		System.out.println("End BB84");
		
		
		System.out.print("Alice: ");
		for(int i=0; i<bitLen; i++) {
			System.out.print(aliceBits[i]+ ",");
		}
		System.out.println();
		
		System.out.print("Bob: ");
		for(int i=0; i<bitLen; i++) {
			System.out.print(bobBits[i]+ ",");
		}
		System.out.println();
		
		
		
		
	}
	
	
	public static BraKetVector network(BraKetVector qbit, boolean wiretap, boolean info) {
		if(info) {
			System.out.println("Send");
			System.out.println("wiretap: "+wiretap);
		}
		
		if(wiretap == false) return qbit;
		
		Random rand = new Random();
		
		int eveMeasurement = rand.nextInt(2);
		BraKetVector tmp = qbit.measurement(eveMeasurement);
		
		if(info) {
			System.out.println("Eve_measurement: "+eveMeasurement);
			tmp.show(true);
		}
		
		System.out.println();
		
		return tmp;
	}
	
	public static BraKetVector network(BraKetVector qbit, boolean wiretap) {
		return network(qbit, wiretap, false);
	}

}
