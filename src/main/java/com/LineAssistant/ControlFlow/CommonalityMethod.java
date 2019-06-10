package com.LineAssistant.ControlFlow;
import com.GUI.Chart;
import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.Main;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.PictureDispose.PictureFind;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public interface CommonalityMethod {
    /** 直接设置第一个好友图标中心点 */
    Point oneFriend = new Point(84, 118);

    /** 接口方法，方法中转 */
    void callMain();

    /**
     *  程序启动前的准备工作
     *  1.Line主窗口移动到左上角
     *  2.复制需要评论的数据
     */
    static void initCopy() {
        listInit();
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "line");
        WinDef.RECT lineRect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, lineRect);
        User32.INSTANCE.MoveWindow(hwnd, 0, 0, 472, 712, true);

        System.out.println(Chart.textArea.getText());
        Main.clip.setContents(new StringSelection(Chart.textArea.getText()), null);
    }

    /**
     *  Line登陆
     * @param user  帐号
     * @param password  密码
     */
    static void login(String user, String password){
        List<Point> list = PictureFind.getResult(Main.url + "\\open.png");
        System.out.println(list.size());

        /** 鼠标移动到line程序上并运行 */
        int x = list.get(0).x;
        int y = list.get(0).y;
        Main.robot.mouseMove(x, y);
        MouseDispose.leftDoublicClick();

        CommonalityMethod.sleep(2000);
        /** 获取登录的位置 */
        list = PictureFind.getResult(Main.url + "\\login.png");
        /** 鼠标移动到邮箱地址栏并触发单击事件 */
        x = list.get(0).x;
        y = list.get(0).y - 72;
        Main.robot.mouseMove(x, y);
        MouseDispose.leftClick();

        /** 把帐号复制到剪切板中 */
        Main.clip.setContents(new StringSelection(user), null);

        /** 对当前输入框中的数据进行全选删除 */
        KeyboardDispose.delectData();

        /** 把当前剪切板中的数据复制到输入框中 */
        KeyboardDispose.pasteData();

        /** 鼠标移动到密码地址栏并触发单击事件 */
        y = list.get(0).y - 36;
        Main.robot.mouseMove(x, y);
        MouseDispose.leftClick();

        /** 把密码复制到剪切板中 */
        Main.clip.setContents(new StringSelection(password), null);

        /** 对当前输入框中的数据进行全选删除 */
        KeyboardDispose.delectData();

        /** 把当前剪切板中的数据复制到输入框中 */
        KeyboardDispose.pasteData();

        /** 鼠标移动到登陆按钮并触发单击事件 */
        y = list.get(0).y;
        Main.robot.mouseMove(x, y);
        MouseDispose.leftClick();
    }

    /**
     * Line登陆成功后先关闭所有列表，然后再单独把好友列表打开
     */
    static void listInit() {
        List<Point> newF = PictureFind.getResult(Main.url + "\\newFriend.png");
        if( newF.size() > 0 ){
            Main.robot.mouseMove(newF.get(0).x, newF.get(0).y);
            MouseDispose.leftClick();
            listInit();
        }

        List<Point> list = PictureFind.getResult(Main.url + "\\close.png");
        if(list.size() > 0 ){
            Main.robot.mouseMove(list.get(0).x, list.get(0).y);
            MouseDispose.leftClick();
            listInit();
        }
        else {
            List<Point> lists = PictureFind.getResult(Main.url + "\\openFriend.png");
            Main.robot.mouseMove(lists.get(0).x, lists.get(0).y);
            MouseDispose.leftClick();
        }
    }

    /**
     * 根据当前窗口大小来判断是不是好友的好友主页窗口
     * @return
     */
    static boolean lineHWNDNumber() {
        sleep(500);
        WinDef.HWND hwnd = User32.INSTANCE.FindWindowEx(null, null, "Qt5QWindowIcon", null);
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);

        if(rect.bottom  - rect.top == 272 && rect.right - rect.left ==  213) { return true; }
        return false;
    }

    /**
     * 自动移动到当前Line最上层窗口中心点并返回当前窗口标题
     * @return
     */
    static String mouseMove() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindowEx(null, null, "Qt5QWindowIcon", null);
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        Main.robot.mouseMove((rect.right - rect.left)/2 + rect.left, rect.bottom/2);

        char[] by = new char[100];
        User32.INSTANCE.GetWindowText(hwnd, by, 1024);
        return new String(by);
    }

    /**
     * 设置线程停顿时间
     * @param time
     */
    static void sleep(long time){
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException intE){
            System.out.println(intE.getMessage());
        }
    }




    /**
     *  判断当前图标是否在屏幕中出现
     * @param url   图标地址
     * @param quantity   当图标未出现时，滚动滚轮数量
     * @return  返回第一个图标中心点坐标
     */
    static Point isExist(String url, int quantity, int next){
        List<Point> exists = PictureFind.getResult(url);
        if( exists.size() > 0 ) {
            return exists.get(0);
        }
        else {
            if( next >= 5){ return null;}
            Main.robot.mouseWheel(quantity);
            CommonalityMethod.sleep(500);
            return isExist(url, quantity, ++next);
        }
    }


    /**
     *  循环好友列表
     * @param method  多态，根据对象调用对应方法
     */
    static void circulationFriendList(CommonalityMethod method) {
        List<Point> list =  PictureFind.getResult(Main.url + "\\numberList.png");
        if( list.size() == 1 ) { initFriend_2(method); }
        else if( list.size() == 2 ) { initFriend_3(method); }

        /** 为轮循好友列表第滚动一次就添加一次快照，用来判断好友列表是否轮循结束 */
        List<BufferedImage> images = new ArrayList<>();

        /** 为好友列表添加第一次快照 */
        images.add(Main.robot.createScreenCapture(new Rectangle(55,123, 472, 712)));

        while ( true ) {

            /** 每14个好友一次轮循 */
            for(int y = 0; y < 14; y++) {
                if(y == 13) { break; }
                CommonalityMethod.sleep(500);
                Main.robot.mouseMove(oneFriend.x, oneFriend.y + y/3*10 + y%3*54);
                CommonalityMethod.sleep(500);

                /** 每3个好友滚动一次 */
                if(0 == y%3 && y != 0) {
                    images.add(Main.robot.createScreenCapture(new Rectangle(55,123, 472, 712)));
                    /** 判断好友列表最后一张快照和倒数第二张快照是否全等，如果全等代表好友列表轮循到最后，不能滚动 */
                    if(PictureFind.isImageCompare(images.get(images.size()-1), images.get(images.size()-2))){
                        System.out.println("正式退出好友列表循环！！！");
                        behindFriend(method, oneFriend.x, oneFriend.y + y/3*10 + 3*54);
                        return;
                    }

                    Main.robot.mouseWheel(1);
                    CommonalityMethod.sleep(500);
                }
                MouseDispose.leftClick();

                while(true){
                    if(openFriendHomePage(method)){
                        break;
                    }
                    else {
                        Point point = MouseInfo.getPointerInfo().getLocation();
                        oneFriend.y += 30;
                        Main.robot.mouseMove(point.x, point.y + y/3*10 + 30);
                        MouseDispose.leftClick();
                    }
                }
            }
        }
    }

    /**
     *  进入好友主页并回复信息
     *  循环好友列表并为好友的好友说说回复评论
     * @param method  多态，根据对象调用对应方法
     * @throws Exception
     */
    static boolean openFriendHomePage(CommonalityMethod method) {
        List<Point> coordinates = PictureFind.getResult(Main.url + "\\homepage1.png");
        if(coordinates.size() != 1) {
            coordinates = PictureFind.getResult(Main.url + "\\homepage2.png");
        }
        if(coordinates.size() != 1) {
            coordinates = PictureFind.getResult(Main.url + "\\homepage3.png");
        }
        if(coordinates.size() != 1) {
            coordinates = PictureFind.getResult(Main.url + "\\homepage4.png");
        }
        if(coordinates.size() == 0) {
            return false;
        }
        else if(coordinates.size() == 1){
            Main.robot.mouseMove(coordinates.get(0).x, coordinates.get(0).y);
            MouseDispose.leftClick();
            CommonalityMethod.sleep(500);

            FriendControlFlow.sumFriend++;

            Main.logger.info("目前正在回复" + Chart.user + "第" + FriendControlFlow.sumFriend + "个好友的朋友圈，已经回复了" + FriendControlFlow.sum  + "条说说");

            if(PictureFind.getResult(Main.url + "\\blank.png").size() == 1 || PictureFind.getResult(Main.url + "\\delect.png").size() == 1){
                Main.logger.info("--------------------------------------好友空白主页，自动跳过！--------------------------------------");
                KeyboardDispose.esc(); return true;
            }

            /** 进入好友主页中心点并记录当前好友的名称 */
            FriendControlFlow.friendName = CommonalityMethod.mouseMove();

            method.callMain();

            KeyboardDispose.esc();
        }
        return true;
    }

    /**
     * 循环好友列表前对列表进行通一格式调配(当只有两种列表时)
     * @param method  多态，根据对象调用对应方法
     * @param method
     */
    static void initFriend_2(CommonalityMethod method){
        for(int x = 0; x < 5; x++){
            Main.robot.mouseMove(85, 150 + 54 * x);
            MouseDispose.leftClick();
            openFriendHomePage(method);
            System.out.println(MouseInfo.getPointerInfo().getLocation().x + "----------" + MouseInfo.getPointerInfo().getLocation().y);
        }
        Main.robot.mouseMove(85, 150);
        Main.robot.mouseWheel(2);
    }
    static void initFriend_3(CommonalityMethod method){
        for(int x = 9; x < 10; x++){
            Main.robot.mouseMove(85, 180 + 54 * x);
            MouseDispose.leftClick();
            openFriendHomePage(method);
            System.out.println(MouseInfo.getPointerInfo().getLocation().x + "----------" + MouseInfo.getPointerInfo().getLocation().y);
        }
        Main.robot.mouseMove(85, 180);
        Main.robot.mouseWheel(4);
        sleep(500);
    }

    /**
     * 好友列表循环到末尾
     * @param method  多态，根据对象调用对应方法
     * @param method
     */
    static void behindFriend(CommonalityMethod method, int x, int y) {
        for(int number = 0; number < 8; number++) {
            Main.robot.mouseMove(x, y + 54 * number);
            MouseDispose.leftClick();
            if(openFriendHomePage(method)) {
                continue;
            }
            else {
                Point point = MouseInfo.getPointerInfo().getLocation();
                Main.robot.mouseMove(point.x, point.y + 30);
            }
            Point point = MouseInfo.getPointerInfo().getLocation();
            Main.robot.mouseMove(point.x, point.y + 54);
        }
    }

    static void formerlyComment(){
        Pattern pat = Pattern.compile("");
    }
}
