package mathengine;

import java.util.ArrayList;



public class StaticFormula {
	public Node RHS;
	public String Result;
	public ArrayList<String> Reqs;
	public String formulaStr;
	
	public StaticFormula(String str)
	{
		formulaStr = str;
		Result = str.substring(0, str.indexOf('='));
		Result = Result.trim();
		RHS = Parser.parse(str.substring(str.indexOf('=')+1).trim());
		Reqs = RHS.getLeaves();
	}
	
	public StaticFormula(StaticFormula f)
	{
		formulaStr = f.formulaStr;
		Result = f.Result;
		RHS = Parser.parse(f.RHS.infix());
		Reqs = new ArrayList<String>(f.Reqs);
	}
	
}
