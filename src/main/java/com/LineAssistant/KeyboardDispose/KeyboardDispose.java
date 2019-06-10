package com.LineAssistant.KeyboardDispose;

import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.Main;

import java.awt.event.KeyEvent;

public class KeyboardDispose {
    /**
     *  把剪切板中的数据复制到当前输入框中
     */
    public static void pasteData(){
        CommonalityMethod.sleep(1000);
        Main.robot.keyPress(KeyEvent.VK_CONTROL);
        Main.robot.keyPress(KeyEvent.VK_V);
        Main.robot.keyRelease(KeyEvent.VK_V);
        Main.robot.keyRelease(KeyEvent.VK_CONTROL);
        CommonalityMethod.sleep(1000);
    }

    /** 删除当前输入框中的数据 */
    public static void delectData(){
        CommonalityMethod.sleep(1000);
        Main.robot.keyPress(KeyEvent.VK_CONTROL);
        Main.robot.keyPress(KeyEvent.VK_A);
        Main.robot.keyRelease(KeyEvent.VK_A);
        Main.robot.keyRelease(KeyEvent.VK_CONTROL);
        Main.robot.keyPress(KeyEvent.VK_DELETE);
        Main.robot.keyRelease(KeyEvent.VK_DELETE);
        CommonalityMethod.sleep(1000);
    }

    /** 退出ESC */
    public static void esc(){
        Main.robot.keyPress(KeyEvent.VK_ESCAPE);
        Main.robot.keyRelease(KeyEvent.VK_ESCAPE);
    }
}