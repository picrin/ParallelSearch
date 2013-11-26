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


public class OuterWorker extends RecursiveAction{
	
	private static final long serialVersionUID = 1697151285077771441L;
	Node[] nodes;
	public OuterWorker(DataGraph dg){
		this.nodes = dg.nodes;
	}
	
	@Override
	protected void compute() {
		ArrayList<InnerWorker> innerWorkers = new ArrayList<InnerWorker>(nodes.length);
		for (Node node: nodes){
			innerWorkers.add(new InnerWorker(node, node));
		}
		invokeAll(innerWorkers);
	}
	
	

}
