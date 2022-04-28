import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        int initialState = 0;
        char[] inputChars = {'a','b'};

        // accepts null only
        int[] finalStatesNull = {0};
        int[][] transitionTableForNull = {
                {1,1},
                {1,1}
        };

        // 3 divides number of a's
        int[] finalStates1 ={3};
        int[][] transitionTable1 = {
                {1,0},
                {2,0},
                {3,2},
                {1,3}
        };

        // has b at the end.
        int[] finalStates2 ={1};
        int[][] transitionTable2 = {
                {0,1},
                {0,1},
        };

        FiniteAutomata finiteAutomata1 = new FiniteAutomata(inputChars,initialState,finalStates1,transitionTable1);
        FiniteAutomata finiteAutomata2 = new FiniteAutomata(inputChars,initialState,finalStates2,transitionTable2);
        FiniteAutomata finiteAutomataNull =  new FiniteAutomata(inputChars,initialState,finalStatesNull,transitionTableForNull);
        FiniteAutomata sumAutomata = FiniteAutomata.unionAutomatas(finiteAutomata1,finiteAutomata2);

        FiniteAutomata closure = FiniteAutomata.unionAutomatas(finiteAutomata1,finiteAutomataNull);

        Scanner scanner = new Scanner(System.in);
        System.out.println("either the length of a's is divisible by 3, or ends with b.");
        String input;
        while(true){
            System.out.print("Enter a word: ");
            input = scanner.nextLine();
            System.out.println(closure.validate(input));
        }
    }
}
