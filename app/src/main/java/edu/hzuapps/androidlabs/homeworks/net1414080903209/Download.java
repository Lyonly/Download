package edu.hzuapps.androidlabs.homeworks.net1414080903209;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;



public class Download {
    private String path;//下载路径
    public Download(String PATH){
        this.path=PATH;
    }
    private  final  int threadCount = 3;

    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public  void start(){
        try {
            URL url = new URL(path);
            //获取HttpURLConnection对象 打开链接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");//设置发送get请求
            con.setConnectTimeout(5000);//设置连接超时时间
            if (con.getResponseCode() == 200) {
                int contentLength = con.getContentLength();

                System.out.println(contentLength);

                //创建一个和下载文件一样大的文件 RandomAccessFile 随机访问文件
                RandomAccessFile accessFile = new RandomAccessFile("/storage/sdcard/temp.apk", "rw");
                accessFile.setLength(contentLength);

                //每个线程下载的开始和结束位置
                int threadSize = contentLength / threadCount;
                for (int i = 0; i < threadCount;i++) {
                    int start = i * threadSize;
                    int end = (i + 1) * threadSize - 1;
                    if (i == threadCount - 1) {
                        end = contentLength - 1;
                    }

                    //启动线程的位置
                    DownloadThread thread = new DownloadThread(start, end, i,path);
                    thread.start();
                    thread.join();
                }

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

