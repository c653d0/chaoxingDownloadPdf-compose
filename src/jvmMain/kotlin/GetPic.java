import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class GetPic {

    public GetPic(){

    }

    public  void getPicFromHtml(String url, String path) {
        try {
            ArrayList<String> urls = new ArrayList<>();
            Document document = Jsoup.connect(url).get();
            Elements divs = document.getElementsByTag("div");
            Elements imgs = divs.get(0).getElementsByTag("img");
            for (int i = 0; i < imgs.size(); i++) {
                urls.add(imgs.get(i).attr("src"));
                savePic(path,i+".jpg",urls.get(i));

                System.out.println("已下载："+(i+1)+'/'+imgs.size());
            }
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }

    }

    public  void getPicByNumber(int start,int end,String format,String path, String url){
        for(int i=start ; i<=end ; i++){
            savePic(path,i+".jpg",url+i+format);
        }
    }

    public  void savePic(String savePath, String saveName, String ul) {
        try {
            URL url = new URL(ul);
            InputStream is = url.openStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            File imageFile = new File(savePath + saveName);
            OutputStream os = new FileOutputStream(imageFile);
            if (!imageFile.exists()) {
                imageFile.mkdirs();
            }
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
