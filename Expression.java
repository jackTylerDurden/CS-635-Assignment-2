import java.util.*;

public interface Expression{
    public static final String sinAbbreviaition = "€";
    public static final String logAbbreviaition = "¶";        
    public static final String numberRegex = "[+-]?\\d*\\.?\\d+";
    public static final Set<String> operators = new HashSet<String>(Arrays.asList("+","-","*","/",sinAbbreviaition,logAbbreviaition));
    public static final int firstAlphabetAscii = (int) ('a');
	// public static Set<String> = new HashSet<String>();
    public double evaluate(Context values);
    public String toString();
}