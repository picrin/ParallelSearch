import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;


public class HashTreeComparatorTest {
	Node n1 = new Node(1L);
	Node n2 = new Node(2L);
	Node n3 = new Node(3L);
	Node n4 = new Node(4L);
	Node n5 = new Node(5L);
	@Test
	public void test() {
		TreeMapComparator comp = new TreeMapComparator();
		TreeMap<Long, Node> hashTree1 = new TreeMap<Long, Node>();
		TreeMap<Long, Node> hashTree2 = new TreeMap<Long, Node>();

		hashTree1.put(4L, n1);
		hashTree1.put(2L, n2);
		//hashTree1.put(3L, n3);
		//hashTree2.put(1L, n4);
		hashTree2.put(2L, n4);
		hashTree2.put(7L, n5);

		//System.out.println("whas?" + ((Long) 1L).compareTo((Long) 4L));
		System.out.println(comp.compare(hashTree1, hashTree2));
		
	}

}
