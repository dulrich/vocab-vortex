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