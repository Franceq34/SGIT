import java.io._

object HelloWorld {
  def main(args: Array[String]): Unit = {
    init()
  }

  def init(): Unit ={
    val fm:FileManager = new FileManager("/home/quentin/IdeaProjects/output/")
    fm.createDir(".sgit/objects")
    fm.createDir(".sgit/refs/heads")
    fm.createDir(".sgit/refs/tags")
    fm.writeFile("HEAD.txt", "ref: refs/heads/master")
    print(fm.readFile("HED.txt").getOrElse("echec"))
  }
}