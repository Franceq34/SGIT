package classes

import tools.FileManager

case class Branch(name: String, commits: List[Commit]){

  def init():Boolean ={
    if(FileManager.exists(".sgit/logs/refs") && FileManager.exists(".sgit/refs")){
      FileManager.writeFile(".sgit/refs/heads/" + name, "")
      FileManager.writeFile(".sgit/logs/refs/heads/" + name, "")
    } else {
      false
    }
  }

  def addCommit(commit: Commit): Branch = copy(commits = commits :+ commit)

  def saveToLogs(): Boolean ={
    if(FileManager.exists(".sgit/logs/refs/heads/"+name)){
      println("oui")
      val content = commits.mkString("\n")
      FileManager.writeFile(".sgit/logs/refs/heads/"+name, content)
    } else {
      false
    }
  }
}
