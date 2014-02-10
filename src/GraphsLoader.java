import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GraphsLoader{
		static public TarjanGraph loadGraphFromFile(String filepath){
			//String filepath = "/home/iva/Programming/SCDC/graphsForTesting/test.txt";
			BufferedReader fr = null;
			try {
				fr = new BufferedReader(new FileReader(filepath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line;
			String[] splitted;
			TarjanGraph tdg = new TarjanGraph();	
			ArrayList<Long> ids = new ArrayList<>();
			long idparent;
			long idchild;
			
			try {
				while((line = fr.readLine())!=null ){
					
					if(!line.contains("#")){
						splitted = line.split("\t");
						idparent = Long.valueOf(splitted[0]).longValue();
						idchild = Long.valueOf(splitted[1]).longValue();					
						TarjanNode parent = new TarjanNode();
						TarjanNode child = new TarjanNode(idchild);
						
						if(!ids.contains(idparent)){
							parent = new TarjanNode(idparent);
							tdg.addNode(parent);
							ids.add(idparent);
						}
						
						if(ids.contains(idparent)){ 
						
							if(!ids.contains(idchild)){
								child = new TarjanNode(idchild);
								tdg.addNode(child);
								ids.add(idchild);
							}
							for(TarjanNode node: tdg.getNodes()){
								if(idparent==node.getID()){ parent = node; }
								if(idchild==node.getID()){ child = node; }
							}
						}
						parent.connectChild(child);
					}
				}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tdg;	
		}
}
