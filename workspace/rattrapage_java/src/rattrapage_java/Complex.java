package rattrapage_java;

public class Complex {

	private double re;
	private double im;

	Complex(double x, double y) {
		re = x;
		im = y;
	}

	public static Complex add(Complex u, Complex v) {

		Complex result = new Complex(0, 0);
		result.re = u.re + v.re;
		result.im = u.im + v.im;
		return result;
	}

	public Complex add(Complex z) {

		Complex result = new Complex(0, 0);
		result.re = this.re + z.re;
		result.im = this.im + z.im;
		return result;
	}

	public static Complex mul(Complex u, Complex v) {

		Complex result = new Complex(0, 0);
		result.re = u.re * v.re - u.im * v.im;
		result.im = u.im * v.re + u.re * v.im;
		return result;
	}

	public Complex mul(Complex z) {

		Complex result = new Complex(0, 0);
		result.re = this.re * z.re - this.im * z.im;
		result.im = this.im * z.re + this.re * z.im;
		return result;
	}

	public String toString() {

		String result = "";

		if (this.im == 0) {
			result = "" + this.re;
		} else if (this.re == 0 && this.im != 1) {
			result = "" + this.im + ".i";
		} else if (this.re == 0 && this.im == 1) {
			result = "i";
		} else if (this.re != 0 && this.im == 1) {
			result = "" + this.re + " + i";
		} else {
			result = "" + this.re + " + " + this.im + ".i";
		}
		return result;
	}

	public static void main(String[] arg) {

		double x = Double.valueOf(arg[0]);
		double y = Double.valueOf(arg[1]);

		Complex z = new Complex(x, y);
		Complex one = new Complex(1, 0);

		Complex calc1 = mul(z, add(mul(z, add(z, one)), one));
		Complex calc2 = z.mul(one.add(z.mul(z.add(one))));

		System.out.println("z = " + z.toString());
		System.out.println("z(z(z + 1) + 1) = " + calc1.toString());
		System.out.println("z(z(z + 1) + 1) = " + calc2.toString());
	}
}
