import java.util.Random;

public class BB84 {

	public static void main(String[] args) {
		Random rand = new Random();
		
		int bitLength = 4;
		
		
		//Alice
		int tmp = rand.nextInt(16)%4;
		BraKetVector Alice_qbit = new BraKetVector(tmp); 
		int Alice_bit = tmp%2;
		int Alice_measurement = 0;
		if(tmp < 2) Alice_measurement = 0;
		else Alice_measurement = 1;
		
		//Show Alice data
		System.out.println("Show Alice data");
		Alice_qbit.Show();
		System.out.println(Alice_bit);
		System.out.println();
		
		
		/*
		 * data sending
		 * 
		 */
		
		
		//Bob
		int bob_measurement = rand.nextInt(2);
		BraKetVector Bob_qbit = Alice_qbit.Measurement(bob_measurement);
		
		//Show Bob data
		System.out.println("Show Bob data");
		Bob_qbit.Show();
		System.out.println();
		
		System.out.println(Alice_measurement == bob_measurement);
		
	}

}
