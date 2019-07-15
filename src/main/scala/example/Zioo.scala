import zio.App
import zio.console._

object MyApp extends App {
  def run(args: List[String]) =
    myAppLogic.fold(_ => 1, _ => 0)

  val myAppLogic =
    for {
      _ <- putStrLn("hello, what u")
      name <- getStrLn
      _ <- putStrLn(s"hello ${name} welcome")
    } yield ()
}
