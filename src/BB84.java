import java.util.Random;

public class BB84 {

	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */
	
	public static void main(String[] args) {
		Random rand = new Random();
		
		
		
		
		
		//Alice
		int aliceMeasurement = rand.nextInt(2);
		int aliceBit = rand.nextInt(2);
		BraKetVector aliceQbit = new BraKetVector(aliceBit, aliceMeasurement); 
		
		//Show Alice data
		System.out.println("Show Alice data");
		aliceQbit.show();
		System.out.println();
		
		
		//Send
		BraKetVector getData = network(aliceQbit, false);
		
		
		//Bob
		int bobMeasurement = rand.nextInt(2);
		BraKetVector bobQbit = getData.measurement(bobMeasurement);
		
		//Show Bob data
		System.out.println("Show Bob data");
		bobQbit.show();
		System.out.println();
		
		System.out.println(aliceMeasurement == bobMeasurement);
		
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
