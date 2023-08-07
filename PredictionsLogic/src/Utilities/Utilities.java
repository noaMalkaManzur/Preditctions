package Utilities;

import Entities.*;

import java.util.Random;

public class Utilities
{

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,_-.() ";
    public static final int MAXSTRLEN = 50;
    public static final int MINSTRLEN = 10;
    public static Object randomInit(EntityProperty entProp)
    {
        Random MyRand = new Random();
        Object MyRandVal = new Object();
        Range MyRange = null;

        if(entProp.getRange() != null)
        {
             MyRange = entProp.getRange();
        }
        switch (entProp.getType())
        {
            case eDecimal:
                MyRandVal = MyRand.nextInt((int)(MyRange.getRangeTo()-MyRange.getRangeFrom()+1))+ 10;
                return MyRandVal;
            case eFloat:
                MyRange = entProp.getRange();
                MyRandVal = MyRand.nextDouble() * MyRange.getRangeFrom()+(MyRange.getRangeTo()-MyRange.getRangeFrom());
                return MyRandVal;
            case eBool:
                MyRandVal = MyRand.nextBoolean();
                return MyRandVal;
            case eString:
                int MyStrLen = MyRand.nextInt(MAXSTRLEN-MINSTRLEN +1)+10;
                MyRandVal = generateRandomString(MyStrLen);
                return MyRandVal;
            default:
                return null;
        }
    }
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static double random(int range) {
        Random random = new Random();
        return random.nextDouble() * range;
    }



}

