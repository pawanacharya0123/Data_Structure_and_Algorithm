import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
	static Node root;
	Queue<Node> queue= new LinkedList<>();
	static class Node{
		int data;
		Node left;
		Node right;
		
		Node(int data){
			this.data= data;
		}
	}
	
	private static void reverseEach(Node node) {
		// TODO Auto-generated method stub
		if(node==null) return;
		Node current_node= node;
		Node left= current_node.left; 
		Node right= current_node.right;
		
		current_node.left= right;
		current_node.right= left;
		
		reverseEach(node.left);
		reverseEach(node.right);
	}

	
	private void showTree() {
		// TODO Auto-generated method stub
		System.out.println(this.root.data);
		showEachBranch(this.root);
		
	}
	private void showEachBranch(Node current_node) {
		if(!(current_node.left== null||current_node.right== null)) {
			this.queue.add(current_node.left);
			this.queue.add(current_node.right);
			
			System.out.print("{"+current_node.data+"}");
			System.out.print("[ "+current_node.left.data);
			System.out.println(current_node.right.data+ " ]");
		}
		if(this.queue.isEmpty()) return;
		showEachBranch(this.queue.remove());
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryTree b_tree= new BinaryTree();
		b_tree.root= new Node(1);
		
		b_tree.root.left= new Node(2);
		b_tree.root.right= new Node(3);
		
		b_tree.root.left.left= new Node(4);
		b_tree.root.left.right= new Node(5);
		
		b_tree.root.right.left= new Node(6);
		b_tree.root.right.right= new Node(7);
		
		b_tree.root.left.left.left= new Node(8);
		b_tree.root.left.left.right= new Node(9);
		
		b_tree.showTree();
		
		System.out.println("revesed B_TREE is:");
		reverseEach(b_tree.root);
		
		b_tree.showTree();
	}

	

	

}
