import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class HttpClientLenarTest {
    public static void main(String[] args) {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet("http://www.win4000.com/meinv86226.html");
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
//                System.out.println(html);整个html页面
                //6.Jsoup解析html
                Document document = Jsoup.parse(html);
                //像js一样，通过标签获取title
                System.out.println(document.getElementsByTag("title").first());
                //像js一样，通过id找到你想要获取的父级
                Element postList = document.getElementById("pic-meinv");
                //像js一样，通过class 或者 tag 获取次父级
                Elements postItems = postList.getElementsByTag("a");
                System.out.println("pic_meinv->a"+postItems);
                Element imgItem = postItems.get(0);
                System.out.println("pic_meinv->a->item"+imgItem);
                Elements img_node = imgItem.getElementsByClass("pic-large");
                String imgsrc = img_node.attr("url");
                System.out.println(imgsrc);//拿到图片url
                DownloadFile(imgsrc,"D:/12.jpg");

            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
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
}
