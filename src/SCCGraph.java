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

public interface SCCGraph<NodeType extends DeNode<?, ?>>
                          extends Comparable<SCCGraph<? extends DeNode<?, ?>>>{
	
	public long start();
	public NonBlockingHashMap<Long, NodeType> getNodes();
	public void addNode(NodeType node);
	ConcurrentLinkedQueue<NonBlockingHashMap<Long, NodeType>> getSolutions();
	public void reportSolution(NonBlockingHashMap<Long, NodeType> solution);
	
}
