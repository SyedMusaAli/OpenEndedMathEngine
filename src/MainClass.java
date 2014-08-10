
import mathengine.Engine;
import mathengine.ParsedExpression;
import mathengine.Parser;

import java.util.ArrayList;

public class MainClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		Engine r = new Engine();
		//xmlFile xml=new xmlFile("kbtest.xml");
		//Excelfile xfile=new Excelfile();
		
	//	Node n =new Node("(&exp:(x*2.0))*((x*0.0)+2.0)");
		
		ParsedExpression expression = Parser.parseExpression("&derivate:(x^2,x)");

		ArrayList<String> kw = new ArrayList<String>();
		r.learnDynamicFormula("&derivate:(&exp:(u),x)", "&exp:(u)*&derivate:(u,x)", kw);
		r.learnDynamicFormula("&derivate:(&ln:(u),x)", "(1/u)*&derivate:(u,x)", kw);
		r.learnDynamicFormula("a+0", "a", kw);
		r.learnDynamicFormula("a*1", "a", kw);
		r.learnDynamicFormula("a*0", "0", kw);
		r.learnDynamicFormula("&derivate:(u^a,x)", "a*u^(a-1)*&derivate:(u,x)", kw);
		r.learnDynamicFormula("&derivate:(u+v,x)", "&derivate:(u,x)+&derivate:(v,x)", kw);
		r.learnDynamicFormula("&derivate:(u*v,x)", "&derivate:(u,x)*v+u*&derivate:(v,x)", kw);
		r.learnDynamicFormula("&derivate:(#,x)", "0", kw);
		
		r.learnDynamicFormula("&derivate:(x,x)", "1", kw);
		r.learnDynamicFormula("x^1", "x", kw);

        ParsedExpression result = r.TransformationalQuery(expression, kw);



		r.learnStaticFormula("m = &derivate:(fx, x)");
		r.learnStaticFormula("b = m*(a-x)+y");
		
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

        st = r.getAllSteps();
		
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
		//r.learnStaticFormula("ans = "+ans.get(0));
		ToFind.add("b");
		
		ans = r.KnowledgeQuery(GivenVars, GivenValues, ToFind);
		
		
		st = r.getAllSteps();
		
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
