import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class PerformanceRoR {
	public static void measurePerThread(int noThreads){
	    
	    
		DataGraph random = GraphFactory.makeRandomSparseGraph();
	    ExecutorService executor = Executors.newFixedThreadPool(noThreads);
	    
	    ExplorePredecessors graphExplorator = new ExplorePredecessors(executor, random.nodes[0], random);
	    graphExplorator.startWithTimer();
	    while (GraphExplorator.finished == false){
	    	//System.out.println(GraphExplorator.finished);
	    	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    ArrayList<Long> resultsPerThread = GraphFactory.results.get(noThreads);
	    
	    if (resultsPerThread == null){
	    	resultsPerThread = new ArrayList<Long>();
	    	GraphFactory.results.put(noThreads, resultsPerThread);
	    }
	    resultsPerThread.add(GraphExplorator.stopTime - GraphExplorator.startTime);
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
	    for(int i = GraphFactory.threadsNoStart; i <= GraphFactory.threadsNoEnd; i++){
		    for(int ii = 0; ii < GraphFactory.measurmentsPerThread; ii++){
		    	measurePerThread(i);
		    }
		}
	    prettyPrint();
	}
}
