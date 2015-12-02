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
import java.util.List;
import java.util.TreeSet;

public class WordLoader {
	private int mListCount = 4;
	
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
		int i;
		int len;
		
		mWordLists = new ArrayList<TreeSet<Word>>(4);
		
		for(i = 0;i < mListCount;i++) {
			mWordLists.add(new TreeSet<Word>());
		}
		
		for (String raw : this.mRawWordList) {
			len = raw.length();
			
			if (len > 6 || len < 3) {
				continue;
			}
			
			mWordLists.get(len - 3).add(new Word(raw));
		}
		
		System.out.println("Processed:");
		
		for(i = 0;i < mListCount;i++) {
			System.out.println((i+3)+": "+ mWordLists.get(i).size());
		}
	}
}


