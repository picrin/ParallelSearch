import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

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
	
	private static ExecutorService executor;
    protected int innerCounter;
    //TODO tweak this value to make it fit the real data. 20 is a safe bet, I'd like to see how good it is for 200 or 2000.
    public static int spawnRate = 200; 
    
    protected ExploreDescendants(Node start, DataGraph dg){
        super(start, dg);
    }
    
    //TODO this HAS TO disappear, because it's WRONG.
    public ExploreDescendants(ExecutorService executor, Node start, DataGraph dg){
        super(executor, start, dg);
        innerCounter = 0;
    }
    
    public ExploreDescendants(int levelOfParallelism, Node start, DataGraph dg) {
    	super(start, dg);
    	ExploreDescendants.executor = new ForkJoinPool(levelOfParallelism);
		// TODO Auto-generated constructor stub
	}
    protected void whenFinished(){
    	//uncomment this to carry out the measurement
    	//stopTime = System.currentTimeMillis();
        counter.set(1);
        
        //System.out.println("shutdown");
    	executor.shutdown();
        //mainThread.interrupt();
    }
    public void startExploration(){
    	System.out.println("exploring dg");
    	counter.set(1);
    	executor.submit(this);
    }
    
    public void awaitTermination(){
    	try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {e.printStackTrace();}
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
