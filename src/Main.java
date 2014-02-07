/*
 * I'm well aware how much of a mess this program is. That's what tends
 * to happen with complicated programs. If you're trying to grok it,
 * please drop me an e-mail at adam@kurkiewicz.pl, I'll be happy to give
 * you a few pointers.
 * Adam
 */
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.junit.Assert;



public class Main{
	final static ForkJoinPool uberPool;
	static{
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
		uberPool = new ForkJoinPool(GraphFactory.locales.threads);
	}

	public static void main(String[] args){
		DataGraph dg = GraphFactory.makeTwoRandomSparseGraphs(10000, 3).get(0);
		UltimateRecurssion newRecurssion = new UltimateRecurssion(dg);
		Future<?> future = uberPool.submit(newRecurssion);
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		DataGraph.solutions.equals(DataGraph.solutions);
		//for (NonBlockingHashMap map: dg.solutions){
		//	//System.out.println(map);
		//}
	}
	
	
}
