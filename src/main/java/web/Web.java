package web;
import util.Util;

public class Web {

    public Util util = new Util();

    public int getWebNumber()	{

        return util.GetUtilNumber() + 1;
    }
}
