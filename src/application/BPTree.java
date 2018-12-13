package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to many different
 * indexes of a large data set. BPTree objects are created for each type of
 * index needed by the program. BPTrees provide an efficient range search as
 * compared to other types of data structures due to the ability to perform
 * log_m N lookups and linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K>
 *            key - expect a string that is the type of id for each item
 * @param <V>
 *            value - expect a user-defined type that stores all data for a food
 *            item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

	// Root of the tree
	private Node root;

	// Branching factor is the number of children nodes
	// for internal nodes of the tree
	private int branchingFactor;

	/**
	 * Public constructor
	 * 
	 * @param branchingFactor
	 */
	public BPTree(int branchingFactor) {
		if (branchingFactor <= 2) {
			throw new IllegalArgumentException("Illegal branching factor: " + branchingFactor);
		}
		this.branchingFactor = branchingFactor;
		root = new LeafNode();
		// TODO : Complete
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void insert(K key, V value) {
		root.insert(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<V> rangeSearch(K key, String comparator) {
		if (!comparator.contentEquals(">=") && !comparator.contentEquals("==") && !comparator.contentEquals("<=")) {
			return new ArrayList<V>();

		}

		return root.rangeSearch(key, comparator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else {
					sb.append('\n');
				}
			}
			queue = nextQueue;
		}
		return sb.toString();
	}

	/**
	 * This abstract class represents any type of node in the tree This class is a
	 * super class of the LeafNode and InternalNode types.
	 * 
	 * @author sapan
	 */
	private abstract class Node {

		// List of keys
		List<K> keys;

		/**
		 * Package constructor
		 */
		Node() {
			this.keys = new ArrayList<K>();
		}

		/**
		 * Inserts key and value in the appropriate leaf node and balances the tree if
		 * required by splitting
		 * 
		 * @param key
		 * @param value
		 */
		abstract void insert(K key, V value);

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @return key
		 */
		abstract K getFirstLeafKey();

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @return Node
		 */
		abstract Node split();

		/*
		 * (non-Javadoc)
		 * 
		 * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
		 */
		abstract List<V> rangeSearch(K key, String comparator);

		/**
		 * 
		 * @return boolean
		 */
		abstract boolean isOverflow();

		public String toString() {
			return keys.toString();
		}

	} // End of abstract class Node

	/**
	 * This class represents an internal node of the tree. This class is a concrete
	 * sub class of the abstract Node class and provides implementation of the
	 * operations required for internal (non-leaf) nodes.
	 * 
	 * @author sapan
	 */
	private class InternalNode extends Node {

		// List of children nodes
		List<Node> children;

		/**
		 * Package constructor
		 */
		InternalNode() {
			super();
			this.children = new ArrayList<Node>();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {
			return children.get(0).getFirstLeafKey();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {
			if (children.size() > branchingFactor) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
		 */
		void insert(K key, V value) {
		int location = Collections.binarySearch(keys, key);
		 int locationIndex = location >= 0? location +1: -location -1;
		 
		 Node child = children.get(locationIndex);
		 
		 child.insert(key,value);
		 
		 
		 if(child.isOverflow()) {
			 
			 Node childSplit = child.split();//split in half, push the value up the tree.
	          K childSplitValue = childSplit.getFirstLeafKey();
	          
	          int insertionLocation = Collections.binarySearch(keys, childSplitValue);
	          
	          int insertionLocationPositive = insertionLocation >=0 ? insertionLocation +1: -insertionLocation -1;
	          
	          if(insertionLocation >=0) {
	        	  children.set(insertionLocationPositive, childSplit);
	        	  
	          }
	          
	          else {
	        	  keys.add(insertionLocationPositive, childSplitValue);
	        	  children.add(insertionLocationPositive +1, childSplit);
	          }
	          
	          
			 
		 }
		 
		 if(root.isOverflow()) {
			 Node childSplit = split();
			 InternalNode newRoot = new InternalNode();
			 newRoot.keys.add(childSplit.getFirstLeafKey());
			 newRoot.children.add(this);
			 newRoot.children.add(childSplit);
			 root = newRoot;
		 }
		 
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {
			int startingSplit = (keys.size() / 2) + 1;
			int endingSplit = keys.size();

			InternalNode newNode = new InternalNode();
			newNode.keys.addAll(keys.subList(startingSplit, endingSplit));
			newNode.children.addAll(children.subList(startingSplit, endingSplit));

			keys.subList(startingSplit -1, endingSplit).clear();
			children.subList(startingSplit, endingSplit +1).clear();

			return newNode;

			
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
		 */
		List<V> rangeSearch(K key, String comparator) {

			Node treeTraverser = root;

			while (treeTraverser instanceof BPTree.InternalNode) { // traverse until reach leafNode

				int nextLocation = Collections.binarySearch(keys, key);
				int posLocation = nextLocation >= 0 ? nextLocation : -nextLocation - 1;
				treeTraverser = children.get(posLocation);
			}

			LeafNode leafs =  (BPTree<K, V>.LeafNode) treeTraverser;

			return leafs.rangeSearch(key, comparator);

			
			
		}
	}
		/**
		 * This class represents a leaf node of the tree. This class is a concrete sub
		 * class of the abstract Node class and provides implementation of the
		 * operations that required for leaf nodes.
		 * 
		 * @author sapan
		 */
		private class LeafNode extends Node {

			// List of values
			List<V> values;

			// Reference to the next leaf node
			LeafNode next;

			// Reference to the previous leaf node
			LeafNode previous;

			/**
			 * Package constructor
			 */
			LeafNode() {
				super();
				this.values = new ArrayList<V>();
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see BPTree.Node#getFirstLeafKey()
			 */
			K getFirstLeafKey() {
				return keys.get(0);
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see BPTree.Node#isOverflow()
			 */
			boolean isOverflow() {

				if (keys.size() > branchingFactor - 1) {// values list is beyond capacity
					return true;
				} else {
					return false;

				}
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see BPTree.Node#insert(Comparable, Object)
			 */
			void insert(K key, V value) {
           int location = Collections.binarySearch(keys, key);
           
           int valueLocation = location >=0 ? location: -location -1; //get positive value of either
           
           if (location >= 0) {// if the key already exists, add it to the value list
        	   values.add(location, value);
           }
           
           else {
        	   keys.add(valueLocation, key);
        	   values.add(valueLocation,value);
        	   
           }
           
           if(root.isOverflow()) { //when root reaches branchingFactor, split node and set it to the new root.
        	   Node split = split();
        	   InternalNode newRoot = new InternalNode();
        	   newRoot.keys.add(split.getFirstLeafKey());
        	   newRoot.children.add(this);
        	   newRoot.children.add(split);
        	   root = newRoot;
           }
           
	
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see BPTree.Node#split()
			 */
			Node split() {
				LeafNode newNode = new LeafNode();

				int indexFrom = (keys.size() + 1) / 2; // split the contents in half
				int indexTo = (keys.size());

				newNode.keys.addAll(keys.subList(indexFrom, indexTo));// add keys and and values into new node
				newNode.values.addAll(values.subList(indexFrom, indexTo));

				keys.subList(indexFrom, indexTo).clear();
				values.subList(indexFrom, indexTo).clear();

				newNode.next = this.next;
				newNode.previous = this;
				
				
			
				this.next = newNode;
				return newNode;
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see BPTree.Node#rangeSearch(Comparable, String)
			 */
			List<V> rangeSearch(K key, String comparator) {
				ArrayList<V> rangeSearchValues = new ArrayList<V>(); // ArrayList to hold the values which are to be
																		// returned
				LeafNode leafs = this;

				if (comparator.equals("<=")) { // if less than iterate to first leafNode

					while (leafs.previous != null) {
						leafs = leafs.previous;
					}

					boolean breakout = true;

					while (breakout) {// iterate through the leafs to add the values

						for (int i = 0; i < leafs.keys.size(); i++) {

							int compareKey = key.compareTo(leafs.keys.get(i));

							if (compareKey == -1) { // key in current leafNode is greater than key so breakout
								breakout = false;
								break;

							} else {// add value to the list to be returned
								rangeSearchValues.add(leafs.values.get(i));
							}

						}
						if (leafs.next != null) { // get next leaf
							leafs = leafs.next;

						} else {
							breakout = false;
							break;
						}

					}

				}

				if (comparator.equals("=>")) {

					boolean breakOut = true;

					while (breakOut) {

						for (int j = 0; j < leafs.keys.size(); j++) {

							int compareKey = key.compareTo(leafs.keys.get(j));

							if (compareKey != 1) {
								rangeSearchValues.add(leafs.values.get(j));
							}

						}

						if (leafs.next != null) {
							leafs = leafs.next;

						}

						else {
							break;
						}

					}
				}
				
				if(comparator.equals("==")) {//if key matches only return this value
					
					for(int k = 0; k <leafs.keys.size(); k++) {
						int keyCompare = key.compareTo(leafs.keys.get(k));
						
						if(keyCompare == 0) {
							rangeSearchValues.add(leafs.values.get(k));
							return rangeSearchValues;
							
						}
						
						
					}
					return null;
				}

				return rangeSearchValues;
			}

		}
	


	// End of class LeafNode

	/**
	 * Contains a basic test scenario for a BPTree instance. It shows a simple
	 * example of the use of this class and its related types.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create empty BPTree with branching factor of 3
		BPTree<Double, Double> bpTree = new BPTree<>(3);

		// create a pseudo random number generator
		Random rnd1 = new Random();

		// some value to add to the BPTree
		Double[] dd = { 0.0d, 0.5d, 0.2d, 0.8d, 2d, 3d,4d, 5d,9d,10d,12d,14d,15d,20d,23d,25d,29d,30d,45d };

		// build an ArrayList of those value and add to BPTree also
		// allows for comparing the contents of the ArrayList
		// against the contents and functionality of the BPTree
		// does not ensure BPTree is implemented correctly
		// just that it functions as a data structure with
		// insert, rangeSearch, and toString() working.
		List<Double> list = new ArrayList<>();
		for (int i = 0; i < 400; i++) {
			Double j = dd[rnd1.nextInt(10)];
			list.add(j);
			bpTree.insert(j, j);
			System.out.println("\n\nTree structure:\n" + bpTree.toString());
		}
		List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
		System.out.println("Filtered values: " + filteredValues.toString());
	}

}
// End of class BPTree
