package hadoop;

import java.util.StringTokenizer;

/**
 *
 * Created by yuanhao on 6/11/18.
 */
public class Test {

    public static void main(String[] args) {
        String line = "1979 23 23 2 43 24 25 26 26 26 26 25 26 25 ";
        String lasttoken = null;
        StringTokenizer s = new StringTokenizer(line,"	 ");
        String year = s.nextToken();
        System.out.println(year);

        while(s.hasMoreTokens())
        {
            lasttoken=s.nextToken();
        }
        System.out.println(lasttoken);
    }

}
