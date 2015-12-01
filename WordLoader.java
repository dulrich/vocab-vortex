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


