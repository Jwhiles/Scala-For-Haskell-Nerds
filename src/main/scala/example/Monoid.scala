// package jonoid

trait Monoid[A] {
  def mapp(a1: A, a2: A): A
  def memp: A
}



object MonoidStuff {
  def mconcat [A](xs: List[A])(implicit m: Monoid[A]): A =
    xs.foldLeft(m.memp)(m.mapp)

  implicit object IntMonoid extends Monoid[Int] {
    def mapp(a1: Int, a2: Int): Int = a1 + a2
    def memp: Int = 0
  }

  implicit object StringMonid extends Monoid[String] {
    def mapp(a1: String, a2: String): String = a1 ++ a2
    def memp: String = ""
  }

  implicit object IntProdMonoid extends Monoid[Int] {
    def mapp(a1: Int, a2: Int): Int = a1 * a2
    def memp: Int = 1
  }
}
