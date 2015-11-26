import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordLoader {
	private ArrayList<String> mRawWordList;
	
	private ArrayList<Word> mWordList3;
	private ArrayList<Word> mWordList4;
	private ArrayList<Word> mWordList5;
	private ArrayList<Word> mWordList6;
	
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
		mWordList3 = new ArrayList<Word>();
		mWordList4 = new ArrayList<Word>();
		mWordList5 = new ArrayList<Word>();
		mWordList6 = new ArrayList<Word>();
		
		
	}
}


