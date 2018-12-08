package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class BPTreeTest<K extends Comparable<K>, V> {

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

}
