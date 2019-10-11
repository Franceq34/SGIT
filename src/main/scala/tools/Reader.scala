package tools

import classes.{Blob, Branch, Commit, Index}

object Reader {


  def currentIndex():Option[Index] ={
    try{
      val index = FileManager.readFile(".sgit/index")
      val blobs = index.getOrElse("").split("\n").map(str => Blob(str.split(" ")(0), "", str.split(" ")(1)))
      Some(Index(blobs.toList))
    }
    catch
      {
        case _: Throwable => None
      }
  }

  def currentBranch():Option[Branch] ={
    try{
      val headContent = FileManager.readFile(".sgit/HEAD.txt")
      val currentBranchPath = headContent.get.split(" ")(1)
      val currentBranchName = currentBranchPath.split("/").last
      val stringCommits = FileManager.readFile(".sgit/logs/" + currentBranchPath)
      if(stringCommits.isDefined && stringCommits.get != ""){
        val commits = stringCommits.get.split("\n").map(
          str =>
            Commit(
            str.split(" ")(0),
            str.split(" ")(1),
            str.split(" ")(2),
            str.split(" ")(3),
            Index(List())
          )).toList
        Some(Branch(currentBranchName, commits))
      } else {
        Some(Branch(currentBranchName, List()))
      }
    }
    catch
      {
        case _: Throwable => None
      }
  }
}
