package eu.sarunas.atf.demo.geometry.shapes;

public class Triangle
{
	private float a;
	private float b;
	private float c;

	public Triangle()
	{
	}
	
	public Triangle(float a, float b, float c)
	{
		setA(a);
		setB(b);
		setC(c);
	}
	
	public float getA()
	{
		return this.a;
	}

	public float getB()
	{
		return this.b;
	}

	public float getC()
	{
		return this.c;
	}

	public void setA(float a)
	{
		this.a = a;
	}

	public void setB(float b)
	{
		this.b = b;
	}

	public void setC(float c)
	{
		this.c = c;
	}
}
