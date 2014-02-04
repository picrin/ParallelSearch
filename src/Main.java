mport java.util.concurrent.ExecutionException;
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
		for (NonBlockingHashMap map: dg.solutions){
			System.out.println(map);
		}
	}
	
	
}
