/**
 * This class will allow users to create their own DPDA and let the user
 * test any possible string they want to try.
 * @author Alan C. Huang
 */
import java.util.Scanner;
import java.util.Stack;

public class pda 
{		
	public static String lambda = ".";
	public static String alphabet; 
	public static Stack<String> stack; 
	public static Scanner userInput = new Scanner(System.in); 
	public static state[] states; 
	public static int stateNum = 0; 
	public static int totalStates;
	public static boolean stringAccept = false; 
	public static boolean transitionAccept  = false; 	
	public static boolean finalStates = false;	
	public static boolean anotherTest = true;
	public static boolean endOfinput = false;
	
	public static void main(String[] args) 
	{
		
		
		System.out.print("Number of states: ");
		
		totalStates = userInput.nextInt();		
		states = new state[totalStates];
		
		for (int i = 0; i < totalStates; i++) 
		{	
			states[i] = new state();
		}
		
		stack = new Stack<String>();
		stack.push("$"); 
		
		
		System.out.print("Enter alphabet not seperated by spaces: ");
		
		alphabet = userInput.next();
		
		System.out.println("Enter the Transitions, or -1 to end:");
				
		do 
		{
			int stateSource = userInput.nextInt();
			
			if (stateSource == -1)
			{
				endOfinput = true;
			}
			
			else
				states[stateSource].addNewStates(userInput.next(), userInput.next(),
						userInput.nextInt(), userInput.next());
			
		} while (!endOfinput);
		
		System.out.print("Enter end of transition: ");
					
		do 
		{
			int finalState = userInput.nextInt();
			
			if (finalState == -1)
			{
				finalStates = true;
			}
				else
				{
					states[finalState].changeStateStatus(true);
				}
		} while (!finalStates);
		
		System.out.println("Enter a alphabet to test, or -1 to end the test:");
						
		while (anotherTest) 
		{
			stateNum = 0;
			testStrings();
			System.out.println("Do you want another try? (y/n) ");
			
			if (!(userInput.next().toLowerCase()=="y"))
			
				anotherTest = false;	
			
				stack.clear();			
				stack.push("$");			
				stringAccept = false;		
		}	
					
		System.out.println("Program end");
	}
	
	/**
	 * This methods will test user input string and show 
	 * result if the string is accept or not.
	 */
	public static void testStrings() 
	{
		boolean inputEnd = false;
		
		do {			
			System.out.print("Current Status: " + stateNum + ": ");
					
			for (int i = stack.size() - 1; i >= 0; i--)
			{
				System.out.print(stack.elementAt(i));
			}
			
			System.out.print(", Enter input: ");	
			
			String inputedString = userInput.next();	
			
			if (!inputedString.equals(lambda) ) 
			{
				if (alphabet.contains(inputedString) || inputedString.equals(lambda))
				{	
					inputEnd = userInput(inputedString);
				}
				else 
				{
					inputEnd = true;
					stringAccept = false;				
				}
			} 
			else 
			{ 
				inputEnd = true;
			}

		} while (!inputEnd);
		

		if (states[stateNum].isFinalState() && transitionAccept )
		{
			stringAccept = true;
		}		
		else
		{
			stringAccept = false;
		}
		if (!stringAccept)
		{	
			System.out.println("String is not Accepted.");
		}
			else
			{	
				System.out.println("String Accepted.");
			}
	}
	

	/** 
	 * This method will test user input and decide if the alphabet
	 * Allow it to move to next states and return true. If the alphabet
	 * is not recognized by any transition then it will return false. 
	 * @param input string use input alphabet
	 * @return true or false
	 */
	public static boolean userInput(String inputedString)
	{
		int transitionNumber = transitionSearch(inputedString);
		
		if (transitionNumber >= 0) 
		{ 
			popStack(transitionNumber);
			pushStack(transitionNumber);
			
			stateNum = states[stateNum].getTransitions().get(transitionNumber).whereTo();
			
			transitionAccept  = true;
			return false;
		} 
		
		else 
		{ 
					// accepted
			stringAccept = false;
			transitionAccept  = false;
			return true;
		}
	}

	/**
	 * This method will push alphabet to the stack depend by current 
	 * state transitions rule.  
	 * @param transitionNumber
	 */
	public static void pushStack(int transitionNumber)
	{
		if (!states[stateNum].getTransitions().get(transitionNumber).getPush().equals(".")) 
		{
			for (int j = states[stateNum].getTransitions().
					get(transitionNumber).getPush().length()- 1; j >= 0; j--) 
			{
				stack.push(Character
						.toString(states[stateNum].getTransitions().
								get(transitionNumber).getPush().charAt(j)));
			}
		}
	}

	/**
	 * This method will pop the alphabet from the stack depends by
	 * the current state transitions rule.
	 * @param transitionNumber
	 */
	public static void popStack(int transitionNumber)
	{
		for (int j = 0; j < states[stateNum].getTransitions().
				get(transitionNumber).getPop().length(); j++) 
		{
			if (Character.toString(states[stateNum].getTransitions().
					get(transitionNumber).getPop().charAt(j))
					.equals(stack.peek())) 
			{
				stack.pop();
			}
		}
	}

	/**
	 * This method will search through the transition array and 
	 * use input alphabet to see if there is a  match transition
	 * rule.
	 * @param inputedString alphabet enter by user
	 * @return
	 */
	public static int transitionSearch(String inputedString) 
	{		
		for (int i = 0; i < states[stateNum].getTransitions().size(); i++) 
		{			
			if (states[stateNum].getTransitions().get(i).getInput().equals(inputedString)) 
			{				
				for (int j = 0; j < states[stateNum].getTransitions().get(i).getPop().length(); j++) 
				{
					if (Character.toString(states[stateNum].getTransitions().get(i).getPop().charAt(j))
							.equals(stack.elementAt(stack.size() - 1 - j)))
					{
						return i;
					}
				}
			}
		}
		return -1; 
	}
}