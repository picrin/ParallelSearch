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

final class ExplorePredecessorsLocal extends GraphExplorator{

    protected int innerCounter;
    public static int spawnRate = 20;
    protected ExplorePredecessorsLocal(Node start, DataGraph dg){
        super(start, dg);

    }
    public ExplorePredecessorsLocal(ExecutorService executor, Node start, DataGraph dg){
        super(executor, start, dg);
        innerCounter = 0;
    }
    @Override
    public void run(){
        Node current = start;
        current.visit();
        stackedNodes.add(current.children);
        while(!stackedNodes.isEmpty()){
        	//System.out.println("inloop");
        	ArrayList<Node> nodes = stackedNodes.poll();
        	for(Node node: nodes){
            	if (node.visit()){
            		innerCounter += 1;
            		if (innerCounter % spawnRate == 19){
            			for(Node child: node.children){
            				counter.incrementAndGet();
            				//Future<?> future = 
	                    	executor.execute(new ExplorePredecessorsLocal(child, dg));
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
    }
}

