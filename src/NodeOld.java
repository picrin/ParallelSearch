

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
import java.util.concurrent.atomic.AtomicBoolean;

class NodeOld{
	final long id;
	AtomicBoolean visited;
	ArrayList<Node> children;
	
    public NodeOld(long id, int estimatedChildren){
        this.id = id;
        visited = new AtomicBoolean(false);
        children = new ArrayList<Node>(estimatedChildren);
    }
    
    public NodeOld(long id){
        this.id = id;
        visited = new AtomicBoolean(false);
        children = new ArrayList<Node>();
    }
    
    public void connectChild(Node child){
        children.add(child);
    }
    
    public boolean visit(){
    	return !visited.getAndSet(true);
    }

    
}
