public class translate {
    // All parts of this class are static so you don't have to create an object to use it
    static private String [] letters = new String[]{"A","B","C","D","E","F","G","H","I","J"};;

    //class for translating letter to number index

    public translate(){

    }

    public static int convert(String s){
        for(int i = 0; i < letters.length; i++){
            if(letters[i].equals(s))
                return (i+1);
        }
        System.out.println("ERROR. Letter not available.");
        return -1;
    }
}
