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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;


abstract class GraphExplorator implements Runnable{
	protected LinkedList<ArrayList<Node>> stackedNodes;
	static public Thread mainThread;
    static protected AtomicInteger counter = new AtomicInteger(1);
    static protected ExecutorService executor;
    static public long startTime;
    static public long stopTime;
    final protected Node start;
    final protected DataGraph dg;

    protected GraphExplorator(Node start, DataGraph dg){
        this.start = start;
        this.dg = dg;
        stackedNodes = new LinkedList<ArrayList<Node>>();
    }
    
    public void startWithTimer(){
    	System.gc();
    	startTime = System.currentTimeMillis();
    	startExploration();
    }
    
    public void startExploration(){
    	counter.set(1);
    	executor.submit(this);
    }
    
    public GraphExplorator(ExecutorService executor, Node start, DataGraph dg){
        this(start, dg);
        GraphExplorator.executor = executor;
    }
    
    protected void whenFinished(){
    	//uncomment this to carry out the measurement
    	stopTime = System.currentTimeMillis();
        counter.set(1);
        
        //System.out.println("shutdown");
    	executor.shutdown();
        //mainThread.interrupt();
    }
    @Override
    abstract public void run();
    
}
