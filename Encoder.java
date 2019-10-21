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
	
	public static void main(String args[]) {
		try {
			splitter(encode("test.bin").getBytes(), 102400);
			broken.forEach((k, v)-> {
				try {
					writeStringToFile(new String(v), "data/data"+k+".json");
				} catch(Exception e) {
					e.printStackTrace();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
