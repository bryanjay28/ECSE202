//	Bryan Jay
//	260738764

package assignment4;
import java.util.Scanner;
import java.util.StringTokenizer;

public class In2pJ {

	public static void main(String[] args) {
		
		String keyboardInput = "";
		System.out.print("Enter String: ");
		
//		Create scanner object to take in input
		Scanner sc = new Scanner(System.in);
		
//		Next line stored into keyboardInput variable
		keyboardInput = sc.nextLine();
		
//		Check that input is not empty
		if(keyboardInput.isEmpty()) {
			System.out.println("Please enter valid string");
			System.exit(0);
		}

//		Initialize both queues one to store input expression and the other to store the output expression
		stack operator = new stack();		
		queue inputQ = new queue();
		queue outputQ = new queue();

//		Parse the input string and store in the inputQ
		StringTokenizer inputStr = new StringTokenizer(keyboardInput, "%()+-*/.^ ", true);
		
//		Fills the queue
		while(inputStr.hasMoreTokens()) {
			inputQ.enqueue(inputStr.nextToken());
		}

//		count the number of operator and numbers ot determine if it's negative
		int countoper = 0;
		int countnumb = 0;
		
//		Shunting yard algorithm with queue and stack then store item in outputQ
		String element;
		while(!inputQ.isEmpty()) {
			element = inputQ.dequeue();
			
//			Check if it's an integer or "." then store element in outputQ
			if(isInteger(element) || element.equalsIgnoreCase(".")) {
				if(isInteger(element)) {
//				increment because number
					countnumb++;
				} else {
// 	Increment because "." is operator
					
					countoper++;
				}
				outputQ.enqueue(element);
				
//			Check if it has brackets to parse bracket expressions
			} else if(element.equalsIgnoreCase("(")) {
				operator.push(element);
				
			} else if(element.equalsIgnoreCase(")")) {
				
//	once it reaches right bracket pop elements in stack until it reaches the left bracket
				while(!operator.peek().equalsIgnoreCase("(")){
					outputQ.enqueue(" ");
					outputQ.enqueue(operator.pop());
				}
//	pop the left bracket
				operator.pop();
				
//	ignore whitespace
			} else if(element.equalsIgnoreCase(" ")){
				continue;
				
			} else {
//		check when its - if it means the binary or unary operator
				countoper++;
				if (element.equalsIgnoreCase("-") && outputQ.isEmpty()) {
//				if - is the first element it's automatically known its the minus value
					outputQ.enqueue(element);
//			after the - is queued uncount the operator.
					countoper--;
					continue;
				} else if (!operator.isEmpty()) { 
//		check that the number of operators is greater the amount of number.
					if (element.equalsIgnoreCase("-") && (countnumb < countoper)) {
						outputQ.enqueue(element);
						countoper--;
						continue;
					}
				}
//	when element is an operator continue checking precedence and pop if it's less than or equal to the top of stack
				while(!operator.isEmpty() && precedence(element) <= precedence(operator.peek())) {
					outputQ.enqueue(" ");
					outputQ.enqueue(operator.pop());
				}
				
				outputQ.enqueue(" ");
				operator.push(element);
				
			}
		}
		
// pop all remaining operators in stack until it's empty
		while(!operator.isEmpty()) {
			outputQ.enqueue(" ");
			outputQ.enqueue(operator.pop());
		}
		
//	print the postfix outputQ
		System.out.print("Postfix: ");
		String item;
		while(!outputQ.isEmpty()) {
			item = outputQ.dequeue();
			System.out.print(item);

		}


	}	
	
//	Check is the String is integer	
	public static boolean isInteger(String item){
		try{
//		Use Java Integer methods to determine if its an integer
			Integer.valueOf(item);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
// Check the precedence of the String
	public static int precedence(String operator) {

//	set operator values 
		if(operator.equalsIgnoreCase("+") || operator.equalsIgnoreCase("-")) {
			return 0;
		} else if(operator.equalsIgnoreCase("/") || operator.equalsIgnoreCase("*")) {
			return 1;
		} else if(operator.equalsIgnoreCase("^")) {
			return 2;
		}
//	if not operator return code -1
		return -1;
	}
}
