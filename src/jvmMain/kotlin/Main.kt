import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.Desktop
import java.io.File

@Composable
fun App() {
    var url by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var start by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }

    var statues by remember { mutableStateOf("") }

    MaterialTheme {
        Column{
            Row {
                OutlinedTextField(
                    value = url,
                    onValueChange = {url = it},
                    label = { Text("链接") },
                    modifier = Modifier.weight(0.8f).padding(start = 24.dp).padding(end = 4.dp)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    label = { Text("pdf名称") },
                    modifier = Modifier.weight(0.2f).padding(end = 24.dp).padding(start = 4.dp)
                )
            }
            Row {
                OutlinedTextField(
                    value = start,
                    onValueChange = { start = it },
                    label = { Text("开始页数") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(start = 24.dp).padding(end = 4.dp).weight(0.5f)
                )
                OutlinedTextField(
                    value = end,
                    onValueChange = { end = it },
                    label = { Text("结束页数") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(start = 4.dp).padding(end = 24.dp).weight(0.5f)
                )
            }
            Row {
                Button(
                    onClick = {
                        statues = "正在获取....."
                        val startPage = Integer.parseInt(start)
                        val endPage = Integer.parseInt(end)
                        GlobalScope.launch {
                            saveAsPdf(pdfName = "$name.pdf", url = url, start = startPage, end = endPage)
                            statues = "获取完成"
                        }
                    },
                    modifier = Modifier.weight(0.5f).padding(start = 24.dp).padding(end = 4.dp)
                ){
                    Text("开始下载")
                }
                Button(
                    onClick = {
                        val workDirectory = "${System.getProperty("user.dir")}\\userData\\pdf\\"
                        val file = File(workDirectory)
                        if(!file.exists()){
                            file.mkdirs()
                        }
                        Desktop.getDesktop().open(file)
                    },
                    modifier = Modifier.weight(0.5f).padding(start = 4.dp).padding(end = 24.dp)
                ){
                    Text("打开保存文件夹")
                }
            }

            Text(statues, modifier = Modifier.padding(8.dp))
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

private suspend fun saveAsPdf(pdfName: String, url: String, start: Int, end: Int) {
    // 设置pdf保存的路径
    val workDirectory = "${System.getProperty("user.dir")}\\userData"
    val picFolderPath = "${workDirectory}\\pic\\"
    val pdfFolderPath = "${workDirectory}\\pdf\\"

    val getPic = GetPicture()
    val picToPdf = PicToPdf()
    val fileOp = FileOperate()


    //操作前检查文件夹是否存在，若不存在则创建该文件夹
    val picFolder = File(picFolderPath)
    val pdfFolder = File(pdfFolderPath)
    if (!picFolder.exists()) {
        picFolder.mkdirs()
    }
    if (!pdfFolder.exists()) {
        pdfFolder.mkdirs()
    }
    runBlocking {
        if (start + end <= 0) {
            //
            getPic.getPicFromHtml(url, picFolderPath)
        } else {
            //
            getPic.getPicByNumber(start, end, ".png", picFolderPath, url)
        }


        //
        picToPdf.toPdf(picFolderPath, pdfFolderPath + pdfName)

        //
        fileOp.deleteFile(File(picFolderPath))
    }
}
