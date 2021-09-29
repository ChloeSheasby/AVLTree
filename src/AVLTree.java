import java.util.ArrayList;

public class AVLTree // implements Cloneable
{
	private static class Node
	{
		private String value;
		private int key;
		private Node left;
		private Node right;
		private Node parent;
		private int height;
		public Node(int k, String e, Node p)
		{
			this.key = k;
			this.value = e;
			this.parent = p;
		}
		public String getValue()
		{
			return value;
		}
		public int getKey()
		{
			return key;
		}
		public Node getLeft()
		{
			return left;
		}
		public Node getRight()
		{
			return right;
		}
		public Node getParent()
		{
			return parent;
		}
		@SuppressWarnings("unused")
		public void setValue(String e)
		{
			value = e;
		}

		public void setLeft(Node l)
		{
			left = l;
			//left.setParent(this);
		}
		public void setRight(Node r)
		{
			right = r;
			//right.setParent(this);
		}
		public void setParent(Node p)
		{
			parent = p;
		}
		
		public Boolean isRight(Node child)
		{
			if(child == this.getRight())
				return true;
			return false;
		}
		
		public Boolean isLeft(Node child)
		{
			if(child == this.getLeft())
				return true;
			return false;
		}
		
		public int height()
		{
			if(this.getParent() == null)
			{
				this.height = 0;
				return 0;
			}
			else
			{
				this.height = 1 + this.getParent().height();
				return this.height;
			}
		}
		
		public Boolean isBalanced()
		{
			if(this.getLeft() == null && this.getRight() == null)
				return true;
			else if(this.getLeft() == null)
			{
				return Math.abs(this.height() - this.getRight().height()) <= 1;
			}
			else if(this.getRight() == null)
			{
				return Math.abs(this.height() - this.getLeft().height()) <= 1;
			}
			return Math.abs(this.getLeft().height() - this.getRight().height()) <= 1;
		}
		
		public void insert(int key, String value, AVLTree tree)
		{
			if(key < this.getKey())
			{
				if(this.getLeft() != null)
					this.getLeft().insert(key, value, tree);
				else
					this.setLeft(new Node(key, value, this));
				rebalance(this.getLeft(), null, tree);
			}
			else if(key > this.getKey())
			{
				if(this.getRight() != null)
					this.getRight().insert(key, value, tree);
				else
					this.setRight(new Node(key, value, this));		
				rebalance(this.getRight(), null, tree);
			}
		}
		
		public void rebalance(Node child, Node grandchild, AVLTree tree)
		{
			if(isBalanced())
			{
				if(this.getParent() == null)
					return;
				else
					this.getParent().rebalance(this, child, tree);
			}
			else
			{
//			a)	y is left child of z and x is left child of y (Left Left Case) - rotateRight(z)
//			b)	y is left child of z and x is right child of y (Left Right Case) rotateLeft(y) rotateRight(z)
//			c)	y is right child of z and x is right child of y (Right Right Case)  rotateLeft(z)  
//			d)	y is right child of z and x is left child of y (Right Left Case) rotateRight(y) rotateLeft(z)

				//left left
				if(this.isLeft(child) && child.isLeft(grandchild))
				{
					rotateRight(child, tree);
				}
				//left right
				else if(this.isLeft(child) && child.isRight(grandchild))
				{
					child.rotateLeft(grandchild, tree);
					rotateRight(grandchild, tree);
				}
				//right right
				else if(this.isRight(child) && child.isRight(grandchild))
				{
					rotateLeft(child, tree);
				}
				//right left
				else if(this.isRight(child) && child.isLeft(grandchild))
				{
					child.rotateRight(grandchild, tree);
					rotateLeft(grandchild, tree);
				}
			}
		}
		
		private void rotateRight(Node child, AVLTree tree)	
		{
			if(this.getParent() != null)
				this.getParent().setLeft(child);
			this.setLeft(child.getRight());
			child.setParent(this.getParent());
			this.setParent(child);
			child.setRight(this);
			
			if(child.getParent() == null)
			{
				tree.setRoot(this);
			}
		}
		
		private void rotateLeft(Node child, AVLTree tree)
		{		
			if(this.getParent() != null)
				this.getParent().setRight(child);
			this.setRight(child.getLeft());
			child.setParent(this.getParent());
			this.setParent(child);
			child.setLeft(this);
			
			if(child.getParent() == null)
			{
				tree.setRoot(this);
			}
		}
	}
	
	private Node root;
	private ArrayList<String> inOrder;
	private ArrayList<String> postOrder;
	private ArrayList<String> preOrder;
	
	public AVLTree()
	{
		this.inOrder = new ArrayList<String>();
		this.postOrder = new ArrayList<String>();
		this.preOrder = new ArrayList<String>();
	}
	
	public Node getRoot()
	{
		return this.root;
	}
	
	public void setRoot(Node r)
	{
		this.root = r;
	}

	public void insert(int key, String value)
	{
		if(this.root == null)
		{
			this.root = new Node(key, value, null);
			return;
		}
		else
		{
			this.root.insert(key, value, this);
		}
	}
	
	public void delete(int key, Node node)
	{
		if(key == node.getKey())
		{
			// don't know how to actually delete
		}
		else if(key < node.getKey())
		{
			delete(key, node.getLeft());
		}
		else if(key > node.getKey())
		{
			delete(key, node.getRight());
		}
		
	}
	
	private void inOrderTraversal(Node node)
	{
		if(node == null)
			return;
		inOrderTraversal(node.getLeft());
		this.inOrder.add(node.getValue());
		inOrderTraversal(node.getRight());
	}
	
	public ArrayList<String> getInOrder()
	{
		inOrderTraversal(this.root);
		return this.inOrder;
	}
	
	public void printInOrder()
	{
		for(String item : getInOrder())
		{
			System.out.println(item);
		}
		System.out.println();
	}
	
	private void postOrderTraversal(Node node)
	{
		if(node == null)
			return;
		this.postOrder.add(node.getValue());
		postOrderTraversal(node.getLeft());
		postOrderTraversal(node.getRight());
	}
	
	public ArrayList<String> getPostOrder()
	{
		postOrderTraversal(this.root);
		return this.postOrder;
	}
	
	public void printPostOrder()
	{
		for(String item : getPostOrder())
		{
			System.out.println(item);
		}
		System.out.println();
	}
	
	private void preOrderTraversal(Node node)
	{
		if(node == null)
			return;
		preOrderTraversal(node.getLeft());
		preOrderTraversal(node.getRight());
		this.preOrder.add(node.getValue());
	}
	
	public ArrayList<String> getPreOrder()
	{
		preOrderTraversal(this.root);
		return this.preOrder;
	}
	
	public void printPreOrder()
	{
		for(String item : getPreOrder())
		{
			System.out.println(item);
		}
		System.out.println();
	}
	
	public String findValueForKey(int key, Node node)
	{
		if(key < node.getKey())
		{
			if(node.getLeft() != null)
			{
				if(key == node.getLeft().getKey())
					return node.getLeft().getValue();
				else
					findValueForKey(key, node.getLeft());
			} 
		}
		else if(key > node.getKey())
		{
			if(node.getRight() != null)
			{
				if(key == node.getRight().getKey())
					return node.getRight().getValue();
				else
					findValueForKey(key, node.getRight());
			} 
		}
		return node.getValue();
	}
	
	public Boolean isEmpty()
	{
		return (this.root == null);
	}
}



