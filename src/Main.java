import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;


public class Main {
	public static void main(String[] args) throws InterruptedException{
		GraphFactory.nodesNo = 30;
        GraphFactory.threadsNoEnd = 10;
        GraphFactory.maxChildren = 10;
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        
        ExplorePredecessorsLocal graphExplorator = new ExplorePredecessorsLocal(executor, sanity.nodes.get(5), sanity);
	    GraphExplorator.mainThread = Thread.currentThread();
        try{
        	graphExplorator.startWithTimer();
        	//throw new InterruptedException();
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {e.printStackTrace();}
	    
	    while(!executor.isShutdown()){
	    	Thread.sleep(20);
	    }
        System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");
        
        Assert.assertTrue(sanity.nodes.get(3).visited.get());
        Assert.assertTrue(sanity.nodes.get(4).visited.get());
        Assert.assertTrue(sanity.nodes.get(5).visited.get());

        Assert.assertFalse(sanity.nodes.get(0).visited.get());
        Assert.assertFalse(sanity.nodes.get(1).visited.get());
        Assert.assertFalse(sanity.nodes.get(2).visited.get());
        Assert.assertFalse(sanity.nodes.get(6).visited.get());
        Assert.assertFalse(sanity.nodes.get(7).visited.get());
        Assert.assertFalse(sanity.nodes.get(8).visited.get());
        Assert.assertFalse(sanity.nodes.get(9).visited.get());
        Assert.assertFalse(sanity.nodes.get(10).visited.get());
		/*GraphFactory.nodesNo = 30;
        GraphFactory.threadsNoEnd = 10;
        GraphFactory.maxChildren = 10;
        DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        

	    Node current = sanity.nodes.get(5);
        current.visit();
        int innerCounter = 0;
        LinkedList<ArrayList<Node>> stackedNodes = new LinkedList<ArrayList<Node>>();
        stackedNodes.add(current.children);
        while(!stackedNodes.isEmpty()){
        	System.out.println("inloop");
        	ArrayList<Node> nodes = stackedNodes.poll();
        	for(Node node: nodes){
        		if (node.visit()){
        			innerCounter += 1;
        			if (innerCounter % 20 == 21){
        				for(Node child: node.children){
        					//counter.incrementAndGet();
                    		executor.execute(new ExplorePredecessorsLocal(child, sanity));
        				}
            		} else {
        				
            			stackedNodes.add(node.children);
        			}
                }
            	System.out.println(stackedNodes.isEmpty());

            }
        }
        System.out.println("Recurssion on runnables finished");
        */
	}
}
