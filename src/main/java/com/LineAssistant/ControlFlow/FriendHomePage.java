package com.LineAssistant.ControlFlow;

import com.GUI.Chart;
import com.LineAssistant.KeyboardDispose.KeyboardDispose;
import com.LineAssistant.ParamStatic;
import com.LineAssistant.MouseDispose.MouseDispose;
import com.LineAssistant.PictureDispose.PictureFind;

import java.awt.*;

public class FriendHomePage implements CommonalityMethod{

    public void callMain(){
        /** 对所有好友主页的第一条分享进行评论 */
        /*FriendHomePage.comment();*/
    }

    /**
     *  循环好友列表并对所有好友主页的第一条分享进行评论
     */
    public static void comment(){

        /** 单击评论按钮 */
        Point comment = PictureFind.isExist(ParamStatic.url + "\\Comment_Friend\\comment.png", 1, 0);
        if( comment == null ){ return; }
        ParamStatic.robot.mouseMove(comment.x, comment.y);
        MouseDispose.leftClick();

        if(PictureFind.getResult(ParamStatic.url + "\\Comment_Friend\\networkConnect.png").size() > 0 ){ return ; }

        /** 移动到评论框 */
        Point commentOut = PictureFind.isExist(ParamStatic.url + "\\Comment_Friend\\commentOut.png", 1, 0);
        ParamStatic.robot.mouseMove(commentOut.x - 50, commentOut.y);
        MouseDispose.leftClick();

        KeyboardDispose.pasteData();

        ParamStatic.robot.mouseMove(commentOut.x, commentOut.y);
        MouseDispose.leftClick();

        ParamStatic.logger.info("-----目前已经回复了" + ++ParamStatic.sum  + "条说说");
        if( ParamStatic.sum >= (Chart.amountOne == 0 ? 100 : Chart.amountOne)){ ParamStatic.logger.info("评论目标数已达到，程序关闭!!!");System.exit(0); }
    }
}