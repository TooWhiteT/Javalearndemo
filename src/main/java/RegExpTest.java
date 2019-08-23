
public class RegExpTest {
    public static void main(String[] args) {
        String testText = "1064690854";
        String rex = "^\\d{5,12}$";
        String s = "1B41110A0A0A0A080307";//8.37
        String ss = "Printer001 ( "+"112221131"+" ) ";
        System.out.println(s.substring(7,s.length()-5));
        System.out.println(ss);
        System.out.println(testText.matches(rex));
    }
}
