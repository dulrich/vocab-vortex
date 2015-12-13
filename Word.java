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

import java.util.Arrays;
import java.util.TreeSet;

public class Word implements Comparable<Word> {
	private TreeSet<String> mRaw;
	private TreeSet<String> mSubwords;
	private String mSorted;
	
	public boolean add(String str) {
		String temp = this.sort(str);
		
		if (temp == this.mSorted) {
			this.mRaw.add(temp);
			
			return true;
		}
		
		return false;
	}
	
	public boolean add(Word w) {
		if (this.compareTo(w) != 0) return false;
		
		return this.mRaw.addAll(w.words());
	}
	
	public boolean addSubword(String str) {
		return this.addSubword(new Word(str));
	}
	
	public boolean addSubword(Word w) {
		if (w.length() >= this.length()) return false;
		
		if (!this.contains(w)) return false;
		
		this.mSubwords.addAll(w.words());
		this.mSubwords.addAll(w.subwords());
		
		return true;
	}
	
	public int compareTo(Word w) {
		int order;
		
		order = this.mSorted.compareToIgnoreCase(w.mSorted);
		
		return order;
	}
	
	public boolean contains(String str) {
		int cur,i,len,pos;
		String sorted;
		
		if (str.length() > this.mSorted.length()) return false;
		
		cur = 0;
		sorted = this.sort(str);
		len = sorted.length();
		
		for(i = 0;i < len;i++) {
			pos = this.mSorted.indexOf(sorted.charAt(i),cur);
			
			if (pos < 0) return false;
			
			cur = pos + 1;
		}
		
		return true;
	}
	
	public boolean contains(Word w) {
		return this.contains(w.sorted());
	}
	
	public String toString() {
		return this.mSorted + "|" + this.mRaw;
	}
	
	public int length() {
		return this.mSorted.length();
	}
	
	public int size() {
		return this.mRaw.size();
	}
	
	private String sort(String str) {
		char[] chars = str.toLowerCase().toCharArray();
		Arrays.sort(chars);
		
		return new String(chars);
	}
	
	public String sorted() {
		return new String(this.mSorted);
	}
	
	public TreeSet<String> subwords() {
		return this.mSubwords;
	}
	
	public TreeSet<String> words() {
		return this.mRaw;
	}
	
	public Word(String str) {
		this.mRaw = new TreeSet<String>();
		this.mSubwords = new TreeSet<String>();
		
		this.mRaw.add(str.toLowerCase());
		
		this.mSorted = this.sort(str);
	}
}