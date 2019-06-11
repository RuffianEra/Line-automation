package com.LineAssistant.MouseDispose;

import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.ParamStatic;

import java.awt.event.InputEvent;

public class MouseDispose {

    /**
     * 左键鼠标单击
     */
    public static void leftClick() {
        CommonalityMethod.sleep(1000);
        ParamStatic.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        ParamStatic.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        CommonalityMethod.sleep(1000);
    }

    /**
     * 左键鼠标双击
     */
    public static void leftDoublicClick() {
        CommonalityMethod.sleep(1000);
        ParamStatic.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        ParamStatic.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        ParamStatic.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        ParamStatic.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        CommonalityMethod.sleep(1000);
    }

    /**
     * 右键鼠标单击
     */
    public static void rightClick(){
        CommonalityMethod.sleep(1000);
        ParamStatic.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        ParamStatic.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        CommonalityMethod.sleep(1000);
    }
}
