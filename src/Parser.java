import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Musa Ali
 * Date: 7/5/14
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    public static Node parse(String given)
    {
        Node node = new Node();
        node.child = new ArrayList<Node>();
        given = bracketless(given);
        node.data = new String(given);
        boolean found = false;
        int br = 0;
        node.right = null;
        node.left = null;
        for(int i = 0; i  < given.length() && !found; i++)
        {
            char ch = given.charAt(i);
            if( ch == '(' )
                br++;
            else if(ch == ')')
                br--;
            else if(br == 0)
            {
                switch(ch)
                {
                    case '+':
                        if(i==0)
                            break;
                        String temp1 = given.substring(0, i);
                        String temp2 = given.substring(i+1, given.length());
                        node.left = parse(temp1);
                        node.right = parse(temp2);
                        char arr[] = new char[1];
                        arr[0] = ch;
                        node.data = new String(arr);
                        found = true;
                        break;
                    case '-':
                        if(i==0)
                            break;
                        temp1 = given.substring(0, i);
                        temp2 = given.substring(i+1, given.length());
                        node.left = parse(temp1);

                        node.right = parse("-"+temp2);
                        arr = new char[1];
                        arr[0] = '+';
                        node.data = new String(arr);
                        found = true;
                        break;
                }
            }
        }

        for(int i = 0; i  < given.length() && !found; i++)
        {
            char ch = given.charAt(i);
            if( ch == '(' )
                br++;
            else if(ch == ')')
                br--;
            else if(br == 0)
            {
                switch(ch)
                {
                    case '*':
                        String temp1 = given.substring(0, i);
                        String temp2 = given.substring(i+1, given.length());
                        node.left = parse(temp1);
                        node.right = parse(temp2);
                        char arr[] = new char[1];
                        arr[0] = ch;
                        node.data = new String(arr);
                        found = true;
                        break;
                }
            }
        }


        for(int i = 0; i  < given.length() && !found; i++)
        {
            char ch = given.charAt(i);
            if( ch == '(' )
                br++;
            else if(ch == ')')
                br--;
            else if(br == 0)
            {
                switch(ch)
                {
                    case '/':
                        String temp1 = given.substring(0, i);
                        String temp2 = given.substring(i+1, given.length());
                        node.left = parse(temp1);
                        node.right = parse(temp2);
                        char arr[] = new char[1];
                        arr[0] = ch;
                        node.data = new String(arr);
                        found = true;
                        break;
                }
            }
        }

        for(int i = 0; i  < given.length() && !found; i++)
        {
            char ch = given.charAt(i);
            if( ch == '(' )
                br++;
            else if(ch == ')')
                br--;
            else if(br == 0)
            {
                switch(ch)
                {
                    case '^':
                        String temp1 = given.substring(0, i);
                        String temp2 = given.substring(i+1, given.length());
                        node.left = parse(temp1);
                        node.right = parse(temp2);
                        char arr[] = new char[1];
                        arr[0] = ch;
                        node.data = new String(arr);
                        found = true;
                        break;
                }
            }
        }

        if(node.data.charAt(0) == '-')
        {
            String temp = node.data.substring(1);
            node.right = parse(temp);
            node.data = "-";
        }

        for(int i = 0; i  < given.length() && !found; i++)
        {
            char ch = given.charAt(i);
            if( ch == '(' )
                br++;
            else if(ch == ')')
                br--;
            else if(br == 0)
            {
                switch(ch)
                {
                    case ':':
                        node.isFunc = true;
                        String temp1 = given.substring(0, i);
                        String temp2 = given.substring(i+1, given.length());
                        node.data = temp1;
                        ArrayList<String> parameters = parameterSplit(bracketless(temp2));
                        for(String str: parameters)
                        {
                            node.child.add(parse(str));
                        }
                        found = true;
                        break;
                }
            }
        }

        condense(node);
        node.data = node.data.trim();
        return node;
    }

    static ArrayList<String> parameterSplit(String str)
    {
        ArrayList<String> arr = new ArrayList<String>();
        int anchor = 0, br=0;
        for(int i = 0; i  < str.length(); i++)
        {
            char ch = str.charAt(i);
            if( ch == '(' )
                br++;
            else if(ch == ')')
                br--;
            else if(br == 0)
            {
                if(ch == ',')
                {
                    arr.add(str.substring(anchor, i));
                    anchor = i+1;
                }
            }
        }
        arr.add(str.substring(anchor));
        return arr;
    }

    static String bracketless(String s)
    {
        boolean neg = false;
        int b = 1, i;
        if(s.charAt(0) == '-' && s.charAt(1) == '(')
        {
            neg = true;
            i = 2;
        }
        else if( s.charAt(0) != '(')
            return s;
        else
            i = 1;

        for(; i< s.length() && b>0; i++)
        {
            if( s.charAt(i) == '(')
                b++;
            else if( s.charAt(i) == ')')
                b--;
        }

        if(i == s.length())
        {
            if(!neg)
                return s.substring(1, i-1);
            char[] temp = new char[s.length()-1];

            int x ,y;

            for(x = 0, y =2; y<i-1; x++, y++)
            {
                //System.out.println("got: "+s.charAt(y));
                if( s.charAt(y) == '-' )
                {
                    temp[x] = '+';
                }
                else if( s.charAt(y) == '+')
                {
                    temp[x] = '-';
                }
                else if(y == 2)
                {
                    temp[x++]= '-';
                    temp[x] = s.charAt(y);
                }
                else
                    temp[x] = s.charAt(y);
                //System.out.println("wrote: "+temp[x]);
            }
            return new String(temp, 0, x);
        }
        return s;
    }
    public static void condense(Node node)
    {
        if(node.condensed)			//return if it has been condensed before
            return;
        else
            node.condensed = true;

        if(node.data.charAt(0) != '&')		//exception for specials
        {

            Node temp = node;
            if(temp.left != null)		//if there exists something on the left, that'll be the first child.
                node.child.add(node.left);

            if(node.data.charAt(0) == '/' || node.data.charAt(0) == '^' && node.right != null)
                node.child.add(node.right);
            else
            {

                //ASSUMPTION: there won't be anything like 1/2/3
                //TODO: make it for (x/y)/(a/d)

                if(temp.right!=null)			//iterate through all node.right Node who have the same operator, and gather their nodes in child.
                {
                    while(temp.right.data.equals(node.data) )
                    {
                        temp = temp.right;			//keep moving node.right
                        node.child.add(temp.left);		//keep adding node.left Node to child
                    }
                    node.child.add(temp.right);				//when u reach last Node of this operator type. add the node.right to child
                }

                for( int i = 0; i < node.child.size();)		//iterate thorough the number of children
                {
                    Node n = node.child.get(i);              //get ith child
                    if(n.data.equals(node.data) )            //same operator
                    {
                        node.child.add(i,n.left);            //keep adding the same operator operands of node.left and node.right
                        node.child.add(i+1,n.right);
                        node.child.remove(i+2);
                    }
                    else
                        ++i;							//else move on
                }
            }
        }

        //condense each child of it
        for(Node n: node.child)
        {
            condense(n);
        }
    }
}
