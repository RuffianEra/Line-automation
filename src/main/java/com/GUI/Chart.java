package com.GUI;

import com.LineAssistant.ControlFlow.*;
import com.LineAssistant.MouseDispose.MouseListenerImp;
import com.LineAssistant.ParamStatic;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chart extends JFrame {
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 20000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());
    static Set<String> LineID = new HashSet<>();

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


        JFrame jFrame = new JFrame();
        jFrame.add(new JFileChooser());
        jFrame.setBounds(1000, 100, 400, 700);

        /** 登陆面板 */
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.LINE_AXIS));
        JButton login = new JButton("Line自动化评论");
        login.addMouseListener(new MouseListenerImp() {
            public void mouseReleased(MouseEvent e) {
                amountOne = amount.getText().equals("") ? 0 : Integer.valueOf(amount.getText());
                depthTwo = depth.getText().equals("") ? 0 : Integer.valueOf(depth.getText());

                if("1".equals(group.getSelection().getActionCommand())) {
                    keyValues(new FriendHomePage(), 0);
                }
                else if("2".equals(group.getSelection().getActionCommand())) {
                    keyValues(new FriendControlFlow(), 0);
                }
            }
        });
        loginPanel.add(login);
        JTextField URL = new JTextField();
        URL.setMaximumSize(new Dimension(200, 28));
        loginPanel.add(URL);
        JButton skipFirend = new JButton("跨好友评论");
        skipFirend.addMouseListener(new MouseListenerImp() {
            public void mouseReleased(MouseEvent e) {
                amountOne = amount.getText().equals("") ? 0 : Integer.valueOf(amount.getText());
                depthTwo = depth.getText().equals("") ? 0 : Integer.valueOf(depth.getText());

                if("1".equals(group.getSelection().getActionCommand())) {
                    keyValues(new FriendHomePage(), Pattern.compile("\\d*").matcher(URL.getText()).matches()?Integer.valueOf(URL.getText()):0);
                }
                else if("2".equals(group.getSelection().getActionCommand())) {
                    keyValues(new FriendControlFlow(), Pattern.compile("\\d*").matcher(URL.getText()).matches()?Integer.valueOf(URL.getText()):0);
                }
            }
        });
        /** 爬虫功能目前不需要，不显示 */
        JButton addLine = new JButton("爬取指定网站Line ID并添加");
        addLine.addMouseListener(new MouseListenerImp() {
            public void mouseReleased(MouseEvent e) {
                try {
                    Set<String> set = new HashSet<>();
                    pool.allowCoreThreadTimeOut(true);
                    reptileURL(set, ParamStatic.prop.getProperty("reptile"), ParamStatic.prop.getProperty("network"));

                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\reptiles_5.txt")));

                    while( !(pool.getPoolSize() == 0) ) { Thread.sleep(10000); }

                    for(String str:set) {
                        System.out.println(str);
                        writer.println(str);
                        pool.execute(() -> {
                            String returns = reptile(str, ParamStatic.prop.getProperty("LineID"));
                            if( returns != "" && returns != null){
                                String ID = returns.substring(returns.indexOf("<span>") + 6, returns.indexOf("</span>"));
                                writer.write(ID);
                                LineID.add(ID);
                            }
                        });
                    }

                    while( !(pool.getPoolSize() == 0) ) { Thread.sleep(10000); }
                    writer.flush();
                    writer.close();

                    CommonalityMethod.addFriend(LineID);
                }
                catch (Exception e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });
        loginPanel.add(skipFirend);
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
        comment.addMouseListener(new MouseListenerImp() {
            public void mouseReleased(MouseEvent e) { two.setVisible(false);System.out.println(amount.getText()); }
        });
        group.add(commentDiffusion);
        commentDiffusion.addMouseListener(new MouseListenerImp() {
            public void mouseReleased(MouseEvent e) { two.setVisible(true);System.out.println(amount.getText() + "________" + depth.getText()); }
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


    /**
     * 获取配置文件中的所有帐号密码键值对(key_value)，并循环所有帐号进行广告投放
     * @param common
     */
    public static void keyValues(CommonalityMethod common, int skip) {
        System.out.println(Chart.textArea.getText());
        ParamStatic.clip.setContents(new StringSelection(Chart.textArea.getText()), null);
        CommonalityMethod.openLine();

        new Thread( () -> {
            System.out.println(ParamStatic.prop.getProperty("key_value"));
            /** 解析配置文件帐号和密码 */
            String[] key_values = ParamStatic.prop.getProperty("key_value").split(" ");

            for(String key_value : key_values){
                ParamStatic.logger.info("-----------------------正在进行" + key_value + "的评论-------------------------------");
                /** Line正式登陆 */
                CommonalityMethod.sleep(4000);
                CommonalityMethod.login(key_value.split(","));
                CommonalityMethod.sleep(4000);
                /** 移动Line窗口位置 */
                CommonalityMethod.initCopy();

                /** 评论数据复制到剪切板 */
                ParamStatic.clip.setContents(new StringSelection(ParamStatic.prop.getProperty("message")), null);

                /** 登陆Line，开始好友列表循环，广告投放(不同的系统调用接口不同) */
                /*String system = System.getProperty("os.name");
                if(system.equals("Windows 10")) {
                    Windows_10_Line.circulationFriendList(common);
                }
                else {
                    Windows_Line.circulationFriendList(common);
                }*/
                /** 好友列表循环前把列表统一格式调配 */
                Windows_10_Line.skip(common, 0);

                /** 好友列表循环 */
                Windows_10_Line.circulationFriendList(common);

                /** 完成当前帐号的广告投放，开始退出当前帐号，准备下一个帐号的广告投放 */
                CommonalityMethod.logout();
            }
        } ).start();
    }


    /**
     * 简单爬虫
     * @param spec  网络地址
     * @param regex 过滤正则
     * @return  爬虫爬取到的数据集
     * @throws Exception
     */
    public static String reptile( String spec, String regex) {
        Pattern pat = Pattern.compile(regex);
        try {
            URLConnection url = new URL(spec).openConnection();
            url.setConnectTimeout(30000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream(), "UTF-8"));
            String str = "";
            while( (str = reader.readLine()) != null) {
                Matcher matcher = pat.matcher(str);
                while(matcher.find()){
                    return matcher.group();
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     *  简单爬虫
     * @param list  爬虫爬取到的数据集
     * @param spec  网络地址
     * @param regex 过滤正则
     * @throws Exception
     */
    public static void reptileURL(Set<String> list, String spec, String regex) {
        try {
            Pattern pat = Pattern.compile(regex);
            URLConnection url = new URL(spec).openConnection();
            url.setConnectTimeout(30000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream(), "UTF-8"));
            String str = "";
            while( (str = reader.readLine()) != null) {
                Matcher matcher = pat.matcher(str);
                while(matcher.find()){
                    String ing = "http://eline.tw" + matcher.group();
                    if(!list.contains(ing)) {
                        list.add(ing);
                        pool.execute( () -> {
                            try {
                                reptileURL(list, ing, regex);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
