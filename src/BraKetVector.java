import java.util.Random;

public class BraKetVector{
	double[] vector;
	int size;
	boolean bra;
	
	
	public BraKetVector() {
		createVector(0);
	}
	
	public BraKetVector(int bit, int kei) {
		createVector(kei*2+bit);
	}
	
	public BraKetVector(char c) {
		if(c == '0') {
			createVector(0);
		}else if(c == '1') {
			createVector(1);
		}else if(c == '+') {
			createVector(2);
		}else if(c == '-') {
			createVector(3);
		}
	}
	
	public BraKetVector(int v) {
		createVector(v);
		
	};
	
	public BraKetVector(double[] vec) {
		setData(vec);
	};
	
	private void createVector(int v) {
		if(v == 0) {
			double[] vec = new double[2];
			vec[0] = 1;
			vec[1] = 0;
			setData(vec);
		}else if(v == 1) {
			double[] vec = new double[2];
			vec[0] = 0;
			vec[1] = 1;
			setData(vec);
		}else if(v == 2) {
			BraKetVector a = new BraKetVector();
			a.add(new BraKetVector(0), new BraKetVector(1));
			a.mul(1/Math.sqrt(2), a);
			setData(a);
		}else if(v == 3) {
			BraKetVector a = new BraKetVector();
			a.sub(new BraKetVector(0), new BraKetVector(1));
			a.mul(1/Math.sqrt(2), a);
			setData(a);
		}
	}
	
	private void setData(BraKetVector a) {
		setData(a.vector, a.bra);
	}
	
	private void setData(double[] vector) {
		setData(vector, true);
	}
	
	private void setData(double[] vector, boolean bra) {
		this.vector = vector;
		this.size = vector.length;
		this.bra = bra;
	}
	
	public void show() {
		this.show(false);
	}
	
	public void show(boolean a) {
		if(a) {
			System.out.print(this.size + ": ");
			for(int i=0; i<this.size; i++) {
				System.out.print(this.vector[i] + ", ");
			}
		}
		
		if(this.isEqual('0') || this.isEqual('+')) System.out.print("bit: "+0+" ");
		else if(this.isEqual('1') || this.isEqual('-')) System.out.print("bit: "+1+" ");
		
		
		System.out.print(this.bra+"\n");
	}
	
	public boolean isEqual(char a) {
		return this.isEqual(new BraKetVector(a));
	}
	
	public boolean isEqual(BraKetVector a) {
		if(this.size != a.size) return false;
		if(this.bra != a.bra) return false;
				
		for(int i=0; i<this.size; i++) {
			if(this.vector[i] != a.vector[i]) return false;
		}
		return true;
	}
	
	//転置
	public BraKetVector t() {
		BraKetVector a = new BraKetVector(this.vector);
		a.bra = !this.bra;
		return a;
	}
	
	//テンソル積
	public void tensorProduct(BraKetVector a, BraKetVector b) {
		
		double[] ans = new double[a.size*b.size];
		
		for(int i=0; i<a.size; i++) {
			for(int j=0; j<b.size; j++) {
				ans[(i*b.size)+j] = a.vector[i]*b.vector[j];
			}
		}
		
		this.setData(ans);
	}
	
	
	public void add(BraKetVector a,BraKetVector b) {
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
		this.setData(ans);
	}
	
	
	public void sub(BraKetVector a,BraKetVector b) {
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
		this.setData(ans);
	}
	
	
	public void mul(double a, BraKetVector b) {
		double[] ans = new double[b.size];
		
		for(int i=0; i<b.size; i++) {
			ans[i] = a * b.vector[i];
		}
		this.setData(ans, b.bra);
	}
	
	public double dot(BraKetVector b) {
		
		
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
	
	
	
	public BraKetVector measurement(String group) {
		
		if(group == "01") {
			BraKetVector a = new BraKetVector('0');
			Random r = new Random();
			
			if (r.nextInt(10000) < (int)(10000*(Math.pow(a.t().dot(this), 2)+0.0000000000000001))) {
				return new BraKetVector('0');
			}else {
				return new BraKetVector('1');
			}
			
		}else if(group == "+-") {
			BraKetVector a = new BraKetVector('+');
			Random r = new Random();
			
			if (r.nextInt(10000) < (int)(10000*(Math.pow(a.t().dot(this), 2)+0.0000000000000001))) {
				return new BraKetVector('+');
			}else {
				return new BraKetVector('-');
			}
		}
		
		return new BraKetVector();
	}
	
	public BraKetVector measurement(int a) {
		if(a%2 == 0) return measurement("01");
		else return measurement("+-");
	}

};
