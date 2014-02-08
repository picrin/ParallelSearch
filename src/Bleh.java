/*
 * I'm well aware how much of a mess this program is. That's what tends
 * to happen with complicated programs. If you're trying to grok it,
 * please drop me an e-mail at adam@kurkiewicz.pl, I'll be happy to give
 * you a few pointers.
 * Adam
 */
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

//import org.junit.Assert;



public class Bleh{
	final static ForkJoinPool uberPool;
	static{
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
		uberPool = new ForkJoinPool(GraphFactory.locales.threads);
	}
	
	static ConcurrentLinkedQueue<NonBlockingHashMap<Long, Node>> giveSolutions(DataGraph dg){
		UltimateRecurssion newRecurssion = new UltimateRecurssion(dg);
		Future<?> future = uberPool.submit(newRecurssion);
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return dg.getSolutions();
	}
	
	public static void main(String[] args){
		ArrayList<DataGraph> graphs = GraphFactory.makeTwoRandomSparseGraphs(10, 3);
		//DataGraph dg = GraphFactory.makeSanityCheckGraph();
		//TarjansGraph dgt = GraphFactory.makeSanityCheckGraphTarjan();
		//DataGraph dg = GraphFactory.makeSanityCheckGraph();
		//SCCGraph<? extends DeNode<?, ?>> tusia;
		//tusia = graphs.get(0);
		for (NonBlockingHashMap<Long, ? extends DeNode<?, ?>> map: giveSolutions(graphs.get(0))){
			System.out.println(map);
		}

		for (NonBlockingHashMap<Long, ? extends DeNode<?, ?>> map: giveSolutions(graphs.get(1))){
			System.out.println(map);
		}
		
		
		System.out.println(graphs.get(0).compareTo(graphs.get(1)));
		
	}
		
}
