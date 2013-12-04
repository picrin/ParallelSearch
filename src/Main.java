import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;



public class Main{
	final static ForkJoinPool uberPool;
	static{
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
		uberPool = new ForkJoinPool(GraphFactory.locales.threads);
	}

	public static void main(String[] args){
		DataGraph dg = GraphFactory.makeSanityCheckGraph();
		/*
		//System.out.println(dg.remainder.get(1L).id);
		
		ExplorePredecessors predecessors = new ExplorePredecessors(GraphFactory.locales.threads, dg.remainder.get(3L), dg);
		ExploreDescendants descendants = new ExploreDescendants(GraphFactory.locales.threads, dg.remainder.get(3L), dg);
		
		predecessors.startExploration();
		descendants.startExploration();
		
		predecessors.awaitTermination();
		descendants.awaitTermination();
		
		System.out.println(dg.scc);*/
		//Future<?>
		UltimateRecurssion newRecurssion = new UltimateRecurssion(dg);
		Future<?> future = uberPool.submit(newRecurssion);
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		for (NonBlockingHashMap map: dg.solutions){
			System.out.println(map);
		}
	}
	
	
}
