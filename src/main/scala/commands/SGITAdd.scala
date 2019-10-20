package commands
import classes.{Blob, Index}
import tools.Reader
object SGITAdd {

  def apply(path:String): Boolean ={
    add(Reader.currentIndex(), Reader.getBlobsFromPath(path))
  }

  def add(currentIndexOption:Option[Index], blobs: List[Blob]): Boolean ={
    if(currentIndexOption.isDefined){
      val currentIndex = currentIndexOption.get
      val indexToSave = currentIndex.getNewBlobs(Index(blobs))
      //Save new stage
      if(!indexToSave.isEmpty){
        indexToSave.saveAsStage()
        //Save blob objects
        indexToSave.list.map(blob => blob.addToObject())
      }
    }
    currentIndexOption.isDefined
  }
}
