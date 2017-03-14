package edu.hzuapps.androidlabs.homeworks.net1414080903209;

import android.widget.Toast;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/3/15.
 */
public class DownloadThread extends Thread{
    private int Start;
    private int End;
    private int Threadid;
    private  String Path;
    public DownloadThread(int start,int end,int threadid,String path){
        this.Start=start;
        this.End=end;
        this.Threadid=threadid;
        this.Path=path;
    }


    public void run(){
        try{
            //读取断点
            File file = new File(Threadid+".txt");
            if (file.exists()&&file.length()>0) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader buf = new BufferedReader(new InputStreamReader(fis));
                String lastPosition = buf.readLine();
                Start=Integer.parseInt(lastPosition);
                fis.close();
            }

            URL url = new URL(Path);
            //获取HttpURLConnection对象 打开链接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");//设置发送get请求
            con.setConnectTimeout(5000);//设置连接超时时间
            con.setRequestProperty("Range","bytes="+Start+"-"+End);//设置线程下载的位置

            if(con.getResponseCode()==206) {
                RandomAccessFile raf = new RandomAccessFile("/storage/sdcard/temp.apk", "rw");
                raf.seek(Start);//创建一个本地文件，seek()设置偏移量 该位置发生下一个写操作

                InputStream inputstream = con.getInputStream();

                int len = -1;
                byte[] buffer = new byte[1024 * 1024 * 3];

                while ((len = inputstream.read(buffer)) != -1) {
                    raf.write(buffer, 0, len);
                    //记录断点
                    int total = 0;
                    total += len;
                    int currentSchedule = Start + total;
                    RandomAccessFile saveRaf = new RandomAccessFile(Threadid + ".txt", "rwd");
                    saveRaf.write(String.valueOf(currentSchedule).getBytes());
                    saveRaf.close();
                }
                raf.close();
                File delfile = new File(Threadid + ".txt");
                delfile.delete();
            }



        }catch(IOException e){

        }
    }



}
