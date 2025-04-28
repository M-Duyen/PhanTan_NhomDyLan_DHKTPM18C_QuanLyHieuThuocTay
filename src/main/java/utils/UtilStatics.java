package utils;

public class UtilStatics {
    public static boolean awaiKey = false;
    public synchronized static void setAwaiKey(boolean awaiKeySet) {
        awaiKey = awaiKeySet;
    }
    public synchronized static boolean getAwaiKey(){
        return awaiKey;
    }
}
