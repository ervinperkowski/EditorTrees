package editortrees;

import java.util.ArrayList;

public class Node {

	enum Code {
		SAME, LEFT, RIGHT;

		public String toString() {
			switch (this) {
			case LEFT:
				return "/";
			case SAME:
				return "=";
			case RIGHT:
				return "\\";
			default:
				throw new IllegalStateException();
			}
		}
	}

	char data;
	Node left, right; 
	int rank; 
	Code balance;
	DisplayableNodeWrapper displayableNodeWrapper;
	private int totalRotationCount = 0;

	static final Node NULL_NODE = new Node('\0', null, null);
	
	public Node(char data, Node left, Node right) {
		this.left = left;
		this.right = right;
		this.data = data;
		
	}

	public Node(char data) {
		
		this(data, NULL_NODE, NULL_NODE);
		this.balance = Code.SAME;
		displayableNodeWrapper = new DisplayableNodeWrapper(this);
	}

	int slowHeight() {
		if (this == NULL_NODE) {
			return -1;
		}
		return Math.max(left.slowHeight(), right.slowHeight()) + 1;
	}

	public int slowSize() {
		if (this == NULL_NODE) {
			return 0;
		}
		return left.slowSize() + right.slowSize() + 1;
	}
	
	public boolean hasLeft() {
		return this.left != NULL_NODE;
	}

	public boolean hasRight() {
		return this.right != NULL_NODE;
	}

	public boolean hasParent() {
		return false;
	}

	public Node getParent() {
		return NULL_NODE;
	}

	public String generateString() {
		if(this == NULL_NODE) {
			return "";
		}
		else {
			//inorder-cat
			return this.left.generateString()+this.data+ this.right.generateString();
		}
		
	}
	
	public NodeInfo insert(char ch, int pos) {
		if(this == NULL_NODE) {
			//leaf-insert
			Node leaf = new Node(ch);
			return new NodeInfo(leaf, true);
			
		}
		else {
			NodeInfo insertInfo;
			if (pos <= this.rank) {
				//rank++left
				this.rank ++;
				insertInfo = left.insert(ch, pos);
				left = insertInfo.myNode;
				if(insertInfo.continueRebalance) { 
					//rebalance-left
					return this.rebalanceFromInsert(Code.LEFT); 
				}
				else {
					return new NodeInfo(this, false);
				}
			}
			else {
				insertInfo = right.insert(ch, pos - (this.rank+1));
				right = insertInfo.myNode;
				if(insertInfo.continueRebalance) {
					//rebalance-right
					return this.rebalanceFromInsert(Code.RIGHT); 
				}
				else {
					return new NodeInfo(this, false);
				}
			}
			
		}
		
	}
	
	private NodeInfo rebalanceFromInsert(Code tilt) {
		Node returnNode = this;
		boolean continueRebalance = true;
		switch(this.balance) {
		case LEFT:
			if(tilt == Code.LEFT) {
				//fix-LL
				if(this.left.balance == Code.LEFT) { 	
					returnNode = this.singleRotation(false);		
					continueRebalance = false;
				}
				else {										
					//fix-LR
					returnNode = this.doubleRotation(false); 		
					continueRebalance = false;
				}
			}
			else {
				this.balance = Code.SAME;
				continueRebalance = false;
			}
			break;
		case RIGHT:
			if(tilt == Code.LEFT) {
				this.balance = Code.SAME;
				continueRebalance = false;
			}
			else {
				//fix-R*
				if(this.right.balance == Code.RIGHT) { 	
					returnNode = singleRotation(true); 			
					continueRebalance = false;
				}
				else {										
					//fix-RL
					returnNode = doubleRotation(true); 			
					continueRebalance = false;
				}
			}
			break;
		case SAME:
			if(tilt == Code.LEFT) {
				this.balance = Code.LEFT;
			}
			else {
				this.balance = Code.RIGHT;
			}
			break;
		}
		return new NodeInfo(returnNode, continueRebalance);
	}
	
	public String toRankString() {
		if(this == NULL_NODE) {
			return "";
		}
		else {
			return "" + this.data + this.rank + ", " + left.toRankString() + right.toRankString();
		}
	}

	public boolean ranksMatchLeftSubtreeSize() {
	    if (this == NULL_NODE) {
	        return true; 
	    }
	    int leftSize = left.slowSize();  
	    if (this.rank != leftSize) {
	        return false;  
	    }
	    return left.ranksMatchLeftSubtreeSize() && right.ranksMatchLeftSubtreeSize();
	}

