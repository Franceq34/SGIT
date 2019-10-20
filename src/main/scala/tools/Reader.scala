package tools

import java.io.File

import classes.{Blob, Branch, Commit, Index}

object Reader {

  def clearStage(): Boolean = FileManager.writeFile(".sgit"+ File.separator +"index", text = "")

  def isGitRepo: Boolean = FileManager.exists(".sgit")

  def currentIndex():Option[Index] ={
    try{
      //Read the index file
      val index = FileManager.readFile(".sgit"+ File.separator +"index")
      //Map the index in blob list
      val blobsList = index.getOrElse("").split("\n")
      if (blobsList.nonEmpty && blobsList(0) != "") {
        //Return an index object made of blob list
        Some(Index(blobsList.map(str => Blob(str.split(" ")(0), "", str.split(" ")(1))).toList))
      } else {
        Some(Index())
      }
    }
    catch
      {
        case _: Throwable => None
      }
  }

  def currentBranch():Option[Branch] = {
    try {
      val headContent = FileManager.readFile(".sgit"+ File.separator +"HEAD.txt")
      val currentBranchPath = headContent.get.split(" ")(1)
      val currentBranchName = currentBranchPath.split("/").last
      readBranchByBranchname(currentBranchName)
    }
    catch
      {
        case _: Throwable => None
      }
  }

  def readBranchByBranchname(nameBranch:String):Option[Branch] = {
    try{
      val stringCommits = FileManager.readFile(".sgit"+ File.separator +"logs"+ File.separator +"refs"+ File.separator +"heads"+ File.separator + nameBranch)
      if(stringCommits.isDefined && stringCommits.get != ""){
        val commits = stringCommits.get.split("\n").map(
          str =>
            Commit(
              idHash = str.split(" ")(0),
              author = str.split(" ")(1),
              mail = str.split(" ")(2),
              message = str.split(" ")(3),
              index = Index(readBlobsByCommitIdHash(str.split(" ")(0)).getOrElse(List()))
            )).toList
        Some(Branch(nameBranch, commits))
      } else {
        Some(Branch(nameBranch, List()))
      }
    }
    catch
      {
        case _: Throwable => None
      }
  }

  def getBlobsFromPath(path:String):List[Blob] = {
    try {
      if(FileManager.exists(path) && !path.contains(".sgit")){
        //if it's a directory
        if(FileManager.isDirectory(path)){
          //call add() on each child
          val files = FileManager.listFiles(path).getOrElse(List[String]())
          files.flatMap((str: String) => getBlobsFromPath(path + File.separator + str))
        }
        //if it's a file
        else if(FileManager.isFile(path)){
          //add the file
          List(getBlobFromPath(path))
        }
        else {
          List()
        }
      }
      else {
        List()
      }
    }
    catch
      {
        case _: Throwable => List()
      }
  }

  def getBlobFromPath(path:String): Blob ={
    //read the file to add
    val content = FileManager.readFile(path).getOrElse("")
    //crypt the content
    val cryptedContent = CryptSHA1(content).get
    //return the blob
    if(path.startsWith("/")){
      Blob(cryptedContent, content, path.substring(1))
    } else {
      Blob(cryptedContent, content, path)
    }
  }

  def readBlobsByCommitIdHash(idHash:String):Option[List[Blob]] = {
    try {
      val stringBlobs = FileManager.readFile(".sgit"+ File.separator +"objects"+ File.separator + idHash.substring(0, 2) + "/" + idHash.substring(2))
      val blobs = stringBlobs.getOrElse("").split("\n").map(
        str =>
          Blob(
            idHash = str.split(" ")(0),
            content = FileManager.readFile(".sgit"+ File.separator +"objects"+ File.separator + str.split(" ")(0).substring(0, 2)+ File.separator +str.split(" ")(0).substring(2)).get,
            path = str.split(" ")(1)
          )
      ).toList
      Some(blobs)
    }
    catch {
      case _: Throwable => None
    }
  }
}
