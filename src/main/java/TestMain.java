import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.ControlFlow.FriendHomePage;
import com.LineAssistant.Main;
import com.LineAssistant.PictureDispose.PictureFind;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestMain {
    private static final String REGEX = "\\bcat\\b";
    private static final String INPUT =
            "cat cat cat cattie cat";

    public static void main( String args[] ){
        /*Pattern pat = Pattern.compile("^\\w+");
        Matcher m = pat.matcher("目前正在回复第7个好友的朋友圈，已经回复了3条说说");*/CommonalityMethod.circulationFriendList(new FriendHomePage());
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

    public static void t1(){
        FriendHomePage method = new FriendHomePage();

        List<Point> list =  PictureFind.getResult(Main.url + "\\numberList.png");
        if( list.size() == 1 ) { CommonalityMethod.initFriend_2(method); }
        else if( list.size() == 2 ) { CommonalityMethod.initFriend_3(method); }
    }
}
