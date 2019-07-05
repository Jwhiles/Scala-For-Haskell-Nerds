package example


object MyModule {
  def abs(n: Int): Int =
    if (n < 0) -n
    else n


  def findFirst[A](as: Array[A], p: A => Boolean): Int = {
    @annotation.tailrec
    def loop(n: Int): Int =
      if (n > as.length) -1
      else if (p(as(n))) n
      else loop(n + 1)

    loop(0)
  }

  def main(args: Array[String]): Unit = {
    val answer = findFirst(Array(5, 6, 7, 8, 7, 8), (x: Int) => x == 7)
    def addTwo: Int => Int = curry(canIcurry)(5)
    println(answer)
    identity(4)
    badidentity(4)
    println(addTwo compose curry(canIcurry)(4))
  }

  def canIcurry(a: Int, b: Int): Int = a + b

  def curry[A,B,C](f: (A,B) => C): A => (B => C) =
    (a: A) => (b: B) => f(a,b)

  def compose[A,B,C](f: (A => B))(g: (B => C)): A => C =
    (a: A) => g(f(a))

  def identity[A](a: A): A = a

  def badidentity[A](a: A): A = {
    println("hey")
    a
  }
}


sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = foldR(ints, 0)(_ + _)



  @annotation.tailrec
  def foldR[A,B](xs: List[A], acc: B)(f: ((A, B) => B)): B = xs match {
    case Nil => acc
    case Cons(head, tail) => foldR(tail, f(head, acc))(f)
  }
}
