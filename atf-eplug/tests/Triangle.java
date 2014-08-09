public class Triangle
{

    public int v[];

    public Triangle(int i, int j, int k)
    {
        v = new int[3];
        v[0] = i;
        v[1] = j;
        v[2] = k;
    }
    
    public float a, b, c;
    
    public Triangle(float[] abc)
    {
    	this.a = abc[0];
    	this.b = abc[1];
    	this.c = abc[2];
    }
}