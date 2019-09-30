public class transition 
{
	String input;
	String pop;
	String push;
	int whereTo;

	public transition()
	{
		whereTo = 0;
		input = null;
		pop = null;
		push = null;
	}

	public transition(String input, String pop, int whereTo, String push) 
	{
		this.whereTo = whereTo;
		this.input = input;
		this.pop = pop;
		this.push = push;
	}
	
	public int whereTo() 
	{
		return whereTo;
	}

	public String getInput() 
	{
		return input;
	}

	public String getPop()
	{
		return pop;
	}

	public String getPush() 
	{
		return push;
	}

}