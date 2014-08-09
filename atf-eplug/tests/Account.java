
public class Account
{
	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public Person getHolder()
	{
		return holder;
	}

	public void setHolder(Person holder)
	{
		this.holder = holder;
	}
	
	public void addMoney(Money money)
	{
		this.balance = this.balance.add(money);
	}
	
	public boolean removeMoney(Money money)
	{
		this.balance = this.balance.sub(money);
		
		return true;
	}
	
	protected Money balance = new Money();
	protected Person holder = new Person();
}
