import java.util.ArrayList;

public class formula {
	Node state1, state2;
	Node match_node;
	
	String str;
	
	private ArrayList<Node> matches;
	private ArrayList<String> leafNodes;
	
	public ArrayList<String> keywords;
	
	formula(String st1, String st2)
	{
		state1 = new Node(st1);
		state2 = new Node(st2);
		keywords = new ArrayList<String>();
		
		str = st1+" = "+st2;
		
		state1.condense();
		state2.condense();
		
		leafNodes = new ArrayList<String>();
		matches = new ArrayList<Node>();
	}
	
	formula(String st1, String st2, ArrayList<String> kw)
	{
		state1 = new Node(st1);
		state2 = new Node(st2);
		keywords = kw;
		
		str = st1+" = "+st2;
		
		state1.condense();
		state2.condense();
		
		leafNodes = new ArrayList<String>();
		matches = new ArrayList<Node>();
	}
	
	public boolean q_simplify(Node exp)
	{
		exp.clearMarks();
		leafNodes.clear();
		matches.clear();
        return substruct(exp, state1);
	}
	
	public boolean q_expand(Node exp)
	{
        return substruct(exp, state2);
	}
	
	
	/*
	private Node match_returnnode(Node a, Node n)	//checks if n is the sub-structure of a
	{
		if(structMatch(a,n))	//if their structures are same, then n is the sub-structure of a
			return a;
		else
		{
			for(int i = 0; i<a.child.size(); ++i)	
			{
				Node temp=match_returnnode(a.child.get(i) , n);
				if(temp != null)  		//if a and n aren't equal, check substruct with all children of a 
					return  temp;
			}
			return null;
		}
	}*/
	 
	private ArrayList<Node> unused_simplify(Node a)
	{
		ArrayList<Node> ret= new ArrayList<Node>();
		
		for(int i = 0; i<a.child.size(); i++)
		{
			if(!a.child.get(i).marked)
				ret.add(a.child.get(i));
		}
		
		return ret;
		
	}
	
	 public Node simplify(Node n)
	 {
		 leafNodes.clear();
		 matches.clear();
		 n.clearMarks();
		 
		 if(!q_simplify(n))			//if it can not be simplified with this formula, return as it is
		 	return n;
		 	
		 Node target = match_node;		//get Node where the match is found
		 
		 if( target.equals(n) )			//if match was found at root
		 {
			 Node ret = new Node(state2);			//take other state
			 put_in_vals(ret);			//put the values of this variables in it
			 
			 ArrayList<Node> temp = unused_simplify(target);		//make a list of unused children
			 
			 if(state2.data.equals(n.data) || temp.size() == 0)			//if new root operator is same as old, merge em
			 {
                 for (Node aTemp : temp) {
                     ret.child.add(aTemp);            //make all unused children, the children of ret
                 }
				 
				 ret.simplifySolve();			//apply simplifySolve() before returning
				 ret.condense();
				 return ret;
			 }
			 else								//otherwise, make new root a children of the old
			 {
				 Node ret2 = new Node();
				 ret2.data = n.data;
				 ret2.child.add(ret);
                 for (Node aTemp : temp) {
                     ret2.child.add(aTemp);            //make all unused children, the children of ret
                 }
				 ret2.simplifySolve();			//apply simplifySolve() before returning
				 ret2.condense();
				 return ret2;
			 }
		 }
		 else
		 {
			 Node p_targ = find_parent(n, target);
			 for(int j = 0; j<p_targ.child.size(); j++)			//loop through parent's kids
			 {
				  if(p_targ.child.get(j).equals(target))	//find target
				  {
					  ArrayList<Node> temp = unused_simplify(target);
					  
					  p_targ.child.set(j, new Node(state2));
					  put_in_vals(p_targ.child.get(j));

                      for (Node aTemp : temp) {
                          p_targ.child.get(j).child.add(aTemp);
                      }
				      n.simplifySolve();
				      n.condense();
				      return n;
				  }
			 }
		 }
		 n.simplifySolve();
		 n.condense();
		 return n;
	 }
	 
	 
	 
