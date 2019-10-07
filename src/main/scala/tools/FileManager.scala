package tools

import java.io._

class FileManager(val globalPath:String = "."){
  import FileManager._

  def exists(path:String): Boolean = { new File(globalPath+path).exists()}

  def isDirectory(path:String): Boolean = { new File(globalPath+path).isDirectory() }

  def isFile(path:String): Boolean ={ new File(globalPath+path).isFile() }

  def listFiles(path:String): Option[List[String]] ={ Some(new File(globalPath+path).listFiles().map((f:File) => f.getName()).toList) }

  def readFile(path:String): Option[String] = {
    try
    {
      val br = new BufferedReader(new FileReader(globalPath + path))
      Some(readFileRec(br))
    }
    catch
    {
      case _: Throwable => return None
    }
  }

  def createDir(path:String): Boolean = new File(globalPath + path).mkdirs()

  def writeFile(path:String, text: String): Boolean = {
    try
      {
        val HEAD = new PrintWriter(new File(globalPath + path))
        HEAD.write(text)
        HEAD.close()
        true
      }
    catch
      {
        case _: Throwable => return false
      }
  }
}

object FileManager {
  private def readFileRec(br:BufferedReader): String = {
    val st = br.readLine()
    if ( st!= null) st + readFileRec(br)
    else ""
  }
}
