import tools.{FileManager, Parser}

object Launcher {
  def main(args: Array[String]): Unit = {
    Parser(args)

    /*Parser(Array("init"))
    FileManager.writeFile("test/file.txt", "Bonjour\n")
    Parser(Array("diff"))
    Parser(Array("status"))
    Parser(Array("add", "test"))
    Parser(Array("status"))
    Parser(Array("commit", "-m", "Premier commit"))
    Parser(Array("status"))
    FileManager.writeFile("test/test.txt", "Bonjour\nle monde")
    Parser(Array("add", "test/test.txt"))
    Parser(Array("commit", "-m", "Second commit"))
    Parser(Array("log", "-p"))
*/
  }
}