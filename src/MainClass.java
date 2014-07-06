
import java.util.ArrayList;

public class MainClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		ProceduralReasoner r = ProceduralReasoner.instance();
		//xmlFile xml=new xmlFile("kbtest.xml");
		//Excelfile xfile=new Excelfile();
		
	//	Node n =new Node("(&exp:(x*2.0))*((x*0.0)+2.0)");
		
		Node n =Parser.parse("&derivate:(x^2,x)");
		ArrayList<String> kw = new ArrayList<String>();
		r.learnFormula("&derivate:(&exp:(u),x)", "&exp:(u)*&derivate:(u,x)", kw);
		r.learnFormula("&derivate:(&ln:(u),x)", "(1/u)*&derivate:(u,x)", kw);
		r.learnFormula("a+0","a", kw);
		r.learnFormula("a*1","a", kw);
		r.learnFormula("a*0","0", kw);
		r.learnFormula("&derivate:(u^a,x)", "a*u^(a-1)*&derivate:(u,x)", kw);
		r.learnFormula("&derivate:(u+v,x)", "&derivate:(u,x)+&derivate:(v,x)", kw);
		r.learnFormula("&derivate:(u*v,x)", "&derivate:(u,x)*v+u*&derivate:(v,x)", kw);
		r.learnFormula("&derivate:(#,x)", "0", kw);
		
		r.learnFormula("&derivate:(x,x)", "1", kw);
		r.learnFormula("x^1", "x", kw);
		
		r.learnFormulaStatic("m = &derivate:(fx, x)");
		r.learnFormulaStatic("b = m*(a-x)+y");
		
		ArrayList<String> GivenVars = new ArrayList<String>();
		GivenVars.add("fx");
		GivenVars.add("x1");
		GivenVars.add("y1");
		GivenVars.add("x");
		
		ArrayList<String> GivenValues = new ArrayList<String>();
		GivenValues.add("x^2+1");
		GivenValues.add("2");
		GivenValues.add("5");
		GivenValues.add("x");
		
		ArrayList<String> ToFind = new ArrayList<String>();
		ToFind.add("m");
		
		ArrayList<String> ans = r.KnowledgeQuery(GivenVars, GivenValues, ToFind);
		
		ArrayList<String> st = new ArrayList<String>();
		
		r.PrintSteps(st);
		
		for(String str: st )
			System.out.println(str);
		
		GivenVars.clear();
		GivenValues.clear();
		ToFind.clear();
		
		GivenVars.add("m");
		GivenValues.add(ans.get(0));
		GivenVars.add("x");
		GivenValues.add("2");
		GivenVars.add("y");
		GivenValues.add("5");
		GivenVars.add("a");
		GivenValues.add("a");
		//r.learnFormulaStatic("ans = "+ans.get(0));
		ToFind.add("b");
		
		ans = r.KnowledgeQuery(GivenVars, GivenValues, ToFind);
		
		
		r.PrintSteps(st);
		
		for(String str: st )
			System.out.println(str);
		
		//Node ans = r.TransformationalQuery(n, kw);

/*		ans.show_condensed();
		System.out.println(ans.infix());
		System.out.println(ans.DisplayDepthFirst());
	
		for(String str: r.steps)
		{
			System.out.println(str);
		}
		
		/*
		xml.xmlWrite(f.formulaStr, "DynamicFormula");
		xml.xmlWrite(f2.formulaStr, "DynamicFormula");
		xml.xSave();
		xml.xmlRead("kbtest.xml", "DynamicFormula");
		*/
		
	}

}
