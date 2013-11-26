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
import java.util.concurrent.RecursiveAction;

public class InnerWorker extends RecursiveAction{
	private static final long serialVersionUID = -5283490868734796087L;
	Node startNode;
	Node currentNode;
	
	public InnerWorker(Node startNode, Node currentNode){
		this.startNode = startNode;
		this.currentNode = currentNode;
	}
	
	@Override
	protected void compute(){
		if (startNode == null) throw new RuntimeException("startNode null");
		if (currentNode == null) throw new RuntimeException("currentNode null");

		if (startNode.dependencies.containsKey(currentNode)){
			//System.out.println("finished at: " + currentNode.filename);
			return;
		}
		//System.out.println("putting to hashmap: " + currentNode.filename);
		startNode.dependencies.put(currentNode, 0);
		
		ArrayList<InnerWorker> workers = new ArrayList<InnerWorker>(currentNode.children.size());
		//System.out.println(currentNode.filename + "has got" + currentNode.children.get(0).filename);
		for (Node child: currentNode.children){
			//System.out.println("adding children: " + child.filename);			
			workers.add(new InnerWorker(startNode, child));
		}
		invokeAll(workers);
	}
	
}