	 public void put_in_vals(Node a)
	 {
		if( a.child.size() == 0 )
		{
			if(a.isConstant())		//you dont need to put values in constants
				return;
			for(int i = 0; i<leafNodes.size(); i++)
			{
				if( leafNodes.get(i).equals(a.data) )
				{
					a.data = matches.get(i).data;
					a.child = matches.get(i).child;	//TODO:make sure this works
				}
			}
		}
		else
		{
			boolean found;
			for(int i =0; i< a.child.size(); i++)
			{
				found = false;
				for(int j = 0; j< leafNodes.size(); j++)
				{
					if( a.child.get(i).data.equals( leafNodes.get(j) ))
					{
						a.child.set(i, matches.get(j));
						found = true;
					}
				}
				if(!found)
				{
					put_in_vals( a.child.get(i) );
				}
			}
		}
	 }
	 
	 public Node find_parent(Node a, Node n)
	 {
		 if(a.equals(n)) 
			 return null;
		 else
		 {
			 for(int i=0; i<a.child.size();i++)
			 {
				 if(a.child.get(i).equals(n))
					 return a;
				 else
				  {
					 Node ret = find_parent(a.child.get(i), n);
					 if( ret!= null )
						 return ret;
				  }
					  
			 }
		 }
		 return null;
	 }
	
	
	
	public boolean substruct(Node a, Node n)	//checks if n is the sub-structure of a
	{	
		if(structMatch(a, n))	//if their structures are same, then n is the sub-structure of a
		{
			match_node = a;
			return true;
		}
		else
		{
			for(int i = 0; i<a.child.size(); ++i)	
			{
				if( substruct(a.child.get(i) , n) )		//if a and n aren't equal, check substruct with all children of a 
					return true;
			}
			return false;
		}
	}
	
	private boolean structMatch(Node a, Node n)		//used on root of n. allows n to have less child then a
	{
		leafNodes.clear();
		matches.clear();
		if(n.isConstant())	//for constants, value must match
		{
			if(!a.isConstant())
				return false;
			if( Double.parseDouble(a.data) != Double.parseDouble(n.data) || a.child.size() < n.child.size() )		//if operator doesn't match, or if child sizes don't match. obviously not a structMatch
					return false;
		}
		else if(n.data.charAt(0) == '#')
		{
            return a.isConstant();
		}
		else if(n.data.charAt(0) == '&')
		{
			if(!n.data.equals(a.data))
				return false;
		}
		else if( !a.data.equals(n.data) || a.child.size() < n.child.size() )		//if operator doesn't match, or if child sizes don't match. obviously not a structMatch
		{
			return false;
		}
		
		if(n.isFunc)
		{
			return func_match(a,n);
		}
		
		//TODO: can a childless Node reach here??
		
		boolean[] used = new boolean[a.child.size()];		//array of checks against each child of a
		boolean found;
		
		for(int i = 0; i<n.child.size(); ++i)		//for every child of n, find a match in children of a
		{	
			if(n.child.get(i).child.size() == 0)		//no need to call structMatch on leaf nodes (leaves have no structure)
				continue;
			
			found = false; 
			for(int j = 0; j<a.child.size(); ++j)	//checks if child of n, found an unused match in child of a
			{
				if(!used[j])
				{
					if( ch_structmatch( a.child.get(j), n.child.get(i) ))	//TODO: Think later equal? sequal?
					{
						used[j] = true;
						a.child.get(j).marked = true;
						found = true;
						break;
					}
				}
			}
			if(!found)
				return false;
		}
		
		for(int i =0; i< n.child.size(); i++)		//loops through children, focusing on leaves
		{
			if( n.child.get(i).child.size() > 0)	//ignore non-leaves
				continue;
			
			found = false;
			for(int j=0; j<a.child.size();j++)		
			{
				if(n.child.get(i).child.size() == 0 && !used[j])	//match leaves with unused nodes in a 
				{
					boolean valid = false, exists = false;
					
					if(n.child.get(i).isConstant())		//for constants, values must match (no association)
					{
						double t;
						try
						{
							t = Double.parseDouble(a.child.get(j).data);
						}
						catch(NumberFormatException e)
						{
							continue;
						}
					
						if(t == Double.parseDouble(n.child.get(i).data))
						{
							used[j] = true;					//mark as used
							a.child.get(j).marked=true;
							found = true;
							break;
						}
					}
					else if(n.child.get(i).data.charAt(0) == '&')
					{
						if(n.child.get(i).data.equals(a.child.get(j).data))
						{
							used[j] = true;					//mark as used
							a.child.get(j).marked=true;
							found = true;
							break;
						}
					}
					else		//otherwise, check/make associations
					{
						for(int k =0; k< leafNodes.size(); k++)		//loop through list of associated leaves
						{
							if( leafNodes.get(k).equals( n.child.get(i).data ) )		//found current n Node in our list
							{
								exists = true;								//n's Node already has an association
								if( matches.get(k).equals(a.child.get(j)) )
								{
									valid = true;							//current 'a' Node same as previous association
									break;
								}
							}
						}
						
						if(!exists || valid)				//no previous association found
						{
							used[j] = true;					//mark as used
							a.child.get(j).marked=true;
							leafNodes.add(n.child.get(i).data);
							matches.add(a.child.get(j));		//add association to our list
							found = true;
							break;
						}
					}
				}
			}
			if(!found)	//match failed
			{
				for(int j = 0; j<a.child.size(); j++)		//delete associations formed recently because they are not applicable as we are not using this struct as a whole
				{
					if(used[j])			//for every used (we made associations with used)
					{
						//delete_assoc(a.child.get(j));	//TODO: make this function
					}
				}
				return false;
			}
		}
		return true;
	}
	
