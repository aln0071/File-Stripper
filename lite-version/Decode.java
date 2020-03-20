import java.nio.file.Files;
import java.nio.file.Paths;

public class Decode {
	public static void main(String args[]) throws Exception {
		Files.list(Paths.get("./data/")).sorted().forEach(object->System.out.println(object));
	}
}