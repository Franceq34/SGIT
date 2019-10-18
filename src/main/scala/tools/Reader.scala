package tools

import classes.{Blob, Branch, Commit, Index}

object Reader {

  def clearStage(): Boolean = FileManager.writeFile(".sgit/index", text = "")

  /*def currentBlo():Option[Index] ={
    try{
      //Read the index file
      val index = FileManager.readFile(".sgit/index")
      //Map the index in blob list
      val blobs = index.getOrElse("").split("\n").map(str => Blob(str.split(" ")(0), "", str.split(" ")(1)))
      //Return an index object made of blob list
      Some(Index(blobs.toList))
    }
    catch
      {
        case _: Throwable => None
      }
  }*/

  def currentIndex():Option[Index] ={
    try{
      //Read the index file
      val index = FileManager.readFile(".sgit/index")
      //Map the index in blob list
      val blobs = index.getOrElse("").split("\n").map(str => Blob(str.split(" ")(0), "", str.split(" ")(1)))
      //Return an index object made of blob list
      Some(Index(blobs.toList))
    }
    catch
      {
        case _: Throwable => None
      }
  }

  def currentBranch():Option[Branch] = {
    try {
      val headContent = FileManager.readFile(".sgit/HEAD.txt")
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
      val stringCommits = FileManager.readFile(".sgit/logs/refs/heads/" + nameBranch)
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

  def getBlobsFromPath(path:String):List[Blob] ={
    try {
      if(FileManager.exists(path) && !path.contains(".sgit")){
        //if it's a directory
        if(FileManager.isDirectory(path)){
          //call add() on each child
          val files = FileManager.listFiles(path).getOrElse(List[String]())
          files.flatMap((str: String) => getBlobsFromPath(path + "/" + str))
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
      val stringBlobs = FileManager.readFile(".sgit/objects/" + idHash.substring(0, 2) + "/" + idHash.substring(2))
      val blobs = stringBlobs.getOrElse("").split("\n").map(
        str =>
          Blob(
            idHash = str.split(" ")(0),
            content = "",
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
