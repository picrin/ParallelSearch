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

/**
 * DeNode stands for double ended Node -- a Node which stores access to 
 * its parents and children
 * FamilyNode is to be interpreted as the type of children and parents.
 * GraphType is a type, which stores references to all graph nodes and keeps solutions. 
 */

public interface DeNode<FamilyNode extends DeNode<?, ?>,
                        GraphType extends SCCGraph<? extends DeNode<?, ?>>>
                        extends Comparable<DeNode<?, ?>>{

	public Long getID();
	
	public GraphType getGraph();
	public void setGraph(GraphType graph);

	public ArrayList<FamilyNode> getChildren();
	public ArrayList<FamilyNode> getParents();
	
	public void connectChild(FamilyNode node);
	
	/*
	 * these two methods are crucial from the point of view of
	 * the SCDC algorithm. They mark a node as belonging to the
	 * predecessors of another, currently explored node.
	 *
	 * public boolean markPredecessor(DataGraph graph);
	 * public boolean markDescendant(DataGraph graph);
	 */
}
