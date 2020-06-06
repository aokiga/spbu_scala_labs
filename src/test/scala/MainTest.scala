import java.nio.file.{Files, Path, Paths}
import scala.reflect.io.Directory

import cats.Id
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MainTest extends AnyFlatSpec with Matchers {
  implicit val fs: RealFileSystem[Id] = new RealFileSystem[Id]
  implicit val printer: ConsoleRealPrinter[Id] = new ConsoleRealPrinter[Id]
  val program = new Program[Id, Path, Path]

  val root: Path = Paths.get("./tmp")
  Files.createDirectory(root)

  program.run(root, "test_dir", List("foo", "bar", "baz"))

  var test_dir: Path = root.resolve("test_dir")


  var f: Path = test_dir.resolve("f")
  var b: Path = test_dir.resolve("b")

  Files.exists(test_dir) shouldBe true
  Files.isDirectory(test_dir) shouldBe true

  Files.exists(f) shouldBe true
  Files.isDirectory(f) shouldBe true

  Files.exists(b) shouldBe true
  Files.isDirectory(b) shouldBe true

  val foo: Path = f.resolve("foo")
  val bar: Path = b.resolve("bar")
  val baz: Path = b.resolve("baz")

  Files.exists(foo) shouldBe true
  Files.exists(bar) shouldBe true
  Files.exists(baz) shouldBe true

  val tmp = new Directory(root.toFile)
  tmp.deleteRecursively()
}