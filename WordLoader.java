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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
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
	
	private void StoreWordInDB(Connection conn,Word w) {
		Iterator<String> wordIter;
		
		String query_sorted,query_words,query_subwords;
		String sorted;
		PreparedStatement stmt_sorted = null,
			stmt_words = null,
			stmt_subwords = null;
		
		sorted = w.sorted();
		
		query_sorted = "INSERT IGNORE INTO vv_word (VVWordSorted) VALUES (?)";
		
		int i,pos,size;
		try {
			stmt_sorted = conn.prepareStatement(query_sorted);
			stmt_sorted.setString(1,sorted);
			
			stmt_sorted.execute();
			stmt_sorted.close();
			
			// add the six letter words in w to the db
			query_words = "INSERT IGNORE INTO vv_subword (VVWordSorted,VVSubWord) VALUES ";
			
			size = w.words().size();
			for(i = 0; i < size; i++) {
				query_words = query_words + " (?,?)";
				
				if (i < size - 1) query_words = query_words + ",";
			}
			// System.out.println("WORDS: " + query_words);
			stmt_words = conn.prepareStatement(query_words);
			
			wordIter = w.words().iterator();
			
			pos = 1;
			while(wordIter.hasNext()) {
				stmt_words.setString(pos,sorted);
				stmt_words.setString(pos + 1,wordIter.next());
				
				pos = pos + 2;
			}
			
			stmt_words.execute();
			stmt_words.close();
			
			// add the shorter subwords in w to the db
			query_subwords = "INSERT IGNORE INTO vv_subword (VVWordSorted,VVSubWord) VALUES ";
			
			size = w.subwords().size();
			for(i = 0; i < size; i++) {
				query_subwords = query_subwords + " (?,?)";
				
				if (i < size - 1) query_subwords = query_subwords + ",";
			}
			// System.out.println("SUBWORDS: " + query_subwords);
			stmt_subwords = conn.prepareStatement(query_subwords);
			
			wordIter = w.subwords().iterator();
			
			pos = 1;
			while(wordIter.hasNext()) {
				stmt_subwords.setString(pos,sorted);
				stmt_subwords.setString(pos + 1,wordIter.next());
				
				pos = pos + 2;
			}
			
			stmt_subwords.execute();
			stmt_subwords.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally {
			if (stmt_sorted != null) {
				try {
					stmt_sorted.close();
				} catch (SQLException e) { } // ignore
				
				stmt_sorted = null;
			}
			
			if (stmt_words != null) {
				try {
					stmt_words.close();
				} catch (SQLException e) { } // ignore
				
				stmt_words = null;
			}
			
			if (stmt_subwords != null) {
				try {
					stmt_subwords.close();
				} catch (SQLException e) { } // ignore
				
				stmt_subwords = null;
			}
		}
	}
	
	public void StoreAllInDB() {
		Connection conn = null;
		Properties prop = new Properties();
		
		// todo: load db settings from config.properties
		prop.put("user","vv_user");
		prop.put("password","vv_pass");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");//.newInstance();
		} catch (Exception e) {
			System.out.println("Failed to initialize mySQL driver: " + e);
			return;
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/vocab_vortex",prop);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			
			return;
		}
		
		TreeMap<String,Word> sixLetterWords;
		Iterator<String> sixLetterIter;
		
		
		sixLetterWords = this.mWordLists.get(this.mListCount - 1);
		sixLetterIter = sixLetterWords.keySet().iterator();
		
		while(sixLetterIter.hasNext()) {
			this.StoreWordInDB(conn,sixLetterWords.get(sixLetterIter.next()));
		}
		
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
}


