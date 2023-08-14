package definition.property.api;

import exceptions.InvalidInputException;

public enum PropertyType {
    DECIMAL {

        public Integer convert(Object value) {
            if (!(value instanceof Integer)) {
                throw new IllegalArgumentException("value " + value + " is not of a DECIMAL type (expected Integer class)");
            }
            return (Integer) value;
        }
        public Integer parse(String value)
        {
            try {
                return Integer.parseInt(value);
            }
            catch (NumberFormatException ex)
            {
                throw new InvalidInputException("Input value " + value + " is not of a DECIMAL type (expected Integer class)");
            }
        }
    }, BOOLEAN {

        public Boolean convert(Object value) {
            if (!(value instanceof Boolean)) {
                throw new IllegalArgumentException("value " + value + " is not of a BOOLEAN type (expected Boolean class)");
            }
            return (Boolean) value;
        }
        public Boolean parse(String value)
        {
            try {
                return Boolean.parseBoolean(value);
            }
            catch (NumberFormatException ex)
            {
                throw new InvalidInputException("Input value " + value + " is not of a BOOLEAN type (expected Boolean class)");
            }
        }
    }, FLOAT {

        public Double convert(Object value) {
            if (!(value instanceof Double)) {
                throw new IllegalArgumentException("value " + value + " is not of a FLOAT type (expected Double class)");
            }
            return (Double) value;
        }
        public Double parse(String value)
        {
            try {
                return Double.parseDouble(value);
            }
            catch (NumberFormatException ex)
            {
                throw new InvalidInputException("Input value " + value + " is not of a FLOAT type (expected Double class)");
            }
        }
    }, STRING {

        public String convert(Object value) {
            if (!(value instanceof String)) {
                throw new IllegalArgumentException("value " + value + " is not of a STRING type (expected STRING class)");
            }
            return (String) value;
        }
        public String parse(String value)
        {
            return value;
        }
    };

    public abstract <T> T convert(Object value);
    public abstract <T> T parse(String value);

}