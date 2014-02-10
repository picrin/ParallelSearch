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
	static{
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
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
		int graphSize = 10000;
		int threads = 1;
		int maxThreads = 10;
		int repetitions = 4;
		DataGraph dg;
		TarjanGraph dgt;
		//DataGraph dg = GraphFactory.makeSanityCheckGraph();
		//SCCGraph<? extends DeNode<?, ?>> tusia;
		//tusia = graphs.get(0);
		/*for (NonBlockingHashMap<Long, ? extends DeNode<?, ?>> map: giveSolutions(dg)){
			//System.out.println(map);
		}*/
		System.out.println("# Cores | SCC speed | Tarjan ");
		for(int i = threads; i < maxThreads; i++){

			ProblemLocales locales = new ProblemLocales(graphSize, i, 0.75F);
		    GraphFactory.setProblemLocales(locales);
		    DataGraph.setLocales();
		    
		    for(int ii = 0; ii < repetitions; ii++){
			    System.out.print(i + " ");
			    dg = new DataGraph();
				dgt = new TarjanGraph();
				
			    GraphFactory.changeSeed();
				GraphFactory.makeRandomSparseGraph(graphSize, 2, dg, Node.class);
				GraphFactory.makeRandomSparseGraph(graphSize, 2, dgt, TarjanNode.class);
				long dgtResult = dgt.start();
				long dgResult = dg.start();
				System.out.print(dgResult + " ");
				System.out.println(dgtResult + " ");
		    }
		    
		}
		/*for (NonBlockingHashMap<Long, ? extends DeNode<?, ?>> map: dgt.getSolutions()){
			//System.out.println(map);
		}*/

		//System.out.println(dg.compareTo(dgt));
		//System.out.println(dg.getNodes().get(1).getChildren());
		//System.out.println(dgt.getSolutions());
		
		//dg.compareTo(dgt);
	}

}
