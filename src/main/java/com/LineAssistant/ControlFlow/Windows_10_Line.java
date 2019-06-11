package com.LineAssistant.ControlFlow;

import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.PictureDispose.PictureFind;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/** 在Windows 10系统中运行时使用该类进行列表循环 */
public interface Windows_10_Line {
    /** 直接设置第一个好友图标中心点 */
    Point oneFriend = new Point(84, 115);

    /**
     *  循环好友列表
     * @param method  多态，根据对象调用对应方法
     */
    static void circulationFriendList(CommonalityMethod method) {
        /** 循环列表之前先关闭所有列表 */
        CommonalityMethod.listInit();

        List<Point> list =  PictureFind.getResult(ParamStatic.url + "\\numberList.png");
        if( list.size() == 1 ) { CommonalityMethod.initFriend(method, 85, 150, 5, 2); }
        else if( list.size() == 2 ) { CommonalityMethod.initFriend(method, 85, 150, 10, 4); }

        /** 为轮循好友列表第滚动一次就添加一次快照，用来判断好友列表是否轮循结束 */
        List<BufferedImage> images = new ArrayList<>();

        /** 为好友列表添加第一次快照 */
        images.add(ParamStatic.robot.createScreenCapture(new Rectangle(55,123, 472, 712)));

        while ( true ) {
            /** 每14个好友一次轮循 */
            for(int y = 0; y < 14; y++) {
                CommonalityMethod.sleep(500);
                ParamStatic.robot.mouseMove(oneFriend.x, oneFriend.y + y/3*10 + y%3*54);
                CommonalityMethod.sleep(500);

                /** 每3个好友滚动一次 */
                if(0 == y%3 && y != 0) {
                    images.add(ParamStatic.robot.createScreenCapture(new Rectangle(55,123, 472, 712)));
                    /** 判断好友列表最后一张快照和倒数第二张快照是否全等，如果全等代表好友列表轮循到最后，不能滚动 */
                    if(PictureFind.isImageCompare(images.get(images.size()-1), images.get(images.size()-2))) {
                        System.out.println("正式退出好友列表循环！！！");
                        CommonalityMethod.behindFriend(method, oneFriend.x, oneFriend.y);
                        return;
                    }
                    ParamStatic.robot.mouseWheel(1);
                }
                MouseDispose.leftClick();

                while(true){
                    if(CommonalityMethod.openFriendHomePage(method)){
                        break;
                    }
                    else {
                        Point point = MouseInfo.getPointerInfo().getLocation();
                        oneFriend.y += 20;
                        ParamStatic.robot.mouseMove(point.x, point.y + y/3*10 + 20);
                        MouseDispose.leftClick();
                    }
                }
            }
            ParamStatic.robot.mouseWheel(1);
            oneFriend.y += 1;
        }
    }
}
