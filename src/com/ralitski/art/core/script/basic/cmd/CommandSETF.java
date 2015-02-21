package com.ralitski.art.core.script.basic.cmd;

import com.ralitski.art.core.script.ProgramData;
import com.ralitski.art.core.script.ValueList;
import com.ralitski.art.core.script.basic.Command;
import com.ralitski.art.core.script.basic.CommandExecutor;
import com.ralitski.art.core.script.basic.var.Variable;
import com.ralitski.art.core.script.basic.var.VariableFixed;
import com.ralitski.art.core.script.basic.var.VariableReference;
import com.ralitski.art.core.script.exception.SyntaxException;
import com.ralitski.art.core.script.exception.VariableNotFoundException;

public class CommandSETF implements Command {

	@Override
	public String name() {
		return "SETF";
	}

	//if you like big if statements and array accessing, clap your hands
	@Override
	public CommandExecutor init(ProgramData data, String arguments) {
		String[] a = arguments.split(" ");
		ValueList vars = data.getObject("$VARIABLES");
		if(a.length == 5) {
			//x y r g b
			String x = a[0];
			String y = a[1];
			String r = a[2];
			String g = a[3];
			String b = a[4];
			Variable xVar = getVar(x, vars);
			Variable yVar = getVar(y, vars);
			Variable rVar = getVar(r, vars);
			Variable gVar = getVar(g, vars);
			Variable bVar = getVar(b, vars);
			return new CommandSETFExecutor(xVar, yVar, rVar, gVar, bVar);
		} else if(a.length == 6) {
			//x y r g b a
			String x = a[0];
			String y = a[1];
			String r = a[2];
			String g = a[3];
			String b = a[4];
			String alpha = a[5];
			Variable xVar = getVar(x, vars);
			Variable yVar = getVar(y, vars);
			Variable rVar = getVar(r, vars);
			Variable gVar = getVar(g, vars);
			Variable bVar = getVar(b, vars);
			Variable aVar = getVar(alpha, vars);
			return new CommandSETFExecutor(xVar, yVar, rVar, gVar, bVar, aVar);
		} else throw new SyntaxException("SETF must have 5 or 6 arguments: x, y, r, g, b, or x, y, r, g, b, a");
	}
	
	private Variable getVar(String var, ValueList vars) {
		try {
			float f = Float.parseFloat(var);
			return new VariableFixed(f);
		} catch (NumberFormatException e) {
			if(vars.has(var)) {
				return new VariableReference(var);
			} else {
				throw new VariableNotFoundException("Variable \"" + var + "\" not found");
			}
		}
	}
	
}
