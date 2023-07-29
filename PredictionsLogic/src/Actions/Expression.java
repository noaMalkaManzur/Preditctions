package Actions;

import java.util.logging.XMLFormatter;


public class Expression
{
    public String type;
    protected String Entity;
    public final String INCREASE = "Increase";

    public Expression() {
        /* C'tor flow:
        * parse XML for action type and entity
        * switchcase(type):
        *  call correct parser function by type.
        *
        *
        * */

        // same args as strings // keep xml file for a bit.
    }

    public void ParseExpression()
    {

    }
    public Increase ParseIncrease()
    {
        //parse the reset of the elements in XML
        // create Increase class instance with this Entity and args
        //return the new instance
        return null;
    }
    // parser for increase, Decrease, Multiply, whatevers


    public static Action getAction(XMLFormatter xml_file){
        //type = parseType
        //Entity = entity;
        /**switch (type){
            case type == INCREASE:
                return ParseIncrease();
        }*/
        return null;
    }
}
