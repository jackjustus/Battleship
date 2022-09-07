public class translate {

    private String [] letters;

    //class for translating letter to number index

    public translate(){
        letters = new String[]{"A","B","C","D","E","F","G","H","I","J"};
    }

    public int convert(String s){
        for(int i = 0; i < letters.length; i++){
            if(letters[i].equals(s))
                return (i+1);
        }
        System.out.println("ERROR. Letter not available.");
        return -1;
    }
}
