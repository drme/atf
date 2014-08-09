
public class SavingAccount extends Account
{
	public int getInterestRate()
	{
		return interestRate;
	}
	
	public void setInterestRate(int interestRate)
	{
		this.interestRate = interestRate;
	}
	
	public void addInterest()
	{
		Money m = new Money();
		
		int total = this.getBalance().getMain() * this.interestRate / 100;
		int f = this.getBalance().getFraction() * this.interestRate / 100;
		
		if (f > 100)
		{
			total++;
			f = f % 100;
		}
		
		m.setMain(total);
		m.setFraction(f);
		
		this.getBalance().add(m);
	}

	private int interestRate = 0;
}
