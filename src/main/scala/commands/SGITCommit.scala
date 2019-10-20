package commands

import classes.{Branch, Commit, Index}
import tools.{CryptSHA1, Reader}

object SGITCommit {

  def apply(author: String = "Unknown", mail: String = "noemail", message : String="Default"): Boolean = {
    commit(Reader.currentIndex(), Reader.currentBranch(), author, mail, message)
  }

  def commit(currentIndexOption: Option[Index], currentBranchOption: Option[Branch], author: String, mail: String, message: String):Boolean = {
    if( currentIndexOption.isDefined && currentBranchOption.isDefined) {
      val currentIndex = currentIndexOption.get
      val currentBranch = currentBranchOption.get
      val commitHash = CryptSHA1(currentIndex.toString).get
      val commit = Commit(commitHash, author: String, mail: String, message : String, currentIndex)
      //ajouter le commit à la fin de la branche (logs/refs/heads/branchname)
      val newBranch:Branch = currentBranch.addCommit(commit)
      newBranch.saveToLogs()
      //remplacer le commit courant de la branche (refs/heads/branchname)
      newBranch.saveToRefs()
      //ajouter le commit dans objects
      commit.saveToObjects()
      //vider le stage
      Reader.clearStage()
    }
    currentIndexOption.isDefined && currentBranchOption.isDefined
  }
}
