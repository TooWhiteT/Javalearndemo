import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class JsoupDown {
    public static void main(String[] args) {
        try {
            //http://www.win4000.com/meinv184397.html
            Document document = Jsoup.connect("http://www.win4000.com/meinv86226.html").get();//get请求到Html页面数据
            Elements ptitle = document.getElementsByClass("ptitle");
            Elements em = ptitle.get(0).getElementsByTag("em");
            em.get(0).data();
            for (int i = 2;i<15;i++){
                document = Jsoup.connect("http://www.win4000.com/meinv86226_"+i+".html").get();//get请求到Html页面数据
                Element nodeId = document.getElementById("pic-meinv");
                Elements nodeTag_img = nodeId.getElementsByTag("img");
                String url = nodeTag_img.attr("url");
                System.out.println(url);
                downImg(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void downImg(String path){

        File imgFile = new File("D:/"+path.substring(33));
        URL url = null;
        try {
            url = new URL(path);
            DataInputStream dis = new DataInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(imgFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int length;
            while ((length = dis.read(buff))>0){
                bos.write(buff,0,length);
            }
            fos.write(bos.toByteArray());
            dis.close();
            fos.close();
            System.out.println("图片》》》》》"+path.substring(33)+"》》》》》下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
