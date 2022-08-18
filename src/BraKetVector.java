import java.util.Random;

public class BraKetVector{
	double[] vector;
	int size;
	boolean ket;
	
	//|0>の生成
	public BraKetVector() {
		setBraket(0);
	}
	
	//bit(0, 1), kei(0:01系, 1:+-系)のケットベクトルの生成
	public BraKetVector(int bit, int kei) {
		setBraket(kei*2+bit);
	}

	//bit(true:0, false:1), kei(0:01系, 1:+-系)のケットベクトルの生成
	public BraKetVector(Boolean bit, int kei){
		int tmp = kei*2;
		if(!bit)tmp+=1;
		setBraket(tmp);
	}
	
	//c(0, 1, +, -) |c>の生成
	public BraKetVector(char c) {
		if(c == '0') {
			setBraket(0);
		}else if(c == '1') {
			setBraket(1);
		}else if(c == '+') {
			setBraket(2);
		}else if(c == '-') {
			setBraket(3);
		}
	}
	
	//v(0:0, 1:1, 2:+, 3:-) |v>の生成
	public BraKetVector(int v) {
		setBraket(v);
		
	};
	
	//指定したベクトルのケットベクトルの生成
	public BraKetVector(double[] vec) {
		setData(vec);
	};
	
	//v(0:0, 1:1, 2:+, 3:-) |v>に対応したベクトルを生成
	private void setBraket(int v) {
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
	
	//フィールドに値を代入
	private void setData(BraKetVector a) {
		setData(a.vector, a.ket);
	}
	
	private void setData(double[] vector) {
		setData(vector, true);
	}
	
	private void setData(double[] vector, boolean ket) {
		this.vector = vector;
		this.size = vector.length;
		this.ket = ket;
	}
	
	//対応するbitを表示
	public void show() {
		this.show(false);
	}
	
	//ベクトルの状態と対応するbitを表示
	public void show(boolean a) {
		if(a) {
			System.out.print(this.size + ": ");
			for(int i=0; i<this.size; i++) {
				System.out.print(this.vector[i] + ", ");
			}
		}
		
		if(this.isEqual('0') || this.isEqual('+')) System.out.print("bit: "+0+" ");
		else if(this.isEqual('1') || this.isEqual('-')) System.out.print("bit: "+1+" ");
		
		
		System.out.print(this.ket+"\n");
	}
	
	//ベクトルが等しいか
	public boolean isEqual(char a) {
		return this.isEqual(new BraKetVector(a));
	}
	
	//与えられたブラケットベクトルと等しいか
	public boolean isEqual(BraKetVector a) {
		if(this.size != a.size) return false;
		if(this.ket != a.ket) return false;
				
		for(int i=0; i<this.size; i++) {
			if(this.vector[i] != a.vector[i]) return false;
		}
		return true;
	}
	
	//転置
	public BraKetVector t() {
		BraKetVector a = new BraKetVector(this.vector);
		a.ket = !this.ket;
		return a;
	}
	
	//加算
	public void add(BraKetVector a,BraKetVector b) {
		if(a.size != b.size) {
			System.out.println("Different size of Kets");
			return;
		}
		
		if(a.ket != b.ket) {
			System.out.println("Different Bra-Ket of Kets");
			return;
		}
		
		double[] ans = new double[a.size];
		for(int i=0; i<a.size; i++) {
			ans[i] = a.vector[i] + b.vector[i];
		}
		this.setData(ans);
	}
	
	//減算
	public void sub(BraKetVector a,BraKetVector b) {
		if(a.size != b.size) {
			System.out.println("Different size of Kets");
			return;
		}
		
		if(a.ket != b.ket) {
			System.out.println("Different Bra-Ket of Kets");
			return;
		}
		
		double[] ans = new double[a.size];
		for(int i=0; i<a.size; i++) {
			ans[i] = a.vector[i] - b.vector[i];
		}
		this.setData(ans);
	}
	
	//乗算
	public void mul(double a, BraKetVector b) {
		double[] ans = new double[b.size];
		
		for(int i=0; i<b.size; i++) {
			ans[i] = a * b.vector[i];
		}
		this.setData(ans, b.ket);
	}
	
	//内積
	public double dot(BraKetVector b) {
		if(!(this.ket == false && b.ket == true)) {
			System.out.print("ベクトル方向が違う: ");
			System.out.print("a="+this.ket + "b="+b.ket);
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
	
	//対応するビットを返す
	public Boolean toBit(){
		if(this.isEqual('0') || this.isEqual('+')) return true;
		else return false;
	}
	
	//group("01":01測定, "+-": +-測定)測定
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
	
	//group(偶数:01系測定, 奇数: +-系測定)測定
	public BraKetVector measurement(int group) {
		if(group%2 == 0) return measurement("01");
		else return measurement("+-");
	}

};
