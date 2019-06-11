package com.LineAssistant;

import com.GUI.LoggerJFrame;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParamStatic {
    /** 机器人对象，用于控制鼠标和键盘的各种操作 */
    public static Robot robot = null;

    /** 系统剪切板，用于控制数据的输出 */
    public static Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

    /** 设置图标位置 */
    public static String url = System.getProperty("user.dir") + "\\image";

    /** 添加配置文件 */
    public static Properties prop = new Properties();

    /** 配置日志 */
    public static Logger logger = Logger.getLogger(ParamStatic.class);

    /** 记录分享的数据数目 */
    public static int sum = 0;

    /** 记录分享的好友数目 */
    public static int sumFriend = 0;

    /** 记录当前好友名称 */
    public static String friendName = "";

    /** 保存所有循环过的好友 */
    public static List<String> list = new ArrayList<>();

    static{
        try{
            prop.load(new FileInputStream(System.getProperty("user.dir") + "\\parameter.properties"));
            robot = new Robot();
            logger.addAppender(new FileAppender(new PatternLayout("%d{yyyy MM dd HH:mm:ss}  %m %n"), System.getProperty("user.dir") + "\\logger.txt", true));
            logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy MM dd HH:mm:ss}  %m %n")));
            logger.addAppender(new LoggerJFrame());

        }catch (AWTException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException ioE) {
            System.out.println(ioE.getMessage());
        }
    }
    public static void main(String[] args) {
        setModule();
    }


    /**
     * ** 该功能已废弃 **
     *  获取电脑已启动程序的窗口句柄，再通过句柄获取当前程序下的子句柄
     */
    public static void setModule(){
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "line");
        System.out.println(User32.INSTANCE.ShowWindow(hwnd, 9));
        System.out.println(User32.INSTANCE.SetForegroundWindow(hwnd));

        //记录窗口的边框与屏幕顶端和左端的边距
        WinDef.RECT lineRect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, lineRect);

        User32.INSTANCE.MoveWindow(hwnd, 0, 0, 472, 712, true);
        System.out.println("top: " + lineRect.top);
        System.out.println("left: " + lineRect.left);
        System.out.println("bottom: " + lineRect.bottom);
        System.out.println("right: " + lineRect.right);
    }
}
