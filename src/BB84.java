import java.util.Random;

public class BB84 {

	/*
	 * measurement: 01系, +-系測定のパラメータ 0:01, 1:+-
	 */
	
	public static void main(String[] args) {
		Random rand = new Random();
		
		int bitLength = 4;
		
		
		
		
		//Alice
		int Alice_measurement = rand.nextInt(2);
		int Alice_bit = rand.nextInt(2);
		BraKetVector Alice_qbit = new BraKetVector(Alice_bit, Alice_measurement); 
		
		//Show Alice data
		System.out.println("Show Alice data");
		Alice_qbit.Show();
		System.out.println();
		
		
		//Send
		BraKetVector Data = Send(Alice_qbit, false);
		
		
		//Bob
		int bob_measurement = rand.nextInt(2);
		BraKetVector Bob_qbit = Data.Measurement(bob_measurement);
		
		//Show Bob data
		System.out.println("Show Bob data");
		Bob_qbit.Show();
		System.out.println();
		
		System.out.println(Alice_measurement == bob_measurement);
		
	}
	
	
	public static BraKetVector Send(BraKetVector qbit, boolean wiretap, boolean info) {
		if(info) {
			System.out.println("Send");
			System.out.println("wiretap: "+wiretap);
		}
		
		if(wiretap == false) return qbit;
		
		Random rand = new Random();
		
		int Eve_measurement = rand.nextInt(2);
		BraKetVector tmp = qbit.Measurement(Eve_measurement);
		
		if(info) {
			System.out.println("Eve_measurement: "+Eve_measurement);
			tmp.Show(true);
		}
		
		System.out.println();
		
		return tmp;
	}
	
	public static BraKetVector Send(BraKetVector qbit, boolean wiretap) {
		if(wiretap == false) return qbit;
		
		Random rand = new Random();
		
		int Eve_measurement = rand.nextInt(2);
		
		return qbit.Measurement(Eve_measurement);
	}

}
