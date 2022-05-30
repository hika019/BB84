import java.util.Random;

public class BB84 {

	public static void main(String[] args) {
		Random rand = new Random();
		
		BraKetVector[] Alice_qbit = new BraKetVector[4];
		int[] Alice_bit = new int[Alice_qbit.length];
		
		for(int i=0; i<Alice_qbit.length; i++) {
			int tmp = rand.nextInt(16)%4;
			Alice_qbit[i] = new BraKetVector(tmp); 
			Alice_bit[i] = tmp%2;
		}
		
		
		//Show Alice data
		for(int i=0; i<Alice_qbit.length; i++) {
			Alice_qbit[i].Show();
			System.out.println(Alice_bit[i]);
		}
		
		
		
		
		
		
		
	}

}
