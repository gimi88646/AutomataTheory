public class CFG {
    public static void main(String[] args) {
        System.out.println(validate("abbc"));

    }
    private static int i =0 ;
    private static String word;

    // <S> => <X><Y><Z>
    // <X> => a<x>
    // <X> => b
    // <Y> => b<Y>
    // <Y> => c
    // <Z> => c<Z>
    // <Z> => null
    private static boolean S(){
        if (X())
            if (Y())
                if (Z())
                    return true;
        return false;
    }

    private static boolean X(){
        if (i==word.length()) return false;
        if (word.charAt(i)=='a'){
            i++;
            if (X())
                return true;
        }
        else if (word.charAt(i)=='b'){
            i++;
            return true;
        }
        return false;

    }
    private static boolean Y(){
        if (i==word.length()) return false;
        if (word.charAt(i)=='b'){
            i++;
            if (Y())
                return true;
        }
        else if (word.charAt(i)=='c'){
            i++;
            return true;
        }
        return false;

    }
    private static boolean Z(){
        if (i==word.length()) return true;
        if (word.charAt(i)=='c'){
            i++;
            if (Z())
                return true;
        }
        return false;
    }

    public static boolean validate(String word){
        i=0;
        CFG.word = word;
        if (S()){
            if (i==word.length())
                return true;
        }
        return false;
    }
}

