package Android;

import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.PictureDispose.PictureFind;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class T1 {
    static int sum;
    public static void main(String[] args) throws Exception{
        /*int t1 = 519;
        System.out.println(t1%10 + "----------------" + t1/10);
        while(true){
            int i = t1%10;
            if(i == 0)break;
            System.out.println(i);
            t1 /= 10;
        }*/

        Point point = PictureFind.getResult(System.getProperty("user.dir") + "\\image\\NEW_Friend\\Android\\fiend.png").get(0);
        ParamStatic.robot.mouseMove(point.x - 100, point.y);
        MouseDispose.leftClick();


    }

    static void fiend(Point point, String phone) throws Exception{
        ParamStatic.robot.mouseMove(point.x, point.y);
        MouseDispose.leftClick();
        Thread.sleep(500);

        while(true) {
            if(PictureFind.getResult(System.getProperty("user.dir") + "\\image\\NEW_Friend\\Android\\wait.png").size() == 1) {
                Thread.sleep(500);
                continue;
            }
            break;
        }

        if(PictureFind.getResult(System.getProperty("user.dir") + "\\image\\NEW_Friend\\Android\\stop.png").size() == 1) {
            System.out.println(new SimpleDateFormat("yyyy MM dd HH:mm:ss  ").format(new Date()) + "__________网络中断，结束程序");
            System.exit(0);
        }

        if(!(PictureFind.getResult(System.getProperty("user.dir") + "\\image\\NEW_Friend\\Android\\NOUser.png").size() == 1)) {
            System.out.println(phone + "---------------------" + ++sum);
        }
        ParamStatic.robot.mouseMove(point.x - 100, point.y);
        MouseDispose.leftClick();
    }

    /**
     * 触发键盘事件
     * @param x 事件的代表数
     * @throws Exception
     */
    static void keyboard(int x) throws Exception{
        switch (x) {
            case 0:
                ParamStatic.robot.keyPress(KeyEvent.VK_0);
                ParamStatic.robot.keyRelease(KeyEvent.VK_0);
                break;
            case 1:
                ParamStatic.robot.keyPress(KeyEvent.VK_1);
                ParamStatic.robot.keyRelease(KeyEvent.VK_1);
                break;
            case 2:
                ParamStatic.robot.keyPress(KeyEvent.VK_2);
                ParamStatic.robot.keyRelease(KeyEvent.VK_2);
                break;
            case 3:
                ParamStatic.robot.keyPress(KeyEvent.VK_3);
                ParamStatic.robot.keyRelease(KeyEvent.VK_3);
                break;
            case 4:
                ParamStatic.robot.keyPress(KeyEvent.VK_4);
                ParamStatic.robot.keyRelease(KeyEvent.VK_4);
                break;
            case 5:
                ParamStatic.robot.keyPress(KeyEvent.VK_5);
                ParamStatic.robot.keyRelease(KeyEvent.VK_5);
                break;
            case 6:
                ParamStatic.robot.keyPress(KeyEvent.VK_6);
                ParamStatic.robot.keyRelease(KeyEvent.VK_6);
                break;
            case 7:
                ParamStatic.robot.keyPress(KeyEvent.VK_7);
                ParamStatic.robot.keyRelease(KeyEvent.VK_7);
                break;
            case 8:
                ParamStatic.robot.keyPress(KeyEvent.VK_8);
                ParamStatic.robot.keyRelease(KeyEvent.VK_8);
                break;
            case 9:
                ParamStatic.robot.keyPress(KeyEvent.VK_9);
                ParamStatic.robot.keyRelease(KeyEvent.VK_9);
                break;
            case 10:
                ParamStatic.robot.keyPress(KeyEvent.VK_BACK_SPACE);
                ParamStatic.robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                break;
             default:
                 Thread.sleep(500);
        }

    }



    public static void T2() throws Exception{
        ParamStatic.robot.mouseMove(334, 260);
        MouseDispose.leftClick();
        keyboard(10);
    }

    /**
     * 按顺序循环时，只能删除单个数字并且会立刻输入一个数字
     * @param point
     * @throws Exception
     */
    public static void T3(Point point) throws Exception{
        int[] phone = {9, 0, 6, 8, 6, 9, 6, 6, 0};
        for(int x:phone){
            keyboard(x);
        }

        for(int thousand = 6; thousand < 7; thousand++) {   //百
            keyboard(10);
            Thread.sleep(500);
            keyboard(thousand);
            for(int hundred = 6; hundred < 10; hundred++) { //十
                keyboard(10);
                Thread.sleep(500);
                keyboard(hundred);
                for(int ten = 0; ten < 10; ten++) {     //个
                    keyboard(10);
                    Thread.sleep(500);
                    keyboard(ten);
                    fiend(point, "906869" + thousand + hundred + ten);
                }
            }
        }
    }

    /**
     * 无序循环时，删除所有数字之后再添加一个完整的号码数字
     * @param point
     * @throws Exception
     */
    public static void T4(Point point)throws Exception{
        Set<Integer> set = new HashSet<>();
        for(int x = 906869660; x < 906869760; x++ ) {
            set.add(x);
        }
        for(int phone:set) {
            String ph = String.valueOf(phone);
            String[] sum = new String[ph.length()];
            for(int x = 0; x < sum.length; x++) {
                sum[x] = ph.charAt(x) + "";
                keyboard(10);
            }

            for(String str:sum){
                keyboard(Integer.valueOf(str));
            }
            fiend(point, ph);
        }
    }
}
