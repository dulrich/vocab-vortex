// http://stackoverflow.com/questions/2786899/fastest-sort-of-fixed-length-6-int-array

static inline void sort3_sorting_network_simple_swap(char* d) {
	#define min(x,y) (x < y ? x : y)
	#define max(x,y) (x < y ? y : x)
	#define SWAP(x,y) { const int a = min(d[x], d[y]); const int b = max(d[x], d[y]); d[x] = a; d[y] = b; }
	
	SWAP(1,2);
	SWAP(0,2);
	SWAP(0,1);
	
	#undef SWAP
	#undef min
	#undef max
}
static inline void sort4_sorting_network_simple_swap(char* d) {
	#define min(x,y) (x < y ? x : y)
	#define max(x,y) (x < y ? y : x)
	#define SWAP(x,y) { const int a = min(d[x], d[y]); const int b = max(d[x], d[y]); d[x] = a; d[y] = b; }
	
	SWAP(0,1);
	SWAP(2,3);
	SWAP(0,2);
	SWAP(1,3);
	SWAP(1,2);
	
	#undef SWAP
	#undef min
	#undef max
}

static inline void sort5_sorting_network_simple_swap(char* d) {
	#define min(x,y) (x < y ? x : y)
	#define max(x,y) (x < y ? y : x)
	#define SWAP(x,y) { const int a = min(d[x], d[y]); const int b = max(d[x], d[y]); d[x] = a; d[y] = b; }
	
	SWAP(0,1);
	SWAP(3,4);
	SWAP(2,4);
	SWAP(2,3);
	SWAP(0,3);
	SWAP(0,2);
	SWAP(1,4);
	SWAP(1,3);
	SWAP(1,2);
	
	#undef SWAP
	#undef min
	#undef max
}

static inline void sort6_sorting_network_simple_swap(char* d) {
	#define min(x,y) (x < y ? x : y)
	#define max(x,y) (x < y ? y : x)
	#define SWAP(x,y) { const int a = min(d[x], d[y]); const int b = max(d[x], d[y]); d[x] = a; d[y] = b; }
	
	SWAP(1,2);
	SWAP(4,5);
	SWAP(0,2);
	SWAP(3,5);
	SWAP(0,1);
	SWAP(3,4);
	SWAP(1,4);
	SWAP(0,3);
	SWAP(2,5);
	SWAP(1,3);
	SWAP(2,4);
	SWAP(2,3);
	
	#undef SWAP
	#undef min
	#undef max
}

static inline void word_sort(char* word,int len) {
	switch(len) {
		case 3: sort3_sorting_network_simple_swap(word); break;
		case 4: sort4_sorting_network_simple_swap(word); break;
		case 5: sort5_sorting_network_simple_swap(word); break;
		case 6: sort6_sorting_network_simple_swap(word); break;
	}
}