	private boolean func_match(Node a, Node n)
	{
		if(n.isConstant())	//for constants, value must match
		{
			if(!a.isConstant())
				return false;
			if( Double.parseDouble(a.data) != Double.parseDouble(n.data) || a.child.size() < n.child.size() )		//if operator doesn't match, or if child sizes don't match. obviously not a structMatch
					return false;
		}
		else if(n.data.charAt(0) == '#')
		{
            return a.isConstant();
		}
		else if(n.data.charAt(0) == '&')
		{
			if(!n.data.equals(a.data))
				return false;
		}
		if( !a.data.equals(n.data) || a.child.size() != n.child.size() )
		{
			return false;
		}
		
		
		//TODO: think: can a childless Node reach here??
		//current answer: No, cuz only structMatch calls this, and only on non-leaf nodes
		
		for(int i = 0; i<n.child.size(); ++i)		//loops through children, focusing on non-leaves
		{			
			if(n.child.get(i).child.size() == 0 )	//match leaves with unused nodes in a 
			{
				if(n.child.get(i).isConstant())	//for constants, value must match
				{
					if(!a.child.get(i).isConstant())
							return false;
					if( Double.parseDouble(a.child.get(i).data) != Double.parseDouble(n.child.get(i).data) || a.child.get(i).child.size() < n.child.get(i).child.size() )		//if operator doesn't match, or if child sizes don't match. obviously not a structMatch
							return false;
				}
				else if(n.child.get(i).data.charAt(0) == '#')
				{
                    return a.child.get(i).isConstant();
				}
				else if(n.child.get(i).data.charAt(0) == '&')
				{
					if(!n.child.get(i).data.equals(a.child.get(i).data))
						return false;
				}
				
				boolean valid = false, exists = false;
				for(int k =0; k< leafNodes.size(); k++)		//loop through list of associated leaves
				{
					if( leafNodes.get(k).equals( n.child.get(i).data ) )		//found current n Node in our list
					{
						exists = true;								//n's Node already has an association
						if( matches.get(k).equals(a.child.get(i)) )
						{
							valid = true;							//current 'a' Node same as previous association
							break;
						}
					}
				}
				
				if(!exists || valid)				//no previous association found
				{
					//used[j] = true;					//mark as used
					a.child.get(i).marked = true;
					leafNodes.add(n.child.get(i).data);
					matches.add(a.child.get(i));		//add association to our list
				}
				else
					return false;
			}	
			else if( !ch_structmatch(a.child.get(i), n.child.get(i) ))	//TODO: Think later equal? sequal?
			{
				return false;
			}
			else
			{
				a.child.get(i).marked = true;
			}
			
			
		}
		
		
		
		return true;
	}
	
