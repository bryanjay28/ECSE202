package assignment5;

//Bryan Jay
//260738764

import java.util.Scanner;
import java.util.StringTokenizer;

public class JCalc {

	public static void main(String[] args) {
			
			String keyboardInput = "";
			System.out.print("Enter Expression: ");
			
	//		Create scanner object to take in input
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			
	//		Next line stored into keyboardInput variable
			keyboardInput = sc.nextLine();
			queue out = new queue();
			
			
	//		Check that input is not empty
			if(keyboardInput.isEmpty()) {
				System.out.print("Please enter valid string");
				System.exit(0);
			}
			out = In2post(keyboardInput);
			double result = pCalc(out);
			
			System.out.println(keyboardInput + " = " + result);			
	}
	
	public static double pCalc(queue outputQ) {
		
		stack expression = new stack();
		String token;
		double result = 0, numb1, numb2;
		
		while(!outputQ.isEmpty()) {
			token = outputQ.dequeue();
			
			if(isNumber(token)) {
				expression.push(token);
				
			} else if(token.equalsIgnoreCase("+")) {
				numb2 = converter(expression.pop());				
				numb1 = converter(expression.pop());
				result = numb1 + numb2;

				expression.push(Double.toString(result));				
			} else if(token.equalsIgnoreCase("-")) {
				numb2 = converter(expression.pop());				
				numb1 = converter(expression.pop());
				result = numb1 - numb2;

				expression.push(Double.toString(result));				
			} else if(token.equalsIgnoreCase("*")) {
				numb2 = converter(expression.pop());				
				numb1 = converter(expression.pop());
				result = numb1 * numb2;

				expression.push(Double.toString(result));				
			} else if(token.equalsIgnoreCase("/")) {
				numb2 = converter(expression.pop());				
				numb1 = converter(expression.pop());
				if(numb2 == 0) {
					System.out.println("Can't divide by 0");
					System.exit(0);
				}
				result = numb1 / numb2;

				expression.push(Double.toString(result));				
			} else if(token.equalsIgnoreCase("^")) {
				numb2 = converter(expression.pop());				
				numb1 = converter(expression.pop());
				result = numb1;
				if(numb2 == 0 ) {
					result = 1;
				} else {
					for(int i = 1; i < numb2; i++) {
						result *= numb1;
					}
				}

				expression.push(Double.toString(result));				
			}
		}
		return converter(expression.pop());
		
	}
	
//	Check is the String is integer	
	public static boolean isNumber(String item) {
		try {
			Double.parseDouble(item);
		}
		catch(NumberFormatException nfe) {
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
	
	public static double converter(String item) {
		return Double.valueOf(item);

	}
	
	public static queue In2post(String Input) {

//		Initialize both queues one to store input expression and the other to store the output expression
		stack operator = new stack();		
		queue inputQ = new queue();
		queue outputQ = new queue();
		queue tempQ = new queue();

//		Parse the input string and store in the inputQ
		StringTokenizer inputStr = new StringTokenizer(Input, "%()+-*/^ ", true);
		
//		Fills the queue
		while(inputStr.hasMoreTokens()) {
			tempQ.enqueue(inputStr.nextToken());
		}
		
		String whitespace;
		while(!tempQ.isEmpty()) {
			whitespace = tempQ.dequeue();
			if(whitespace.equalsIgnoreCase(" ")) {
				continue;
			} else {
				inputQ.enqueue(whitespace);
			}
		}
		
//		Shunting yard algorithm with queue and stack then store item in outputQ
		String element, temp;
		while(!inputQ.isEmpty()) {
			element = inputQ.dequeue();

//			Check if it's an number then store element in outputQ
			if(isNumber(element)) {
				if(isNumber(element)) {
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

//		if first token is "-" then it is unary minus
			} else if(element.equalsIgnoreCase("-") && outputQ.isEmpty()) {
				temp = inputQ.dequeue();					
				outputQ.enqueue(element.concat(temp));
				
//		if token is operator and the next token is minus then it must be urnary minus
			} else {
				if(inputQ.front.token.equalsIgnoreCase("-")) {
					temp = inputQ.dequeue();
					inputQ.front.token = temp.concat(inputQ.front.token);
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
		return outputQ;
	}
}

