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

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		WordLoader loader = new WordLoader();
		
		try {
			loader.LoadWordList("/usr/share/dict/american-english");
		} catch(IOException e) {
			System.out.println("file read failed");
		}
		
		loader.ProcessWordList();
		
		loader.CalculateSubwords();
		
		System.out.println("Done.");
	}
}
