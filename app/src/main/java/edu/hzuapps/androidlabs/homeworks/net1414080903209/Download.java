package edu.hzuapps.androidlabs.homeworks.net1414080903209;

import android.util.Log;

import java.io.*;
import java.net.*;



public class Download extends Thread{
    private String path="";//下载路径
    private String fileName;
    private int fileSize;
    public Download(String PATH,String FileName){
        this.path=PATH;
        this.fileName=FileName;
    }

    private  final  int threadCount = 3;


    public  void run(){
        try{
            URL url = new URL(path);
            //获取HttpURLConnection对象 打开链接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");//设置发送get请求
            con.setConnectTimeout(5000);//设置连接超时时间
            if(con.getResponseCode()==200) {
                int contentlength = con.getContentLength();
                fileSize = contentlength;

                //创建一个和下载文件一样大的文件 RandomAccessFile 随机访问文件
                RandomAccessFile accessfile = new RandomAccessFile("/storage/sdcard/"+fileName, "rw");
                accessfile.setLength(contentlength);

                //每个线程下载的开始和结束位置
                int threadsize = contentlength / threadCount;
                for (int i = 0; i < threadCount; i++) {
                    int start = i * threadsize;
                    int end = (i + 1) * threadsize - 1;
                    if (i == threadCount - 1) {
                        end = contentlength - 1;
                    }

                    //启动线程的位置
                    DownloadThread thread = new DownloadThread(start, end, i, path,fileName);
                    Log.i("System.out", "run:start:"+start+"end:"+end+"threadid:"+i);
                    thread.start();
                }

            }
        }catch(Exception e){
            Log.i("System.out", "err");
        }
    }

    public  int returnSize(){
        return fileSize;
    }
}

