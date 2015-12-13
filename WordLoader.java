// Vocab Vortex: Vibrate verbs, vary vowels, numerate nouns, and alter adjectives in a brain-bending grammar gambit
// Copyright (C) 2015  David Ulrich
// 
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
// 
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class WordLoader {
	private int mListCount = 4;
	
	private ArrayList<String> mRawWordList;
	
	private ArrayList<TreeMap<String,Word>> mWordLists;
	
	public void CalculateSubwords() {
		int i;
		TreeMap<String,Word> sixLetterWords;
		Iterator<String> sixLetterIter;
		TreeMap<String,Word> curWords;
		Iterator<String> curWordMapIter;
		Word w;
		
		sixLetterWords = this.mWordLists.get(this.mListCount - 1);
		sixLetterIter = sixLetterWords.keySet().iterator();
		
		while(sixLetterIter.hasNext()) {
			w = sixLetterWords.get(sixLetterIter.next());
			
			for(i = 0;i < mListCount - 1;i++) { // 3,4,5 letter lists
				curWords = this.mWordLists.get(i);
				curWordMapIter = curWords.keySet().iterator();
				
				while(curWordMapIter.hasNext()) {
					w.addSubword(curWords.get(curWordMapIter.next()));
				}
			}
			
			// if (w.size() > 1) {
			// 	System.out.println(w);
			// 	System.out.println(w.subwords().toString());
			// 	System.out.println("=====");
			// }
		}
	}
	
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
		int i;
		int len;
		String key;
		TreeMap<String,Word> map;
		Word wordNew,wordOld;
		
		mWordLists = new ArrayList<TreeMap<String,Word>>(4);
		
		for(i = 0;i < mListCount;i++) {
			mWordLists.add(new TreeMap<String,Word>());
		}
		
		for (String raw : this.mRawWordList) {
			len = raw.length();
			
			if (len > 6 || len < 3) {
				continue;
			}
			
			map = mWordLists.get(len - 3);
			wordNew = new Word(raw);
			key = wordNew.sorted();
			
			wordOld = map.get(key);
			
			if (wordOld == null) {
				map.put(key,wordNew);
			}
			else {
				wordOld.add(wordNew);
			}
			
			// mWordLists.get(len - 3).add(new Word(raw));
		}
		
		System.out.println("Processed:");
		
		for(i = 0;i < mListCount;i++) {
			System.out.println((i+3)+": "+ mWordLists.get(i).size());
		}
	}
}


