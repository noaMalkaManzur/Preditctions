package Utilities;

public class Enums
{
    public static enum eTypes
    {
        eDecimal,
        eFloat,
        eBool,
        eString;

        @Override
        public String toString() {
            switch (this)
            {
                case eDecimal:
                    return "Decimal";
                case eFloat:
                    return "Float";
                case eBool:
                    return "Boolean";
                case eString:
                    return "String";
                default:
                    return super.toString();
            }
        }
    }
}
