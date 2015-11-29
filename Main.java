import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		WordLoader loader = new WordLoader();
		
		try {
			loader.LoadWordList("/usr/share/dict/american-english");
		} catch(IOException e) {
			System.out.println("file read failed");
		}
		
		loader.ProcessWordList();
		
		System.out.println("Done.");
	}
}
