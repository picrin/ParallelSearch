import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.LinkedList;
//import java.util.concurrent.ConcurrentHashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class Coppersmith2005Test{
    public static int bigHashMapSize = 85000; // should create 680 MB size object; Will the object creation scale?
    final static int treeElements = 25;
    final static int treeWidth = 2;
    //static ExecutorService threadPool = Executors.newFixedThreadPool(5);

    @Before
    public void before(){
        GraphFactory.setProblemLocales(new ProblemLocales(10_000, 5, (float) 0.75));
    }

    @Test
    public void testDG(){
    	GraphFactory.setProblemLocales(new ProblemLocales(10, 2, 0.75F));
		ArrayList<DataGraph> arrayList = GraphFactory.makeTwoRandomSparseGraphs(7, 2);
        DataGraph random1 = arrayList.get(0);
        DataGraph random2 = arrayList.get(1);

        for(Node node: random1.remainder.values()){
			System.out.println(node + " children = " + node.children);
			//System.out.println(node + " parents = " + node.parents);
		}
        System.out.println("=========================");
        /*for(Node node: random2.remainder.values()){
			System.out.println(node + " children = " + node.children);
			System.out.println(node + " parents = " + node.parents);
		}*/

        Assert.assertFalse(random1.remainder.get(1L) == random2.remainder.get(1L));
        Assert.assertTrue(random1.remainder.get(1L).id == random2.remainder.get(1L).id);

        //ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        GraphFactory.setProblemLocales(new ProblemLocales(10, 2, 0.75F));
        //System.out.println(sanity.remainder);
        //ExploreDescendants graphExplorator = new ExploreDescendants(2, sanity.remainder.get(5L), sanity);
        //graphExplorator.startExploration();
        	//throw new InterruptedException();
        //graphExplorator.awaitExploration();
        //System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");
        //System.out.println(graphExplorator.getDG().remainder);
        //System.out.println(graphExplorator.getDG().descendants);
		//DFS
		
        Node node1 = random1.remainder.get(1L);
        LinkedList<Node> stack = new LinkedList<Node>();

        stack.push(node1);
		node1.markDescendant(random1);
        while (!stack.isEmpty()){
			Node current = stack.pop();
			
			for(Node child: current.children){
				if (child.markDescendant(random1)){
					stack.push(child);
				}
			}
		}

		ArrayList<Long> DFSVisited = new ArrayList<Long>();
		
		for(Node node: random1.descendants.values()){
        		DFSVisited.add(node.id);
        }
		Collections.sort(DFSVisited);

		
		ArrayList<Long> ParallelSearchVisited = new ArrayList<Long>();
        
        ExploreDescendants graphExplorator = new ExploreDescendants(4, random2.remainder.get(1L), random2);
        
       	graphExplorator.startExploration();
       	graphExplorator.awaitExploration();
	    
		for(Node node: random2.descendants.values()){
			ParallelSearchVisited.add(node.id);
		}
	    
        Collections.sort(ParallelSearchVisited);

		System.out.println(DFSVisited);
        System.out.println(ParallelSearchVisited);
        
        		
		assertTrue(ParallelSearchVisited.size() == DFSVisited.size());
		for(int i = 0; i < ParallelSearchVisited.size(); i++){
			//System.out.println(ParallelSearchVisited.get(i) + " " + DFSVisited.get(i));
			assertTrue(ParallelSearchVisited.get(i).equals(DFSVisited.get(i)));
		}

    }
    
    @Test
    public void testVisitor(){
        DataGraph dg = new DataGraph();

        LinkedList<Node> all = new LinkedList<Node>();
        int already = 2;
        Node root = new Node(1);

        LinkedList<Node> bfs = new LinkedList<Node>(); 
        bfs.addFirst(root);
        all.addFirst(root);
        while(already <= treeElements){
            Node subroot = bfs.pollLast();
            for(int i = 1; i <= treeWidth; i++){
                Node newElement = new Node(already);
                subroot.connectChild(newElement);
                all.addFirst(newElement);
                bfs.addFirst(newElement);
                already += 1;
            }
        }

        for(Node element: all){
            element.setGraph(dg);
            
        }
        for(Node child: root.children){
        	//System.out.println(child.id);
        }
        ExploreDescendants magia = new ExploreDescendants(2, root, dg);
        magia.startExploration();
        //threadPool.
        magia.awaitExploration();

        long[] array = new long[treeElements];
        int ii = 0;
        //System.out.println(ii);
        for(Node node: dg.descendants.values()){
        	array[ii] = node.id;
        	//System.out.println(ii);
        	ii++;
        }
        Arrays.sort(array);
        for(int i = 0; i < treeElements; i++){
           Assert.assertTrue(array[i] == (i + 1));
        }         
    }
    
    @Test
    public void testL6(){
        GraphFactory.setProblemLocales(new ProblemLocales(10, 2, 0.75F));
		DataGraph sanity = GraphFactory.makeSanityCheckGraph();

        //ExecutorService executor = new ForkJoinPool(GraphFactory.threadsNoEnd);
        GraphFactory.setProblemLocales(new ProblemLocales(10, 2, 0.75F));
        ExploreDescendants graphExplorator = new ExploreDescendants(2, sanity.remainder.get(5L), sanity);
        graphExplorator.startExploration();
        	//throw new InterruptedException();
        graphExplorator.awaitExploration();
        //System.out.println("Recurssion on runnables finished" + (GraphExplorator.stopTime - GraphExplorator.startTime) + "millis after execution");
        System.out.println(graphExplorator.getDG().remainder);
        System.out.println(graphExplorator.getDG().descendants);
        
        Assert.assertTrue(graphExplorator.getDG().descendants.get(4L).descendant);
        Assert.assertTrue(graphExplorator.getDG().descendants.get(5L).descendant);
        Assert.assertTrue(graphExplorator.getDG().descendants.get(6L).descendant);

        
        Assert.assertTrue(graphExplorator.getDG().remainder.get(1L).id == 1L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(2L).id == 2L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(3L).id == 3L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(7L).id == 7L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(8L).id == 8L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(9L).id == 9L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(10L).id == 10L);
        Assert.assertTrue(graphExplorator.getDG().remainder.get(11L).id == 11L);
    }
    
    
    @Test
    public void repeatElements(){
        Random generator = new Random();
        final int test_rep = 30;
        final int nodeNumber = 20;
        final int thread_number = 8;
        final int test_length = 100;
        
        class PreDesThread extends Thread{
            int track;
            boolean[][] operation;
            int[][] order;
            Node[] nodes;
            DataGraph dg;
            public PreDesThread(int track, boolean[][] operation, int[][] order, Node[] nodes, DataGraph dg){
                super();
                this.track = track;
                this.operation = operation;
                this.order = order;
                this.dg = dg;
                this.nodes = nodes;
            }
            @Override
            public void run(){
                for(int i = 0; i < test_length; i++){
                    Node node = nodes[order[track][i]];
                    //System.out.print(node.id);
                    
                    if(operation[track][i]){
                        node.markPredecessor(dg);
                    } else {
                        node.markDescendant(dg);
                    }
                            
                }                
            }

        }
        
        for(int i = 0; i < test_rep; i++){
            final DataGraph dg = new DataGraph();
            final Node[] nodes = new Node[nodeNumber];
            final int[][] order = new int[thread_number][test_length];
            final boolean[][] operation = new boolean[thread_number][test_length];
            PreDesThread[] threads = new PreDesThread[thread_number];
            
            for(int ii = 1; ii <= nodeNumber; ii++){
                nodes[ii - 1] = new Node(ii);
                dg.addNode(nodes[ii - 1]);
            }

            for(int ti = 0; ti < thread_number; ti++){
                for(int tii = 0; tii < test_length; tii++){
                    order[ti][tii] = generator.nextInt(3);
                }
            }
            
            for(int ti = 0; ti < thread_number; ti++){
                for(int tii = 0; tii < test_length; tii++){
                    operation[ti][tii] = generator.nextBoolean();
                }
            }
            
            for(int ti = 0; ti < thread_number; ti++){
                threads[ti] = new PreDesThread(ti, operation, order, nodes, dg);
            }
            
            for(int ti = 0; ti < thread_number; ti++){
                threads[ti].start();
            }
                   
        }

    }
    @Test
    public void sanityCheck(){
        DataGraph dg = new DataGraph();
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        
        dg.addNode(n1);
        dg.addNode(n2);
        dg.addNode(n3);
        dg.addNode(n4);
        dg.addNode(n5);
        
        n1.markPredecessor(dg);
        n2.markPredecessor(dg);
        n3.markDescendant(dg);
        n2.markPredecessor(dg);
        n1.markDescendant(dg);
        
        Node[] scc = new Node[1];
        int i = 0;
        for( Node node: dg.scc.values()){
        	scc[i] = node;
        	i++;
        }
        Assert.assertTrue(scc.length == 1);
        Assert.assertTrue(scc[0].id == 1);

        Node[] descendants = new Node[1];

        i = 0;
        for( Node node: dg.descendants.values()){
        	descendants[i] = node;
        	i++;
        }

        Assert.assertTrue(descendants.length == 1);
        Assert.assertTrue(descendants[0].id == 3);

        Node[] predecessors = new Node[1];
        i = 0;
        Assert.assertTrue(dg.predecessors.size() == 1);
        for( Node node: dg.predecessors.values()){
        	System.out.println(node.id);
        	predecessors[i] = node;
        	i++;
        }
        
        Assert.assertTrue(predecessors[0].id == 2);

        Assert.assertTrue(dg.remainder.size() == 2);
        long[] remainder = new long[2];
        //dg.remainder.toArray(remainder);
        i = 0;
        for( Node node: dg.remainder.values()){
        	remainder[i] = node.id;
        	i++;
        }


        Arrays.sort(remainder);
        Assert.assertTrue(remainder[0] == 4L);
        Assert.assertTrue(remainder[1] == 5L);
    }
    
    @Test
    public void KISS_SCCDecomposition(){
    ForkJoinPool uberPool = new ForkJoinPool(GraphFactory.locales.threads);
	DataGraph dg = GraphFactory.makeSanityCheckGraph();
	UltimateRecurssion newRecurssion = new UltimateRecurssion(dg);
	Future<?> future = uberPool.submit(newRecurssion);
	try {
		future.get();
	} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	}
	for (NonBlockingHashMap map: dg.solutions){
		if(map.size() == 1){
			Assert.assertTrue(map.containsKey(3L));
		} else if(map.size() == 4){
			Assert.assertTrue(map.containsKey(8L));
			Assert.assertTrue(map.containsKey(11L));
			Assert.assertTrue(map.containsKey(9L));
			Assert.assertTrue(map.containsKey(10L));
		} else if(map.size() == 3){
			if (map.containsKey(2L)){
				Assert.assertTrue(map.containsKey(1L));
				Assert.assertTrue(map.containsKey(7L));
			} else if (map.containsKey(6L)){
				Assert.assertTrue(map.containsKey(5L));
				Assert.assertTrue(map.containsKey(4L));					
			} else{
				System.out.println(map.containsKey(2L));
				Assert.fail("SCC with unexpected value" + map);
			}
		} else {
			Assert.fail("SCC of unexpected length" + map);
		}
		//System.out.println(map);
	}
    }
    /*@Test
    **
     * That's another test, which looks for possible race condition when there's a lot of threads pushing to one hashset.
     *
    public void multiThreads() throws InterruptedException{
        final DataGraph multiGraph = new DataGraph();
        int size = Globals.problemSizeTests;
        for(int i = 1; i <= size; i++){
            int id = i;
            multiGraph.addNode(new Node(id));
        }
    
        Node[] remainderFlat = new Node[multiGraph.remainder.size()];
        multiGraph.remainder.toArray(remainderFlat);
        List<Node> pullList = Arrays.asList(remainderFlat);
    
        Collections.shuffle(pullList);

        int numberOfThreads = 30;      
        Random generator = new Random();
        
        final int partition[] = new int[numberOfThreads + 1];
        partition[0] = 0;
        for(int i = 1; i < numberOfThreads; i++){
            partition[i] = generator.nextInt(Globals.problemSizeTests + 1);
        }
        
        partition[numberOfThreads] = Globals.problemSizeTests;
        Arrays.sort(partition);
        
        abstract class PreDesThread extends Thread{
            int start;
            int stop;
            List<Node> nodes;
            public PreDesThread(int start, int stop, List<Node> nodes){
                super();
                this.start = start;
                this.stop = stop;
                this.nodes = nodes;
            }
        }
        
        class PreThread extends PreDesThread{
            public PreThread(int start, int stop, List<Node> nodes) {
                super(start, stop, nodes);
            }
            @Override
            public void run(){
                for(int i = this.start; i < this.stop; i++){
                    //System.out.println(nodes.get(i).id);
                    nodes.get(i).mark_predecessor(multiGraph);
                }                
            }
        }
    
        class DesThread extends PreDesThread{
            public DesThread(int start, int stop, List<Node> nodes) {
                super(start, stop, nodes);
            }
            @Override
            public void run(){
                for(int i = this.start; i < this.stop; i++){
                    //System.out.println(nodes.get(i).id);
                    nodes.get(i).mark_descendant(multiGraph);
                }                
            }
        }
    
        PreThread[] predecessorThreads = new PreThread[numberOfThreads]; 
        DesThread[] descendantsThreads = new DesThread[numberOfThreads]; 
    
        for(int i = 0; i < numberOfThreads; i++){
            predecessorThreads[i] = new PreThread(partition[i], partition[i + 1], pullList);
        }
    
        for(int i = 0; i < numberOfThreads; i++){
            descendantsThreads[i] = new DesThread(partition[i], partition[i + 1], pullList);
        }
        Assert.assertTrue(multiGraph.remainder.size() + multiGraph.predecessors.size() + multiGraph.descendants.size() + multiGraph.scc.size() == Globals.problemSizeTests);
        
                
        Node[] entireNode = new Node[Globals.problemSizeTests];
        
        for(PreThread thread: predecessorThreads){
            thread.start();
        }
        for(DesThread thread: descendantsThreads){
            thread.start();
        }
        for(PreThread thread: predecessorThreads){
            thread.join();
        }
        for(DesThread thread: descendantsThreads){
            thread.join();
        }

        
        multiGraph.scc.toArray(entireNode);
        
        Long[] entire = new Long[Globals.problemSizeTests];
        int ii = 0;
        for(Node node : entireNode){
            entire[ii] = entireNode[ii].id;
            ii++;
        }
        
        Arrays.sort(entire);
        Long[] compare = new Long[Globals.problemSizeTests];
        
        for(long li = 0; li < Globals.problemSizeTests; li++){
            compare[(int)li] = li + 1;
        }
        
        Assert.assertArrayEquals(entire, compare);
        
    }*/

