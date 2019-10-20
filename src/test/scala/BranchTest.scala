import classes.{Blob, Branch, Commit, Index}
import org.scalatest.FlatSpec

class BranchTest extends FlatSpec{

  "addCommit" should "return a new Branch containing the new Commit" in {
    val commit1 = Commit("a", "b", "c", "d", Index())
    val branch = Branch("a", List(commit1))
    val commitToAdd = Commit("e", "b", "c", "d", Index())
    val branchWithNewCommit = branch.addCommit(commitToAdd)
    assert(branchWithNewCommit.commits.size == branch.commits.size + 1)
    assert(branchWithNewCommit.commits.contains(commitToAdd))
  }

  "getAllBlobs" should "return all the blobs in the branch" in {
    val blob1 = Blob("a", "b", "c")
    val blob2 = Blob("d", "e", "f")
    val commit = Commit("a", "b", "c", "d", Index(List(blob1, blob2)))
    val branch = Branch("a", List(commit))
    assert(branch.getAllBlobs == List(blob1, blob2))
  }

  it should "return all the blobs in the branch in all the commits" in {
    val blob1 = Blob("a", "b", "c")
    val blob2 = Blob("d", "e", "f")
    val commit1 = Commit("a", "b", "c", "d", Index(List(blob1)))
    val commit2 = Commit("a", "b", "c", "d", Index(List(blob2)))
    val branch = Branch("a", List(commit1, commit2))
    assert(branch.getAllBlobs == List(blob1, blob2))
  }

  it should "return all the blobs in the branch in all the commits only once when blobs are duplicated" in {
    val blob1 = Blob("a", "b", "c")
    val blob2 = Blob("d", "e", "f")
    val commit1 = Commit("a", "b", "c", "d", Index(List(blob1)))
    val commit2 = Commit("a", "b", "c", "d", Index(List(blob1, blob2)))
    val branch = Branch("a", List(commit1, commit2))
    assert(branch.getAllBlobs == List(blob1, blob2))
  }
}
