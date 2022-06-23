package dataStructure;

class LinkedList {
	Node head;
	class Node{
		String data;
		Node next;
		
		Node(String data){
			this.data= data;
		}
	}
	
	private void addFront(String string) {
		// TODO Auto-generated method stub
		Node current_node= new Node(string);
		if(this.head==null) {
			head= current_node;
		}else {
			current_node.next= head;
			head= current_node;
		}
		return;
	}
	
	private void addToEnd(String string) {
		Node last_node= new Node(string);
		if(this.head==null) {
			head= last_node;
		}else {
			Node current_node= this.head;
			while(current_node.next != null) {
				current_node= current_node.next;
			}
			current_node.next= last_node;
		}
		return;
	}
	
	private void addInBetween(int index, String string) {

		Node middle_node= new Node(string);
		Node head= this.head;
		if(index==0|| head==null) {
			this.head= middle_node;
			middle_node.next= head;
		}else {
			Node current_node= head;
			for (int i = 1; ; i++) {
				if(i== index) {
					middle_node.next= current_node.next;
					current_node.next= middle_node;
					
					break;
				}
				current_node= current_node.next;
			}
		}
		return;
	}
	
	
	private void removeFirst() {
		// TODO Auto-generated method stub
		if(this.head==null)
			return;
		else {
			this.head= this.head.next;
		}
	}
	
	private void removeLast() {
		// TODO Auto-generated method stub
		Node current_node= this.head;
		if(current_node==null)
			return;
		while(current_node.next != null) {
			if(current_node.next.next== null) {
				current_node.next= null;
				break;
			}
			current_node= current_node.next;
		}
	}
	
	private void removeMiddle(int index) {
		// TODO Auto-generated method stub
		Node current_node= this.head;
		for (int i = 1; ; i++) {
			if(i== index) {
				current_node.next= current_node.next.next;
				break;
			}
			current_node= current_node.next;
		}
	}

	private void reverse() {
		// TODO Auto-generated method stub
		Node previous_node= this.head;
		Node current_node= this.head.next;
		
		
		while(current_node!=null) {
			Node next_node= current_node.next;
			current_node.next=previous_node;
			
			//for next iteration
			previous_node= current_node;
			current_node= next_node;
		}
		this.head.next= null;
		this.head= previous_node;
	}
	
	private void print() {
		// TODO Auto-generated method stub
		
		Node current_node= head;
		if(head== null)
			return;
		System.out.print("head -->");
		while(current_node.next != null) {
			System.out.print(current_node.data+ "-->");
			current_node= current_node.next;
		}
		System.out.print(current_node.data+ "--> NULL");
		return;
	}
	
	public static void main(String[] args)
	{
		LinkedList linkList= new LinkedList();
		linkList.addFront("acharya");
		linkList.addFront("pawan");
		linkList.addFront("is");
		linkList.addFront("name");
		linkList.addFront("my");
		
		
		linkList.addInBetween(5, "and");
		linkList.addToEnd("my");
		linkList.addToEnd("college");
		linkList.addToEnd("is ");
		linkList.addToEnd("KEC");
		
		linkList.removeFirst();
		linkList.removeLast();
		linkList.removeMiddle(4);

		linkList.reverse();
		
		linkList.print();
		
	}

	

	
	

	

	
}

// This code has been contributed by Mayank Jaiswal
