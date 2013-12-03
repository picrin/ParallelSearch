import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

final class ExploreDescendants extends GraphExplorator{

    protected int innerCounter;
    //TODO tweak this value to make it fit the real data. 20 is a safe bet, I'd like to see how good it is for 200 or 2000.
    public static int spawnRate = 200; 
    
    protected ExploreDescendants(Node start, DataGraph dg){
        super(start, dg);
    }
    
    public ExploreDescendants(ExecutorService executor, Node start, DataGraph dg){
        super(executor, start, dg);
        innerCounter = 0;
    }
    
    public ExploreDescendants(int levelOfParallelism, Node start, DataGraph dg) {
    	super(levelOfParallelism, start, dg);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void run(){
    	try{
	        Node current = start;
	        current.mark_descendant(dg);
	        stackedNodes.add(current.children);
	        while(!stackedNodes.isEmpty()){
	        	//System.out.println("inloop");
	        	ArrayList<Node> nodes = stackedNodes.poll();
	        	for(Node node: nodes){
	        		boolean markedSuccess = node.mark_descendant(dg);
	        		//System.out.println(markedSuccess);
	        		if (markedSuccess){
	            		innerCounter += 1;
	            		if (innerCounter % spawnRate == spawnRate - 1){
	            			for(Node child: node.children){
	            				counter.incrementAndGet();
	            				//Future<?> future = 
		                    	executor.execute(new ExploreDescendants(child, dg));
		                    	/*try{
		                    		future.get();
		                    	} catch (Exception e){
		                    		e.printStackTrace();
		                    	}*/
	            			}
		            	} else {
		            		stackedNodes.offer(node.children);
	            		}
	                }
	              	//System.out.println(stackedNodes.isEmpty());
	            }
	        }
	        //System.out.println(counter);
	        if (counter.decrementAndGet() == 0) this.whenFinished();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
