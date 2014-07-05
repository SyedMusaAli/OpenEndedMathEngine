/**
 * Created with IntelliJ IDEA.
 * User: Musa Ali
 * Date: 7/5/14
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionSolver {

    public double solve(NodeBean node)			//TODO: make for (x/y)/(a/d)
    {
        if(node.data.length() == 0)
            return 0;
            if(node.child.size() == 0)
            return Double.parseDouble(node.data);
        double ans, leftArgument, rightArgument;
        switch( node.data.charAt(0) )
        {
            case '+':
                ans= 0;
                for (NodeBean aChild : node.child) {
                    ans += solve(aChild);
                }
                return ans;
            case '-':
                return -1*solve(node.child.get(0));			//return the negative of child's value
            case '*':
                ans= 1;
                for (NodeBean aChild : node.child) {
                    ans *= solve(aChild);
                }
                return ans;
            case '/':
                leftArgument = solve(node.child.get(0));
                rightArgument = solve(node.child.get(1));
                return leftArgument / rightArgument;
            case '^':
                leftArgument = solve(node.child.get(0));
                rightArgument = solve(node.child.get(1));
                return Math.pow(leftArgument,rightArgument);
            default:
                return Double.parseDouble(node.data);

        }
    }

    public void simplifySolve(NodeBean node)			//simplifies all solvable child nodes
    {
        if(node.child.size() == 0)				//leaf nodes would be values, so no simplification needed
        {
            return;
        }
        else if(node.child.size() == 1 && !node.data.equals("-") && node.data.charAt(0) != '&')		//remove redundancies like +(y)  except when like -(y)
        {
            node.data = node.child.get(0).data;
            node.child = node.child.get(0).child;
        }

        double t;

        try
        {
            t = solve(node);	//try solving
        }
        catch(NumberFormatException e)				//if not solvable
        {
            for (NodeBean aChild : node.child) {
                simplifySolve(aChild);        //recursion
                solveFlexible(aChild);
            }
            solveFlexible(node);
            return;
        }

        node.data = String.valueOf(t);				//if solvable, replace data with value
        node.child.clear();							//empty child array
    }

    public void solveFlexible(NodeBean node)			//TODO: make for (x/y)/(a/d)
    {
        if(node.data.length() == 0)
            return;
        double ans;
        boolean modified = false;

        switch( node.data.charAt(0) )
        {
            case '+':
                ans= 0;
                for(int i = 0; i<node.child.size();)		//sum the "solve()" of all child (recursion)
                {
                    try
                    {
                        ans += solve(node.child.get(i));
                        node.child.remove(i);
                        modified = true;
                    }
                    catch(NumberFormatException e)				//if not solvable
                    {
                        i++;
                    }
                }
                if(modified)
                    node.child.add(new NodeBean( Double.toString(ans)));
            case '*':
                ans= 1;
                for(int i = 0; i<node.child.size();)		//sum the "solve()" of all child (recursion)
                {
                    try
                    {
                        ans *= solve(node.child.get(i));
                        node.child.remove(i);
                        modified = true;
                    }
                    catch(NumberFormatException e)				//if not solvable
                    {
                        i++;
                    }
                }
                if(modified)
                    node.child.add(new NodeBean( Double.toString(ans)));
        }
    }

}
