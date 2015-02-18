package com.ralitski.art.core;

import java.awt.image.BufferedImage;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.ProgramData;
import com.ralitski.art.core.script.ScriptReader;
import com.ralitski.art.core.script.ValueList;
import com.ralitski.art.core.script.basic.BasicReader;
import com.ralitski.art.core.script.basic.cmd.CommandSET;

public class ArtCreatorScript implements ArtCreator {
	
	private String name;
	private String script;
	
	public ArtCreatorScript(String name, String script) {
		//get rid of path
		int i = name.lastIndexOf('.');
		name = name.substring(i + 1);
		
		this.name = name;
		this.script = script;
	}

	@Override
	public String getName() {
		return "$" + name;
	}

	@Override
	public BufferedImage drawImage(Settings s) {
		ScriptReader reader = new ScriptReader(new BasicReader());
		ProgramData data = new ProgramData();
		ArtCanvas canvas = new ArtCanvas(100, 100);
		ValueList sys = data.addObject("$SYS");
		sys.add("canvas", canvas);
		ValueList cmd = data.addObject("$COMMANDS");
		cmd.add("SET", new CommandSET());
		//TODO: add more commands
		Program p = reader.read(script, data);
		p.execute();
		return canvas.getImage();
	}
}
