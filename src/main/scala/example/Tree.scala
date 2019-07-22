package treez



sealed trait Tree[+A]
case object Bud extends Tree[Nothing]
case class Leaf[+A](value: A) extends Tree[A]
case class Node[+A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]

object Tree {
  def insert[T <% Ordered[T]](tree: Tree[T], x: T): Tree[T] = tree match {
    case Bud => Leaf(x)
    case Leaf(y) => 
      if (x < y) Node(Leaf(x), y, Bud)
      else if (x > y) Node(Bud, y, Leaf(x))
      else Leaf(y)
    case Node(l, y, r) => 
      if (x < y) Node(insert(l, x), y, r)
      else if (x > y) Node(l, y, insert(r, x))
      else Node(l, y, r)
  }


  private def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B =
    as match {
    case Nil => z
    case x :: xs => f(x, foldRight(xs, z)(f))
    }

  def listToTree[T <% Ordered[T]](xs: List[T]): Tree[T] =
    foldRight(xs, Bud: Tree[T])((x, acc) => insert(acc, x))
}
