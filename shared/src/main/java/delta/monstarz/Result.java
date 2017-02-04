package delta.monstarz;

/**
 * Created by oliphaun on 2/3/17.
 */

public class Result {
    private String _str;
    private int _int;
    private NumberFormatException _err;
    private int _status = 0;

    public String getResultStr() {
        return _str;
    }
    public int getResultInt() {
        return _int;
    }
    public void throwResultErr() {
        String exception = "Exception in thread \"main\" "+ _err.getClass().getName() +": "+ _err.getMessage();
        System.out.println(exception);
    }
    public int status() {
        return _status;
    }

    public void setResultStr(String str) {
        _str = str;
        _status = 1;
    }
    public void setResultInt(int n) {
        _int = n;
        _status = 2;
    }
    public void setResultErr(NumberFormatException e) {
        _err = e;
        _status = 3;
    }

}