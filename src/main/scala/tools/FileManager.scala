package tools

import java.io._

object FileManager{

  def exists(path:String): Boolean = { new File(path).exists()}

  def isDirectory(path:String): Boolean = { new File(path).isDirectory }

  def isFile(path:String): Boolean ={ new File(path).isFile }

  def listFiles(path:String): Option[List[String]] ={ Some(new File(path).listFiles().map((f:File) => f.getName).toList) }

  def readFile(path:String): Option[String] = {
    try
    {
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

  private def readFileRec(br:BufferedReader): String = {
    val st = br.readLine()
    if ( st!= null) st + "\n" + readFileRec(br)
    else ""
  }

  def createDir(path:String): Boolean = if(!exists(path)){ new File(path).mkdirs() } else true

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
