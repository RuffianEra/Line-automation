package com.LineAssistant.ControlFlow;

import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.PictureDispose.PictureFind;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/** 非Windows 10和WindowsPhone的Windows系统中运行时使用该类进行列表循环 */
public interface Windows_Line {
    /** 直接设置第一个好友图标中心点 */
    Point oneFriend = new Point(85, 112);

    /**
     *  循环好友列表
     * @param method  多态，根据对象调用对应方法
     */
    static void circulationFriendList(CommonalityMethod method) {

        /** 把列表调整为可以通一调用的形式 */
        CommonalityMethod.initFriend(method, 85, 150, 5, 2);
        /*List<Point> list =  PictureFind.getResult(ParamStatic.url + "\\List_Close\\numberList.png");
        if( list.size() == 1 ) { CommonalityMethod.initFriend(method, 85, 150, 6, 4); }
        else if( list.size() == 2 ) { CommonalityMethod.initFriend(method, 85, 180, 4, 3); }*/

        /** 为轮循好友列表第滚动一次就添加一次快照，用来判断好友列表是否轮循结束 */
        List<BufferedImage> images = new ArrayList<>();

        /** 为好友列表添加第一次快照 */
        images.add(ParamStatic.robot.createScreenCapture(new Rectangle(55,123, 472, 712)));

        /** 好友列表无限轮循 */
        for(int y = 0; true; y++) {
            ParamStatic.robot.mouseMove(oneFriend.x, oneFriend.y + y%5*54);

            /** 每5个好友滚动3次 */
            if(0 == y%5 && y != 0) {
                oneFriend.y --;
                if(oneFriend.y <= 105) {
                    oneFriend.y += 55;
                    ParamStatic.robot.mouseMove(oneFriend.x, oneFriend.y + y%5*54);
                    clickHomePage(method);
                }

                ParamStatic.robot.mouseWheel(3);
                images.add(ParamStatic.robot.createScreenCapture(new Rectangle(55,123, 472, 712)));
                /** 判断好友列表最后一张快照和倒数第二张快照是否全等，如果全等代表好友列表轮循到最后，不能滚动 */
                if(PictureFind.isImageCompare(images.get(images.size()-1), images.get(images.size()-2))) {
                    System.out.println("正式退出好友列表循环！！！");
                    CommonalityMethod.behindFriend(method, oneFriend.x, oneFriend.y + y%5*54);
                    return;
                }
            }
            clickHomePage(method);
        }
    }


    /**
     * 单击好友头像
     * @param method
     */
    static void clickHomePage(CommonalityMethod method) {
        MouseDispose.leftClick();

        while(true){
            if(CommonalityMethod.openFriendHomePage(method)){
                break;
            }
            else {
                Point point = MouseInfo.getPointerInfo().getLocation();
                oneFriend.y += 20;
                ParamStatic.robot.mouseMove(point.x, point.y + 20);
                MouseDispose.leftClick();
            }
        }
    }
}