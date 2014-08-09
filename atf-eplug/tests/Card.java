
public class Card
{
	public Person getHolder()
	{
		return holder;
	}
	public void setHolder(Person holder)
	{
		this.holder = holder;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}

	private Person holder = null;
	private String number = null;
}
