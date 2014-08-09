
public class ATM
{
	public void deposit(Account account, Money money)
	{
		account.addMoney(money);
	}
	
	public void withdraw(Account account, Money money)
	{
		account.removeMoney(money);
	}
}
