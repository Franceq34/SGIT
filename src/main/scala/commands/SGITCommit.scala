package commands

import classes.{Branch, Commit, Index}
import tools.{CryptSHA1, Reader}

object SGITCommit {

  def apply(author: String = "Unknown", mail: String = "noemail", message : String="Default"): Boolean = {
    //recuperer l'index courant
    val currentIndex = Reader.currentIndex().getOrElse(Index())
    val commitHash = CryptSHA1(currentIndex.toString).get
    val commit = Commit(commitHash, author: String, mail: String, message : String, currentIndex)
    //recuperer la branche courante dans le HEAD
    val currentBranch = Reader.currentBranch()
    //ajouter le commit à la fin de la branche (logs/refs/heads/branchname)
    val newBranch:Branch = currentBranch.get.addCommit(commit)
    newBranch.saveToLogs()
    //remplacer le commit courant de la branche (refs/heads/branchname)
    newBranch.saveToRefs()
    //ajouter le commit dans objects
    commit.saveToObjects()
    //vider le stage
    Reader.clearStage()
  }
}
