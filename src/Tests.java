
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
import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class Tests {

    @Test
    public void testSanityNode6() throws InterruptedException {
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        ExecutorService executor = Executors.newFixedThreadPool(GraphFactory.threadsNo);
        
        ExplorePredecessors graphExplorator = new ExplorePredecessors(executor, sanity.nodes[5], sanity);
        graphExplorator.startWithTimer();
        while (graphExplorator.finished == false){
        	Thread.sleep(20);
        }
        System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");
        
        Assert.assertTrue(sanity.nodes[3].visited.get());
        Assert.assertTrue(sanity.nodes[4].visited.get());
        Assert.assertTrue(sanity.nodes[5].visited.get());

        Assert.assertFalse(sanity.nodes[0].visited.get());
        Assert.assertFalse(sanity.nodes[1].visited.get());
        Assert.assertFalse(sanity.nodes[2].visited.get());
        Assert.assertFalse(sanity.nodes[6].visited.get());
        Assert.assertFalse(sanity.nodes[7].visited.get());
        Assert.assertFalse(sanity.nodes[8].visited.get());
        Assert.assertFalse(sanity.nodes[9].visited.get());
        Assert.assertFalse(sanity.nodes[10].visited.get());
    }

    @Test
    public void testSanityNode8() throws InterruptedException {
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        ExecutorService executor = Executors.newFixedThreadPool(GraphFactory.threadsNo);
        
        ExplorePredecessors graphExplorator = new ExplorePredecessors(executor, sanity.nodes[7], sanity);
        graphExplorator.startWithTimer();
        while (graphExplorator.finished == false){
        	Thread.sleep(20);
        }
        GraphExplorator.counter.set(1);
        System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");

        Assert.assertTrue(sanity.nodes[3].visited.get());
        Assert.assertTrue(sanity.nodes[4].visited.get());
        Assert.assertTrue(sanity.nodes[5].visited.get());
        Assert.assertTrue(sanity.nodes[7].visited.get());
        Assert.assertTrue(sanity.nodes[8].visited.get());
        Assert.assertTrue(sanity.nodes[9].visited.get());
        Assert.assertTrue(sanity.nodes[10].visited.get());   

        Assert.assertFalse(sanity.nodes[0].visited.get());
        Assert.assertFalse(sanity.nodes[1].visited.get());
        Assert.assertFalse(sanity.nodes[2].visited.get());
        Assert.assertFalse(sanity.nodes[6].visited.get());

    }

}
