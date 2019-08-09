import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.ControlFlow.FriendHomePage;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.PictureDispose.PictureFind;
import java.awt.*;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestMain {
    private static final String REGEX = "\\bcat\\b";
    private static final String INPUT =
            "cat cat cat cattie cat";

    /** 添加配置文件 */
    public static Properties prop = new Properties();

    public static void main( String args[] ) throws Exception{
        String URL = "123a";
        System.out.println(Pattern.compile("\\d*").matcher(URL).matches()?Integer.valueOf(URL):0);
        //CommonalityMethod.logout();

        /*String[] str = new String[]{"barry", "@ndg7991p", "young723"};
        Set<String> set = new HashSet<>();
        set.add("barry");
        set.add("@ndg7991p");
        set.add("young723");
        set.add("@flg9831k");
        CommonalityMethod.addFriend(set);*/
    }

    public static void t2(){

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT); // 获取 matcher 对象
        int count = 0;

        while(m.find()) {
            count++;
            System.out.println("Match number "+count);
            System.out.println("start(): "+m.start());
            System.out.println("end(): "+m.end());
            System.out.println("group(): "+m.group());
        }
    }
}
