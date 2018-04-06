package entity;

public class A{
	//update test 2
	private int a;
	private int b;
	
	public static void main(String[] args) {
		//ajfdòsjgkd
		A a1 = new A(2,3);
		A a2 = a1.copy(a1);
		System.out.println(a2.getA());
		System.out.println(a2.getB());
		
	}
	
	public A(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	int sum() {
		return a+b;
	}
	
	int getA() {
		return this.a;
	}
	
	int getB() {
		return this.b;
	}
	
	A copy(A a) {
		A a1 = new A( a.getA() , a.getB());
		return a1;
	}
}