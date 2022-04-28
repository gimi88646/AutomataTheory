import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
public class EmailAndPhoneValidator {

    public static void main(String[] args) {
        System.out.println("""
                DISCLAIMER! 
                this program is to verify that some user has provided his or her contact details and those details are valid,
                contact can either be a pakistani cell phone number or valid email \n
                """);
        System.out.print("Enter contact details(can either be email or contact number): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase(Locale.ROOT);
        String eitherEmailOrPhoneRe = "([a-z]+[0-9]*([.][a-z0-9]+)*[a-z0-9]*@[a-z]+[.](com|org|edu|net|io)([.]([a-z]{2}))?)|(00|[+])923[0-4]([0-9]{8})";
        System.out.println(validateInput(eitherEmailOrPhoneRe,input));
    }
    public static boolean validateInput(String regex,String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}

