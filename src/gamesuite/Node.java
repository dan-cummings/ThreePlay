package gamesuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic node class for local use in MinMaxTree.
 * @author Daniel Cummings
 * @version 1.0
 * @param <E> Type of data being stored in node.
 */
public class Node<E> {
	
	/** Data stored in Node. */
	private E data;
	
	/** List of child nodes. */
	private List<Node<E>> children;
	
	/** Reference to root node of current node. */
	private Node<E> root;
	
	/** Value of the data state. */
	private int value;
	
	/**
	 * Constructor for node class. Allows Node to be given a parent
	 * and data to store.
	 * @param d Data to be stored in the node.
	 * @param r Reference to parent node.
	 */
	public Node(final E d, final Node<E> r) {
			this.data = d;
			this.value = 0;
			this.root = r;
			this.children = new ArrayList<Node<E>>();
	}
	
	/**
	 * Creates a new node and makes it a child of the parent node.
	 * @param info Data to be stored in a new child node object.
	 */
	public void addChild(final E info) {
		this.children.add(new Node<E>(info, this));
	}
	
	/**
	 * Goes through children of the root node to find the node which
	 * contains the given data.
	 * @param d The data that is trying to be found.
	 * @return The child node which contains the data passed to the
	 * function.
	 * If none are found a null value is passed.
	 */
	public Node<E> getChild(final E d) {
		for (Node<E> c : children) {
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
	 * Deletes the node that is passed to the method from the parent
	 * node's children.
	 * @param c Node to be deleted.
	 */
	public void deleteChild(final Node<E> c) {
		this.children.remove(c);
	}
	
	/**
	 * Deletes children nodes which contain the provided data.
	 * @param d Data contained in the node to be deleted.
	 */
	public void deleteChild(final E d) {
		this.children.remove(getChild(d));
	}
	
	/**
	 * Getter method for the parent root node.
	 * @return a reference to the parent node, Null if no parent is found.
	 */
	public Node<E> getRoot() {
		return this.root;
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
}
