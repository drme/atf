public class Triangle2
{
	public float getSize(float a, float b, float c)
	{
		//float s = (a + b + c) / 2.0f;
		//return (float)Math.sqrt(s * (s - a) * (s - b) * (s - c));


		return b * c * (float)Math.cos(Math.asin(b * b + c * c - a * a) / (2 * b * c) );
	};
};
