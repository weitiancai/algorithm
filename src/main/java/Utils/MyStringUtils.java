package Utils;

public class MyStringUtils {

    public static boolean equals(String a,String b) {
        if (a == b) {
            return true;
        } else {
            if (a != null && b != null && a.length() == b.length()) {
                char[] charArray = a.toCharArray();
                char[] charArray2 = a.toCharArray();
                int i = 0;
                while(i-->0){
                    if(charArray[i] != charArray2[i]){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
}
