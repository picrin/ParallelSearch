import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;



public class PerformanceRoR {
	
	public static void measurePerThread(int noThreads){
		DataGraph random = GraphFactory.makeRandomSparseGraph();
	    ExecutorService executor = new ForkJoinPool(noThreads);
	    
	    ExplorePredecessorsLocal graphExplorator = new ExplorePredecessorsLocal(executor, random.nodes.get(0), random);
	    
	    GraphExplorator.mainThread = Thread.currentThread();
	    graphExplorator.startWithTimer();
	    try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {}
	    ArrayList<Long> resultsPerThread = GraphFactory.results.get(noThreads);
	    
	    if (resultsPerThread == null){
	    	resultsPerThread = new ArrayList<Long>();
	    	GraphFactory.results.put(noThreads, resultsPerThread);
	    }
	    resultsPerThread.add(GraphExplorator.stopTime - GraphExplorator.startTime);
	}
	
	public static long DFS(){
		LinkedList<Node> stack = new LinkedList<Node>();
		DataGraph random = GraphFactory.makeRandomSparseGraph();
		Node start = random.nodes.get(0);
		long startTime = System.currentTimeMillis();
		stack.push(start);
		while (!stack.isEmpty()){
			Node current = stack.pop();
			current.visit();
			for(Node child: current.children){
				if (child.visit()){
					stack.push(child);
				}
			}
		}
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}		
	
	public static void prettyPrint(){
		System.out.println("starting number of threads " + GraphFactory.threadsNoStart);
		System.out.println("ending number of threads " + GraphFactory.threadsNoEnd);
		System.out.println("nodes number " + GraphFactory.nodesNo);
		System.out.println("children per node " + GraphFactory.maxChildren);
		for(int thread: GraphFactory.results.keySet()){
			System.out.println("current number of threads " + thread);
			for(Long times: GraphFactory.results.get(thread)){
				System.out.println(times);
			}
		}
	}
	
	public static void main (String args[]){
	    GraphFactory.loadConfig(args[0]);
    	ArrayList<Long> results = new ArrayList<Long>();
    	for(int ii = 0; ii < 10; ii++){
	    	results.add(DFS());
	    }
    	System.out.println("DepthFirstSearch");
    	for(long time: results){
    		System.out.println(time);
    	}

	    for(int i = GraphFactory.threadsNoStart; i <= GraphFactory.threadsNoEnd; i++){
	    	for(int iii = 0; iii < GraphFactory.measurmentsPerThread; iii++){
		    	measurePerThread(i);
		    }
		}
	    prettyPrint();
	}
}
