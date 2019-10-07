import java.io._
import crypt._

object Main {
  def main(args: Array[String]): Unit = {
    init()
    add("coucou.txt")
  }

  def init(): Unit ={
    val fm:FileManager = new FileManager("/home/quentin/IdeaProjects/output/")
    fm.createDir(".sgit/objects")
    fm.createDir(".sgit/refs/heads")
    fm.createDir(".sgit/refs/tags")
    fm.writeFile(".sgit/HEAD.txt", "ref: refs/heads/master")
  }

  def add(path:String): Unit ={
    //create the file manager
    val fm:FileManager = new FileManager("/home/quentin/IdeaProjects/output/")
    //read the file to add
    val content = fm.readFile(path).getOrElse("")
    //create the encryptor
    val cr:Crypt = new CryptSHA1()
    val cryptedContent = cr.crypt(content)
    cryptedContent match {
      case Some(i) =>
        //write the staged files in index
        fm.writeFile(".sgit/index", cryptedContent)
        //create the correct directory in objects folder
        fm.createDir(".sgit/objects/" + cryptedContent.substring(0, 2))
        //write the staged files in the objects
        print(fm.writeFile(".sgit/objects/" + cryptedContent.substring(0, 2) + "/" + cryptedContent.substring(2), content))
      case None => println("That didn't work.")
    }
  }
}