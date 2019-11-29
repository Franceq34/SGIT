/*
import tools.FileManager
import java.io._
import org.scalatest._
import scala.util.Random
import scala.annotation.tailrec

class FileManagerTest extends FlatSpec {

  "exists" should "return true when a file of same path exists" in {
    val pathFileUnknown = rdmPathUnknown()
    val file:File = new File(pathFileUnknown)
    assert(file.createNewFile())
    assert(FileManager.exists(pathFileUnknown))
    file.delete()
  }

  it should "return false when a file of the path doesn't exist" in {
    val pathFileUnknown = rdmPathUnknown()
    assert(!FileManager.exists(pathFileUnknown))
  }

  "isDirectory" should "return true when a directory of the same path exists" in {
    val pathDirectoryUnknown = rdmPathUnknown()
    assert(new File(pathDirectoryUnknown).mkdirs())
    val newDirectory = new File(pathDirectoryUnknown)
    assert(FileManager.isDirectory(pathDirectoryUnknown))
    newDirectory.delete()
  }

  it should "return false when a directory of the same path doesn't exist" in {
    val pathDirectoryUnknown = rdmPathUnknown()
    assert(!FileManager.isDirectory(pathDirectoryUnknown))
  }

  "isFile" should "return true when a file of the same path exist" in {
    val pathFileUnknown = rdmPathUnknown()
    val newFile = new File(pathFileUnknown)
    assert(newFile.createNewFile())
    assert(FileManager.isFile(pathFileUnknown))
    newFile.delete()
  }

  it should "return false when a directory of the same path doesn't exist" in {
    val pathFileUnknown = rdmPathUnknown()
    assert(!FileManager.isFile(pathFileUnknown))
  }

  "listFiles" should "return an empty list in option when given a path from empty directory" in {
    val pathDirectoryUnknown = rdmPathUnknown()
    assert(new File(pathDirectoryUnknown).mkdirs())
    val newDirectory = new File(pathDirectoryUnknown)
    assert(FileManager.listFiles(pathDirectoryUnknown).get == List())
    newDirectory.delete()
  }

  it should "return None when given a path from directory that doesn't exist" in {
    val pathDirectoryUnknown = rdmPathUnknown()
    assert(FileManager.listFiles(pathDirectoryUnknown).isEmpty)
  }

  it should "return the list of files in an existing directory when given a valid path" in {
    val pathDirectoryUnknown = rdmPathUnknown()
    assert(new File(pathDirectoryUnknown).mkdirs())
    val newDirectory = new File(pathDirectoryUnknown)
    val newFile = new File(pathDirectoryUnknown + "/abc.txt")
    val newFile2 = new File(pathDirectoryUnknown + "/def.txt")
    assert(newFile.createNewFile())
    assert(newFile2.createNewFile())
    assert(FileManager.listFiles(pathDirectoryUnknown).get.sorted == List("abc.txt", "def.txt").sorted)
    newFile.delete()
    newFile2.delete()
    newDirectory.delete()
  }

  "readFile" should "return None when given a path from a file that doesn't exist" in {
    val pathFileUnknown = rdmPathUnknown()
    assert(FileManager.readFile(pathFileUnknown).isEmpty)
  }

  it should "return empty String when given a path from an empty file" ignore {
    val pathFileUnknown = rdmPathUnknown()
    val newFile = new File(pathFileUnknown)
    assert(newFile.createNewFile())
    assert(FileManager.readFile(pathFileUnknown) == "")
    newFile.delete()
  }

  it should "return a simple String when given a path from a one line file" in {
    val pathFileUnknown = rdmPathUnknown()
    val newFile = new File(pathFileUnknown)
    assert(newFile.createNewFile())
    val fileWriter = new PrintWriter(newFile)
    fileWriter.write("A simple text")
    fileWriter.close()
    assert(FileManager.readFile(pathFileUnknown).get == "A simple text")
    newFile.delete()
  }

  it should "return a String representing the content of the file cut by \\n when given a path from a file with many rows" in {
    val pathFileUnknown = rdmPathUnknown()
    val newFile = new File(pathFileUnknown)
    assert(newFile.createNewFile())
    val fileWriter = new PrintWriter(newFile)
    fileWriter.write("A text\non many lines")
    fileWriter.close()
    assert(FileManager.readFile(pathFileUnknown).get == "A text\non many lines")
    newFile.delete()
  }

  it should "return a String representing the content of the file cut by \\n when given a path from a file with many rows and empty lines" in {
    val pathFileUnknown = rdmPathUnknown()
    val newFile = new File(pathFileUnknown)
    assert(newFile.createNewFile())
    val fileWriter = new PrintWriter(newFile)
    fileWriter.write("A text\non many lines\n\nand empty lines")
    fileWriter.close()
    assert(FileManager.readFile(pathFileUnknown).get == "A text\non many lines\n\nand empty lines")
    newFile.delete()
  }

  it should "return a String representing the content of the file cut by \\n when given a path from a file with many rows finishing by \\n " ignore {
    val pathFileUnknown = rdmPathUnknown()
    val newFile = new File(pathFileUnknown)
    assert(newFile.createNewFile())
    val fileWriter = new PrintWriter(newFile)
    fileWriter.write("A text\non many lines\n")
    fileWriter.close()
    assert(FileManager.readFile(pathFileUnknown).get == "A text\non many lines\n")
    newFile.delete()
  }

  "createDir" should "create a new directory and return true when given a valid path" in {
    val pathFileUnknown = rdmPathUnknown()
    assert(FileManager.createDir(pathFileUnknown))
    assert(FileManager.exists(pathFileUnknown))
    val newDirectory = new File(pathFileUnknown)
    newDirectory.delete()
  }

  it should "create nothing and return false when given an already existing directory path" in {
    val pathFileUnknown = rdmPathUnknown()
    val newDirectory = new File(pathFileUnknown)
    newDirectory.mkdir()
    assert(!FileManager.createDir(pathFileUnknown))
    newDirectory.delete()
  }

  "writeFile" should "create a new file and return true when given an unknown path" in {
    val pathFileUnknown = rdmPathUnknown()
    assert(FileManager.writeFile(pathFileUnknown, ""))
    val file = new File(pathFileUnknown)
    assert(file.exists())
    file.delete()
  }

  it should "create a new file with correct content and return true when given an unknown path and text" in {
    val pathFileUnknown = rdmPathUnknown()
    assert(FileManager.writeFile(pathFileUnknown, "A simple text\nOn several lines"))
    val file:File = new File(pathFileUnknown)
    assert(FileManager.readFile(pathFileUnknown).get == "A simple text\nOn several lines")
    file.delete()
  }

  it should "replace the content of a file and return true when given a path from existing file" in {
    val pathFileUnknown = rdmPathUnknown()
    val file:File = new File(pathFileUnknown)
    assert(file.createNewFile())
    assert(FileManager.writeFile(pathFileUnknown, "A simple text"))
    assert(FileManager.readFile(pathFileUnknown).get == "A simple text")
    file.delete()
  }

  "deleteFileRec" should "return true and delete every folder and file from path when given a valid path" in {
    val pathDirectoryUnknown = rdmPathUnknown()
    assert(new File(pathDirectoryUnknown).mkdirs())
    val newDirectory = new File(pathDirectoryUnknown)
    val newFile = new File(pathDirectoryUnknown + "/abc.txt")
    val newFile2 = new File(pathDirectoryUnknown + "/def.txt")
    assert(newFile.createNewFile())
    assert(newFile2.createNewFile())
    assert(FileManager.deleteFileRec(pathDirectoryUnknown))
  }

  //Return a path from a file that doesn't exist
  @tailrec
  final def rdmPathUnknown():String = {
    val rdmName = Random.alphanumeric.take(10).mkString("")
    if(new File(rdmName).exists()){
      rdmPathUnknown()
    } else {
      rdmName
    }
  }
}

*/
