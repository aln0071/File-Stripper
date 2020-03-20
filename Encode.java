import java.io.*;
import java.util.*;
public class Encode {
	
	static int i = 0;
	static HashMap<Integer, byte[]> broken = new HashMap<Integer, byte[]>();
	
	public static String encode(String filename) throws Exception {
		File file = new File(filename);
		FileInputStream fin = new FileInputStream(file);
		byte data[] = new byte[(int) file.length()];
		fin.read(data);
		fin.close();
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static void splitter(byte[] data, int size) {
		if(data.length>size) {
			splitter(Arrays.copyOfRange(data,0,data.length/2), size);
			splitter(Arrays.copyOfRange(data,data.length/2, data.length), size);
		} else {
			broken.put(new Integer(i++), data);
		}
	}
	
	public static void writeStringToFile(String data, String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(data);
		writer.close();
	}
	
	public static void decode(String data, String filename) throws Exception{
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(Base64.getDecoder().decode(data));
		fout.close();
	}

	private static void delete(File file) throws IOException {
		for(File childFile : file.listFiles()) {
			if(childFile.isDirectory()) {
				delete(childFile);
			} else if(!childFile.delete()) {
				throw new IOException();
			}
		}
		if(!file.delete()) {
			throw new IOException();
		}
	}

	private static void printHelp() {
		System.out.println("Usage: java Encode path size");
				System.out.println("\nThis program has two mandatory parameters.");
				System.out.println("path : this is the path to the .bin compressed file");
				System.out.println("size : (number of bytes) the encoded output will be splitted into equally sized files less than this size. Example - 102400");
				System.exit(0);
	}
	
	public static void main(String args[]) {
		try {
			if(args.length != 2 || !args[0].endsWith(".bin")) {
				printHelp();
			}
			int size = Integer.parseInt(args[1]);
			try {
				delete(new File("./data"));
			} catch(IOException e) {
				
			}
			new File("./data/").mkdirs();
			splitter(encode(args[0]).getBytes(), size);
			broken.forEach((k, v)-> {
				try {
					writeStringToFile(new String(v), "data/data"+k+".json");
				} catch(Exception e) {
					e.printStackTrace();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
			if(e instanceof NumberFormatException) {
				printHelp();
			}
		}
	}
}
