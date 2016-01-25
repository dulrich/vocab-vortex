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
	1 <<  0, // A
	1 <<  1, // B
	1 <<  2, // C
	1 <<  3, // D
	1 <<  4, // E
	1 <<  5, // F
	1 <<  6, // G
	1 <<  7, // H
	1 <<  8, // I
	1 <<  9, // J
	1 << 10, // K
	1 << 11, // L
	1 << 12, // M
	1 << 13, // N
	1 << 14, // O
	1 << 15, // P
	1 << 16, // Q
	1 << 17, // R
	1 << 18, // S
	1 << 19, // T
	1 << 20, // U
	1 << 21, // V
	1 << 22, // W
	1 << 23, // X
	1 << 24, // Y
	1 << 25  // Z
};

const letter_mask empty_mask   = 0;
const letter_mask multi_mask   = 1 << 30;
const letter_mask invalid_mask = 1 << 31;

typedef struct Word {
	unsigned int invalid :  1;
	unsigned int multi   :  1;
	unsigned int mask    : 26;
	unsigned int length  :  4;
	char raw[6];
	char sorted[6];
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
		else if ((mask & letter_masks[cur - 'A']) == letter_masks[cur - 'A']) {
			mask = mask | multi_mask;
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
	
	if ((shorter->mask & longer->mask) != shorter->mask) {
		return 0;
	}
	
	if (shorter->multi == 0) return 1;
	
	// printf("searching %.6s in %.6s\n",shorter->raw,longer->raw);
	
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