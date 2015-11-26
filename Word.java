import java.util.Arrays;

public class Word implements Comparable<Word> {
	public int length = 0;
	public String raw = "";
	public String sorted = "";
	
	public int compareTo(Word w) {
		return this.sorted.compareToIgnoreCase(w.sorted);
	}
	
	public Word(String str) {
		this.length = raw.length();
		this.raw = str.toLowerCase();
		
		char[] chars = this.raw.toCharArray();
		Arrays.sort(chars);
		
		this.sorted = new String(chars);
	}
}