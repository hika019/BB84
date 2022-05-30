
public class BB84 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Ket a = new Ket(0);
		Ket b = new Ket(1);
		
		Ket c = new Ket();
		c.TensorProduct(a, b);
		a.Show();
		b.Show();
		c.Show();
	}

}
