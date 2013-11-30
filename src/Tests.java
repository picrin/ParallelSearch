
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
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class Tests {
	/*@Test
	public void testMagic(){
		GraphFactory.nodesNo = 30;
        GraphFactory.threadsNoEnd = 10;
        GraphFactory.maxChildren = 10;
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);

	    
	    Node current = sanity.nodes.get(5);
        current.visit();
        int innerCounter = 0;
        LinkedList<ArrayList<Node>> stackedNodes = new LinkedList<ArrayList<Node>>();
        stackedNodes.add(current.children);
        while(!stackedNodes.isEmpty()){
        	//System.out.println("inloop");
        	ArrayList<Node> nodes = stackedNodes.poll();
        	for(Node node: nodes){
        		if (node.visit()){
        			innerCounter += 1;
        			if (innerCounter % 20 == 21){
        				for(Node child: node.children){
        					//counter.incrementAndGet();
                    		executor.execute(new ExplorePredecessorsLocal(child, sanity));
        				}
            		} else {
        				
            			stackedNodes.add(node.children);
        			}
                }
            	//System.out.println(stackedNodes.isEmpty());

            }
        }
        System.out.println("Recurssion on runnables finished");

        Assert.assertTrue(sanity.nodes.get(3).visited.get());
        Assert.assertTrue(sanity.nodes.get(4).visited.get());
        Assert.assertTrue(sanity.nodes.get(5).visited.get());

        Assert.assertFalse(sanity.nodes.get(0).visited.get());
        Assert.assertFalse(sanity.nodes.get(1).visited.get());
        Assert.assertFalse(sanity.nodes.get(2).visited.get());
        Assert.assertFalse(sanity.nodes.get(6).visited.get());
        Assert.assertFalse(sanity.nodes.get(7).visited.get());
        Assert.assertFalse(sanity.nodes.get(8).visited.get());
        Assert.assertFalse(sanity.nodes.get(9).visited.get());
        Assert.assertFalse(sanity.nodes.get(10).visited.get());        
	}*/
	
    @Test
    public void testSanityNodeLocale6() throws InterruptedException {
        GraphFactory.nodesNo = 30;
        GraphFactory.threadsNoEnd = 10;
        GraphFactory.maxChildren = 10;
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        
        ExplorePredecessorsLocal graphExplorator = new ExplorePredecessorsLocal(executor, sanity.nodes.get(5), sanity);
	    GraphExplorator.mainThread = Thread.currentThread();
        try{
        	graphExplorator.startWithTimer();
        	//throw new InterruptedException();
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {e.printStackTrace();}
        //System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");
        
        Assert.assertTrue(sanity.nodes.get(3).visited.get());
        Assert.assertTrue(sanity.nodes.get(4).visited.get());
        Assert.assertTrue(sanity.nodes.get(5).visited.get());

        Assert.assertFalse(sanity.nodes.get(0).visited.get());
        Assert.assertFalse(sanity.nodes.get(1).visited.get());
        Assert.assertFalse(sanity.nodes.get(2).visited.get());
        Assert.assertFalse(sanity.nodes.get(6).visited.get());
        Assert.assertFalse(sanity.nodes.get(7).visited.get());
        Assert.assertFalse(sanity.nodes.get(8).visited.get());
        Assert.assertFalse(sanity.nodes.get(9).visited.get());
        Assert.assertFalse(sanity.nodes.get(10).visited.get());
        System.out.println("================ finished test ================");
    }
    
    @Test
    public void testSanityNodeLocal8() throws InterruptedException {
        
        GraphFactory.nodesNo = 30;
        GraphFactory.threadsNoEnd = 10;
        GraphFactory.maxChildren = 10;
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();
        ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        
        ExplorePredecessorsLocal graphExplorator = new ExplorePredecessorsLocal(executor, sanity.nodes.get(7), sanity);
	    GraphExplorator.mainThread = Thread.currentThread();

        try{
        	graphExplorator.startWithTimer();
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {}

        Assert.assertTrue(sanity.nodes.get(3).visited.get());
        Assert.assertTrue(sanity.nodes.get(4).visited.get());
        Assert.assertTrue(sanity.nodes.get(5).visited.get());
        Assert.assertTrue(sanity.nodes.get(7).visited.get());
        Assert.assertTrue(sanity.nodes.get(8).visited.get());
        Assert.assertTrue(sanity.nodes.get(9).visited.get());
        Assert.assertTrue(sanity.nodes.get(10).visited.get());   

        Assert.assertFalse(sanity.nodes.get(0).visited.get());
        Assert.assertFalse(sanity.nodes.get(1).visited.get());
        Assert.assertFalse(sanity.nodes.get(2).visited.get());
        Assert.assertFalse(sanity.nodes.get(6).visited.get());
        //System.out.println("================ finished test ================");
    }
    
    @Test
    public void testCorrectness(){
        GraphFactory.nodesNo = 150;
        GraphFactory.threadsNoEnd = 10;
        GraphFactory.maxChildren = 2;
		
        LinkedList<Node> stack = new LinkedList<Node>();
        DataGraph random = GraphFactory.makeRandomSparseGraph();
		Node start = random.nodes.get(0);
		
		//DFS
		stack.push(start);
		while (!stack.isEmpty()){
			Node current = stack.pop();
			current.visit();
			for(Node child: current.children){
				if (child.visit()){
					stack.push(child);
				}
			}
		}

		ArrayList<Integer> DFSVisited = new ArrayList<Integer>();
		ArrayList<Integer> ParallelSearchVisited = new ArrayList<Integer>();

		for(int i = 0; i < random.nodes.size(); i++){
			if(random.nodes.get(i).visited.get()){
        		DFSVisited.add(i);
        	}
        }
		

		ExecutorService executor = new ForkJoinPool(2);
        
        ExplorePredecessorsLocal graphExplorator = new ExplorePredecessorsLocal(executor, start, random);
	    GraphExplorator.mainThread = Thread.currentThread();

		for(int i = 0; i < random.nodes.size(); i++){
			if(random.nodes.get(i).visited.get()){
        		ParallelSearchVisited.add(i);
        	}
        }

	    
        try{
        	graphExplorator.startWithTimer();
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {}
        
        
		for(Node node: random.nodes){
			node.visited.set(false);
		}
		
		
		for(int i = 0; i < random.nodes.size(); i++){
			if(random.nodes.get(i).visited.get()){
        		DFSVisited.add(i);
        	}
        }
		
		assertTrue(ParallelSearchVisited.size() == DFSVisited.size());
		for(int i = 0; i < ParallelSearchVisited.size(); i++){
			//System.out.println(ParallelSearchVisited.get(i) + " " + DFSVisited.get(i));
			assertTrue(ParallelSearchVisited.get(i).equals(DFSVisited.get(i)));
		}
		
		
    }

    @Test
    public void testBigRandom() {
        GraphFactory.nodesNo = 3000;
        GraphFactory.threadsNoEnd = 2;
        GraphFactory.maxChildren = 50;
        DataGraph random = GraphFactory.makeRandomSparseGraph();

        ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        
        ExplorePredecessorsLocal graphExplorator = new ExplorePredecessorsLocal(executor, random.nodes.get(5), random);
	    GraphExplorator.mainThread = Thread.currentThread();
        try{
        	graphExplorator.startWithTimer();
        	//throw new InterruptedException();
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {e.printStackTrace();}
        //System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");
        
        Assert.assertTrue(random.nodes.get(2100).visited.get());
        Assert.assertTrue(random.nodes.get(1700).visited.get());
        Assert.assertTrue(random.nodes.get(890).visited.get());
        //System.out.println("================ finished test ================");
    }
    
}
