package commands
import classes.{Blob, Branch, Commit}
import tools.{Diff, Printer, Reader}

object SGITLog {

  def apply(parameter: String = ""): Unit ={
    log(Reader.currentBranch())
  }

  def log(maybeCurrentBranch:Option[Branch]): Boolean = {
    if (maybeCurrentBranch.isDefined) {
      val currentBranch = maybeCurrentBranch.get
      Printer.logCommits(currentBranch.commits)
      true
    } else false
  }
}
