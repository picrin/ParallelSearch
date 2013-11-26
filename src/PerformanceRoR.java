
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;



public class PerformanceRoR {
	
	public static long measurePerThread(int noThreads){
		GraphFactory.threadsNumber = noThreads;
		
		ForkJoinPool magic = new ForkJoinPool(GraphFactory.threadsNumber);
		DataGraph dg = GraphFactory.makeRandomSparseGraph();
		
		OuterWorker forkjoin = new OuterWorker(dg);
		System.gc();
		final long startTime = System.currentTimeMillis();
		magic.execute(forkjoin);
		forkjoin.join();
		final long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	public static float averagePerThread(int noThreads, int noOfTimes, int coolOff){
		ArrayList<Long> measurements = new ArrayList<Long>();
		for(int i = 0; i < noOfTimes; i++){
			long result = measurePerThread(noThreads);
			if (i > coolOff) measurements.add(result);
		}
		long sum = 0;
		for (Long result: measurements){
			sum += result;
		}
		
		return ((int) sum)/ (float)measurements.size();
	}
	
	public static void printGraph(DataGraph dg){
		for(Node node: dg.nodes){
			System.out.print(node.filename + ": ");
			for(Node child: node.children){
				System.out.print(child.filename + " ");
			}
			System.out.println();
		}
	}
	
	public static void main (String args[]){
		//printGraph(GraphFactory.makeRandomSparseGraph());
		measurePerThread(1);
		for(int i = 1; i <= 40; i++){
			System.out.println(i + " " + averagePerThread(i, 20, 4));
		}
	}
}
