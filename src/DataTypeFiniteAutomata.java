import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTypeFiniteAutomata {
    private Scanner scanner = new Scanner(System.in);
    public int numberOfStates;
    public String[] allowedChars;
    public int initialState;
    public int[] finalStates;
    public int[][] transitionTable;
    public static void main(String[] args) {
        String[] allowedChars = {
                "[A-Za-z]",
                "[0-9]",
                "_"
        };

        int [][] TT = {
                {1,3,2},
                {1,1,2},
                {1,1,2},
                {3,3,3}
        };
        String[] allowedCharsFloat = {
                "[0-9]",
                "[.]",
                "+|-"
        };
        int [][] TTforFloat = {
                {1,2,4},
                {1,2,5},
                {3,5,5},
                {3,5,5},
                {1,2,5},
                {5,5,5}
        };
        int numberOfStateFloat = TTforFloat.length;
        int[] finalStateFloat = {3};
        int initialStateFloat = 0;
        DataTypeFiniteAutomata finiteAutomataForFloat = new DataTypeFiniteAutomata(
                numberOfStateFloat,
                allowedCharsFloat,
                initialStateFloat,
                finalStateFloat,
                TTforFloat);
        System.out.println("0.5, is it float:"+ finiteAutomataForFloat.validate("0.5"));
        System.out.println("2, is it float:"+ finiteAutomataForFloat.validate("2"));

        int numberOfStates = TT.length;
        int[] finalStates = {1};
        int initialState = 0;
        DataTypeFiniteAutomata finiteAutomataForIdentifier = new DataTypeFiniteAutomata(
                numberOfStates,
                allowedChars,
                initialState,
                finalStates,
                TT);

        System.out.println("_a1, is it a valid identifier: " +finiteAutomataForIdentifier.validate("_a1"));
        System.out.println("1b, is it a valid identifier: " +finiteAutomataForIdentifier.validate("1b"));
    }

    public DataTypeFiniteAutomata(int numberOfStates, String[] allowedChars, int initialState, int[] finalStates, int[][] transitionTable){
        this.numberOfStates=numberOfStates;
        this.allowedChars = allowedChars;
        this.initialState = initialState;
        this.finalStates = finalStates;
        this.transitionTable= transitionTable;
    }

    public boolean validate(String word) {
        int currentState = initialState;
        for(char ch :word.toCharArray()) {
            boolean exists = false;
            //check if the character exist in allowedChars
            int i = 0;
            for(;i<allowedChars.length;i++) {
                if(validateInput(allowedChars[i],String.valueOf(ch))) {
                    exists = true;
                    break;
                }
            }

            if(!exists) return false;
            // if word exits perform transition , "currentState" index points which state we are at, whereas "i" be the new state
            currentState = transitionTable[currentState][i];
        }
        //check if the current state is in set of final states
        for(int finalState: finalStates) {
            if(currentState ==finalState){
                return true;
            }
        }
        return false;
    }


    public static boolean validateInput(String regex,String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
