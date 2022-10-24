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
		kei = kei%2;
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
		double[] vec = {0, 0};
		if(v == 0) {
			vec[0] = 1;
			vec[1] = 0;
			setData(vec);
		}else if(v == 1) {
			vec[0] = 0;
			vec[1] = 1;
			setData(vec);
		}else if(v == 2) {
			BraKetVector a = new BraKetVector(0);
			BraKetVector b = new BraKetVector(1);
			a.add(a, b);
			a.mul(1/Math.sqrt(2), a);
			setData(a);
		}else if(v == 3) {
			BraKetVector a = new BraKetVector(0);
			BraKetVector b = new BraKetVector(1);
			a.sub(a, b);
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
		int size = a.size;
		for(int i=0; i<size; i++) {
			ans[i] = a.vector[i] - b.vector[i];
		}
		this.setData(ans);
	}
	
	//乗算
	public void mul(double a, BraKetVector b) {
		double[] ans = new double[b.size];
		int size = b.size;
		for(int i=0; i<size; i++) {
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
		int size = this.size;

		if(size != b.size) {
			System.out.println("Different size of Kets");
			return 0;
		}
		
		double ans = 0;
		for(int i=0; i<size; i++) {
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
		return measurement(group == "01");
	}

	public BraKetVector measurement(Boolean group) {
		Random r = new Random();
		float randFloat = r.nextFloat();
		if(group == true) {
			BraKetVector a = new BraKetVector('0');
			double t = a.t().dot(this);
			if (randFloat <=((double)Math.round(t*t*1000000000))/1000000000) {
				return a;
			}else {
				return new BraKetVector('1');
				//a.vector[1] *= -1;
				//return a;
			}
			
		}else{
			BraKetVector a = new BraKetVector('+');
			double t = a.t().dot(this);
			if (randFloat <=((double)Math.round(t*t*1000000000))/1000000000) {
				return a;
			}else {
				//return new BraKetVector('-');と同じ
				a.vector[1] *= -1;
				return a;
			}
		}
	}
	
	//group(偶数:01系測定, 奇数: +-系測定)測定
	public BraKetVector measurement(int group) {
		if(group%2 == 0) return measurement("01");
		else return measurement("+-");
	}
};