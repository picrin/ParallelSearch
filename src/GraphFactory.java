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

public class GraphFactory {
	
    private GraphFactory(){};

    static int threadsNumber = 1;
    static int nodesNo = 6;
    
    public static DataGraph makeFunnyDependencies(){
		DataGraph dg = new DataGraph();
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");
        Node n5 = new Node("5");
        Node n6 = new Node("6");
        dg.addNode(n1);
        dg.addNode(n2);
        dg.addNode(n3);
        dg.addNode(n4);
        dg.addNode(n5);
        dg.addNode(n6);
        n1.connectChild(n4);
        n2.connectChild(n4);
        n2.connectChild(n5);
        n3.connectChild(n5);
        n4.connectChild(n6);
        n4.connectChild(n2);
        n5.connectChild(n6);
        n6.connectChild(n3);
        return dg;
    }
    
    public static DataGraph makeSanityCheckGraph(){
        DataGraph dg = new DataGraph();
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");
        Node n5 = new Node("5");
        Node n6 = new Node("6");
        Node n7 = new Node("7");
        Node n8 = new Node("8");
        Node n9 = new Node("9");
        Node n10 = new Node("10");
        Node n11 = new Node("11");

        dg.addNode(n1);
        dg.addNode(n2);
        dg.addNode(n3);
        dg.addNode(n4);
        dg.addNode(n5);
        dg.addNode(n6);
        dg.addNode(n7);
        dg.addNode(n8);
        dg.addNode(n9);
        dg.addNode(n10);
        dg.addNode(n11);

        n1.connectChild(n2);
        n2.connectChild(n3);
        n2.connectChild(n7);

        n3.connectChild(n4);
        n3.connectChild(n6);
        n4.connectChild(n5);
        n5.connectChild(n6);
        n6.connectChild(n4);
        n7.connectChild(n1);
        n7.connectChild(n2);
        n7.connectChild(n9);
        n8.connectChild(n6);
        n8.connectChild(n10);
        n8.connectChild(n11);
        n9.connectChild(n8);     
        n10.connectChild(n5);
        n10.connectChild(n11);
        n11.connectChild(n9);
        return dg;
    }
}
