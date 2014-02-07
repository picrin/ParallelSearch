

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

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
//import java.io.Serializable;

public class DataGraph extends AbstractGraph<Node>{
    //private static final long serialVersionUID;
    static ExecutorService threadPool;
    static ConcurrentLinkedQueue<NonBlockingHashMap<Long, Node>> solutions;
    
    static {
    	//serialVersionUID = 31337_4045L;
    	solutions = new ConcurrentLinkedQueue<NonBlockingHashMap<Long, Node>>();
    	try{
    		threadPool = new ForkJoinPool(GraphFactory.locales.threads);
    	} catch (NullPointerException e){
            System.err.println("remember to set locales by invoking DataGraph.setLocales()");
        }
    }
    
    NonBlockingHashMap<Long, Node> remainder;
    NonBlockingHashMap<Long, Node> scc;
    NonBlockingHashMap<Long, Node> predecessors;
    NonBlockingHashMap<Long, Node> descendants;
    
    public DataGraph(){
        try{
            remainder = new NonBlockingHashMap<Long, Node>(GraphFactory.locales.hashMapCap);
            scc = new NonBlockingHashMap<Long, Node>(GraphFactory.locales.hashMapCap);
            predecessors = new NonBlockingHashMap<Long, Node>(GraphFactory.locales.hashMapCap);
            descendants = new NonBlockingHashMap<Long, Node>(GraphFactory.locales.hashMapCap);
        } catch (NullPointerException e){
            System.err.println("remember to set locales by invoking DataGraph.setLocales()");
        }
    }

    public DataGraph(NonBlockingHashMap<Long, Node> remainder){
        try{
            this.remainder = remainder;
            scc = new NonBlockingHashMap<Long, Node>(GraphFactory.sizeToCap(remainder.size()));
            predecessors = new NonBlockingHashMap<Long, Node>(GraphFactory.sizeToCap(remainder.size()));
            descendants = new NonBlockingHashMap<Long, Node>(GraphFactory.sizeToCap(remainder.size()));
        } catch (NullPointerException e){
            System.err.println("remember to set locales by invoking DataGraph.setLocales()");
        }
    }
    
    public DataGraph(Iterable<Node> nodes){
        try {
            remainder = new NonBlockingHashMap<Long, Node>(GraphFactory.locales.hashMapCap);
            for (Node node : nodes){
                remainder.put(node.id, node);
            }
        } catch (NullPointerException e){
            System.err.println("remember to set locales by invoking DataGraph.setLocales()");
        }
    }
    
    /*public DataGraph(Node[] nodes){
        remainder = new NodeHashMap(Globals.threads, Globals.hashMapCap, Globals.loadFactor);
        for (Node node : nodes){
            remainder.add(node);
        }
    }*/
	@Override    
    public void addNode(Node node){
        node.setGraph(this);
        this.remainder.put(node.id, node);
    }

	@Override
	public ConcurrentLinkedQueue<NonBlockingHashMap<Long, Node>> getSolutions() {
		return solutions;
	}

	@Override
	public void reportSolution(NonBlockingHashMap<Long, Node> solution) {
		solutions.add(solution);
	}

}
