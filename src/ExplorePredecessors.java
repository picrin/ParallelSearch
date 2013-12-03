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

final class ExplorePredecessors extends GraphExplorator{

    protected int innerCounter = 0;
    //TODO tweak this value to make it fit the real data. 20 is a safe bet, I'd like to see how good it is for 200 or 2000.
    public static int spawnRate = 200; 
    
    protected ExplorePredecessors(Node start, DataGraph dg){
        super(start, dg);
    }
    
    public ExplorePredecessors(ExecutorService executor, Node start, DataGraph dg){
        super(executor, start, dg);
    }

    public ExplorePredecessors(int levelOfParallelism, Node start, DataGraph dg){
        super(levelOfParallelism, start, dg);
    }

    
    @Override
    public void run(){
        Node current = start;
        current.mark_predecessor(dg);
        stackedNodes.add(current.parents);
        while(!stackedNodes.isEmpty()){
        	ArrayList<Node> nodes = stackedNodes.poll();
        	for(Node node: nodes){
            	if (node.mark_predecessor(dg)){
            		innerCounter += 1;
            		if (innerCounter % spawnRate == spawnRate - 1){
            			for(Node parent: node.parents){
            				counter.incrementAndGet();
	                    	executor.execute(new ExplorePredecessors(parent, dg));
            			}
	            	} else {
	            		stackedNodes.offer(node.parents);
            		}
                }
            }
        }
        if (counter.decrementAndGet() == 0) this.whenFinished();
    }
}

