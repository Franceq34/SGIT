import java.io._

object HelloWorld {
  def main(args: Array[String]): Unit = {
    init()
    Commands.add("coucou.txt")
  }

  def init(): Unit ={
    val fm:FileManager = new FileManager("/home/quentin/IdeaProjects/output/")
    fm.createDir(".sgit/objects")
    fm.createDir(".sgit/refs/heads")
    fm.createDir(".sgit/refs/tags")
    fm.writeFile(".sgit/HEAD.txt", "ref: refs/heads/master")
  }
}