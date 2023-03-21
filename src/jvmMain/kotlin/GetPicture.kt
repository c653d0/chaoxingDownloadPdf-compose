import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URL

class GetPicture {
    fun GetPic() {}

    fun getPicFromHtml(url: String?, path: String) {
        try {
            val urls = ArrayList<String>()
            val document = Jsoup.connect(url).get()
            val divs = document.getElementsByTag("div")
            val imgs = divs[0].getElementsByTag("img")
            for (i in imgs.indices) {
                urls.add(imgs[i].attr("src"))
                savePic(path, "$i.jpg", urls[i])
                println("已下载：" + (i + 1) + '/' + imgs.size)
            }
        } catch (ioE: IOException) {
            ioE.printStackTrace()
        }
    }

    fun getPicByNumber(start: Int, end: Int, format: String, path: String, url: String) {
        for (i in start..end) {
            savePic(path, "$i.png", url + i + format)
        }
    }

    fun savePic(savePath: String, saveName: String, ul: String?) {
        try {
            val url = URL(ul)
            val `is` = url.openStream()
            val buffer = ByteArray(1024)
            var len = 0
            val imageFile = File(savePath + saveName)
            val os: OutputStream = FileOutputStream(imageFile)
            if (!imageFile.exists()) {
                imageFile.mkdirs()
            }
            while (`is`.read(buffer).also { len = it } != -1) {
                os.write(buffer, 0, len)
            }
            os.close()
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}