
public class Ket{
	double[] vector;
	int size;
	
	public Ket(double[] vec) {
		SetData(vec);
	};
	
	public Ket() {
		double[] vec = new double[2];
		vec[0] = 1;
		vec[1] = 0;
		SetData(vec);
	}
	
	public Ket(int v) {
		double[] vec = new double[2];
		
		if(v == 0) {
			vec[0] = 1;
			vec[1] = 0;
		}else if(v == 1) {
			vec[0] = 0;
			vec[1] = 1;
		}
		
		SetData(vec);
		
	};
	
	
	private void SetData(double[] vector) {
		this.vector = vector;
		this.size = vector.length;
	}
	
	public void Show() {
		System.out.print(this.size + ": ");
		for(int i=0; i<this.size; i++) {
			System.out.print(this.vector[i] + ", ");
		}
		System.out.print("\n");
	}
	
	
	public void TensorProduct(Ket a, Ket b) {
		
		double[] ans = new double[a.size*b.size];
		System.out.println(ans.length);
		
		for(int i=0; i<a.size; i++) {
			for(int j=0; j<b.size; j++) {
				ans[(i*b.size)+j] = a.vector[i]*b.vector[j];
			}
		}
		
		this.SetData(ans);
	}
	
};
