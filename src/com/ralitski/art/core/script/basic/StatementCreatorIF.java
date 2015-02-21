package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.ProgramData;
import com.ralitski.art.core.script.Statement;
import com.ralitski.art.core.script.StatementCreator;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;
import com.ralitski.art.core.script.exception.SyntaxException;

public class StatementCreatorIF implements StatementCreator {

	@Override
	public Statement read(StringInputStream in, StatementReader reader, ProgramData data) {
		String stringIf = in.read(' ');
		if(stringIf.equals("IF")) {
			Condition c = ConditionReader.reader.read(in, reader, data);
			String stringThen = in.read(4);
			if(stringThen.equals("THEN")) {
				int avail = in.available();
				String s = in.read('\n');
				if(s.length() == 0) {
					//check the next lines until "ENDIF"
					String line;
					StatementBody body = new StatementBody();
					while(true) {
						//hmmmmmmmmmmmmmmmmmmmmmmm
						line = in.read('\n');
						if(line.contains("ENDIF")) {
							//end statement
						} else if(line.contains("ELSE")) {
							//new if child
						}
						body.addChild(reader.readOne(in, data));
					}
				} else {
					//single-line if
					in.back(avail - in.available());
					Statement body = reader.readOne(in, data);
					StatementIF statement = new StatementIF();
					statement.addChild(c, body);
					return statement;
				}
			} else {
				//no THEN
				throw new SyntaxException("IF must be followed by THEN");
			}
		}
		throw new SyntaxException("If statements must start with \"IF\"");
	}

	@Override
	public boolean identify(StringInputStream in, ProgramData data) {
		return in.read(' ').equals("IF");
	}

}
