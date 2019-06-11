package com.LineAssistant.ControlFlow;

import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.PictureDispose.PictureFind;

import java.awt.*;
import java.util.List;

/**
 *  对自己主页空间中所有说说进行轰炸评论
 */
public class OneselfControlFlow {
    /** 查找到的图片中心点集 */
    public static List<Point> list;


    /**
     *  功能实现入口
     *
     *
     *  把Line转到动态消息界面
     */
    public static void autoComment(){
        findImage(ParamStatic.url + "\\message.png");
        if(list.size()>0){
            ParamStatic.robot.mouseMove(list.get(0).x, list.get(0).y);
            MouseDispose.leftClick();
        }

        /** 调用自动评论方法 */
        findComment();
    }

    /**
     *  查找评论并分享
     */
    public static void findComment(){

        /** 查找到所有评论的中心位置 */
        findImage(ParamStatic.url + "\\comment.png");

        /** 遍历所有评论位置并分享信息，注意每次遍历并分享信息之后当前屏幕上的评论位置和数量都可能会发生变化 */
        for(int x = 0; list.size() > x; ++x){

            /** 根据遍历的次数获取评论位置并分享信息 */
            ParamStatic.robot.mouseMove(list.get(x).x, list.get(x).y);
            MouseDispose.leftClick();
            KeyboardDispose.pasteData();
            try {
                List<Point> lists = PictureFind.getResult(ParamStatic.url + "\\commentOut.png");
                if(lists.size() == 1){/** 当前只有一个分享按钮时 */
                    ParamStatic.robot.mouseMove(lists.get(0).x, lists.get(0).y);
                }
                else if(lists.size() == (x+2)){/** 进行遍历的次数比分享按钮次数少1时 */
                    ParamStatic.robot.mouseMove(lists.get(x+1).x, lists.get(x+1).y);
                }
                MouseDispose.leftClick();
                findImage(ParamStatic.url + "\\comment.png");
            }
            catch (Exception ioE){
                System.out.println(ioE.getMessage());
            }
        }
        CommonalityMethod.sleep(1000);
        /** 齿轮转动次数 */
        ParamStatic.robot.mouseWheel(2);
        CommonalityMethod.sleep(1000);
        findComment();
    }

    /**
     *  查找所有回复并分享
     */
    public static void findReply(){
        /** 查找到所有评论的中心位置 */
        findImage(ParamStatic.url + "\\reply.png");
    }

    /**
     *  查找对应图片并获取图片所在中心点集
     * @param url
     */
    public static void findImage(String url) {
        try {
            list = PictureFind.getResult(url);
        }
        catch (Exception ioE){
            System.out.println(ioE.getMessage());
        }
    }
}