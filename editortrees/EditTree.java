package editortrees;

import editortrees.Node.Code;
import editortrees.Node.HeightAndCodesCorrect;
import editortrees.Node.NodeInfo;

public class EditTree {

	Node root;
	private int size;
	private DisplayableBinaryTree display;

	public EditTree() {
		this.root = Node.NULL_NODE;
		this.size = 0;
	}

	public EditTree(char ch) {
		this.root = new Node(ch);
		this.size = 1;
	}

	public EditTree(EditTree e) {
		this.root = cloneTree(e.root);
		this.size = e.size;
	}
	
	public Node cloneTree(Node base) {
		if (base == Node.NULL_NODE) {
			//clone-null
			return Node.NULL_NODE;
		}
		Node cloneBase = new Node(base.data);
		//clone-copy
		cloneBase.balance = base.balance;
		cloneBase.rank = base.rank;
		cloneBase.left = cloneTree(base.left);
		cloneBase.right = cloneTree(base.right);
		return cloneBase;
	}

	public EditTree(String s) {
		this.size = s.length();
		if (s.length() == 0) {
			//build-empty
			this.root = Node.NULL_NODE;
		} else if (s.length() == 1) {
			//build-single
			Node newNode = new Node(s.charAt(s.length() / 2));
			this.root = newNode;
		} else if (s.length() == 2) {
			//build-two
			Node newNode = new Node(s.charAt(1));
			newNode.rank = 1;
			newNode.balance = Code.LEFT;
			this.root = newNode;
			root.left = new EditTree(s.substring(0, s.length() / 2)).root;
		} else {
			//build-recursive
			Node newNode = new Node(s.charAt(s.length() / 2));
			newNode.rank = s.length() / 2;
			this.root = newNode;
			root.left = new EditTree(s.substring(0, s.length() / 2)).root;
			root.right = new EditTree(s.substring(s.length() / 2 + 1, s.length())).root;
		}
	}

	@Override
	public String toString() {
		return root.generateString();
	}

	public int size() {
		return this.size;
	}

	public void add(char ch) {
		add(ch, size);
	}

	public void add(char ch, int pos) throws IndexOutOfBoundsException {
		if (pos > this.size() || pos < 0) {
			//add-bounds
			throw new IndexOutOfBoundsException("The given position is greater than the size of the tree.");
		} else {
			//add-delegate
			root = root.insert(ch, pos).myNode;
		}
		this.size++;
	}

	public String toRankString() {
		String myString = root.toRankString();
		if (!myString.isEmpty()) {
			//drop-tail
			myString = myString.substring(0, myString.length() - 2);
		}
		return "[" + myString + "]";
	}

	public char get(int pos) throws IndexOutOfBoundsException {
		return root.get(pos);
	}

	public int slowHeight() {
		return root.slowHeight();
	}

	public int slowSize() {
		return root.slowSize();
	}

	public boolean ranksMatchLeftSubtreeSize() {
		return root.ranksMatchLeftSubtreeSize();
	}

	public String toDebugString() {
		String myString = root.toDebugString();
		if (!myString.isEmpty()) {
			//drop-tail
			myString = myString.substring(0, myString.length() - 2);
		}
		return "[" + myString + "]";
	}

	public int totalRotationCount() {
		return root.getTotalRotationCount();
	}

	public boolean balanceCodesAreCorrect() {
		return root.balanceCodesAreCorrect().codesCorrect;
	}

	public int fastHeight() {
		return root.fastHeight();
	}

	public char delete(int pos) throws IndexOutOfBoundsException {
		if (pos >= size || pos < 0) {
			//delete-bounds
			throw new IndexOutOfBoundsException();
		}
		NodeInfo deleteInfo = root.delete(pos);
		//delete-relink
		root = deleteInfo.myNode;
		return deleteInfo.deletedNode.data;
	}

	public String get(int pos, int length) throws IndexOutOfBoundsException {
		if (pos + length > size || pos < 0) {
			//slice-bounds
			throw new IndexOutOfBoundsException("Index out of bounds for get method :(");
		}
		StringBuilder sb = new StringBuilder();
		//slice-build
		root.get(pos, pos + length - 1, sb);
		return sb.toString();
	}

	public void show() {
		if (this.display == null) {
			this.display = new DisplayableBinaryTree(this, 960, 1080, true);
		} else {
			this.display.show(true);
		}
	}

	public void close() {
		if (this.display != null) {
			this.display.close();
		}
	}
}
