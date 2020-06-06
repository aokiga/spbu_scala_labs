import cats.syntax.all._
import cats.effect._
import cats.effect.concurrent.MVar
import scala.concurrent.duration._

object CatsEffect extends IOApp {
  def runPrinter(mVar: MVar[IO, String]): Resource[IO, Unit] = {

    def print: IO[Unit] = for {
      value <- mVar.take
      _ <- IO(println(value))
      _ <- print
    } yield ()

    Resource.make(print.start)(_.cancel.flatMap(_ => IO(println("Printer closed")))).void
  }

  def runCounter(mVar: MVar[IO, String]): Resource[IO, Unit] = {

    def count(n: Int): IO[Unit] = for {
      _ <- mVar.put(n.toString)
      _ <- IO.sleep(1.seconds)
      _ <- count(n + 1)
    } yield ()

    Resource.make(count(0).start)(_.cancel.flatMap(_ => IO(println("Counter closed")))).void
  }

  val program: Resource[IO, Unit] = for {
    mVar <- Resource.make(MVar.empty[IO, String])(_ => IO(print("")))
    _ <- runCounter(mVar)
    _ <- runPrinter(mVar)
  } yield()

  override def run(args: List[String]): IO[ExitCode] = program.use(_ => IO.never)
}