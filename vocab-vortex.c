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

#include <ctype.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "letter_mask.h"
#include "word_sort.h"

#define MAX_WORDS 20000

#define STATUS_OK       0
#define STATUS_NOFILE   1
#define STATUS_NOMALLOC 2
#define STATUS_NULLPTR  3
#define STATUS_NOTASCII 4

typedef int ecode;

ecode word_destroy(word* w) {
	ecode status = STATUS_OK;
	
	if (w == NULL) {
		return status;
	}
	
	// if (w->subwords != NULL) {
		// free(w->subwords);
	// }
	
	free(w);
	
	return status;
}

ecode word_create(word** w,const char* raw) {
	ecode status = STATUS_OK;
	
	int i = 0;
	letter_mask mask = empty_mask;
	
	if (raw == NULL) {
		status = STATUS_NULLPTR;
		return status;
	}
	
	if (*w == NULL) {
		*w = (word*)malloc(sizeof(word));
		
		if (*w == NULL) {
			status = STATUS_NOMALLOC;
			return status;
		}
		
		(*w)->invalid = 0;
		(*w)->multi = 0;
		(*w)->mask = empty_mask;
		(*w)->length = 0;
		memset((*w)->raw,0,sizeof (*w)->raw);
		memset((*w)->sorted,0,sizeof (*w)->sorted);
		// (*w)->subwords = NULL;
	}
	
	(*w)->length = strlen(raw);
	
	mask = letter_mask_mask(raw,(*w)->length);
	
	(*w)->invalid = (mask & invalid_mask) == invalid_mask;
	(*w)->multi = (mask & multi_mask) == multi_mask;
	(*w)->mask = mask;
	
	if ((*w)->length == 0 || (*w)->invalid) {
		// printf("invalid mask %x (%x)for %s\n",(*w)->mask,invalid_mask,raw);
		
		word_destroy(*w);
		(*w) = NULL;
		
		status = STATUS_NOTASCII;
		return status;
	}
	
	strncpy((*w)->raw,raw,(*w)->length);
	
	for(i = 0;i < (*w)->length;i++) {
		(*w)->raw[i] = toupper((*w)->raw[i]);
	}
	
	strncpy((*w)->sorted,(*w)->raw,(*w)->length);
	word_sort((*w)->sorted,(*w)->length);
	
	// (*w)->subwords = (word**)malloc(sizeof(word*) * 100);
	
	return status;
}

ecode word_subwords(word* dictionary[],int length,const word* w) {
	ecode status = STATUS_OK;
	
	int i = 0;
	
	if (dictionary == NULL) {
		status = STATUS_NULLPTR;
		return status;
	}
	
	
	for(i = 0;i < length;i++) {
		if (dictionary[i] != NULL) {
			if (letter_mask_subword(w,dictionary[i]) == 1) {
				// printf("%s has subword %s\n",w->raw,dictionary[i]->raw);
				// add to subwords
			}
		}
	}
	
	return status;
}

ecode load_dictionary(word* dictionary[],int length) {
	ecode status = STATUS_OK;
	
	const char* path = "/usr/share/dict/american-english";
	FILE* file = NULL;
	char* line = NULL;
	size_t len = 0;
	ssize_t read = 0;
	
	int i = 0;
	int l = -1;
	
	for(i = 0;i<length;i++) {
		dictionary[i] = NULL;
	}
	
	file = fopen(path,"r");
	
	if (file == NULL) {
		status = STATUS_NOFILE;
		return status;
	}
	
	i = 0;
	while (
		i < length
		&& (read = getline(
			&line,
			&len,
			file)) != -1
	) {
		if (line == NULL) {
			continue;
		}
		
		l = strlen(line);
		
		if (line[l - 1] == '\n') {
			l = l - 1;
			line[l] = '\0';
		}
		
		
		if (l < 3 || l > 6) continue;
		
		// printf("======\n");
		// printf("word %s (%d)\n",line,l);
		
		status = word_create(&dictionary[i],line);
		// if (dictionary[i] == NULL) printf("dictionary ref not set\n");
		if (status == STATUS_NOTASCII) {
			status = STATUS_OK;
			
			// printf("invalid characters in word %s\n",line);
		}
		else if (status == STATUS_OK) {
			i++;
		}
		else {
			printf("stopped loading at word %d: %s\n",i,line);
			break;
		}
	}
	
	if (file != NULL) {
		status = fclose(file);
		file = NULL;
	}
	
	if (line != NULL) {
		free(line);
		line = NULL;
	}
	
	printf("loaded %d words\n",i);
	
	return status;
}

ecode print_dictionary(word* dictionary[],int length) {
	ecode status = STATUS_OK;
	
	int i;
	
	for(i = 0;i<length;i++) {
		if (dictionary[i] != NULL && dictionary[i]->raw != NULL) {
			printf("%6d %.6s %.6s\n",i,dictionary[i]->raw,dictionary[i]->sorted);
		}
	}
	
	return status;
}

ecode unload_dictionary(word* dictionary[],int length) {
	ecode status = STATUS_OK;
	
	int i;
	
	for(i = 0;i<length;i++) {
		if (dictionary[i] != NULL) {
			free(dictionary[i]);
		}
	}
	
	return status;
}

int main(int argc,char* argv[]) {
	ecode status = STATUS_OK;
	
	word* dictionary[MAX_WORDS];
	int i = 0;
	
	status = load_dictionary(dictionary,MAX_WORDS);
	
	if (status != STATUS_OK) return unload_dictionary(dictionary,MAX_WORDS);
	
	// status = print_dictionary(dictionary,MAX_WORDS);
	
	for(i = 0; i < MAX_WORDS;i++) {
		if (
			dictionary[i] != NULL
			&& (dictionary[i]->length) == 6
		) {
			word_subwords(dictionary,MAX_WORDS,dictionary[i]);
			// printf("==========\n");
		}
	}
	
	printf("done [%d].\n",status);
	
	// int wordsize = sizeof(dictionary[0]->);
	printf("word is %u bytes\n",sizeof(word));
	// printf("word->length is %u bytes\n",sizeof(dictionary[0]->length));
	// printf("word->mask is %u bytes\n",sizeof(dictionary[0]->mask));
	printf("word->raw is %u bytes\n",sizeof(dictionary[0]->raw));
	printf("word->sorted is %u bytes\n",sizeof(dictionary[0]->sorted));
	
	return unload_dictionary(dictionary,MAX_WORDS);
}
