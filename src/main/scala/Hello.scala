import java.io._

object HelloWorld {
  def main(args: Array[String]): Unit = {

    new File(".sgit").mkdirs()
    new File(".sgit/objects").mkdirs()
    new File(".sgit/refs").mkdirs()
    new File(".sgit/refs/heads").mkdirs()
    new File(".sgit/refs/tags").mkdirs()
    val HEAD = new PrintWriter(new File("HEAD.txt" ))
    HEAD.write("ref: refs/heads/master")
    HEAD.close()
  }
}