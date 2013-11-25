

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphFactory {
	
    private GraphFactory(){};

    static int threadsNoStart;
    static int threadsNoEnd;
    static int nodesNo;
    static int maxChildren;
    static int measurmentsPerThread;
    
    static LinkedHashMap<Integer, ArrayList<Long>> results;
    
    public static void loadConfig(String filename){
    	FileReader fin = null;
    	try {
			fin = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	Scanner scanner = new Scanner(fin);
    	threadsNoStart = Integer.parseInt(scanner.next());
    	threadsNoEnd = Integer.parseInt(scanner.next());
    	nodesNo = Integer.parseInt(scanner.next());
    	maxChildren = Integer.parseInt(scanner.next());
    	measurmentsPerThread = Integer.parseInt(scanner.next());
    	results = new LinkedHashMap<Integer, ArrayList<Long>>();
    }
    
    public static DataGraph makeRandomGraph(){
        Random generator = new Random();
        DataGraph dg = new DataGraph();
        Node[] nodes = new Node[nodesNo];
        for(int i = 0; i < nodesNo; i++){
            Node newNode = new Node(i + 1, maxChildren);
            nodes[i] = newNode;
            dg.addNode(newNode);
        }

        for(int i = 0; i < nodesNo; i++){
            for(int ii = 0; ii < nodesNo; ii++){
                if(generator.nextBoolean()){
                    nodes[i].connectChild(nodes[ii]);
                    //System.out.println("connected " + nodes[i].id + " with " + nodes[ii].id);
                }
            }
        }

        return dg; 
    }

    public static DataGraph makeRandomSparseGraph(){
        Random generator = new Random();
        DataGraph dg = new DataGraph();
        Node[] nodes = new Node[nodesNo];
        for(int i = 0; i < nodesNo; i++){
            Node newNode = new Node(i + 1, maxChildren);
            nodes[i] = newNode;
            dg.addNode(newNode);
        }
        for(int i = 0; i < nodesNo; i++){
            for(int ii = 0; ii < maxChildren; ii++){
                nodes[i].connectChild(nodes[generator.nextInt(nodesNo)]);
                //System.out.println("connected " + nodes[i].id + " with " + nodes[ii].id);
                
            }
        }
        return dg; 
    }
    
    public static DataGraph makeSanityCheckGraph(){
        DataGraph dg = new DataGraph();
        Node n1 = new Node(1, maxChildren);
        Node n2 = new Node(2, maxChildren);
        Node n3 = new Node(3, maxChildren);
        Node n4 = new Node(4, maxChildren);
        Node n5 = new Node(5, maxChildren);
        Node n6 = new Node(6, maxChildren);
        Node n7 = new Node(7, maxChildren);
        Node n8 = new Node(8, maxChildren);
        Node n9 = new Node(9, maxChildren);
        Node n10 = new Node(10, maxChildren);
        Node n11 = new Node(11, maxChildren);

        dg.addNode(n1);
        dg.addNode(n2);
        dg.addNode(n3);
        dg.addNode(n4);
        dg.addNode(n5);
        dg.addNode(n6);
        dg.addNode(n7);
        dg.addNode(n8);
        dg.addNode(n9);
        dg.addNode(n10);
        dg.addNode(n11);

        n1.connectChild(n2);
        n2.connectChild(n3);
        n2.connectChild(n7);

        n3.connectChild(n4);
        n3.connectChild(n6);
        n4.connectChild(n5);
        n5.connectChild(n6);
        n6.connectChild(n4);
        n7.connectChild(n1);
        n7.connectChild(n2);
        n7.connectChild(n9);
        n8.connectChild(n6);
        n8.connectChild(n10);
        n8.connectChild(n11);
        n9.connectChild(n8);     
        n10.connectChild(n5);
        n10.connectChild(n11);
        n11.connectChild(n9);
        return dg;
    }
}
