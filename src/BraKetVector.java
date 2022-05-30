
public class BraKetVector{
	double[] vector;
	int size;
	boolean bra;
	
	
	public BraKetVector() {
		double[] vec = new double[2];
		vec[0] = 1;
		vec[1] = 0;
		SetData(vec);
	}
	
	public BraKetVector(int v) {
		if(v == 0) {
			double[] vec = new double[2];
			vec[0] = 1;
			vec[1] = 0;
			SetData(vec);
		}else if(v == 1) {
			double[] vec = new double[2];
			vec[0] = 0;
			vec[1] = 1;
			SetData(vec);
		}else if(v == 3) {
			BraKetVector a = new BraKetVector();
			a.Add(new BraKetVector(0), new BraKetVector(1));
			a.Mul(1/Math.sqrt(2), a);
			SetData(a);
		}else if(v == 4) {
			BraKetVector a = new BraKetVector();
			a.Sub(new BraKetVector(0), new BraKetVector(1));
			a.Mul(1/Math.sqrt(2), a);
			SetData(a);
		}
		
	};
	
	public BraKetVector(double[] vec) {
		SetData(vec);
	};
	
	
	private void SetData(BraKetVector a) {
		SetData(a.vector, a.bra);
	}
	
	private void SetData(double[] vector) {
		SetData(vector, true);
	}
	
	private void SetData(double[] vector, boolean bra) {
		this.vector = vector;
		this.size = vector.length;
		this.bra = bra;
	}
	
	public void Show() {
		System.out.print(this.size + ": ");
		for(int i=0; i<this.size; i++) {
			System.out.print(this.vector[i] + ", ");
		}
		System.out.print(this.bra+"\n");
	}
	
	//転置
	public BraKetVector T() {
		BraKetVector a = new BraKetVector(this.vector);
		a.bra = !this.bra;
		return a;
	}
	
	//テンソル積
	public void TensorProduct(BraKetVector a, BraKetVector b) {
		
		double[] ans = new double[a.size*b.size];
		System.out.println(ans.length);
		
		for(int i=0; i<a.size; i++) {
			for(int j=0; j<b.size; j++) {
				ans[(i*b.size)+j] = a.vector[i]*b.vector[j];
			}
		}
		
		this.SetData(ans);
	}
	
	
	public void Add(BraKetVector a,BraKetVector b) {
		if(a.size != b.size) {
			System.out.println("Different size of Kets");
			return;
		}
		
		if(a.bra != b.bra) {
			System.out.println("Different Bra-Ket of Kets");
			return;
		}
		
		double[] ans = new double[a.size];
		for(int i=0; i<a.size; i++) {
			ans[i] = a.vector[i] + b.vector[i];
		}
		this.SetData(ans);
	}
	
	
	public void Sub(BraKetVector a,BraKetVector b) {
		if(a.size != b.size) {
			System.out.println("Different size of Kets");
			return;
		}
		
		if(a.bra != b.bra) {
			System.out.println("Different Bra-Ket of Kets");
			return;
		}
		
		double[] ans = new double[a.size];
		for(int i=0; i<a.size; i++) {
			ans[i] = a.vector[i] - b.vector[i];
		}
		this.SetData(ans);
	}
	
	
	public void Mul(double a, BraKetVector b) {
		double[] ans = new double[b.size];
		
		for(int i=0; i<b.size; i++) {
			ans[i] = a * b.vector[i];
		}
		this.SetData(ans, b.bra);
	}
	
	public double Dot(BraKetVector b) {
		
		
		if(!(this.bra == false && b.bra == true)) {
			System.out.print("ベクトル方向が違う: ");
			System.out.print("a="+this.bra + "b="+b.bra);
			return 0;
		}
		if(this.size != b.size) {
			System.out.println("Different size of Kets");
			return 0;
		}
		
		double ans = 0;
		for(int i=0; i<this.size; i++) {
			ans += this.vector[i]*b.vector[i];
		}
		
		return ans;
	}

};
