//	Bryan Jay
//	260738764

package assignment4;
public class queue {
	
//	Initialize list node objects
	public ListNode front;
	public ListNode rear;
	
//	Queue constructor
	public queue() {
		super();
		front = null;
		rear = null;
		
	}
	
//	Store values into the queue
	public void enqueue(String inString) {
		
//		Create new list node object to store new value
		ListNode element = new ListNode();

//		Check is the queue is empty
		if(front == null && rear == null) {
//		if empty set rear and front to first value
			front = element;			
			rear = element;
			
		} else {
//		if not empty add element to end of the list
			element.next = rear;
			rear.prev = element;
			rear = element;
			rear.prev = null;
		}
		rear.token = inString;
	}
	
	public String dequeue() {
		
		if(front == null) {
			System.out.println("Queue is empty");
			return null;
		}
		
		String temp;
//	Check if there is one element inside the queue
		if(front == rear) {
			temp = front.token;
			front = null;
			rear = null;
			
		} else {
			temp = front.token;
			front = front.prev;
		}			
			return temp;			
	}
	
//	Checks if queue is empty
	public boolean isEmpty() {
		if(front == null && rear == null) {
			return true;
		} else {
			return false;
		}
	}
}