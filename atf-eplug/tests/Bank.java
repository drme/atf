
public class Bank
{
	public void transfer(Account from, Account to, Money ammount)
	{
		to.addMoney(ammount);
		from.removeMoney(ammount);
	}
}
