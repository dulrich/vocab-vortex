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
	1 <<  8, // A
	1 <<  9, // B
	1 << 10, // C
	1 << 11, // D
	1 << 12, // E
	1 << 13, // F
	1 << 14, // G
	1 << 15, // H
	1 << 16, // I
	1 << 17, // J
	1 << 18, // K
	1 << 19, // L
	1 << 20, // M
	1 << 21, // N
	1 << 22, // O
	1 << 23, // P
	1 << 30, // Q
	1 << 24, // R
	1 << 25, // S
	1 << 26, // T
	1 << 27, // U
	1 << 28, // V
	1 << 29, // W
	1 << 30, // X
	1 << 30, // Y
	1 << 30  // Z
};

const letter_mask empty_mask = 0;
const letter_mask invalid_mask = 1 << 31;

const letter_mask length_maskout = 0x000000FF;
const letter_mask mask_maskout = 0xFFFFFF00;

typedef struct Word {
	// int length;
	letter_mask mask_length;
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
	
	if (
		(shorter->mask_length & longer->mask_length & mask_maskout)
		!=
		(shorter->mask_length & mask_maskout)
	) {
		return 0;
	}
	
	for(i = 0;i < (shorter->mask_length & length_maskout);i++) {
		p = -1;
		
		for(j = c;j < (longer->mask_length & length_maskout);j++) {
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