	public char get(int pos) {
	    if (this == NULL_NODE) {
	        throw new IndexOutOfBoundsException("Pos is out of bounds");
	    }
	    if (pos < this.rank) {
	        return left.get(pos);  
	    } else if (pos == this.rank) {
	        return this.data;  
	    } else {
	        return right.get(pos - (this.rank + 1));  
	    }
	}
	
	private Node singleRotation(boolean leftOrRight) { 
		this.totalRotationCount++;
		Node newParent;
		if(leftOrRight) { 	
			//rot-L
			newParent = this.right;
			this.right = newParent.left;
			newParent.left = this;
			newParent.rank = newParent.rank + newParent.left.rank + 1;
		}
		else {				
			//rot-R
			newParent = this.left;
			this.left = newParent.right;
			newParent.right = this;
			this.rank = this.rank - newParent.rank - 1;
		}
		newParent.balance = Code.SAME;
		this.balance = Code.SAME;
		return newParent;
	}
	
	private Node doubleRotation(boolean leftOrRight) { 
		this.totalRotationCount = this.totalRotationCount + 2;
		Node newParent;
		Node newLeft;
		Node newRight;
		if(leftOrRight) { 	
			//rot-RL
			newParent = this.right.left;
			newRight = this.right;
			newLeft = this;
			newRight.rank = newRight.rank - newParent.rank - 1;
			newParent.rank = newParent.rank + newLeft.rank + 1;
		}
		else {				
			//rot-LR
			newParent = this.left.right;
			newRight = this;
			newLeft = this.left;
			newRight.rank = newRight.rank - newLeft.rank - newParent.rank - 2;
			newParent.rank = newParent.rank + newLeft.rank + 1;
		}
		newRight.left = newParent.right;
		newLeft.right = newParent.left;
		newParent.right = newRight;
		newParent.left = newLeft;
		switch(newParent.balance) {
		case LEFT:
			newLeft.balance = Code.SAME;
			newRight.balance = Code.RIGHT;
			break;
		case RIGHT:
			newLeft.balance = Code.LEFT;
			newRight.balance = Code.SAME;
			break;
		case SAME:
			newLeft.balance = Code.SAME;
			newRight.balance = Code.SAME;
			break;
		default:
			break;
		
		}
		newParent.balance = Code.SAME;
		return newParent;
	}
	
	public String toDebugString() {
		if(this == NULL_NODE) {
			return "";
		}
		else {
			return "" + this.data + this.rank + this.balance.toString() + ", " + left.toDebugString() + right.toDebugString();
		}
	}
	
	public int getTotalRotationCount() {
		if(this == NULL_NODE) {
			return 0;
		}
		return this.totalRotationCount+this.left.getTotalRotationCount() + this.right.getTotalRotationCount();
	}
	
	public HeightAndCodesCorrect balanceCodesAreCorrect() {
		if(this == NULL_NODE) {
			return new HeightAndCodesCorrect(-1,  true);
		}
		else {
			HeightAndCodesCorrect leftHCC = this.left.balanceCodesAreCorrect();
			HeightAndCodesCorrect rightHCC = this.right.balanceCodesAreCorrect();
			int myHeight = Math.max(leftHCC.height, rightHCC.height) + 1;
			boolean myCodesAreCorrect = false;
			switch(this.balance) {
			case LEFT:
				if(leftHCC.height == rightHCC.height + 1) {
					myCodesAreCorrect = true;
				}
				break;
			case RIGHT:
				if(leftHCC.height + 1 == rightHCC.height) {
					myCodesAreCorrect = true;
				}
				break;
			case SAME:
				if(leftHCC.height == rightHCC.height) {
					myCodesAreCorrect = true;
				}
				break;
			default:
				break;
			}
			myCodesAreCorrect = myCodesAreCorrect && leftHCC.codesCorrect && rightHCC.codesCorrect; 
			return new HeightAndCodesCorrect(myHeight, myCodesAreCorrect);
		}
	}
	
	public class NodeInfo {
		Node myNode;
		Code rebalanceUpward;
		Node deletedNode;
		boolean continueRebalance;
		NodeInfo(Node myNode, boolean continueRebalance){
			this.myNode = myNode;
			this.continueRebalance = continueRebalance;
		}
		public NodeInfo(Node myNode, boolean continueRebalance, Node deletedNode) {
			this.myNode = myNode;
			this.continueRebalance = continueRebalance;
			this.deletedNode = deletedNode;
		}
	}
	
	public class HeightAndCodesCorrect{
		int height;
		boolean codesCorrect;
		HeightAndCodesCorrect(int height, boolean codesCorrect){
			this.height = height;
			this.codesCorrect = codesCorrect;
		}
	}
	
