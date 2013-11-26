

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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ConcurrentHashMap;

public class Node {
	public static ForkJoinPool threadPool;
	
	String filename;
	
	ConcurrentHashMap<Node, Void> dependencies;
	Worker worker;

	//this doesn't need to be concurrent.
	ArrayList<Node> children;
	
    public Node(String filename){
        children = new ArrayList<Node>();
        this.filename = filename;
    }
    
    public void connectChild(Node child){
        children.add(child);
    }
}
