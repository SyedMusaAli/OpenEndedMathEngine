package mathEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Engine {
		ArrayList<DynamicFormula> KB;
		Node exp;
		ArrayList<DynamicFormula> AFL;
		ArrayList<String> steps;
		FileWriter1 fw;
		ArrayList<Goal> TopLevelGoals = new ArrayList<Goal>();
		HashMap<String, String> KnownValues;
		ArrayList<StaticFormula> KB2;

		public Engine()
		{
			KB = new ArrayList<DynamicFormula>();
			KB2 = new ArrayList<StaticFormula>();
			steps = new ArrayList<String>();
			AFL = new ArrayList<DynamicFormula>();
			KnownValues = new HashMap<String, String>();
			//fw=new FileWriter1("kb.txt");
		}


    public void learnDynamicFormula(String state1, String state2, ArrayList<String> kw)
    {
        KB.add(new DynamicFormula(state1.trim(), state2.trim(), kw));
    }

    public void learnDynamicFormula(String state1, String state2)
    {
        learnDynamicFormula(state1, state2, new ArrayList<String>());
    }
		
		public void learnStaticFormula(String str)
		{
			KB2.add(new StaticFormula(str));
		}
		
		String getKnown(String var)
		{
			return KnownValues.get(var);
		}

    ParsedExpression evaluateExpression(ParsedExpression expression, ArrayList<String> keywords)
    {
        ParsedExpression result = new ParsedExpression();
        result.rootNode = TransformationalQuery(expression.rootNode, keywords);
        return result;
    }

    public String evaluateExpression(String expression, ArrayList<String> keywords)
    {
        ParsedExpression exp = new ParsedExpression(expression);
        ParsedExpression result = new ParsedExpression();
        result.rootNode = TransformationalQuery(exp.rootNode, keywords);
        return result.toString();
    }

    public String evaluateExpression(String expression)
    {
        ParsedExpression exp = new ParsedExpression(expression);
        ParsedExpression result = new ParsedExpression();
        result.rootNode = TransformationalQuery(exp.rootNode, new ArrayList<String>());
        return result.toString();
    }

        Node TransformationalQuery(Node n, ArrayList<String> kw)
		{
			exp = new Node(n);
			steps.clear();
			
			do
			{
				AFL.clear();
				steps.add(exp.infix());
				int max = 0;

                for (DynamicFormula aKB : KB) {
                    if (aKB.q_simplify(exp))        //if it is applicable
                    {
                        int matches = 0;
                        for (String aKw : kw) {
                            for (int k = 0; k < aKB.keywords.size(); k++) {
                                if (aKw.equals(aKB.keywords.get(k))) {
                                    matches++;
                                }
                            }
                        }
                        //		System.out.println(KB.get(i).isRecursive());
                        if (aKB.isRecursive()) {
                            AFL.add(aKB);            //add recursive DynamicFormula at end (least priority)
                        } else if (matches >= max)        //if max keyword matches (best option)
                        {
                            AFL.add(0, aKB);        //insert at first position
                            max = matches;                //update max
                        } else
                            AFL.add(aKB);            //else insert at end
                    }
                }
				
				//apply best match DynamicFormula
				if(AFL.size() > 0)
				{
					exp = AFL.get(0).simplify(exp);
				//	System.out.println(exp.DisplayDepthFirst());
				}
				System.out.println(exp.infix());
			}while(AFL.size() > 0);
			
			ArithmeticSolver.simplifySolve(exp);
			return exp;
		}
		
		
		//TODO: make destructor which saves KB in file
		
		
		boolean usableFormulaStatic(StaticFormula f)		//can we use this DynamicFormula?
		{
			for(String requirement: f.Reqs)							//do we know all the reqs?
			{
				if(!KnownValues.containsKey(requirement))
					return false;
			}
			return true;
		}
		
		void replaceKnown(Node n)				//put in known values
		{
			if(KnownValues.containsKey(n.data))			//if the data is known,
			{
				//get index of the variable in Known, and replace with corresponding KnownValue
				Node t = Parser.parse(KnownValues.get(n.data));
				n.data = t.data;
				n.child.addAll(t.child);
			}
			for(Node a: n.child)
			{
				replaceKnown(a);
			}
		}
		
		void evaluateStaticFormula(StaticFormula f)			//evaluate StaticFormula to add result to known
		{
			if(!usableFormulaStatic(f))
			{
				return;
			}
			Node temp = new Node(f.RHS);		//make temporary copy of DynamicFormula
			replaceKnown(temp);					//put in known values

            //add result to our knowledge base
            ArrayList<String> kw = new ArrayList<String>();
            KnownValues.put(f.Result, TransformationalQuery(temp,kw).infix() );
		}
		
		
		
		public ArrayList<String> KnowledgeQuery(HashMap<String, String> GivenValues, ArrayList<String> ToFind)
		{
			
			//TODO: Make sure if not found, returns error
			
			steps.clear();
			KnownValues = GivenValues;
		//	fw.SaveKB(KB2.toArray().toString(), "kb2.txt");
	//		fw.SaveKB(KB.toArray().toString(), "kb1.txt");
			TopLevelGoals.clear();
			for(String str: ToFind)
			{
				TopLevelGoals.add( new Goal(str));
			}
			
			ArrayList<String> Answers = new ArrayList<String>();
			
			
			for(Goal g: TopLevelGoals)
			{
				Achieve(g);
				Answers.add( KnownValues.get(g.tag) );
			}

			return Answers;
		}
		
		int unknown(StaticFormula f)		//number of unknown reqs
		{
			int count = 0;
			for(String r: f.Reqs)
			{
				if(!KnownValues.containsKey(r))
					count++;
			}
			return count;
		}
		
		public ArrayList<String> getAllSteps()
		{
            ArrayList<String> solSteps = new ArrayList<String>();
			for(Goal g: TopLevelGoals)
			{
				PrintSteps(g,solSteps);
			}
            return solSteps;
		}
		
		public ArrayList<String> getsStepsToCompute(String variable)
		{
            ArrayList<String> solSteps = new ArrayList<String>();
			for(Goal g: TopLevelGoals)
			{
				if(variable.equals(g.tag))
					PrintSteps(g,solSteps);
			}
            return solSteps;
		}
		
		private void PrintSteps(Goal g, ArrayList<String> solSteps)
		{
			for( Goal g2: g.chosenPlan.subgoals)
			{
				PrintSteps(g2,solSteps);
			}
		//	System.out.println( g.chosenPlan.action.formulaStr);
			Node temp = new Node(g.chosenPlan.action.RHS );
			replaceKnown(temp);
			solSteps.add("= "+ temp.infix());
			solSteps.add("= "+ getKnown(g.tag));
			solSteps.add("");
		}
		
		
		
		void Achieve(Goal g)
		{
			
			for(StaticFormula f: KB2)
			{
				if(usableFormulaStatic(f) && f.Result.equals(g.tag))	//if perfect, apply
				{
					g.chosenPlan = new Plan(f);
					evaluateStaticFormula(f);
					g.achieved = true;
					return;
				}
				else if(f.Result.equals(g.tag))		//add applicable plans to APL
				{
					g.APL.add(new Plan(f));
				}
			}
			 
			for(int i =0 ; i<g.APL.size(); i++)		//sort by number of unknowns
			{
				int min = i;
				for(int j = i+1; j<g.APL.size();j++)
				{
					if(unknown(g.APL.get(min).action) > unknown(g.APL.get(j).action))
					{
						min = j;
					}
				}
				Plan temp = g.APL.get(min);			//swapping
				g.APL.set(min, g.APL.get(i));
				g.APL.set(i, temp);
			}
			
			//TODO: Create safety mechanism that insures against infinite looping
			
			int depth = 0;
			while(!g.achieved)			//try APL with increasing depth
			{
				for(Plan p : g.APL)
				{
					if(tryPlan(p, depth))
					{
						g.chosenPlan = p;
						g.achieved = true;
						break;
					}
				}
				depth++;
			}
		}
		
		boolean tryPlan(Plan p, int depth)		//try Plan within given depth
		{
			if(depth < 0)						
				return false;
			
			if(usableFormulaStatic(p.action))		//if all reqs are known, apply DynamicFormula
			{
				evaluateStaticFormula(p.action);
				return true;
			}
			else
			{
				if(p.subgoals.size() == 0)		//no subgoals, make em
				{
					for(String r: p.action.Reqs)	//for each unknown, a subgoal
					{
						if(!KnownValues.containsKey(r))
						{
							p.subgoals.add(new Goal(r));
						}
					}
				}
				
				int achieved = 0;					//counts how many achieved
				for(Goal g: p.subgoals)
				{
					if(g.achieved)
						achieved++;
					else if(trySubGoal(g, depth-1))		//try unachieved subgoals (with one less depth)
						achieved++;
				}
				
				if(achieved == p.subgoals.size())
				{
					evaluateStaticFormula(p.action);
					return true;
				}
				else
					return false;
			}
		}
		
		boolean trySubGoal(Goal g, int depth)
		{
			if(depth < 0)
				return false;
			
			if(g.APL.size() == 0)
			{
				for(StaticFormula f: KB2)
				{
					if(usableFormulaStatic(f) && f.Result.equals(g.tag))	//if perfect, apply
					{
						g.chosenPlan = new Plan(f);
						evaluateStaticFormula(f);
						g.achieved = true;
						return true;
					}
					else if(f.Result.equals(g.tag))		//add applicable plans to APL
					{
						g.APL.add(new Plan(f));
					}
				}
			}
			 
			for(int i =0 ; i<g.APL.size(); i++)		//sort by number of unknowns
			{
				int min = i;
				for(int j = i+1; j<g.APL.size();j++)
				{
					if(unknown(g.APL.get(min).action) > unknown(g.APL.get(j).action))
					{
						min = j;
					}
				}
				Plan temp = g.APL.get(min);			//swapping
				g.APL.set(min, g.APL.get(i));
				g.APL.set(i, temp);
			}
			
			for(Plan p : g.APL)
			{
				if(tryPlan(p, depth-1))
				{
					g.chosenPlan = p;
					g.achieved = true;
					break;
				}
			}

            return g.achieved;
		}
		
		public void rSaveKB(String list, String s) throws IOException
        {
			fw.SaveKB(list,s);
		}

    public void loadDynamicFormulaFile(String filename) throws IOException
    {
        FileInputStream obj_FileInputStream = new FileInputStream(filename);
        InputStreamReader in = new InputStreamReader(obj_FileInputStream);
        BufferedReader reader = new BufferedReader(in);

        String line;
        while( (line = reader.readLine()) != null)
        {
            String[] states = line.split("=>");
            learnDynamicFormula(states[0], states[1], new ArrayList<String>());
        }
    }

    public void loadStaticFormulaFile(String filename) throws IOException
    {
        FileInputStream obj_FileInputStream = new FileInputStream(filename);
        InputStreamReader in = new InputStreamReader(obj_FileInputStream);
        BufferedReader reader = new BufferedReader(in);

        String line;
        while( (line = reader.readLine()) != null)
        {
            learnStaticFormula(line);
        }
    }

}

