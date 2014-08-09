
public class CheckingAccount extends Account
{
	public Money getOverdraftLimit()
	{
		return overdraftLimit;
	}

	public void setOverdraftLimit(Money overdraftLimit)
	{
		this.overdraftLimit = overdraftLimit;
	}
	
	public boolean removeMoney(Money money)
	{
		Money balance = this.balance.sub(money);
		
		if (balance.less(this.overdraftLimit) == true)
		{
			return false;
		}
		else
		{
			this.balance = balance;
			
			return true;
		}
	}

	private Money overdraftLimit = new Money();
}
