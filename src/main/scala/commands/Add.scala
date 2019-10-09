package commands
import classes.{Blob, Index}
import tools.FileManager
import crypt.{Crypt, CryptSHA1}

object Add {

  def apply(path:String): Boolean ={
    val index = Index()
    //Read current stage
    val currentIndex = index.current().getOrElse(Index())
    println("current : " + currentIndex.list)
    //Getting blobs from query
    val blobs = getBlobs(path)
    println("new blobs : " + blobs)
    //Create new stage
    val indexToSave = Index(blobs).update(currentIndex)
    println("index : " + indexToSave)
    //Save new stage
    indexToSave.save()
    //Save blob objects
    indexToSave.list.map(blob => addObject(blob))
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
    val cr:Crypt = new CryptSHA1()
    val cryptedContent = cr.crypt(content).get
    if(path.startsWith("/")){
      Blob(cryptedContent, content, path.substring(1))
    } else {
      Blob(cryptedContent, content, path)
    }
  }

  def addObject(blob: Blob): Boolean ={
    println("j'add : "+ blob)
    //create the correct directory in objects folder
    FileManager.createDir(".sgit/objects/" + blob.idHash.substring(0, 2))
    //write the staged files in the objects
    FileManager.writeFile(".sgit/objects/" + blob.idHash.substring(0, 2) + "/" + blob.idHash.substring(2), blob.content)
  }
}
