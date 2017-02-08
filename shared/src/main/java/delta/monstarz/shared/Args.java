package delta.monstarz.shared;

/**
 * Created by oliphaun on 2/8/17.
 */

public class Args {
    private String str1;
    private String str2;

    public String getStr1() {
        return str1;
    }
    public void setStr1(String str1) {
        this.str1 = str1;
    }
    public String getStr2() {
        return str2;
    }
    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public Args(String arg1, String arg2) {
        str1 = arg1;
        str2 = arg2;
    }
}
