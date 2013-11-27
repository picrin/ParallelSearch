import java.util.ArrayList;


public class IO {
	static String cpath = System.getenv("CPATH");
	static String slashI;

	public static void main(String args[]){
		ArrayList<String> filenames = new ArrayList<String>();
		for(String arg: args){
			if (arg.length() >= 2){
				slashI = arg.substring(2, arg.length());
			} else {
				filenames.add(arg);
			}
		}
		for(String filename: filenames){
			System.out.println(filename);
		}
		if (slashI != null){
			System.out.println(slashI);
		}
		if (cpath != null){
			String[] path_elems = cpath.split(":");
			
			for(String elem: path_elems){
				System.out.println(elem);			
			}
		}
	}
}
