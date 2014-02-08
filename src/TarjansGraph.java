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
import java.util.concurrent.ConcurrentLinkedQueue;


public class TarjansGraph extends AbstractGraph<TarjansNode>{
	
	ArrayList<TarjansNode> nodes = new ArrayList<>();
	ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>> solutions = new ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>>();
	
	// CONSTRUCTORS:
	public TarjansGraph(){}; 
	
	public TarjansGraph(ArrayList<TarjansNode> nodes){
		this.nodes = nodes;
	}
	
	// GETTER and SETTER methods:
	public ArrayList<TarjansNode> getNodes(){
		return this.nodes;
	}
	
	@Override
	public void addNode(TarjansNode node) {
		node.setGraph(this);
		this.nodes.add(node);
	}

	@Override
	public ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>> getSolutions() {
		return this.solutions;
	}

	@Override
	public void reportSolution(NonBlockingHashMap<Long, TarjansNode> solution) {}
}

