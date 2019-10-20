package commands

import classes.{Blob, Branch, Commit}
import tools.{Diff, Printer, Reader}

object SGITLogP {
  def apply():Unit = {
    logP(Reader.currentBranch())
  }

  def logP(maybeCurrentBranch:Option[Branch]): Boolean = {
    try{
      if (maybeCurrentBranch.isDefined) {

        val currentBranch = maybeCurrentBranch.get
        @scala.annotation.tailrec
        def aux(commits: List[Commit], blobsFirstCommit: List[Blob]):Unit = {
          if(commits.size == 1) Printer.logCommits(commits)
          else {
            Printer.logCommits(List(commits(0)))
            val blobsNextCommit = Reader.readBlobsByCommitIdHash(commits(1).idHash)
            val listBlobsUpdated = Diff.diffBlobs(blobsNextCommit.get, blobsFirstCommit)
            listBlobsUpdated.foreach(blobUpdated => Printer.diffs(blobUpdated))
            aux(commits.drop(1), blobsNextCommit.get)
          }
        }
        val commitsOrdered = currentBranch.commits.reverse
        aux(commitsOrdered, Reader.readBlobsByCommitIdHash(commitsOrdered(0).idHash).get)
        true
      } else false
    }
    catch {
      case _: Throwable => false
    }
  }
}

