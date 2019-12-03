//	Bryan Jay
//	260738764



public class stack{ 
//	Initialize top of the stack
	public ListNode top;
	
//	Stack constructor
	public stack() {
		super();
		top = null;
	}
	
// Fill stack
	public void push(String inString) {		
//	Initialize new node for element
		ListNode temp = new ListNode();
		
// Check if stack is empty
		if(top == null) {
			top = temp;
			
//	If not empty link elements
		} else {
			temp.prev = top;
			top.next = temp;			
			top = temp;
		}
		top.token = inString;
	}
	
//	Check if stack is empty
	public boolean isEmpty() {
		if(top == null) {
			return true;
		} else {
			return false;
		}
	}
	

	public String pop() {
		String temp;

//		check if stack is empty
		if(top == null) {
			System.out.println("Stack is empty");
			return null;
		}
//	Check if only one element in stack	
		if(top.prev == null) {
			temp = top.token;
			top = null;
			
			return temp;
			
		} else {
			temp = top.token;
			top = top.prev;
			
			return temp;
		}
	}

//	Returns the top of the stack without removing it from stack
	public String peek() {
		
		if(top == null) {
			return null;
		} else {
			return top.token;			
		}
	}		
}