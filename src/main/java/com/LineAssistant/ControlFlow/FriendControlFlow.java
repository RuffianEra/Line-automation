package com.LineAssistant.ControlFlow;

import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.Main;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.PictureDispose.PictureFind;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/** 进入好友主页，查找为好友说说点赞的用户，进入其主页并对其第一条说说进入评论 */
public class FriendControlFlow implements CommonalityMethod{
    /** 设置赞过的好友数的坐标点 */
    private static Point immobilization = new Point(0, 0);

    /** 设置赞过的第一个好友头像坐标点 */
    private static Point praiseFriend = new Point(0, 0);

    /** 记录分享的数据数目 */
    public static int sum = 0;

    /** 记录分享的好友数目 */
    public static int sumFriend = 0;

    /** 记录当前好友名称 */
    public static String friendName = "";

    public void callMain(){
        List<BufferedImage> list = new ArrayList<>();
        FriendControlFlow.circulationFrinedImage(list);
    }

    /**
     *  进入好友的好友主页对第一条分享进行评论
     * @param images  对该好友进行一次快照
     */
    public static void circulationFrinedImage(List<BufferedImage> images) {
        List<Point> list = PictureFind.getResult(Main.url + "\\expression.png");

        WinDef.HWND hwnd = User32.INSTANCE.FindWindowEx(null, null, "Qt5QWindowIcon", null);
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);

        BufferedImage image = Main.robot.createScreenCapture(new Rectangle(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top));

        if(list.size() == 0) {
            images.add(image);
            if((images.size() > 1 && PictureFind.isImageCompare(images.get(images.size() - 1), images.get(images.size() - 2))) || images.size() > 100){  System.out.println("没有人称赞当前好友,直接跳过!!!"); return; }
            Main.robot.mouseWheel(2);
            CommonalityMethod.sleep(1000);
            circulationFrinedImage(images);
        }
        else{
            Point coord = list.get(0);
            Main.robot.mouseMove(coord.x, coord.y);
            MouseDispose.leftClick();

            immobilization = CommonalityMethod.isExist(Main.url + "\\rightPoint.png", -1, 0);

            praiseFriend.x = immobilization.x + 5;
            praiseFriend.y = immobilization.y + 68;

            circulationPraiseFriend();
            KeyboardDispose.esc();
        }
    }

    /**
     *  开始评论
     */
    public static void circulationPraiseFriend() {
        List<Point> friendReplyICO = PictureFind.getResult(Main.url + "\\comment.png");
        List<BufferedImage> listImage = new ArrayList<>();
        for ( int x = 0; x < 100; x++){
            /** 移动到固定点(好友赞数量上) */
            Main.robot.mouseMove(immobilization.x - 6, immobilization.y);
            MouseDispose.leftClick();

            /** 移动到第x+1个赞用户图标上，每三个用户就鼠标滚动一次 */
            Main.robot.mouseMove(praiseFriend.x, praiseFriend.y + x % 3 * 48);
            CommonalityMethod.sleep(1000);
            for(int y = 0; y < x/3; y++){
                Main.robot.mouseWheel(1);
                CommonalityMethod.sleep(1000);
            }

            if(x%3 == 0){
                WinDef.HWND hwnd1 = User32.INSTANCE.FindWindowEx(null, null, "Qt5QWindowIcon", null);
                WinDef.RECT rect = new WinDef.RECT();
                User32.INSTANCE.GetWindowRect(hwnd1, rect);

                listImage.add(Main.robot.createScreenCapture(new Rectangle(rect.left,rect.top, rect.right - rect.left, rect.bottom - rect.top)));
                if( listImage.size()>= 2 && PictureFind.isImageCompare(listImage.get(x/3), listImage.get(x/3-1)) ) {
                    System.out.println("点赞列表已经遍历结束！！！");
                    KeyboardDispose.esc(); return;
                }
            }
            if(x == 1) {
                List<Point> oneBlank = PictureFind.getResult(Main.url + "\\oneBlankPraise.png");
                for(Point coord:oneBlank){
                    if( coord.x - 136 < immobilization.x && coord.x > immobilization.x){ return; }
                }
            }
            else if(x == 2){
                List<Point> twoBlank = PictureFind.getResult(Main.url + "\\twoBlankPraise.png");
                for(Point coord:twoBlank){
                    if( coord.x - 136 < immobilization.x && coord.x > immobilization.x){ return; }
                }
            }
            MouseDispose.leftClick();
            /** 最后做个判断，单击为好友点赞的网友进入其主页，如果当前Line窗口过小则跳过当前角色，或者是好友自赞时没有出现第三个Line窗口，则直接退出当前好友朋友圈 */
            if(CommonalityMethod.lineHWNDNumber()){
                System.out.println("跳过当前角色！！！");
                continue;
            }
            else if(friendName.equals(CommonalityMethod.mouseMove())){
                return;
            }

            /** 鼠标移动到回复好友的用户主页 */
            CommonalityMethod.sleep(1000);
            CommonalityMethod.mouseMove();

            /** 判断该用户有没有发送过说说 */
            if(PictureFind.getResult(Main.url + "\\blank.png").size() == 1 || PictureFind.getResult(Main.url + "\\delect.png").size() == 1){
                KeyboardDispose.esc();
                continue;
            }
            else{
                /** 获取当前屏幕所有回复评论图标 */
                List<Point> twoFriendReplyICO;
                BufferedImage presentScreen = PictureFind.getScreenPicture();
                while (true){
                    twoFriendReplyICO = PictureFind.getResult(Main.url + "\\comment.png");
                    if(twoFriendReplyICO.size() > friendReplyICO.size()){
                        break;
                    }
                    Main.robot.mouseWheel(1);
                    CommonalityMethod.sleep(500);
                    BufferedImage buff = PictureFind.getScreenPicture();
                    CommonalityMethod.sleep(500);
                    if(PictureFind.isImageCompare(presentScreen, buff)){ return; }
                    CommonalityMethod.sleep(500);
                    presentScreen = buff;
                }

                /** 移动并开始评论好友的好友 */
                Point the = twoFriendReplyICO.get(friendReplyICO.size());
                Main.robot.mouseMove(the.x, the.y);
                MouseDispose.leftClick();
                KeyboardDispose.pasteData();

                /** 评论完成开始分享 */
                for(int y = 0; y < 5; y++){
                    List<Point> share = PictureFind.getResult(Main.url + "\\commentOut.png");
                    if(share.size() > 0){
                        Main.robot.mouseMove(share.get(share.size()-1).x, share.get(share.size()-1).y);
                        break;
                    }
                    CommonalityMethod.sleep(1000);
                    Main.robot.mouseWheel(1);
                    CommonalityMethod.sleep(1000);
                }
                MouseDispose.leftClick();
            }
            sum ++;
            Main.logger.info("------------目前已经回复了" + sum  + "条说说");
            KeyboardDispose.esc();
        }
    }
}