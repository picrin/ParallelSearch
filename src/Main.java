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



public class Main{
	final static ForkJoinPool uberPool;
	static{
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
		uberPool = new ForkJoinPool(GraphFactory.locales.threads);
	}
	
	/*static ConcurrentLinkedQueue<NonBlockingHashMap<Long, Node>> giveSolutions(DataGraph dg){
		UltimateRecurssion newRecurssion = new UltimateRecurssion(dg);
		Future<?> future = uberPool.submit(newRecurssion);
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return dg.getSolutions();
	}*/
	
	public static void main(String[] args){
		//String filepath = args[0];
		//FileReader fr = new FileReader(filepath);
		//ArrayList<DataGraph> graphs = GraphFactory.makeTwoRandomSparseGraphs(10, 2);
		DataGraph dg = new DataGraph();
		GraphFactory.makeRandomSparseGraph(100000, 2, dg, Node.class);
		TarjanGraph dgt = new TarjanGraph();
		GraphFactory.makeRandomSparseGraph(100000, 2, dgt, TarjanNode.class);
		//DataGraph dg = GraphFactory.makeSanityCheckGraph();
		//SCCGraph<? extends DeNode<?, ?>> tusia;
		//tusia = graphs.get(0);
		/*for (NonBlockingHashMap<Long, ? extends DeNode<?, ?>> map: giveSolutions(dg)){
			//System.out.println(map);
		}*/
		dg.start();
		dgt.start();
		/*for (NonBlockingHashMap<Long, ? extends DeNode<?, ?>> map: dgt.getSolutions()){
			//System.out.println(map);
		}*/

		System.out.println(dg.compareTo(dgt));
		//System.out.println(dg.getNodes().get(1).getChildren());
		//System.out.println(dgt.getSolutions());
		
		//dg.compareTo(dgt);
		
	}
		
}
