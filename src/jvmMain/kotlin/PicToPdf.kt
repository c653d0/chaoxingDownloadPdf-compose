import com.lowagie.text.Document
import com.lowagie.text.Image
import com.lowagie.text.Rectangle
import com.lowagie.text.pdf.PdfWriter
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO

class PicToPdf {
    fun PicToPdf() {}

    fun toPdf(imageFolderPath: String, pdfPath: String?) {
        try {
            val fileOp = FileOperate()
            var imagePath: String? = null
            val fos = FileOutputStream(pdfPath)
            val doc = Document(null, 0f, 0f, 0f, 0f)
            //doc.open();
            PdfWriter.getInstance(doc, fos)
            var img: BufferedImage? = null
            var image: Image? = null
            val file = File(imageFolderPath)
            val files = fileOp.sort(file.listFiles())
            for (file1 in files!!) {
                if (file1!!.name.endsWith(".png")
                    || file1.name.endsWith(".jpg")
                    || file1.name.endsWith(".gif")
                    || file1.name.endsWith(".jpeg")
                    || file1.name.endsWith(".tif")
                ) {
                    // System.out.println(file1.getName());
                    imagePath = imageFolderPath + file1.name
                    println(imagePath)
                    img = ImageIO.read(File(imagePath))
                    doc.pageSize = Rectangle(
                        img.width.toFloat(), img
                            .height.toFloat()
                    )
                    image = Image.getInstance(imagePath)
                    doc.open()
                    doc.add(image)
                }
            }
            doc.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}