// Vocab Vortex: Vibrate verbs, vary vowels, numerate nouns, and alter adjectives in a brain-bending grammar gambit
// Copyright (C) 2016  David Ulrich
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

#ifndef VV_LETTER_MASK_H
#define VV_LETTER_MASK_H

#include <ctype.h>
#include <stdint.h>

typedef uint32_t letter_mask;

const letter_mask letter_masks[] = {
	1 <<  0,
	1 <<  1,
	1 <<  2,
	1 <<  3,
	1 <<  4,
	1 <<  5,
	1 <<  6,
	1 <<  7,
	1 <<  8,
	1 <<  9,
	1 << 10,
	1 << 11,
	1 << 12,
	1 << 13,
	1 << 14,
	1 << 15,
	1 << 16,
	1 << 17,
	1 << 18,
	1 << 19,
	1 << 20,
	1 << 21,
	1 << 22,
	1 << 23,
	1 << 24,
	1 << 25,
	1 << 26
};

const letter_mask empty_mask = 0;
const letter_mask invalid_mask = 1 << 31;

typedef struct Word {
	int length;
	letter_mask mask;
	char raw[7];
	char sorted[7];
	// struct Word** subwords;
} word;

letter_mask letter_mask_mask(const char* word,int len) {
	char cur = 0;
	int i = 0;
	letter_mask mask = empty_mask;
	
	if (word == NULL) {
		return mask;
	}
	
	// printf("%s is %d\n",word,len);
	
	for(i = 0;i < len;i++) {
		cur = toupper(word[i]);
		
		if (cur == 0) break;
		
		if (cur < 'A' || cur > 'Z') {
			// printf("invalid char %u in %s\n",cur,word);
			mask = mask | invalid_mask;
		}
		else {
			mask = mask | letter_masks[cur - 'A'];
		}
	}
	
	return mask;
}

int letter_mask_subword(const word* longer,const word* shorter) {
	int c = 0;
	int i = 0;
	int j = 0;
	int p = -1;
	
	if ((shorter->mask & longer->mask) != shorter->mask) return 0;
	
	for(i = 0;i < shorter->length;i++) {
		p = -1;
		
		for(j = c;j < longer->length;j++) {
			if (shorter->sorted[i] == longer->sorted[j]) {
				c = j + 1;
				p = j;
				break;
			}
		}
		
		if (p < 0) return 0;
	}
	
	return 1;
}

#endif // VV_LETTER_MASK_H