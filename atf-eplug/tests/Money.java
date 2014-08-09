
public class Money
{
	public int getFraction()
	{
		return this.amount % 100;
	}
	public void setFraction(int fraction)
	{
		this.amount = this.amount / 100 + fraction;
	}
	public int getMain()
	{
		return this.amount / 100;
	}
	
	public void setMain(int main)
	{
		this.amount = main * 100 + getFraction();
	}
	
	public String getCurrency()
	{
		return currency;
	}
	
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public Money add(Money money)
	{
		Money m = new Money();
		
		m.setMain(this.getMain() + money.getMain());
		
		int f = this.getFraction() + money.getFraction();
		
		if (f >= 100)
		{
			m.setMain(m.getMain() + 1);
			f = f % 100;
		}
		
		m.setFraction(f);
		
		return m;
	}
	
	public Money sub(Money money)
	{
		Money m = new Money();
		
		m.setMain(this.getMain() - money.getMain());
		
		int f = this.getFraction() - money.getFraction();
		
		if (f < 100)
		{
			m.setMain(m.getMain() - 1);
			f = f % 100;
		}
		
		m.setFraction(f);
		
		return m;
	}
	
	public boolean less(Money m)
	{
		if (this.getMain() < m.getMain())
		{
			return true;
		}
		else if (this.getMain() == m.getMain())
		{
			return this.getFraction() < m.getFraction();
		}
		else
		{
			return false;
		}
	}
	
	private int amount = 0;
	private String currency;
}
