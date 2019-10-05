import java.io._

class FileManager(globalPath:String = "."){
  import FileManager._

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

  def createFile(path:String): Boolean = {
    try {
      new File(globalPath + path)
      true
    }
    catch
      {
        case _: Throwable => return false
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
