package commands
import classes.{Blob, Index}
import tools.{CryptSHA1, FileManager, Reader}
object SGITAdd {

  def apply(path:String): Boolean ={
    //Read current stage
    val currentIndex = Reader.currentIndex().getOrElse(Index())
    println("current : " + currentIndex.list)
    //Getting blobs from query
    val blobs = getBlobs(path)
    println("new blobs : " + blobs)
    //Create new stage
    val indexToSave = Index(blobs).update(currentIndex)
    println("index : " + indexToSave)
    //Save new stage
    indexToSave.saveAsStage()
    //Save blob objects
    indexToSave.list.map(blob => blob.addToObject())
    true
  }

  def getBlobs(path:String):List[Blob] ={
    if(FileManager.exists(path) && !path.contains(".sgit")){
      //if it's a directory
      if(FileManager.isDirectory(path)){
        //call add() on each child
        val files = FileManager.listFiles(path).getOrElse(List[String]())
        files.flatMap((str: String) => getBlobs(path + "/" + str))
      }
      //if it's a file
      else if(FileManager.isFile(path)){
        //add the file
        List(getBlob(path))
      }
      else {
        List()
      }
    }
    else {
      List()
    }
  }

  def getBlob(path:String): Blob ={
    //read the file to add
    val content = FileManager.readFile(path).getOrElse("")
    //create the encryptor
    val cryptedContent = CryptSHA1(content).get
    if(path.startsWith("/")){
      Blob(cryptedContent, content, path.substring(1))
    } else {
      Blob(cryptedContent, content, path)
    }
  }
}
