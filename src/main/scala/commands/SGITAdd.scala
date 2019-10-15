package commands
import classes.{Blob, Index}
import tools.{CryptSHA1, FileManager, Reader}
object SGITAdd {

  def apply(path:String): Boolean ={
    //Read current stage
    val currentIndex = Reader.currentIndex().getOrElse(Index())
    //Getting blobs from query
    val blobs = Reader.getBlobsFromPath(path)
    //Create new stage
    val indexToSave = currentIndex.getNewBlobs(Index(blobs))
    //Save new stage
    if(!indexToSave.isEmpty){
      indexToSave.saveAsStage()
      //Save blob objects
      indexToSave.list.map(blob => blob.addToObject())
    }
    !indexToSave.isEmpty
  }
}
