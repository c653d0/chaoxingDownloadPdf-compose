import java.io.File

class FileOperate {
    fun FileOperate() {}

    //删除文件夹下所有文件，包括子文件夹的文件
    fun deleteFile(file: File?) {
        //防止文件不存在
        if (file == null || !file.exists()) {
            println("file is not exist,delete failed")
            return
        }
        try {
            //获取到文件夹下的所有文件
            val files = file.listFiles()
            for (f in files) {
                //如果是文件则进行删除
                if (f.isFile) {
                    if (f.delete()) println(f.name + " has been destroyed") else {
                        println("delete failed")
                        return
                    }
                }
                //如果试文件夹，则继续调用删除
                if (f.isDirectory) {
                    deleteFile(f)
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        //删除这个文件夹，按需使用
        //file.delete();
    }

    //对文件列表按名称进行排序
    fun sort(s: Array<File?>): Array<File?>? {
        //中间值
        var temp: File? = null
        //外循环:我认为最小的数,从0~长度-1
        for (j in 0 until s.size - 1) {
            //最小值:假设第一个数就是最小的
            var min = s[j]!!.name
            //记录最小数的下标的
            var minIndex = j
            //内循环:拿我认为的最小的数和后面的数一个个进行比较
            for (k in j + 1 until s.size) {
                //找到最小值
                if (min.substring(0, min.indexOf(".")).toInt() > s[k]!!.name.substring(0, s[k]!!.name.indexOf("."))
                        .toInt()
                ) {
                    //修改最小
                    min = s[k]!!.name
                    minIndex = k
                }
            }
            //当退出内层循环就找到这次的最小值
            //交换位置
            temp = s[j]
            s[j] = s[minIndex]
            s[minIndex] = temp
        }
        return s
    }
}