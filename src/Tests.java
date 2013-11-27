/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 by Adam Kurkiewicz
 * You can contact me by e-mail at: adam /at\ kurkiewicz /dot\ pl
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software. 
 */

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
	public void innerWorkerTest() {
		GraphFactory.threadsNumber = 1;

		ForkJoinPool magic = new ForkJoinPool(GraphFactory.threadsNumber);
		DataGraph dg = GraphFactory.makeFunnyDependencies();
		Node startN2 = dg.nodes.get(1);
		InnerWorker forkjoin = new InnerWorker(startN2, startN2);
		magic.execute(forkjoin);
		forkjoin.join();
		
		
		Assert.assertTrue(nodeContains(startN2, "2"));
		Assert.assertTrue(nodeContains(startN2, "4"));
		Assert.assertTrue(nodeContains(startN2, "5"));
		Assert.assertTrue(nodeContains(startN2, "6"));
		Assert.assertTrue(nodeContains(startN2, "3"));
		
		Assert.assertFalse(nodeContains(startN2, "1"));
	}

	@Test
	public void outerWorkerTest(){
		GraphFactory.threadsNumber = 1;
		ForkJoinPool magic = new ForkJoinPool(GraphFactory.threadsNumber);
		DataGraph dg = GraphFactory.makeFunnyDependencies();
		
		OuterWorker forkjoin = new OuterWorker(dg);
		magic.execute(forkjoin);
		forkjoin.join();
		
		//1
		Assert.assertTrue(nodeContains(dg.nodes.get(0), "1"));
		Assert.assertTrue(nodeContains(dg.nodes.get(0), "2"));
		Assert.assertTrue(nodeContains(dg.nodes.get(0), "3"));
		Assert.assertTrue(nodeContains(dg.nodes.get(0), "4"));
		Assert.assertTrue(nodeContains(dg.nodes.get(0), "5"));
		Assert.assertTrue(nodeContains(dg.nodes.get(0), "6"));
		
		//2
		Assert.assertTrue(!nodeContains(dg.nodes.get(1), "1"));
		Assert.assertTrue(nodeContains(dg.nodes.get(1), "2"));
		Assert.assertTrue(nodeContains(dg.nodes.get(1), "3"));
		Assert.assertTrue(nodeContains(dg.nodes.get(1), "4"));
		Assert.assertTrue(nodeContains(dg.nodes.get(1), "5"));
		Assert.assertTrue(nodeContains(dg.nodes.get(1), "6"));
		
		
		//3
		Assert.assertTrue(!nodeContains(dg.nodes.get(2), "1"));
		Assert.assertTrue(!nodeContains(dg.nodes.get(2), "2"));
		Assert.assertTrue(nodeContains(dg.nodes.get(2), "3"));
		Assert.assertTrue(!nodeContains(dg.nodes.get(2), "4"));
		Assert.assertTrue(nodeContains(dg.nodes.get(2), "5"));
		Assert.assertTrue(nodeContains(dg.nodes.get(2), "6"));
		
		//4
		Assert.assertTrue(!nodeContains(dg.nodes.get(3), "1"));
		Assert.assertTrue(nodeContains(dg.nodes.get(3), "2"));
		Assert.assertTrue(nodeContains(dg.nodes.get(3), "3"));
		Assert.assertTrue(nodeContains(dg.nodes.get(3), "4"));
		Assert.assertTrue(nodeContains(dg.nodes.get(3), "5"));
		Assert.assertTrue(nodeContains(dg.nodes.get(3), "6"));
		
		//5
		Assert.assertTrue(!nodeContains(dg.nodes.get(4), "1"));
		Assert.assertTrue(!nodeContains(dg.nodes.get(4), "2"));
		Assert.assertTrue(nodeContains(dg.nodes.get(4), "3"));
		Assert.assertTrue(!nodeContains(dg.nodes.get(4), "4"));
		Assert.assertTrue(nodeContains(dg.nodes.get(4), "5"));
		Assert.assertTrue(nodeContains(dg.nodes.get(4), "6"));
		
		//6
		Assert.assertTrue(!nodeContains(dg.nodes.get(5), "1"));
		Assert.assertTrue(!nodeContains(dg.nodes.get(5), "2"));
		Assert.assertTrue(nodeContains(dg.nodes.get(5), "3"));
		Assert.assertTrue(!nodeContains(dg.nodes.get(5), "4"));
		Assert.assertTrue(nodeContains(dg.nodes.get(5), "5"));
		Assert.assertTrue(nodeContains(dg.nodes.get(5), "6"));
		
		PerformanceRoR.printGraph(dg);
	}
	
}
