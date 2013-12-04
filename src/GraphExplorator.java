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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


class GraphExplorator{
    AtomicInteger counter = new AtomicInteger(1);
    ExecutorService executor;
    DataGraph dg;
            
    public void startExploration(Runnable explorator){
    	executor.submit(explorator);
    }    
    
    public void awaitTermination(){
    	try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {e.printStackTrace();}
    }
        
    public GraphExplorator(int levelOfParallelism, DataGraph dg){
    	counter.set(1);
    	//System.out.println("new forkjoin pool");
        executor = new ForkJoinPool(levelOfParallelism);
        this.dg = dg;
    }
    
    protected void whenFinished(){
    	//System.out.println(this + " finished");
    	executor.shutdown();
    }

}
