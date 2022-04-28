import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageIdentifier {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] languages = {
                "1*01*01*",
                "(0*10*10*)*|0*",
                "1*01*01*|0*10*10*10*",
                "((10101)+1*)*01*",
                "1*01*0(1|0)*",
                "1*|(1*01*)|(1*01*01*)"
        };
        System.out.println("""
                DISCLAIMER!
                This program takes some input and compares that input to the stored languages inside the program.
                """);

        while(true){
            System.out.print("input: ");
            String input = scanner.nextLine();
            System.out.println("Matches with language:");
            for(int i=0;i<languages.length; i++){
                if (validateInput(languages[i],input)){
                    System.out.print
                            ((i+1)+", ");
                }
            }
            System.out.println();
        }
    }
    public static boolean validateInput(String regex,String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}

