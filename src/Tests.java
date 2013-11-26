import static org.junit.Assert.*;

import java.util.concurrent.ForkJoinPool;

import junit.framework.Assert;

import org.junit.Test;


public class Tests {
	static boolean nodeContains(Node node, String filename){
		for(Node child: node.dependencies.keySet()){
			if (child.filename.equals(filename)) return true;
		}
		return false;
	}
	@Test
	public void test() {
		ForkJoinPool magic = new ForkJoinPool(2);
		DataGraph dg = GraphFactory.makeFunnyDependencies();
		Node startN2 = dg.nodes[1];
		Worker forkjoin = new Worker(startN2, startN2);
		magic.execute(forkjoin);
		forkjoin.join();
		
		
		Assert.assertTrue(nodeContains(startN2, "2"));
		Assert.assertTrue(nodeContains(startN2, "4"));
		Assert.assertTrue(nodeContains(startN2, "5"));
		Assert.assertTrue(nodeContains(startN2, "6"));
		Assert.assertTrue(nodeContains(startN2, "3"));
		Assert.assertFalse(nodeContains(startN2, "1"));
	}

}
