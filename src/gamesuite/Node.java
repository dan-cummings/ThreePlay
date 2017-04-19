package gamesuite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Generic node class for local use in MinMaxTree and tree for possible jumps.
 * @author Daniel Cummings
 * @version 1.0
 * @param <E> Type of data being stored in node.
 */
public class Node<E> implements Serializable {
	
	/** Default Serial code. */
	private static final long serialVersionUID = 1L;
	/** Depth of the tree. */
	private int depth;
	/** Data stored in Node. */
	private E data;
	/** List of leaf nodes. */
	private List<Node<E>> leaf;
	/** Value of the data state. */
	private int value;
	/** root of the current node. */
	private Node<E> root;

	/**
	 * Constructor for node class. Allows Node to be given a root
	 * and data to store.
	 * @param d Data to be stored in the node.
	 * @param p root of the node.
	 */
	public Node(final E d, final Node<E> p) {
		if (p == null) {
			this.depth = 0;
		} else {
			this.depth = p.depth + 1;
		}
		this.root = p;
		this.data = d;
		this.value = 0;
		this.leaf = new ArrayList<Node<E>>();
	}
	
	/**
	 * Getter method for all of the leaves on the
	 * current node.
	 * @return A list of nodes if the caller is
	 * not a leaf. Otherwise it will return null.
	 */
	public List<Node<E>> getLeaves() {
		return this.leaf;
	}

	/**
	 * Creates a new node and makes it a leaf of the root node.
	 * @param info Data to be stored in a new leaf node object.
	 */
	public void addleaf(final E info) {
		this.leaf.add(new Node<E>(info, this));
	}
	
	/**
	 * Places the node into the leaf of the root node.
	 * @param c Node to be inserted into the leaf.
	 */
	public void addleaf(final Node<E> c) {
		this.leaf.add(c);
	}

	/**
	 * Goes through leaf of the root node to find the node which
	 * contains the given data.
	 * @param d The data that is trying to be found.
	 * @return The leaf node which contains the data passed to the
	 * function.
	 * If none are found a null value is passed.
	 */
	public Node<E> getleaf(final E d) {
		for (Node<E> c : leaf) {
			if (c.getData().equals(d)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Getter method for the data in the node.
	 * @return the data stored in the node.
	 */
	public E getData() {
		return this.data;
	}

	/**
	 * Deletes the node that is passed to the method from the root
	 * node's leaf. 
	 * @param c Node to be deleted.
	 */
	public void deleteLeaf(final Node<E> c) {
		this.leaf.remove(c);
	}

	/**
	 * Deletes leaf nodes which contain the provided data.
	 * @param d Data contained in the node to be deleted.
	 */
	public void deleteLeaf(final E d) {
		Node<E> temp = this.getleaf(d);
		if (temp != null) {
			this.deleteLeaf(temp);
		}
	}

	/**
	 * Getter method to determine if the node has leaf.
	 * @return True if leaf list is not empty.
	 */
	public boolean isLeaf() {
		return this.leaf.isEmpty();
	}

	/**
	 * Clears the leaf of nodes.
	 */
	public void clear() {
		if (this.leaf.isEmpty()) {
			return;
		} else {
			this.leaf.clear();
		}
	}

	/**
	 * Getter method for the nodes derived value. (Local min-max use)
	 * @return The value of the node.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets nodes derived value. (Local min-max use)
	 * @param v Derived value for the node.
	 */
	public void setValue(final int v) {
		this.value = v;
	}

	/**
	 * Getter method for the depth of current node.
	 * @return Depth of the node.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Set's the depth of the current node.
	 * @param dep Depth for the node.
	 */
	public void setDepth(final int dep) {
		this.depth = dep;
	}

	/**
	 * Helper method to find the depth of the tree.
	 * @param r Root node for the subtree.
	 * @return The maximum depth of the tree.
	 */
	public int calcDepth(final Node<E> r) {
		if (r == null) {
			return 0;
		}
		int max = 0;
		for (Node<E> c : r.leaf) {
			max = Math.max(calcDepth(c), max);
		}
		return max + 1;
	}
	
	/**
	 * Method to find the nodes that exist at the specified depth.
	 * @param dep The depth of the nodes you wish to find.
	 * @param stack List to store nodes at depth.
	 */
	public void findAtDepth(final int dep, final Stack<Node<E>> stack) {
		if (this.depth != dep) {
			for (Node<E> l : this.leaf) {
				l.findAtDepth(dep, stack);
			}
		} else {
			stack.push(this);
		}
	}
	
	/**
	 * Getter method for the root of the calling node if it
	 * exists.
	 * @return The root of the current node.
	 */
	public Node<E> getRoot() {
		return this.root;
	}
}
