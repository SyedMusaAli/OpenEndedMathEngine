import java.util.ArrayList;



public class formula_static {
	public Node RHS;
	public String Result;
	public ArrayList<String> Reqs;
	public String formulaStr;
	
	public formula_static(String str)
	{
		formulaStr = str;
		Result = str.substring(0, str.indexOf('='));
		Result = Result.trim();
		RHS = new Node(str.substring(str.indexOf('=')+1).trim());
		Reqs = RHS.getLeaves();
	}
	
	public formula_static(formula_static f)
	{
		formulaStr = f.formulaStr;
		Result = f.Result;
		RHS = new Node(f.RHS.infix());
		Reqs = new ArrayList<String>(f.Reqs);
	}
	
}
