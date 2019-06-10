package com.LineAssistant.ControlFlow;

import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.Main;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.PictureDispose.PictureFind;

import java.awt.*;

public class FriendHomePage implements CommonalityMethod{

    public void callMain(){
        /** 对所有好友主页的第一条分享进行评论 */
        FriendHomePage.comment();
    }

    /**
     *  循环好友列表并对所有好友主页的第一条分享进行评论
     */
    public static void comment(){

        /** 单击评论按钮 */
        Point comment = CommonalityMethod.isExist(Main.url + "\\comment.png", 1, 0);
        if( comment == null ){ return; }
        Main.robot.mouseMove(comment.x, comment.y);
        MouseDispose.leftClick();

        if(PictureFind.getResult(Main.url + "\\networkConnect.png").size() > 0 ){ return ; }

        /** 移动到评论框 */
        Point commentOut = CommonalityMethod.isExist(Main.url + "\\commentOut.png", 1, 0);
        Main.robot.mouseMove(commentOut.x - 50, commentOut.y);
        MouseDispose.leftClick();

        KeyboardDispose.pasteData();

        Main.robot.mouseMove(commentOut.x, commentOut.y);
        MouseDispose.leftClick();

        Main.logger.info("------------目前已经回复了" + ++FriendControlFlow.sum  + "条说说");
    }
}