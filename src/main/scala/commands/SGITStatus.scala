package commands
import classes.Index
import tools.Reader

object SGITStatus {

  def apply(): Unit = {
    //Print current branch
    val currentBranch = Reader.currentBranch()
    currentBranch match {
      case Some(branch) => {
        val blobsFromWD = Reader.getBlobsFromPath("test")
        val allBranchBlobs = branch.getAllBlobs()
        val blobsUncommitted = Index(allBranchBlobs).getNewBlobs(Index(blobsFromWD))
        val blobsFromIndex = Reader.currentIndex().getOrElse(Index())
        val blobsUntracked = blobsFromIndex.getNewBlobs(blobsUncommitted)


        //Print current branch
        println("On branch " + branch.name + "\n")

        //Print untracked files
        if(blobsUntracked.size>0) println("Untracked files:\n"+blobsUntracked + "\n")

        //Print tracked files not committed
        if(blobsFromIndex.size>0) println("Changes to be committed:\n"+blobsFromIndex + "\n")

        //Print files updated since last add
        println("Changes not staged for commit:")

      }
      case None => println("Fatal error")
    }
  }
}
