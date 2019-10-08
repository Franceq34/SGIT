package commands
import tools.FileManager
import crypt.{Crypt, CryptSHA1}

object Add {

  def apply(fileManager: FileManager, path:String): Boolean ={
    if(fileManager.exists(path) && !path.startsWith(".sgit")){
      //if it's a directory
      if(fileManager.isDirectory(path)){
        //call add() on each child
        //if the result doesn't contains false, then all the operations succeeded
        !fileManager.listFiles(path).getOrElse(List[String]()).map((str:String) => apply(fileManager, path+"/"+str)).contains(false)
      }
      //if it's a file
      else if(fileManager.isFile(path)){
        //add the file
        addFile(fileManager, path)
        true
      }
      else {
        false
      }
    }
    else {
      false
    }
  }

  def addFile(fileManager: FileManager, path:String): Unit ={
    //read the file to add
    val content = fileManager.readFile(path).getOrElse("")
    //create the encryptor
    val cr:Crypt = new CryptSHA1()
    val cryptedContent = cr.crypt(content).get
    //write the staged files in index
    fileManager.writeFile(".sgit/index", cryptedContent)
    //create the correct directory in objects folder
    fileManager.createDir(".sgit/objects/" + cryptedContent.substring(0, 2))
    //write the staged files in the objects
    fileManager.writeFile(".sgit/objects/" + cryptedContent.substring(0, 2) + "/" + cryptedContent.substring(2), content)

  }
}
