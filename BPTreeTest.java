package application;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

public class BPTreeTest<K extends Comparable<K>, V> {

	/**
	 * Tests the construction
	 */
	@Test
	public void test01() {
		try {
			BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
			BPTree<Double, Double> tree1 = new BPTree<Double, Double>(3);
			BPTree<Float, Float> tree2 = new BPTree<Float, Float>(4);
			BPTree<String, String> tree3 = new BPTree<String, String>(4);
			BPTree<Integer, Integer> tree4 = new BPTree<Integer, Integer>(8);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Tests the ability to insert values. 
	 */
	@Test
	public void test02() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
		try {
			for (int i = 0; i < 20000; i++) {
				tree.insert(i, i);
			}
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	/**
	 *	Tests the range search functionality with a large amount of values.  
	 */
	@Test public void test03() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
		try {
			for (int i = 0; i < 50000; i++) {
				tree.insert(i, i);
			}
			tree.rangeSearch(3, ">=");
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests the the toString method will function properly. 
	 */
	@Test public void test04() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
		try {
			for (int i = 0; i < 50000; i++) {
				tree.insert(i, i);
			}
			tree.toString();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	/**
	 * Tests to see if two trees equal each other. 
	 */
	@Test
	public void test05() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
		BPTree<Integer, Integer> tree2 = new BPTree<Integer, Integer>(3);
			try {
				tree.insert(1, 1);
				tree.insert(2, 1);
				tree.insert(3, 1);
				tree.insert(4, 1);
				tree.insert(5, 1);
				tree2.insert(1, 1);
				tree2.insert(2, 1);
				tree2.insert(3, 1);
				tree2.insert(4, 1);
				tree2.insert(5, 1);
				if(!(tree.equals(tree2))) {
					fail();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}		
	}
	
	/**
	 * More range search tests. 
	 */
	@Test public void test06() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
		try {
			for (int i = 0; i < 50; i++) {
				tree.insert(i, i);
			}
			
			tree.rangeSearch(3, ">=");
			tree.rangeSearch(5, "<=");
			tree.rangeSearch(9, "==");
			tree.rangeSearch(-1, ">=");
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	/**
	 * More Range search tests
	 */
	@Test public void test07() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(3);
		try {
			for (int i = 0; i < 50; i++) {
				tree.insert(i, i);
			}
			
			List<Integer> list = tree.rangeSearch(5, "greaterTO");
			
			assertTrue(list.isEmpty());
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	

}
