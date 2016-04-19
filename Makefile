# Vocab Vortex: Vibrate verbs, vary vowels, numerate nouns, and alter adjectives in a brain-bending grammar gambit
# Copyright (C) 2015  David Ulrich
# 
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published
# by the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
# 
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

CC     = gcc
DEBUG  = -g
CFLAGS = -W -Wall -I.. -pthread

.PHONY: all c clean debug java memtest prodc runjava runc

all: | clean c java

c:
	$(CC) $(CFLAGS) $(DEBUG) -o vortex c/vocab-vortex.c

clean:
	rm -rf *.class
	rm -rf Main
	rm -rf vortex

debug:
	javac -g java/*.java

java:
	javac java/*.java

memtest:
	valgrind ./vortex

prodc:
	$(CC) $(CFLAGS) -O2 -o vortex c/vocab-vortex.c

runc:
	chmod +x vortex
	./vortex

runjava:
	cd java && java Main
