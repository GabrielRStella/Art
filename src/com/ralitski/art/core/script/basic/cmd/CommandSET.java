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

public class CommandSET implements Command {

	@Override
	public String name() {
		return "SET";
	}

	//if you like big if statements and array accessing, clap your hands
	@Override
	public CommandExecutor init(ProgramData data, String arguments) {
		String[] a = arguments.split(" ");
		ValueList vars = data.getObject("$VARIABLES");
		if(a.length == 3) {
			//x y color
			String x = a[0];
			String y = a[1];
			String color = a[2];
			Variable xVar = getVar(x, vars);
			Variable yVar = getVar(y, vars);
			Variable colorVar = getVar(color, vars);
			return new CommandSETExecutor(xVar, yVar, colorVar);
		} else if(a.length == 5) {
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
			VariableColor colorVar = new VariableColor();
			colorVar.r = rVar;
			colorVar.g = gVar;
			colorVar.b = bVar;
			return new CommandSETExecutor(xVar, yVar, colorVar);
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
			VariableColorAlpha colorVar = new VariableColorAlpha();
			colorVar.r = rVar;
			colorVar.g = gVar;
			colorVar.b = bVar;
			colorVar.a = aVar;
			return new CommandSETExecutor(xVar, yVar, colorVar);
		} else throw new SyntaxException("SET must have 3, 5 or 6 arguments: x, y, color, or x, y, r, g, b, or x, y, r, g, b, a");
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
	
	//used to store compound colors as a single color
	private class VariableColor implements Variable {
		
		private Variable r;
		private Variable g;
		private Variable b;

		@Override
		public float get(ValueList obj) {
			return (r.get(obj) * 256 * 256) + (g.get(obj) * 256) + b.get(obj);
		}
		
		public String toString() {
			return r + " " + g + " " + b;
		}
	}
	
	private class VariableColorAlpha implements Variable {
		
		private Variable r;
		private Variable g;
		private Variable b;
		private Variable a;

		@Override
		public float get(ValueList obj) {
			return (a.get(obj) * 256 * 256 * 256) + (r.get(obj) * 256 * 256) + (g.get(obj) * 256) + b.get(obj);
		}
		
		public String toString() {
			return r + " " + g + " " + b + " " + a;
		}
	}
	
}
