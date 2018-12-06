package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu), Eric Maccoux, Tanner Blanke, Jack Pientka, Tony Tu
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
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
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        
        this.root = new LeafNode();
        this.branchingFactor = branchingFactor;
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        root.insert(key, value);
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && !comparator.contentEquals("==") && !comparator.contentEquals("<=")) {
        	return new ArrayList<V>();
        } else if (key == null) {
        	return new ArrayList<V>();
        } else {
        	return root.rangeSearch(key, comparator);
        }
    }
    
    
    /*
     * (non-Javadoc)
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
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
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
        	keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
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
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
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
            children = new ArrayList<Node>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	try {
        		return children.get(0).getFirstLeafKey();
        	} catch (IndexOutOfBoundsException e) {
        		return null;
        	}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(keys.size() >= branchingFactor) {
            	return true;
            }
            return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
        	int location = Collections.binarySearch(keys, key);
        	Node childNode;
        	
        	if(location >= 0) {
        		childNode = children.get(location);
        	} else {
        		childNode = children.get(-(location) - 1);
        	}
    		childNode.insert(key, value);
        	
    		if(childNode.isOverflow()) {
    			Node split = childNode.split();
    			
    			if(location >= 0) {
    				keys.add(location, split.getFirstLeafKey());
    				children.add(location + 1, split);
    			} else {
    				keys.add(-(location) - 1, split.getFirstLeafKey());
    				children.add(-(location), split);
    			}
    		}
    		
    		if(root.isOverflow()) {
				Node split = split();
				InternalNode newParent = new InternalNode();
				newParent.keys.add(split.getFirstLeafKey());
				newParent.children.add(this);
				newParent.children.add(split);
				root = newParent;
			}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            InternalNode split = new InternalNode();
            
            for(int i = (this.keys.size() / 2) + 1; i < this.keys.size(); i++) {
            	split.keys.add(this.keys.get(i));
            	split.children.add(this.children.get(i));
            }
            split.children.add(this.children.get(this.keys.size()));
            
            this.keys.subList(this.keys.size() / 2, this.keys.size()).clear();
            this.children.subList((this.children.size() / 2), this.children.size()).clear();
            
            return split;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	int location;
        	
        	if(comparator.contentEquals("<=")) {
        		location = 0;
        	} else {
        		location = Collections.binarySearch(keys, key);
        	}
        	
        	Node childNode;
        	
        	if(location >= 0) {
        		childNode = children.get(location);
        	} else {
        		childNode = children.get(-(location) - 1);
        	}
        	
            return childNode.rangeSearch(key, comparator);
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            values = new ArrayList<V>();
            next = null;
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	try {
        		return keys.get(0);
        	} catch (IndexOutOfBoundsException e) {
        		return null;
        	}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	if(keys.size() >= branchingFactor) {
            	return true;
            }
            return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	int location = Collections.binarySearch(keys, key);
			if(location >= 0) {
				keys.add(location, key);
				values.add(location, value);
			} else {
				keys.add(-(location) - 1, key);
				values.add(-(location) - 1, value);
			}
			
			if(root.isOverflow()) {
				Node split = split();
				InternalNode newParent = new InternalNode();
				newParent.keys.add(split.getFirstLeafKey());
				newParent.children.add(this);
				newParent.children.add(split);
				root = newParent;
			}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode split = new LeafNode();
            
            for(int i = (this.keys.size()) / 2; i < this.keys.size(); i++) {
            	split.keys.add(this.keys.get(i));
            	split.values.add(this.values.get(i));
            }
            
            this.keys.subList((this.keys.size()) / 2, this.keys.size()).clear();
            this.values.subList((this.values.size()) / 2, this.values.size()).clear();
            
            split.next = next;
            this.next = split;
            
            return split;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	return rangeSearchHelper(this, key, comparator);
        }
        
        /**
         * 
         * @param node
         * @param key
         * @param comparator
         * @return
         */
        List<V> rangeSearchHelper(LeafNode node, K key, String comparator) {
        	List<V> listToReturn = new ArrayList<V>();
        	
        	if(comparator.contentEquals("==")) {
            	for(int i = 0; i < node.keys.size(); i++) {
            		if(node.keys.get(i).equals(key)) {
                		listToReturn.add(node.values.get(i));
                	}
            	}
            	
            	if(node.next != null && key.equals(node.next.getFirstLeafKey())) {
        			listToReturn.addAll(rangeSearchHelper(node.next, key, comparator));
        		}
            } else if (comparator.contentEquals(">=")) {
            	for(int i = 0; i < node.keys.size(); i++) {
            		if(key.compareTo(node.keys.get(i)) <= 0) {
                		listToReturn.add(node.values.get(i));
                	}
            	}
            	
            	if(node.next != null) {
        			listToReturn.addAll(rangeSearchHelper(node.next, key, comparator));
        		}
            } else if (comparator.contentEquals("<=")) {
            	for(int i = 0; i < node.keys.size(); i++) {
            		if(key.compareTo(node.keys.get(i)) >= 0) {
                		listToReturn.add(0, node.values.get(i));
                	}
            	}
            	
            	if(node.next != null && key.compareTo(node.next.getFirstLeafKey()) >= 0) {
            		listToReturn.addAll(rangeSearchHelper(node.next, key, comparator));
            	}
            }
        	
        	return listToReturn;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);
        
        /*bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(10.0, 10.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(7.0, 7.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(6.0, 6.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(8.0, 8.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        bpTree.insert(5.0, 5.0);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());

        for(double i = 1.0; i < 10.0; i++) {
        	bpTree.insert(i, i);
        	System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }*/
        
        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        //List<Double> filteredValues = bpTree.rangeSearch(0.2d, "==");
        //List<Double> filteredValues = bpTree.rangeSearch(0.2d, "<=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
