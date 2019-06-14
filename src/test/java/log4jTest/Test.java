package log4jTest;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 20000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());
    public static void main(String[] args) throws Exception{
        /** 获取所有Line ID所在地址 */
        Set<String> set = new HashSet<>();

        /** 获取所有Line ID */
        Set<String> list = new HashSet<>();

        reptileURL(set, "http://eline.tw", "/index\\.php\\?m=goods&a=details&content_id=\\d+");

        while( !(pool.getPoolSize() == 0) ) {
            System.out.println(pool.getPoolSize()); Thread.sleep(5000);
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------");

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\reptiles_3.txt")));
        for(String str:set) {
            writer.println(str);
            list.addAll(reptile(str, "<strong>LINE ID：</strong><span>[^这]+</span>"));
        }
        writer.println();
        writer.println();
        System.out.println(list.size());
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------");
        for(String str:list){
            String ing = str.substring(str.indexOf("<span>") + 6, str.indexOf("</span>"));
            System.out.println(ing);
            writer.println(ing);
        }
        writer.flush();
        writer.close();
    }

    public static Set<String> reptile( String spec, String regex) throws Exception{
        Set<String> list = new HashSet<>();
        Pattern pat = Pattern.compile(regex);
        URLConnection url = new URL(spec).openConnection();
        url.setConnectTimeout(30000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream(), "UTF-8"));
        String str = "";
        while( (str = reader.readLine()) != null) {
            Matcher matcher = pat.matcher(str);
            while(matcher.find()){String ing = matcher.group();
                System.out.println(ing);
                list.contains(ing);
            }
        }
        return list;
    }

    public static void reptileURL(Set<String> list, String spec, String regex) throws Exception{
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
                    System.out.println(ing);
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
}
