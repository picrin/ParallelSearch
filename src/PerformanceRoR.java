import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PerformanceRoR {
	public static void main (String args[]){
	    DataGraph random = GraphFactory.makeRandomSparseGraph();
	
	    ExecutorService executor = Executors.newFixedThreadPool(GraphFactory.threadsNo);
	    
	    ExplorePredecessors graphExplorator = new ExplorePredecessors(executor, random.nodes[666], random);
	    graphExplorator.startWithTimer();
	    while (GraphExplorator.finished == false){
	    	//System.out.println(GraphExplorator.finished);
	    	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    System.out.println((GraphExplorator.stopTime - GraphExplorator.startTime));

		GraphExplorator.counter.set(1);
	}
}