// TODO uncomment the implementation of Comparable in Node
    @Test
    /**multiGraph
     * this is a comprehensive, randomized test, which runs two threads, one for predecessors and one for descendants. The testing looks at the following properties:
     * are all pulled descendants either in scc or in descendants?
     * are all pulled predecessors either in scc or in predecessors?
     * is the partition of the set, once merged back into one set, the same set? (roughly).
     * Anyway, do not trust these tests. Write your own ones. 
     * @throws InterruptedException
     */
    public void twoThreads() throws InterruptedException {
    	int problemSize = 1000;
        final DataGraph multiGraph = new DataGraph();
        int pullPreSize = problemSize/2;
        int pullDesSize = problemSize/2;
        for(int i = 1; i <= problemSize; i++ ){
            int id = i; //generator.nextLong()
            multiGraph.addNode(new Node(id));
        }

        Node[] remainderFlat = new Node[multiGraph.remainder.size()];
        int indexo = 0;
        for(Node node: multiGraph.remainder.values()){
        	remainderFlat[indexo] = node;
        	indexo++;
        }
        final Node[] pullPre = new Node[pullPreSize];
        final Node[] pullDes = new Node[pullDesSize];
        List<Node> pullList = Arrays.asList(remainderFlat);

        Collections.shuffle(pullList);
        //System.out.println(multiGraph.reminder.size());
        //pull predecessors
        for(int i = 0; i < pullPreSize; i++){
            pullPre[i] = pullList.get(i);
        }
        
        Collections.shuffle(pullList);
        
        //pull descendants
        for(int i = 0; i < pullDesSize; i++){
            pullDes[i] = pullList.get(i);
        }       
        
        Thread preThread = new Thread(
                new Runnable(){
                    public void run(){
                        for(Node node: pullPre){
                            node.markPredecessor(multiGraph);
                        }
                    }
                }
        );

        Thread desThread = new Thread(
                new Runnable(){
                    public void run(){
                        for(Node node: pullDes){
                            node.markDescendant(multiGraph);
                        }
                    }
                }    
        );
        
        preThread.start();
        desThread.start();

        preThread.join();
        desThread.join();
        
        //System.out.println(Globals.problemSize);
        //System.out.println(multiGraph.remainder.size());
        //System.out.println(multiGraph.scc.size());
        Assert.assertTrue(multiGraph.remainder.size() + multiGraph.predecessors.size() + multiGraph.descendants.size() + multiGraph.scc.size() == problemSize);
        
        //some more assertions
        
        Node[] pushPre = new Node[pullPre.length];
        Node[] pushDes = new Node[pullDes.length];
        int ii = 0;
        for(Node node: multiGraph.predecessors.values()){
            pushPre[ii] = node;
            ii ++;
        }
        
        for(Node node: multiGraph.scc.values()){
            pushPre[ii] = node;
            ii ++;
        }

        ii = 0;
        for(Node node: multiGraph.descendants.values()){
            pushDes[ii] = node;
            ii ++;
        }

        for(Node node: multiGraph.scc.values()){
            pushDes[ii] = node;
            ii++;
        }

        Arrays.sort(pullPre);
        Arrays.sort(pushPre);
        Arrays.sort(pullDes);
        Arrays.sort(pushDes);

        Assert.assertArrayEquals(pullPre, pushPre);
        Assert.assertArrayEquals(pullDes, pushDes);
        
        Long[] entire = new Long[problemSize];
        
        ii = 0;

        for(Node node: multiGraph.predecessors.values()){
            entire[ii] = node.id;
            ii ++;
        }

        for(Node node: multiGraph.descendants.values()){
            entire[ii] = node.id;
            ii ++;
        }


        for(Node node: multiGraph.scc.values()){
            entire[ii] = node.id;
            ii ++;
        }

        for(Node node: multiGraph.remainder.values()){
            entire[ii] = node.id;
            ii ++;
        }
        
        Arrays.sort(entire);
        Long[] compare = new Long[problemSize];
        
        for(long li = 0; li < problemSize; li++){
            compare[(int)li] = li + 1;
        }
        
        Assert.assertArrayEquals(entire, compare);
    }

}
