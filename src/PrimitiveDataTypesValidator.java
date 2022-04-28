import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitiveDataTypesValidator {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[][] langs  = {
        {"int Constant","[+-]?[0-9]+"},
        {"float Constant","[0-9]*[.][0-9]"},
        {"char Constant","."},
        {"string Constant","\".*\""},
        {"identifier","(_)*[A-Za-z][_A-Za-z0-9]*"}
        };
        System.out.println("""
                DISCLAIMER!
                This is a sample program to match the typed word with the primitive data types.
                the program can classify the input as integer, character, float, string, and identifier.
                for string it is required to be wrapped around double quotes.
                """);
        while(true){
            System.out.print("input: ");
            String input = scanner.nextLine();
            for(String[] lang:langs){
                boolean matches = validateInput(lang[1],input);
                if (matches) {
                    System.out.println(lang[0]);
                }
            }
        }

    }
    public static boolean validateInput(String regex,String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
