import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Visitor {
	// This has been confirmed to scale well.
	private final ExecutorService executor;
	private final NonBlockingHashMap<?, Node> map; 
	private final ArrayList<Integer> leftBoundaries = new ArrayList<Integer>();
	private final ArrayList<Integer> rightBoundaries = new ArrayList<Integer>();
	private final int levelOfParallelism;
	private final DataGraph dg;
	
	private static int firstOdd(int number){
		if (number % 2 == 0){
			return number + 1;
		} else return number;

	}
	
	public Visitor(NonBlockingHashMap<?, Node> map, DataGraph dg, int levelOfParallelism){
		this.executor = Executors.newFixedThreadPool(levelOfParallelism);
		this.map = map;
		this.dg = dg;
		this.levelOfParallelism = levelOfParallelism;
	}
	
	class Worker implements Runnable{
		private final int left;
		private final int right;
		//private NonBlockingHashMap<Integer, Node> map; 
		Worker(int left, int right/*, NonBlockingHashMap<Integer, Node> map*/){
			this.left = left;
			this.right = right;
			//this.map = map;
		}
		
		@Override
		public void run() {
			for(int ii = left; ii < right; ii += 2){
				//System.out.println(i);
				if (map._kvs[ii] instanceof Node){
					((Node) map._kvs[ii]).reset(dg);
				}
			}
			
		}
		
	}
		
	public void visitAllInParallel(){
		
		int threadLength = map._kvs.length/levelOfParallelism;

		int leftMost = 3;
		leftBoundaries.add(leftMost);
		for(int i = 1; i < levelOfParallelism; i++){
			int firstOdd = firstOdd(threadLength*i);
			//System.out.println(firstOdd);
			if (firstOdd < 3) firstOdd = 3;
			leftBoundaries.add(firstOdd);
			rightBoundaries.add(firstOdd);
		}
		rightBoundaries.add(map._kvs.length);
		ArrayList<Future<?>> futures = new ArrayList<Future<?>>(threadLength);
		for(int i = 0; i < levelOfParallelism; i++){
			int left = leftBoundaries.get(i);
			int right = rightBoundaries.get(i);
			futures.add(executor.submit(new Worker(left, right)));
		}
		for (Future<?> future: futures){
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		executor.shutdown();
	}
}
