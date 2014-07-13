package mathengine;

/**
 * User: Musa Ali
 * Date: 7/6/14
 * Time: 8:46 PM
 */

public class ParsedExpression {

    Node rootNode;

    @Override
    public String toString()
    {
        return rootNode.infix();
    }



}
