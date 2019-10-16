package tools
import java.io._

object FileManager{

  def exists(path:String): Boolean = { new File(path).exists()}

  def isDirectory(path:String): Boolean = { new File(path).isDirectory }

  def isFile(path:String): Boolean ={ new File(path).isFile }

  def listFiles(path:String): Option[List[String]] ={
    try {
      Some(new File(path).listFiles().map((f:File) => f.getName).toList)
    }
    catch
      {
        case _: Throwable => None
      }
  }

  def readFile(path:String): Option[String] = {
    try
    {
      def readFileRec(br:BufferedReader): String = {
        val st = br.readLine()
        println(st)
        if ( st!= null) st + "\n" + readFileRec(br)
        else ""
      }
      val br = new BufferedReader(new FileReader(path))
      val str = readFileRec(br)
      val res = str.substring(0, str.lastIndexOf("\n"))
      br.close()
      Some(res)
    }
    catch
    {
      case _: Throwable => None
    }
  }

  def createDir(path:String): Boolean = if(!exists(path)){ new File(path).mkdirs() } else false

  def writeFile(path:String, text: String): Boolean = {
    try
      {
        val HEAD = new PrintWriter(new File(path))
        HEAD.write(text)
        HEAD.close()
        true
      }
    catch
      {
        case _: Throwable => false
      }
  }
}
