

import java.util.ArrayList;
import java.util.Random;


public class GraphFactory {
	static Random generator = new Random();
	static long seed = generator.nextLong();
	
	public static void changeSeed(){
		seed = generator.nextLong();
	}
	
	public GraphFactory(){};
    
    static ProblemLocales locales;
    
    static void setProblemLocales(ProblemLocales localesToSet){
        locales = localesToSet;
    }
    
    public static int sizeToCap(int size){
        return (int) ((size/(locales.loadFactor - 0.01)) + 1);
    }

    public static DataGraph makeRandomGraph(int size){
        generator.setSeed(seed);
        //System.out.println(generator.nextFloat());
        DataGraph dg = new DataGraph();
        Node[] nodes = new Node[size];
        for(int i = 0; i < size; i++){
            Node newNode = new Node(i + 1);
            nodes[i] = newNode;
            dg.addNode(newNode);
        }

        for(int i = 0; i < size; i++){
            for(int ii = 0; ii < size; ii++){
                if(generator.nextBoolean()){
                    nodes[i].connectChild(nodes[ii]);
                    //System.out.println("connected " + nodes[i].id + " with " + nodes[ii].id);
                }
            }
        }
        return dg; 
    }
    public static <GraphType extends AbstractGraph<NodeType>, NodeType extends DeNode<NodeType, ?>> void makeRandomSparseGraph(int size, int noChildren, GraphType graph, Class<NodeType> nodeContext){
        generator.setSeed(seed);
        NodeBuilder<NodeType> builder = new NodeBuilder<NodeType>(nodeContext);
        ArrayList<NodeType> nodes = new ArrayList<NodeType>(size);

        for(int i = 0; i < size; i++){
            NodeType newNode = builder.newNode(i + 1);
            nodes.add(i, newNode);
            graph.addNode(newNode);
        }
        
        for(int i = 0; i < size; i++){
            for(int ii = 0; ii < noChildren; ii++){
            	int nextInt = generator.nextInt(size);
            	//System.out.println(nextInt);
            	nodes.get(i).connectChild(nodes.get(nextInt));
                //System.out.println("connected " + nodes[i].id + " with " + nodes[ii].id);        
            }
        }
    }
    public static DataGraph makeSanityCheckGraph(){
        DataGraph dg = new DataGraph();
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);
        Node n10 = new Node(10);
        Node n11 = new Node(11);

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

    public static TarjanGraph makeSanityCheckGraphTarjan(){
    	TarjanGraph dg = new TarjanGraph();
        TarjanNode n1 = new TarjanNode(1);
        TarjanNode n2 = new TarjanNode(2);
        TarjanNode n3 = new TarjanNode(3);
        TarjanNode n4 = new TarjanNode(4);
        TarjanNode n5 = new TarjanNode(5);
        TarjanNode n6 = new TarjanNode(6);
        TarjanNode n7 = new TarjanNode(7);
        TarjanNode n8 = new TarjanNode(8);
        TarjanNode n9 = new TarjanNode(9);
        TarjanNode n10 = new TarjanNode(10);
        TarjanNode n11 = new TarjanNode(11);

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
    
    public static KosarajuGraph makeSanityCheckKosarajuGraph(){
    	KosarajuGraph kdg = new KosarajuGraph();
        TarjanNode n1 = new TarjanNode(1);
        TarjanNode n2 = new TarjanNode(2);
        TarjanNode n3 = new TarjanNode(3);
        TarjanNode n4 = new TarjanNode(4);
        TarjanNode n5 = new TarjanNode(5);
        TarjanNode n6 = new TarjanNode(6);
        TarjanNode n7 = new TarjanNode(7);
        TarjanNode n8 = new TarjanNode(8);
        TarjanNode n9 = new TarjanNode(9);
        TarjanNode n10 = new TarjanNode(10);
        TarjanNode n11 = new TarjanNode(11);

        kdg.addNode(n1);
        kdg.addNode(n2);
        kdg.addNode(n3);
        kdg.addNode(n4);
        kdg.addNode(n5);
        kdg.addNode(n6);
        kdg.addNode(n7);
        kdg.addNode(n8);
        kdg.addNode(n9);
        kdg.addNode(n10);
        kdg.addNode(n11);

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
        return kdg;
    }

}
