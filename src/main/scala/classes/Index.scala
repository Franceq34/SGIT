package classes

import tools.FileManager

case class Index(list: List[Blob] = List()){

  def current():Option[Index] ={
    try{
      val index = FileManager.readFile(".sgit/index")
      println(index)
      val blobs = index.getOrElse("").split("\n").map(str => Blob(str.split(" ")(0), "", str.split(" ")(1)))
      println("blobs : "+blobs.toList)
      Some(Index(blobs.toList))
    }
    catch
      {
        case _: Throwable => return None
      }
  }

  def add(blob:Blob):Index ={
    val blobPosition = list.indexOf(blob)
    //If the list doesnt contain
    if(blobPosition != -1){
      Index(list ++ List(blob))
    }
    else {
      Index(list.updated(blobPosition, blob))
    }
  }

  def update(oldIndex: Index):Index = {
    //Ajoute à la liste de this tous les blobs de oldIndex qui ne sont pas déjà présents
    Index(list ++ oldIndex.list.flatMap{
      case blob if !list.contains(blob) => Some(blob)
      case _ => None
    })
  }

  def containsBlob(blob: Blob):Boolean = { list.exists(b => blob.hasSamePathThan(b) || blob.hasSameHashThan(b)) }

  def save(): Boolean ={
    val newList = list.map(b => b.toStringIndex())
    FileManager.writeFile(".sgit/index", newList.mkString("\n").concat("\n"))
  }
}
