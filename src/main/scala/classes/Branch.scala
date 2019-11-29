package classes

import java.io.File

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

  def getAllBlobs:List[Blob] ={
    val allBlobs = commits.flatMap(commit => commit.index.list)
    filterBlobsRec(allBlobs)
  }

  def filterBlobsRec(blobs:List[Blob]):List[Blob] = {
    blobs match {
        //if list isnt empty
      case head :: tail => {
        val blob = blobs(0)
        val blobReduced = blobs.drop(1)
        if (blobReduced.exists(b => b.hasSamePathThan(blob))) {
          filterBlobsRec(blobReduced)
        } else {
          List(blob) ::: filterBlobsRec(blobReduced)
        }
      }
      case Nil => List()
    }
  }

  //Save the a branch commits in .sgit/logs/refs/heads/branchname
  def saveToLogs(): Boolean ={
    if(FileManager.exists(".sgit"+File.separator +"logs"+File.separator +"refs"+File.separator +"heads"+File.separator +name)){
      val content = commits.mkString("\n")
      println(content)
      println(".sgit"+File.separator +"logs"+File.separator +"refs"+File.separator +"heads"+File.separator +name)
      FileManager.writeFile(".sgit"+File.separator +"logs"+File.separator +"refs"+File.separator +"heads"+File.separator +name, content)
    } else {
      false
    }
  }

  //Save a branch current commit in .sgit/refs/heads/branchname
  def saveToRefs(): Boolean = {
    if (FileManager.exists(".sgit"+File.separator +"refs"+File.separator +"heads"+File.separator +name)) {
      val content = commits.last.idHash
      FileManager.writeFile(".sgit"+File.separator +"refs"+File.separator +"heads"+File.separator +name, content)
    } else {
      false
    }
  }
}
