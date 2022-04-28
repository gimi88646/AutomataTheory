import javax.swing.plaf.TreeUI;
import java.util.ArrayList;
import java.util.Stack;

public class NondeterministicFA {
    public char[] allowedChars;
    public int[] finalStates;
    public ArrayList<Integer>[][] transitionTable;
    public int initialState;
    private int currentState;
    private int pointer;
    private int CI ;
    private int ithRoute;
    private int[] top;
    private ArrayList<Integer> routes;
    private Stack<int[]> stack ;

    public static void main(String[] args) {
          char[] allowedChars = {'a','b'};
          int[] finalStates = {3};
        ArrayList<Integer>[][] TT = new ArrayList[4][2];
        for(int i =0;i< TT.length;i++){
            for (int j=0;j<TT[0].length;j++){
                TT[i][j]= new ArrayList<>();
            }
        }

        //words that end with double letter
        TT[0][0].add(0);
        TT[0][0].add(1);
        TT[0][1].add(0);
        TT[0][1].add(2);
        TT[1][0].add(3);
        TT[2][1].add(3);

        NondeterministicFA NFA = new NondeterministicFA(TT,finalStates,0,allowedChars);

        String word = "aabb";
        System.out.println("is aabb acceptable?"+ NFA.validate("aabb"));
        System.out.println("is aa acceptable?"+ NFA.validate("aa"));
        System.out.println("is bb acceptable?"+ NFA.validate("bb"));
        System.out.println("is ab acceptable?"+ NFA.validate("ab"));
    }

    NondeterministicFA(ArrayList<Integer>[][] transitionTable, int[] finalStates, int initialState,char[] allowedChars){
        this.transitionTable=transitionTable;
        this.finalStates=finalStates;
        this.initialState=initialState;
        this.allowedChars=allowedChars;

    }

    private boolean validate( String word){
        stack = new Stack<>();
        currentState=initialState;
        pointer=0;
        ithRoute=0;

        while (true){
            if(word.length()==pointer){

                if(isFinalState(currentState)) return true;
                if(stack.isEmpty()) return false;
                backTrack();
            }
            else {
                CI = indexOf(word.charAt(pointer));
                if(CI ==-1) return false;
                routes = transitionTable[currentState][CI];
                if (ithRoute == routes.size()){
                    if(stack.isEmpty()) return false;
                    backTrack();
                }
                else {
                    stack.push(new int[] {currentState,ithRoute});
                    currentState = routes.get(ithRoute);
                    pointer++;
                    ithRoute=0;
                }
            }
        }
    }
    private void backTrack(){
        top = stack.pop();
        currentState = top[0];
        ithRoute = top[1] + 1;
        pointer--;
    }
    private int indexOf(char c){
        for(int i=0; i<allowedChars.length;i++){
            if(c==allowedChars[i]){
                return i;
            }
        }
        // -1 indicates the character is not allowed
        return -1;
    }
    private boolean isFinalState(int currentState){
        for(int i : finalStates){
            if (i==currentState) return true;
        }
        return false;
    }
}

