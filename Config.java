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

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties mProp;
	
	public String setDefault(String name,String defaultValue) {
		if (this.mProp.getProperty(name) == null) {
			this.mProp.setProperty(name,defaultValue);
		}
		
		return this.mProp.getProperty(name);
	}
	
	public Properties load(String path) throws IOException {
		if (this.mProp == null) this.mProp = new Properties();
		
		InputStream propStream;
		propStream = getClass().getClassLoader().getResourceAsStream(path);
			
		if (propStream == null) throw new IOException("null config.properties");
		
		this.mProp.load(propStream);
		
		return this.mProp;
	}
	
	public Properties properties() {
		return this.mProp;
	}
	
	public Config() {
		this.mProp = new Properties();
	}
}
