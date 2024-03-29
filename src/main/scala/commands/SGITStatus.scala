package commands
import classes.{Blob, Branch, Index}
import tools.{Printer, Reader}

object SGITStatus {

  def apply(): Unit = {
    status(Reader.currentBranch(), Reader.getBlobsFromPath("."), Reader.currentIndex())
  }

  def status(currentBranch:Option[Branch], blobsFromWD: List[Blob], blobsFromIndex: Option[Index]): Unit ={
    if(currentBranch.isDefined){
      val currentBranchDefined = currentBranch.get
      val blobsFromIndexDefined = blobsFromIndex.getOrElse(Index())
      val allBranchBlobs = currentBranchDefined.getAllBlobs
      val blobsUncommitted = Index(allBranchBlobs).getNewBlobs(Index(blobsFromWD))
      val blobsBeforeUntracked = Index(allBranchBlobs).getNewBlobs(blobsUncommitted)
      val blobsUntracked = blobsFromIndexDefined.getNewBlobs(blobsBeforeUntracked)
      val blobsBeforeUpdated = Index(allBranchBlobs).getBlobsUpdated(Index(blobsFromWD))
      val blobsUpdated = blobsFromIndexDefined.getNewBlobs(blobsBeforeUpdated)

      //Print current branch
      Printer.currentBranch(currentBranchDefined)

      if(allBranchBlobs.isEmpty) Printer.noCommits()

      //Print untracked files
      if(blobsUntracked.size>0) Printer.untrackedFiles(blobsUntracked)

      //Print tracked files not committed
      if(blobsFromIndexDefined.size>0) Printer.changesToBeCommitted(blobsFromIndexDefined)
      else
      if (blobsUntracked.list.isEmpty) Printer.noChangesAdded()
      else Printer.nothingAddedButUntrackedFiles()

      //Print files updated since last add
      if(blobsUpdated.size>0) Printer.changesNotStaged(blobsUpdated)
    } else {
      Printer.fatalError()
    }
  }
}
