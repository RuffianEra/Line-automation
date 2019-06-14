package com.LineAssistant.KeyboardDispose;

import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.ParamStatic;

import java.awt.event.KeyEvent;

public class KeyboardDispose {
    /**
     *  把剪切板中的数据复制到当前输入框中
     */
    public static void pasteData(){
        CommonalityMethod.sleep(1000);
        ParamStatic.robot.keyPress(KeyEvent.VK_CONTROL);
        ParamStatic.robot.keyPress(KeyEvent.VK_V);
        ParamStatic.robot.keyRelease(KeyEvent.VK_V);
        ParamStatic.robot.keyRelease(KeyEvent.VK_CONTROL);
        CommonalityMethod.sleep(1000);
    }

    /** 删除当前输入框中的数据 */
    public static void delectData(){
        CommonalityMethod.sleep(1000);
        ParamStatic.robot.keyPress(KeyEvent.VK_CONTROL);
        ParamStatic.robot.keyPress(KeyEvent.VK_A);
        ParamStatic.robot.keyRelease(KeyEvent.VK_A);
        ParamStatic.robot.keyRelease(KeyEvent.VK_CONTROL);
        ParamStatic.robot.keyPress(KeyEvent.VK_DELETE);
        ParamStatic.robot.keyRelease(KeyEvent.VK_DELETE);
        CommonalityMethod.sleep(1000);
    }

    /** 退出ESC */
    public static void esc(){
        ParamStatic.robot.keyPress(KeyEvent.VK_ESCAPE);
        ParamStatic.robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    /** Enter */
    public static void enter(){
        ParamStatic.robot.keyPress(KeyEvent.VK_ENTER);
        ParamStatic.robot.keyRelease(KeyEvent.VK_ENTER);
    }
}