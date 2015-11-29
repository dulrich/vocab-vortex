import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class WordLoader {
	private ArrayList<String> mRawWordList;
	
	private ArrayList<TreeSet<Word>> mWordLists;
	
	public void LoadWordList(String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		mRawWordList = new ArrayList<String>();

		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			mRawWordList.add(line);
		}
		bufferedReader.close();
	}
	
	public void ProcessWordList() {
		int len;
		
		mWordLists = new ArrayList<TreeSet<Word>>(4);
		
		mWordLists.add(new TreeSet<Word>());
		mWordLists.add(new TreeSet<Word>());
		mWordLists.add(new TreeSet<Word>());
		mWordLists.add(new TreeSet<Word>());
		
		for (String raw : this.mRawWordList) {
			len = raw.length();
			
			if (len > 6 || len < 3) {
				continue;
			}
			
			mWordLists.get(len - 3).add(new Word(raw));
		}
		
		System.out.println("Processed:");
		System.out.println("3: "+ mWordLists.get(3 - 3).size());
		System.out.println("4: "+ mWordLists.get(4 - 3).size());
		System.out.println("5: "+ mWordLists.get(5 - 3).size());
		System.out.println("6: "+ mWordLists.get(6 - 3).size());
	}
}


