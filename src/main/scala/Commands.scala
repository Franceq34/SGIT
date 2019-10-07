import crypt.{Crypt, CryptSHA1}

object Commands {

  def add(path:String): Unit ={
    //create the file manager
    val fm:FileManager = new FileManager("/home/quentin/IdeaProjects/output/")
    //read the file to add
    val content = fm.readFile(path).getOrElse("")
    //create the encryptor
    val cr:Crypt = new CryptSHA1()
    val cryptedContent = cr.crypt(content)
    //write the staged files in index
    fm.writeFile(".sgit/index", cryptedContent)
    //create the correct directory in objects folder
    fm.createDir(".sgit/objects/" + cryptedContent.substring(0, 2))
    //write the staged files in the objects
    print(fm.writeFile(".sgit/objects/" + cryptedContent.substring(0, 2) + "/" + cryptedContent.substring(2), content))

  }
}
