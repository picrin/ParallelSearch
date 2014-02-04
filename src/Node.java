

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
import java.util.Random;

class Node implements Comparable<Node>{
	final long id;
	final static int childrenNo = 5;
	boolean predecessor;
	boolean descendant;
	DataGraph graph;
	ArrayList<Node> children = new ArrayList<Node>(childrenNo);
	ArrayList<Node> parents = new ArrayList<Node>(childrenNo);
	
    public Node(long id){
        this.id = id;
        predecessor = false;
    	descendant = false;
    	graph = null;

    }
    
    /**
     * This isn't thread safe, use it only once, when you prepare the graph.
     */
    public void connectChild(Node child){
        child.parents.add(this);
        this.children.add(child);
    }
    
    public synchronized void reset(DataGraph graph){
        this.graph = graph;
        predecessor = false;
        descendant = false;
        notifyAll();
        return;
    }
    
    //TODO to achieve 
    public synchronized boolean mark_predecessor(DataGraph graph){
        if(predecessor == false && graph == this.graph){
            predecessor = true;
            if(descendant == true){
                graph.scc.put(id, this);
                graph.descendants.remove(this.id);
            } else{
                graph.predecessors.put(id, this);
                graph.remainder.remove(this.id);
            }
            notifyAll();
            return true;
        } else{
            notifyAll();
            return false;
        }

    }
    
    public synchronized boolean mark_descendant(DataGraph graph){
        if(descendant == false && graph == this.graph){
            descendant = true;
            if(predecessor == true){
                graph.scc.put(id, this);
                graph.predecessors.remove(this.id);
            } else{
                graph.descendants.put(id, this);            
                graph.remainder.remove(this.id);
            }
            notifyAll();
            return true;
        } else{
            notifyAll();
            return false;
        }
    }
    public String toString(){
    	return "node" + id;
    }
    @Override
    public int compareTo(Node node) {
        return Long.compare(this.id, node.id);
    }
}
