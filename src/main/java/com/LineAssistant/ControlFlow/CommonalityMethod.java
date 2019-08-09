package com.LineAssistant.ControlFlow;
import com.GUI.Chart;
import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.PictureDispose.PictureFind;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Set;


public interface CommonalityMethod {

    /** 接口方法，方法中转 */
    void callMain();

    /**
     *  打开Line软件
     */
    static void openLine(){
        List<Point> list = PictureFind.getResult(ParamStatic.url + "\\Login_Line\\open.png");
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
        List<Point> list = PictureFind.getResult(ParamStatic.url + "\\Login_Line\\login.png");
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
     *  程序启动前的准备工作
     *  1.Line主窗口移动到左上角
     *  2.复制需要评论的数据
     */
    static void initCopy() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "line");
        WinDef.RECT lineRect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, lineRect);
        User32.INSTANCE.MoveWindow(hwnd, 0, 0, 472, 712, true);

        /** 循环列表之前先关闭所有列表 */
        CommonalityMethod.listInit();
    }

    /**
     * Line登陆成功后先关闭所有列表，然后再单独把好友列表打开
     */
    static void listInit() {
        List<Point> fl = PictureFind.getResult(ParamStatic.url + "\\List_Close\\friendList.png");
        if( fl.size() > 0 ){
            ParamStatic.robot.mouseMove(fl.get(0).x, fl.get(0).y);
            MouseDispose.leftClick();
        }

        List<Point> newF = PictureFind.getResult(ParamStatic.url + "\\List_Close\\newFriend.png");
        if( newF.size() > 0 ){
            ParamStatic.robot.mouseMove(newF.get(0).x, newF.get(0).y);
            MouseDispose.leftClick();
            listInit();
        }

        List<Point> list = PictureFind.getResult(ParamStatic.url + "\\List_Close\\close.png");
        if(list.size() > 0 ){
            ParamStatic.robot.mouseMove(list.get(0).x, list.get(0).y);
            MouseDispose.leftClick();
            listInit();
        }
        else {
            List<Point> lists = PictureFind.getResult(ParamStatic.url + "\\List_Close\\openFriend.png");
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
     *  进入好友主页并回复信息
     *  循环好友列表并为好友的好友说说回复评论
     * @param method  多态，根据对象调用对应方法
     * @throws Exception
     */
    static boolean openFriendHomePage(CommonalityMethod method) {
        if(fiendClick(new String[] {ParamStatic.url + "\\Comment_Friend\\homepage1.png", ParamStatic.url + "\\Comment_Friend\\homepage2.png",
                ParamStatic.url + "\\Comment_Friend\\homepage3.png", ParamStatic.url + "\\Comment_Friend\\homepage4.png"}, true)) {

            WinDef.HWND hwnd = User32.INSTANCE.FindWindowEx(null, null, "Qt5QWindowIcon", null);
            char[] ar = new char[50];
            User32.INSTANCE.GetWindowText(hwnd, ar, 50);

            ParamStatic.friendName = CommonalityMethod.mouseMove();

            ParamStatic.logger.info("目前正在回复" + Chart.user + "第" + ++ParamStatic.sumFriend + "个好友的朋友圈，已经回复了" + ParamStatic.sum  + "条说说");
            System.out.println("-----" + new String(ar) + "-----"  +  ParamStatic.sumFriend);

            if(PictureFind.getResult(ParamStatic.url + "\\Comment_Friend\\blank.png").size() == 1 || PictureFind.getResult(ParamStatic.url + "\\Comment_Friend\\delect.png").size() == 1){
                ParamStatic.logger.info("好友空白主页，自动跳过！");
                KeyboardDispose.esc(); return true;
            }

            /** 进入好友主页中心点 */
            method.callMain();

            KeyboardDispose.esc();
        }
        else {
            return false;
        }
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
        for(int n = 0; n < next; n++){
            ParamStatic.robot.mouseMove(x, y + 54 * n);
            MouseDispose.leftClick();
            CommonalityMethod.openFriendHomePage(method);
        }
        ParamStatic.robot.mouseMove(x, y);
        ParamStatic.robot.mouseWheel(wheel);
    }

    /**
     * 跳过好友列表，默认是刚打开Line
     * @param next  循环列表前先访问好友的个数
     * @param sum   好友列表数量
     */
    static void skip(int next, int wheel, int sum) {
        ParamStatic.robot.mouseMove(100, 150);
        MouseDispose.leftClick();

        /** 对列表进行通一格式调配 */
        ParamStatic.robot.mouseWheel(wheel);

        int sumPX = (sum-next) * 54;
        for(int x = 0; x < sumPX/152; x++){
            ParamStatic.robot.mouseWheel(1);
            try { Thread.sleep(500); } catch (Exception e){ }
        }
        if(sumPX%152 > 76){ ParamStatic.robot.mouseWheel(1); }
        System.out.println("Y轴坐标地址----" + sumPX%152 + " + 150");
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
        List<Point> points = PictureFind.getResult(ParamStatic.url + "\\Logout_Line\\logout1.1.png");
        if(!(points.size() > 0)){
            points = PictureFind.getResult(ParamStatic.url + "\\Logout_Line\\logout1.png");
        }
        ParamStatic.robot.mouseMove(points.get(0).x, points.get(0).y);
        MouseDispose.leftClick();
        points = PictureFind.getResult(ParamStatic.url + "\\Logout_Line\\logout2.png");
        ParamStatic.robot.mouseMove(points.get(0).x, points.get(0).y);
        MouseDispose.leftClick();
    }


    /**
     *  添加好友
     */
    static void addFriend(Set<String> set) {
        boolean header = true;
        for(String str : set) {
            ParamStatic.clip.setContents(new StringSelection(str), null);
            if( header ) {
                fiendClick(new String[]{ParamStatic.url + "\\NEW_Friend\\addFriend_1.png", ParamStatic.url + "\\NEW_Friend\\addFriend_2.png"}, true);
                fiendClick(new String[]{ParamStatic.url + "\\NEW_Friend\\fiendFriend.png"}, true);
                header = addFriend_2();
            }
            else {
                fiendClick(new String[]{ParamStatic.url + "\\NEW_Friend\\eliminate.png"}, true);
                ParamStatic.robot.mouseMove(0, 0);
                header = addFriend_2();
            }
        }
    }
    static boolean addFriend_2() {
        KeyboardDispose.pasteData();
        KeyboardDispose.enter();

        List<Point> points = PictureFind.getResult(ParamStatic.url + "\\NEW_Friend\\add.png");
        if( points.size() > 0) {
            ParamStatic.robot.mouseMove(points.get(0).x, points.get(0).y);
            MouseDispose.leftClick();

            points = PictureFind.getResult(ParamStatic.url + "\\NEW_Friend\\addFriend_Failed.png");
            if(points.size() > 0) {
                KeyboardDispose.esc();
                return false;
            }
        }
        else {
            return false;
        }
        return true;
    }

    /**
     *  查找屏幕上图片位置并根据lean参数执行操作
     * @param strings   图片地址数组
     * @param lean  false__找到图片时直接esc，true__找到图片时鼠标移动到图片中心坐标再单击
     */
    static boolean fiendClick(String[] strings, boolean lean){
        for(String str:strings){
            List<Point> points = PictureFind.getResult(str);
            if(points.size() > 0) {
                if(lean) {
                    ParamStatic.robot.mouseMove(points.get(0).x, points.get(0).y);
                    MouseDispose.leftClick();
                    return true;
                }
                else {
                    KeyboardDispose.esc();
                }
            }
        }
        return false;
    }
}
