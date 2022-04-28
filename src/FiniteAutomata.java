import java.lang.reflect.Array;
import java.nio.file.LinkPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;

// to do : ask user for final states
public class FiniteAutomata {
    public Scanner scanner = new Scanner(System.in);
    public int numberOfStates;
    public char[] allowedChars;
    public int initialState;
    public int[] finalStates;
    public int[][] transitionTable;

    public static void main(String[] args) {

        FiniteAutomata finiteAutomata = new FiniteAutomata();
        System.out.print("Describe your automata: ");
        String automataDescription= finiteAutomata.scanner.nextLine();
        finiteAutomata.inputAutomata();
        System.out.println("input a word to validate");
        System.out.println(automataDescription);
        String input;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Enter a word: ");
            input = scanner.nextLine();
            System.out.println(finiteAutomata.validate(input));
        }
    }
    public FiniteAutomata( char[] inputChars, int initialState, int[] finalStates, int[][] transitionTable){
        this.numberOfStates=transitionTable.length;
        this.allowedChars = inputChars;
        this.initialState = initialState;
        this.finalStates = finalStates;
        this.transitionTable= transitionTable;
    }
    public FiniteAutomata(){}
    public boolean validate(String word){
        int currentState = initialState;
        for(char ch :word.toCharArray()){
            System.out.println("inside char loop.");
            boolean exists = false;
            //check if the character exist in allowedChars
            int i =0;
            for(;i<allowedChars.length;i++){
                if (ch==allowedChars[i]){
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
            System.out.println(finalState+" final state");
            if(currentState ==finalState){
                return true;
            }
        }
        System.out.println("current State:"+ currentState);
        return false;
    }
    public void inputAutomata(){

        int nStates = input("Number of states");
        int nFinalStates;
        String characters;
        numberOfStates = nStates;
        while(true){
            nFinalStates = input("Number of final states");
            // make sure nFinalSates is not greater than total number of states.
            if (nFinalStates<=nStates){
                break;
            }
            System.out.println("Number of final states can't be greater than total number of states! re-enter ");
        }
        while (true){
            characters = scanner.nextLine();
            if (!characters.equals("")) break;
            System.out.print("input set of allowed characters as a string: ");
        }

        allowedChars = characters.toCharArray();
        transitionTable = new int[nStates][allowedChars.length];

        // ask user for transitions at each state
        int transition;
        for(int i=0;i<nStates;i++){
            System.out.println("enter transitions for state "+i);
            for(int j = 0;j<allowedChars.length;j++){
                // the input value should always be less than number of states
                while(true){
                    System.out.print(i+" -- "+allowedChars[j]+" --> ");
                     transition = input(null);

                    // ensure that we have a valid transition.
                    if (transition >= 0 && transition<nStates) break;
                    System.out.println("invalid transition! program takes input form 0 to "+(nStates-1));
                }
                transitionTable[i][j] = transition;
            }
        }
        while(true){
            initialState = input("Start State");
            if (!(initialState<0 || initialState>nStates-1)) break;
            System.out.println("invalid transition! program has states form 0 to "+(nStates-1));
            //break;
        }
        finalStates = new int[nFinalStates];
        System.out.println("Enter final states");
        for(int i = 0;i<nFinalStates;i++){
            finalStates[i] = input("final state "+i);
        }
    }

    public int input(String msg){
        int n;
        while(true){
            try {
                if(msg!=null) System.out.print(msg+": ");
                n = scanner.nextInt();
            }
            catch (InputMismatchException inputMismatchException){
                scanner.nextLine();
                System.out.println("incorrect input!");
                continue;
            }
            break;
        }
        return n;
    }

    public static FiniteAutomata unionAutomatas(FiniteAutomata fa1,FiniteAutomata fa2){

        // task is to develop a transition table and set of final states for the resultant automata.
        int[][] transitionTable;
        ArrayList<Integer> tempFinalStates = new ArrayList<>();
        char[] allowedChars=fa1.allowedChars;
        ArrayList<int[]> tempTT = new ArrayList<>();
        ArrayList<int[]> unionStates = new ArrayList<>();

        // initializing z = x, y
        unionStates.add(new int[]{
                fa1.initialState,fa2.initialState
        });
        // check if null is allowed
        if (fa1.belongsToFinal(fa1.initialState) ||fa2.belongsToFinal(fa2.initialState))
            tempFinalStates.add(0);

        // both FAs have same set of allowed characters
        int CS = 0;
        int[] transitions;
        int[] state;

        while (unionStates.size() > CS){
            transitions = new int[allowedChars.length];
            state = unionStates.get(CS);
            for (int j = 0;j<allowedChars.length;j++){
                int indexOfAllowedChar = indexOf(allowedChars,allowedChars[j]);
                int[] newState = {
                        fa1.transitionTable[state[0]][indexOfAllowedChar],
                        fa2.transitionTable[state[1]][indexOfAllowedChar]
                };

                int i=0;
                // this loop checks if the state already exists
                for (;i<unionStates.size();i++){
                    int[] tempState = unionStates.get(i);
                    if(tempState[0]==newState[0] && tempState[1]==newState[1]){
                        break;
                    }
                }
                // i == unionStates.size() implies uniqueness, if true add the state also checks if it should be a final state as well.
                if (i==unionStates.size()){
                    unionStates.add(newState);
                    if (fa1.belongsToFinal(newState[0]) || fa2.belongsToFinal(newState[1])){
                        tempFinalStates.add(i);
                    }
                }
                transitions[j] = i;
            }
            tempTT.add(transitions);
            CS++;
        }
        transitionTable = new int[tempTT.size()][allowedChars.length];
        for(int s = 0; s < transitionTable.length; s++){
            transitionTable[s] = tempTT.get(s);
        }
        int[] finalStates = new int[tempFinalStates.size()];
        for(int k = 0;k<finalStates.length;k++){
                finalStates[k] = tempFinalStates.get(k);
        }

        return new FiniteAutomata(allowedChars,0,finalStates,transitionTable);
    }

    private static int indexOf(char[] allowedChars, char c){
        for(int i=0; i<allowedChars.length;i++){
            if(c==allowedChars[i]){
                return i;
            }
        }
        // -1 indicates the character is not allowed
        return -1;
    }

    public boolean belongsToFinal(int state){
        for(int finalState: finalStates) {
            if(state == finalState){
                return true;
            }
        }
        return false;
    }
}


