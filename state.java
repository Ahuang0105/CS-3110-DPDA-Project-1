import java.util.ArrayList;


public class state 
{
	ArrayList<transition> transitions = new ArrayList<transition>();
	boolean stateEnd = false;

	public state() 
	{
		this.transitions = new ArrayList< >();
	}

	public void addNewStates(String input, String pop, int destination, String push) 
	{
		transitions.add(new transition(input, pop, destination, push));
	}

	public ArrayList<transition> getTransitions() 
	{
		return transitions;
	}
	
	public boolean isFinalState() 
	{
		return stateEnd;
	}
	
	public void changeStateStatus(boolean status) 
	{
		stateEnd = status;
	}
}