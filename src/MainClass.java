
import mathEngine.Engine;
import mathEngine.ParsedExpression;
import mathEngine.Parser;

import java.util.ArrayList;
import java.util.HashMap;

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
		
//		ParsedExpression expression = Parser.parseExpression("derivate:(x^2,x)");
//        System.out.println(expression.toString());

		ArrayList<String> kw = new ArrayList<String>();
		//r.learnDynamicFormula("derivate:(&exp:(u),x)", "&exp:(u)*&derivate:(u,x)", kw);
		//r.learnDynamicFormula("derivate:(&ln:(u),x)", "(1/u)*&derivate:(u,x)", kw);
		r.learnDynamicFormula("a+0", "a", kw);
		r.learnDynamicFormula("a*1", "a", kw);
		r.learnDynamicFormula("a*0", "0", kw);
		r.learnDynamicFormula("derivate:(u^a,x)", "a*u^(a-1)*derivate:(u,x)", kw);
        r.learnDynamicFormula("derivate:(u+v,x)", "derivate:(u,x)+derivate:(v,x)", kw);
        r.learnDynamicFormula("derivate:(u+v+w,x)", "derivate:(u,x)+derivate:(v,x)+derivate:(w,x)", kw);
		r.learnDynamicFormula("derivate:(u*v,x)", "derivate:(u,x)*v+u*derivate:(v,x)", kw);
		r.learnDynamicFormula("derivate:(#,x)", "0", kw);
		
		r.learnDynamicFormula("derivate:(x,x)", "1", kw);
		r.learnDynamicFormula("x^1", "x", kw);

        String result = r.evaluateExpression("derivate:(3*x^3+2*x^2+4*x,x)");

        System.out.println("Result: " + result);
                             /*

		r.learnStaticFormula("m = &derivate:(fx, x)");
		r.learnStaticFormula("b = m*(a-x)+y");
		
		HashMap<String, String> GivenValues = new HashMap<String, String>();
		GivenValues.put("fx", "x^2+1");
		GivenValues.put("x1", "2");
		GivenValues.put("y1", "5");
		GivenValues.put("x", "x");
		
		ArrayList<String> ToFind = new ArrayList<String>();
		ToFind.add("m");
		
		ArrayList<String> ans = r.KnowledgeQuery(GivenValues, ToFind);
		
		ArrayList<String> st = new ArrayList<String>();

        st = r.getAllSteps();
		
		for(String str: st )
			System.out.println(str);
		
		GivenValues.clear();
		ToFind.clear();

		GivenValues.put("m", ans.get(0));
        GivenValues.put("x", "2");
        GivenValues.put("y", "5");
        GivenValues.put("a", "a");
		//r.learnStaticFormula("ans = "+ans.get(0));
		ToFind.add("b");
		
		ans = r.KnowledgeQuery(GivenValues, ToFind);
		
		
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
