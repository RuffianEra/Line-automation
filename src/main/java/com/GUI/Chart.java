package com.GUI;

import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.ControlFlow.FriendControlFlow;
import com.LineAssistant.ControlFlow.FriendHomePage;
import com.LineAssistant.Main;
import com.LineAssistant.PictureDispose.PictureFind;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Chart extends JFrame {
    public static ButtonGroup group = new ButtonGroup();                             /** 评论功能单选框 */
    public static JTextArea textArea = new JTextArea(1, 1);         /** 评论数据 */
    public static JTextArea textAreaLog = new JTextArea(1, 1);      /** 评论日志记录 */

    public static Box two = Box.createHorizontalBox();                               /** 扩散评论深度面板 */

    public static String user = "";                                                  /** 评论帐号记录 */
    public static int amountOne = 0;                                                 /** 评论量设置 */
    public static int depthTwo = 0;                                                  /** 扩散评论深度设置 */

    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        new Chart().setVisible(true);
    }

    /** 设置图形界面图标、标题、最小化 */
    public void outer() {
        this.setBounds(400, 100, 600, 700);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "\\src\\Line\\GUI\\image\\Line_128px.png").getImage());
        this.setTitle("Line自动化程序");
    }

    /** 图形界面初始化 */
    public Chart() {
        this.setResizable(false);
        this.outer();

        JPanel three = new JPanel(new GridLayout(1, 1));



        /** 设置配置参数 */
        Box one = Box.createHorizontalBox();
        one.add(new JLabel("评论量  "));
        JTextField amount = new JTextField();
        amount.setMaximumSize(new Dimension(150, 30));
        amount.setMinimumSize(new Dimension(150, 30));
        one.add(amount);

        two.setVisible(false);
        two.add(new JLabel("扩散评论深度  "));
        JTextField depth = new JTextField();
        depth.setMaximumSize(new Dimension(150, 30));
        amount.setMinimumSize(new Dimension(150, 30));
        two.add(depth);

        JPanel param = new JPanel(new GridLayout(1, 2));
        param.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        param.setMaximumSize(new Dimension(25, 600));
        param.setMinimumSize(new Dimension(25, 600));
        param.add(one);
        param.add(two);



        /** 登陆面板 */
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.LINE_AXIS));
        loginPanel.add(new JLabel("帐号  "));
        JTextField users = new JTextField();
        loginPanel.add(users);
        loginPanel.add(new JLabel("       密码  "));
        JTextField password = new JTextField();
        loginPanel.add(password);
        JButton login = new JButton("登陆");
        login.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) {
                user = users.getText();
                amountOne = Integer.valueOf(amount.getText());
                System.out.println(depth.getText().equals("") ? 0 : depth.getText());
                //depthTwo = Integer.valueOf(depth.getText().equals("") ? 0 : depth.getText());
                System.out.println(amountOne + "____________" + depthTwo);
                /** Line正式登陆 */
                //CommonalityMethod.login(users.getText(), password.getText());
                //CommonalityMethod.sleep(4000);

                /*CommonalityMethod.initCopy();

                if("1".equals(group.getSelection().getActionCommand())){
                    new Thread( () -> CommonalityMethod.circulationFriendList(new FriendHomePage()) ).start();
                }
                else if("2".equals(group.getSelection().getActionCommand())){
                    new Thread( () -> CommonalityMethod.circulationFriendList(new FriendControlFlow()) ).start();
                }*/
            }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
        loginPanel.add(login);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));



        /** 评论功能面板 */
        JPanel function = new JPanel();
        function.setLayout(new GridLayout(1, 2));

        JRadioButton comment = new JRadioButton("好友主页评论");
        comment.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 0));
        comment.setActionCommand("1");
        JRadioButton commentDiffusion = new JRadioButton("好友主页扩散评论");
        commentDiffusion.setActionCommand("2");
        commentDiffusion.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 0));

        group.add(comment);
        comment.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { two.setVisible(false);System.out.println(amount.getText()); }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
        group.add(commentDiffusion);
        commentDiffusion.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { two.setVisible(true);System.out.println(amount.getText() + "________" + depth.getText()); }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
        comment.setSelected(true);

        function.add(comment);
        function.add(commentDiffusion);



        /** 评论数据面板 */
        JPanel setData = new JPanel();
        setData.setLayout(new BoxLayout(setData, BoxLayout.Y_AXIS));
        JLabel lab = new JLabel("评论数据设置");
        lab.setBorder(BorderFactory.createEmptyBorder(2, 240, 2, 0));
        setData.add(lab);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredSoftBevelBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10,10)));
        setData.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));



        /** 评论日志记录面板 */
        JPanel logRecord = new JPanel();
        logRecord.setLayout(new BoxLayout(logRecord, BoxLayout.Y_AXIS));
        JLabel labLog = new JLabel("评论功能日志记录");
        labLog.setBorder(BorderFactory.createEmptyBorder(2, 240, 2, 0));
        logRecord.add(labLog);
        textAreaLog.setLineWrap(true);
        textAreaLog.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredSoftBevelBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10,10)));
        logRecord.add(new JScrollPane(textAreaLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));


        JSplitPane jScrollPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                        new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                new JSplitPane(JSplitPane.VERTICAL_SPLIT, loginPanel, function), param),
                        setData),
                logRecord);
        jScrollPane.setResizeWeight(0.05);

        three.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(2, 2,2 ,2)));
        three.add(jScrollPane);
        this.add(three);
    }
}
