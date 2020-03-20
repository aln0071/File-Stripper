import java.io.*;
import java.util.*;
import java.nio.file.*;
public class Integrate {
	static String data = "";

	public static void integrate(String path) {
		File dir = new File(path);
		File[] dirList = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".json");
			}
		});
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(dirList));
		Collections.sort(files, (f1, f2)-> {
			return extractNumber(f1.getName()) - extractNumber(f2.getName());
		});
		files.forEach(file-> {
			try {
				data += readFileAsString(file.getName());
			} catch(Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static void decode(String data, String filename) throws Exception{
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(Base64.getDecoder().decode(data));
		fout.close();
	}

	public static String readFileAsString(String fileName)throws Exception 
	{ 
		String data = ""; 
		data = new String(Files.readAllBytes(Paths.get(fileName))); 
		return data; 
	} 

	public static int extractNumber(String name) {
		int end = name.indexOf('.')-1;
		int number = 0;
		int multiplier = 1;
		char ch;
		while(Character.isDigit(ch = name.charAt(end--))) {
			number = (ch-'0')*multiplier+number;
			multiplier *= 10;
		}
		return number;
	}
	public static void main(String[] args) {
		try {
			integrate(args[0]);
			decode(data, args[1]);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
