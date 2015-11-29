import java.util.Arrays;

public class Word implements Comparable<Word> {
	public int length = 0;
	public String raw = "";
	public String sorted = "";
	
	public int compareTo(Word w) {
		int order;
		
		order = this.sorted.compareToIgnoreCase(w.sorted);
		
		if (order == 0) {
			order = this.raw.compareToIgnoreCase(w.raw);
		}
		
		return order;
	}
	
	public String toString() {
		return this.sorted + "|" + this.raw;
	}
	
	public Word(String str) {
		this.length = raw.length();
		this.raw = str.toLowerCase();
		
		char[] chars = this.raw.toCharArray();
		Arrays.sort(chars);
		
		this.sorted = new String(chars);
	}
}