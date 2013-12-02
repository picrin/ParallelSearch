import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;


public class VisitorTest {

	@Test
	public void test(){
		int size = 20_000_000;
		//ArrayList<Node> nodes = new ArrayList<Node>(size);
		NonBlockingHashMap<Long, Node> map = new NonBlockingHashMap<Long, Node>(size);
		for(int i = 0; i < size; i++){
			Node node = new Node(i);
			//nodes.add(node);
			map.put(node.id, node);
		}
		long start;
		long stop;
		/*
		start = System.currentTimeMillis();
		for(Node node: map.values()){
			node.visit();
		}
		stop = System.currentTimeMillis();
		
		for(Node node: map.values()){
			node.visited.set(false);
		}*/
		
		//System.out.println("single threaded" + (stop - start));
		
		start = System.currentTimeMillis();
		Visitor visitor = new Visitor(map, 10);
		visitor.visitAllInParallel();
		stop = System.currentTimeMillis();
		System.out.println("double threaded " + (stop - start));

		for(Node node: map.values()){
			Assert.assertTrue(node.visited.get());
		}
		
	}

}
