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

final class ExplorePredecessors implements Runnable{
	LinkedList<ArrayList<Node>> stackedNodes;
	GraphExplorator explorator;
	
	protected ArrayList<Node> startParents;
    
	protected int innerCounter;
    
	//TODO tweak this value to make it fit the real data. 20 is a safe bet, I'd like to see how good it is for 200 or 2000.
    public static int spawnRate = 2000;//00; 
    
    protected ExplorePredecessors(ArrayList<Node> startParents, GraphExplorator explorator){
    	//System.out.println("being called");
    	this.explorator = explorator;
    	stackedNodes = new LinkedList<ArrayList<Node>>();
    	this.startParents = startParents;
    }
    
    public ExplorePredecessors(int levelOfParallelism, Node start, DataGraph dg) {
    	this(start.parents, new GraphExplorator(levelOfParallelism, dg));
    	start.markPredecessor(dg);
	}

    public void startExploration(){
    	explorator.startExploration(this);
    }
    
    public void awaitExploration(){
    	explorator.awaitTermination();
    }
    
    public DataGraph getDG(){
    	return explorator.dg;
    }
    
	@Override
    public void run(){
    	try{
	        stackedNodes.add(startParents);
	        while(!stackedNodes.isEmpty()){
	        	ArrayList<Node> nodes = stackedNodes.poll();
	        	for(Node node: nodes){
	        		boolean markedSuccess = node.markPredecessor(explorator.dg);
	        		if (markedSuccess){
	            		innerCounter += 1;
	            		if (innerCounter % spawnRate == spawnRate - 1){
	            			explorator.counter.incrementAndGet();
	            			//System.out.println(explorator.executor.isShutdown());
	            			explorator.executor.execute(new ExplorePredecessors(node.parents, explorator));
		            	} else {
		            		stackedNodes.offer(node.parents);
	            		}
	                }
	            }
	        }
	        if (explorator.counter.decrementAndGet() == 0) explorator.whenFinished();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
