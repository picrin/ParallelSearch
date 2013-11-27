import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
public class IO {
	static String cpath = System.getenv("CPATH");
	static ArrayList<String> slashI = new ArrayList<String>();

	public static boolean isIn(String dir, final String filename){
		if (filename == null || dir == null) return false;
		File f = new File(dir);
		if (f.isDirectory()) {
			File[] matchingFiles = f.listFiles(
				new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return (name.equals(filename));
					}
				}				
			);
			if (matchingFiles.length == 0) return false;
			return true;
		}
		return false;
	}
	
	public static String searchEverywhere(String filename){
		
		if (isIn(".", filename)) return new File(filename).getAbsolutePath();
		if(slashI != null){
			for(String slashd: slashI){
				if (isIn(slashd, filename)) return new File(slashd + "/" + filename).getAbsolutePath();
			}
		}
		if (cpath != null){
			String[] path_elems = cpath.split(":");
			for(String elem: path_elems){
				if (isIn(elem, filename)) return new File(elem + "/" + filename).getAbsolutePath();			
			}
		}
		return null;
	}
	
	public static ArrayList<String> parseFile(String absolutePath){
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(absolutePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> includes = new ArrayList<String>();
		Scanner scanner = new Scanner(reader);
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			Pattern p = Pattern.compile(".*#include \".*\".*");
			Matcher m = p.matcher(line);
			if (m.matches()){
				int firstOccurence = line.indexOf("#include \"");
				int lastOccurence = line.lastIndexOf("\"");
				includes.add(line.substring(firstOccurence + 10, lastOccurence));
				//System.out.println(line.substring(firstOccurence + 10, lastOccurence));
			}

		}
		scanner.close();
		return includes;
	}
	
	public static void main(String args[]){

		ArrayList<String> filenames = new ArrayList<String>();
		for(String arg: args){
			if (arg.length() >= 2 && arg.charAt(0) == '-' && arg.charAt(1) == 'I'){
				String nextInt = arg.substring(2, arg.length());
				//if (nextInt.charAt(nextInt.length() - 1) != '/'){
				//	nextInt = nextInt.concat("/");
				//}
				slashI.add(nextInt);
			} else {
				filenames.add(arg);
			}
		}
		DataGraph dg = new DataGraph();
		for(String filename: filenames){
			if (filename != null){
				String searchResult = searchEverywhere(filename);
				if (searchResult != null){
					System.out.println("searchResult " + searchResult);
					ArrayList<String> dependencies = parseFile(searchResult);
					Node node = new Node(filename);
					for (String dependency: dependencies){
						if (dg.hashSet.containsKey(dependency)){
							node.children.add(dg.hashSet.get(dependency));
						} else{
							dg.addNode(node);
							Node child = new Node(dependency);
							dg.hashSet.put(dependency, child);
							node.children.add(node);
						}
					}
				}
			}
		}
		GraphFactory.threadsNumber = 2;
		ForkJoinPool magic = new ForkJoinPool(GraphFactory.threadsNumber);
		OuterWorker outerworker = new OuterWorker(dg);
		magic.execute(outerworker);
		outerworker.join();
		PerformanceRoR.printGraph(dg);

	}
}