	public int fastHeight() {
		if(this == NULL_NODE) {
			return -1;
		}
		switch(this.balance) {
		case LEFT:
			return this.left.fastHeight()+1;
		case RIGHT:
			return this.right.fastHeight()+1;
		case SAME:
			return this.left.fastHeight()+1;
		default:
			return 0;
		}
	}

	public void get(int leftPos, int rightPos, StringBuilder sb) {
		if(this == NULL_NODE) {
			return;
		}
		if(leftPos<rank) { 
			//pull-left
			left.get(leftPos, Math.min(rank-1,rightPos), sb);
		}
		if(leftPos<=rank && rank<= rightPos) { 
			//pull-self
			sb.append(this.data);
		}
		if(rightPos > rank) {
			//pull-right
			right.get(Math.max(leftPos-rank-1, 0), rightPos-rank-1, sb);
		}
		return;
	}

	public NodeInfo delete(int pos) {
		if (pos == this.rank) {		
			//hit-target
			if(!this.hasLeft() && !this.hasRight()) { 
				return new NodeInfo(NULL_NODE, true, this);
			}
			else if(this.hasLeft() && !this.hasRight()){ 
				//only-left
				return new NodeInfo(this.left, true, this);
			}
			else if(!this.hasLeft() && this.hasRight()){ 
				//only-right
				return new NodeInfo(this.right, true, this);
			}
			else{ 
				//swap-successor
				NodeInfo deleteInfo = this.right.delete(0);
				this.right = deleteInfo.myNode;
				Node successor = deleteInfo.deletedNode;
				char deleteData = this.data; 		
				this.data = successor.data; 		
				if (deleteInfo.continueRebalance) {
					NodeInfo rebalanceInfo = this.leftRebalanceFromDelete();
					return new NodeInfo(rebalanceInfo.myNode, rebalanceInfo.continueRebalance, new Node(deleteData));
				}
				else {
					return new NodeInfo(this, false, new Node(deleteData));
				}
			}
	    } 
		else if (pos < this.rank) {	
			//step-left
			this.rank--;
	    	NodeInfo deleteInfo = left.delete(pos); 
	    	this.left = deleteInfo.myNode;			
	    	if(deleteInfo.continueRebalance) {
	    		NodeInfo rebalanceInfo = this.rightRebalanceFromDelete();
	    		return new NodeInfo(rebalanceInfo.myNode, rebalanceInfo.continueRebalance, deleteInfo.deletedNode);
	    	}
	    	else {
	    		return new NodeInfo(this, false, deleteInfo.deletedNode);
	    	}
	          
	    } 
	    else {					
	    	//step-right
	    	NodeInfo deleteInfo = right.delete(pos - (this.rank + 1)); 
	    	this.right = deleteInfo.myNode;			
	    	if(deleteInfo.continueRebalance) {
	    		NodeInfo rebalanceInfo = this.leftRebalanceFromDelete();
	    		return new NodeInfo(rebalanceInfo.myNode, rebalanceInfo.continueRebalance, deleteInfo.deletedNode);
	    	}
	    	else{
	    		return new NodeInfo(this, false, deleteInfo.deletedNode);
	    	}
	    }
	}
	
	private NodeInfo rightRebalanceFromDelete() {
		NodeInfo rebalanceInfo = new NodeInfo(this, true);
		switch(this.balance) {
		case LEFT:
		//balance=
			this.balance = Code.SAME;
			break;
		case RIGHT:
		//heavy-right
			if(this.right.balance == Code.LEFT) { 
				return new NodeInfo(this.doubleRotation(true), true);
			}
			else {								
			//rot-right
				return new NodeInfo(this.singleRotation(true), true);
			}
		case SAME:
		//tilt-right
			this.balance = Code.RIGHT;
			rebalanceInfo.continueRebalance = false; 
			break;
		default:
			break;
		}
		return rebalanceInfo;
	}
	private NodeInfo leftRebalanceFromDelete() {
		NodeInfo rebalanceInfo = new NodeInfo(this, true);
		switch(this.balance) {
		case LEFT:
		//heavy-left
			if(this.left.balance == Code.RIGHT) { 
				return new NodeInfo(this.doubleRotation(false), true);
			}
			else {								
			//rot-left
				return new NodeInfo(this.singleRotation(false), true);
			}
		case RIGHT:
		//balance=
			this.balance = Code.SAME;
			break;
		case SAME:
		//tilt-left
			this.balance = Code.LEFT;
			rebalanceInfo.continueRebalance = false;
		default:
			break;
		}
		return rebalanceInfo;
	}

	private Node recurseLeft() {
		if(this.hasLeft()) {
			return this.left.recurseLeft();
		}
		else {
			return this;
		}
	}
	
}