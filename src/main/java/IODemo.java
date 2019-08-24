import java.io.*;
import java.net.URL;
import java.util.List;

public class IODemo {
    public static void main(String[] args) throws Exception{
        String path = "D:/1.jpg";
        String url = "http://pic1.win4000.com/pic/1/6d/2d1e351865.jpg";
        //DownloadFile(url,path);
        ForeachFile("D:/轮播壁纸");

    }
    public static void DownloadFile(String urlpath,String filepath){
        URL url = null;
        try {
            url = new URL(urlpath);
            DataInputStream dis = new DataInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(new File(filepath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int length;
            while ((length = dis.read(buff))>0){
                bos.write(buff,0,length);
            }
            fos.write(bos.toByteArray());
            dis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void ForeachFile(String path){
        File flie = new File(path);//给一个文件路径
        File[] filearr = flie.listFiles();//将路径下的文件添加进数组
        for (int i = 0; i<filearr.length;i++){//遍历数组
            if (filearr[i].isDirectory()){//是否为文件夹
                return;
            }else {
                if(filearr[i].getName().endsWith(".jpg")){//获取文件名  判断后缀结尾来分类文件
                    String newname = "轮播壁纸"+(i+123)+".jpg";//更改后的文件名
                    File newfile = new File(path+"/"+newname);//使用更改后的文件名新建文件
                    filearr[i].renameTo(newfile);//新文件覆盖原文件
                }else {
                    return;
                }
            }
        }
    }
}
