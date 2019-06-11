package com.LineAssistant.ControlFlow;
import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.ParamStatic;
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
    Point oneFriend = new Point(84, 115);

    /** 接口方法，方法中转 */
    void callMain();

    /**
     *  程序启动前的准备工作
     *  1.Line主窗口移动到左上角
     *  2.复制需要评论的数据
     */
    static void initCopy() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "line");
        WinDef.RECT lineRect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, lineRect);
        User32.INSTANCE.MoveWindow(hwnd, 0, 0, 472, 712, true);
    }

    /**
     *  打开Line软件
     */
    static void openLine(){
        List<Point> list = PictureFind.getResult(ParamStatic.url + "\\open.png");
        System.out.println(list.size());

        /** 鼠标移动到line程序上并运行 */
        int x = list.get(0).x;
        int y = list.get(0).y;
        ParamStatic.robot.mouseMove(x, y);
        MouseDispose.leftDoublicClick();
    }

    /**
     *  Line登陆
     * @param key_value  key_value[0] 帐号    key_value[1] 密码
     */
    static void login(String[] key_value){
        /** 获取登录的位置 */
        List<Point> list = PictureFind.getResult(ParamStatic.url + "\\login.png");
        /** 鼠标移动到邮箱地址栏并触发单击事件 */
        int x = list.get(0).x;
        int y = list.get(0).y - 72;
        ParamStatic.robot.mouseMove(x, y);
        MouseDispose.leftClick();

        /** 把帐号复制到剪切板中 */
        ParamStatic.clip.setContents(new StringSelection(key_value[0]), null);

        /** 对当前输入框中的数据进行全选删除 */
        KeyboardDispose.delectData();

        /** 把当前剪切板中的数据复制到输入框中 */
        KeyboardDispose.pasteData();

        /** 鼠标移动到密码地址栏并触发单击事件 */
        y = list.get(0).y - 36;
        ParamStatic.robot.mouseMove(x, y);
        MouseDispose.leftClick();

        /** 把密码复制到剪切板中 */
        ParamStatic.clip.setContents(new StringSelection(key_value[1]), null);

        /** 对当前输入框中的数据进行全选删除 */
        KeyboardDispose.delectData();

        /** 把当前剪切板中的数据复制到输入框中 */
        KeyboardDispose.pasteData();

        /** 鼠标移动到登陆按钮并触发单击事件 */
        y = list.get(0).y;
        ParamStatic.robot.mouseMove(x, y);
        MouseDispose.leftClick();
    }

    /**
     * Line登陆成功后先关闭所有列表，然后再单独把好友列表打开
     */
    static void listInit() {
        List<Point> newF = PictureFind.getResult(ParamStatic.url + "\\newFriend.png");
        if( newF.size() > 0 ){
            ParamStatic.robot.mouseMove(newF.get(0).x, newF.get(0).y);
            MouseDispose.leftClick();
            listInit();
        }

        List<Point> list = PictureFind.getResult(ParamStatic.url + "\\close.png");
        if(list.size() > 0 ){
            ParamStatic.robot.mouseMove(list.get(0).x, list.get(0).y);
            MouseDispose.leftClick();
            listInit();
        }
        else {
            List<Point> lists = PictureFind.getResult(ParamStatic.url + "\\openFriend.png");
            ParamStatic.robot.mouseMove(lists.get(0).x, lists.get(0).y);
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
        ParamStatic.robot.mouseMove((rect.right - rect.left)/2 + rect.left, rect.bottom/2);

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
            ParamStatic.robot.mouseWheel(quantity);
            CommonalityMethod.sleep(500);
            return isExist(url, quantity, ++next);
        }
    }




    /**
     *  进入好友主页并回复信息
     *  循环好友列表并为好友的好友说说回复评论
     * @param method  多态，根据对象调用对应方法
     * @throws Exception
     */
    static boolean openFriendHomePage(CommonalityMethod method) {
        /*List<Point> coordinates = PictureFind.getResult(ParamStatic.url + "\\homepage1.png");
        if(coordinates.size() != 1) {
            coordinates = PictureFind.getResult(ParamStatic.url + "\\homepage2.png");
        }
        if(coordinates.size() != 1) {
            coordinates = PictureFind.getResult(ParamStatic.url + "\\homepage3.png");
        }
        if(coordinates.size() != 1) {
            coordinates = PictureFind.getResult(ParamStatic.url + "\\homepage4.png");
        }
        if(coordinates.size() == 0) {
            return false;
        }
        else if(coordinates.size() == 1){
            ParamStatic.robot.mouseMove(coordinates.get(0).x, coordinates.get(0).y);
            MouseDispose.leftClick();

            WinDef.HWND hwnd = User32.INSTANCE.FindWindowEx(null, null, "Qt5QWindowIcon", null);
            char[] ar = new char[50];
            User32.INSTANCE.GetWindowText(hwnd, ar, 50);
            System.out.println(new String(ar) + "___________"  +  ++ParamStatic.sumFriend);

            CommonalityMethod.sleep(500);

            ParamStatic.friendName = CommonalityMethod.mouseMove();

            ParamStatic.logger.info("目前正在回复" + Chart.user + "第" + ++ParamStatic.sumFriend + "个好友的朋友圈，已经回复了" + ParamStatic.sum  + "条说说");

            if(PictureFind.getResult(ParamStatic.url + "\\blank.png").size() == 1 || PictureFind.getResult(ParamStatic.url + "\\delect.png").size() == 1){
                ParamStatic.logger.info("--------------------------------------好友空白主页，自动跳过！--------------------------------------");
                KeyboardDispose.esc(); return true;
            }

            *//** 进入好友主页中心点 *//*
            method.callMain();

            KeyboardDispose.esc();
        }*/
        return true;
    }


    /**
     *
     * 循环好友列表前对列表进行通一格式调配(当只有两种列表时)
     * @param method  多态，根据对象调用对应方法
     * @param x 鼠标x坐标
     * @param y 鼠标y坐标
     * @param next  循环列表前先访问好友的个数
     * @param wheel 鼠标滚动轴滚动次数
     */
    static void initFriend(CommonalityMethod method, int x, int y, int next, int wheel){
        for(int n = 0; x < next; n++){
            ParamStatic.robot.mouseMove(x, y + 54 * n);
            MouseDispose.leftClick();
            CommonalityMethod.openFriendHomePage(method);
        }
        ParamStatic.robot.mouseMove(x, y);
        ParamStatic.robot.mouseWheel(wheel);
    }

    /**
     * 好友列表循环到末尾
     * @param method  多态，根据对象调用对应方法
     * @param method
     */
    static void behindFriend(CommonalityMethod method, int x, int y) {
        for(int number = 0; number < 8; number++) {
            ParamStatic.robot.mouseMove(x, y + 54 * number);
            MouseDispose.leftClick();
            if(openFriendHomePage(method)) {
                continue;
            }
            else {
                Point point = MouseInfo.getPointerInfo().getLocation();
                ParamStatic.robot.mouseMove(point.x, point.y + 30);
            }
            Point point = MouseInfo.getPointerInfo().getLocation();
            ParamStatic.robot.mouseMove(point.x, point.y + 54);
        }
    }


    /**
     *  当前帐号结束广告投放，开始登出
     */
    static void logout() {
        List<Point> points = PictureFind.getResult("logout1.1.png");
        if(!(points.size() > 0)){
            points = PictureFind.getResult("logout1.png");
        }
        ParamStatic.robot.mouseMove(points.get(0).x, points.get(0).y);
        MouseDispose.leftClick();
        points = PictureFind.getResult("logout1.1.png");
        ParamStatic.robot.mouseMove(points.get(0).x, points.get(0).y);
        MouseDispose.leftClick();
    }
}