	private boolean ch_structmatch(Node a, Node n)		//used on child of n. requires an exact struct match (children shud be equal)
	{
		if(n.isConstant())	//for constants, value must match
		{
			if(!a.isConstant())
				return false;
			if( Double.parseDouble(a.data) != Double.parseDouble(n.data))		//if operator doesn't match, or if child sizes don't match. obviously not a structMatch
					return false;
		}
		else if(n.data.charAt(0) == '#')
		{
            return a.isConstant();
		}
		else if(n.data.charAt(0) == '&')
		{
			if(!n.data.equals(a.data))
				return false;
		}
		else if( !a.data.equals(n.data) || a.child.size() != n.child.size() )
		{
			return false;
		}
		
		if(n.isFunc)
		{
			return func_match(a,n);
		}
		//TODO: think: can a childless Node reach here??
		//current answer: No, cuz only structMatch calls this, and only on non-leaf nodes
		
		boolean[] used = new boolean[a.child.size()];		//TODO: consider using an int index to store matches of n's children with a's children
		boolean found;
		
		for(int i = 0; i<n.child.size(); ++i)		//loops through children, focusing on non-leaves
		{			
			
			if(n.child.get(i).child.size() == 0)		//skip leaves for now. first match formula structures with exp structures
				continue;
			
			found = false;
			for(int j = 0; j<a.child.size(); ++j)		//match children of n with children of a
			{
				if(!used[j])
				{
					if( ch_structmatch(a.child.get(j), n.child.get(i) ))	//TODO: Think later equal? sequal?
					{
						used[j] = true;						//mark child of a as used
						a.child.get(j).marked = true;
						found = true;
						break;
					}
				}
			}
			if(!found)
				return false;
		}
		
		
		for(int i =0; i< n.child.size(); i++)		//loops through children, focusing on leaves
		{
			if( n.child.get(i).child.size() > 0)	//ignore non-leaves
				continue;
			
			found = false;
			for(int j=0; j<a.child.size();j++)		
			{
				if(n.child.get(i).child.size() == 0 && !used[j])	//match leaves with unused nodes in a 
				{
					boolean valid = false, exists = false;
					for(int k =0; k< leafNodes.size(); k++)		//loop through list of associated leaves
					{
						if( leafNodes.get(k).equals( n.child.get(i).data ) )		//found current n Node in our list
						{
							exists = true;								//n's Node already has an association
							if( matches.get(k).equals(a.child.get(j)) )
							{
								valid = true;							//current 'a' Node same as previous association
								break;
							}
						}
					}
					
					if(!exists || valid)				//no previous association found
					{
						used[j] = true;					//mark as used
						a.child.get(i).marked = true;
						leafNodes.add(n.child.get(i).data);
						matches.add(a.child.get(j));		//add association to our list
						found = true;
						break;
					}
				}
			}
			if(!found)	//match failed
			{
				for(int j = 0; j<a.child.size(); j++)		//delete associations formed recently because they are not applicable as we are not using this struct as a whole
				{
					if(used[j])			//for every used (we made associations with used)
					{
						//delete_assoc(a.child.get(j));	//TODO: make this function
					}
				}
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isRecursive()
	{
		state1.clearMarks();
		state2.clearMarks();
		leafNodes.clear();
		matches.clear();
        return substruct(state2, state1);
	}
}
