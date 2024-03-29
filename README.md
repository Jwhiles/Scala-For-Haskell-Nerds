# scala-stuff

scala-stuff

## Differences from Haskell

### Data Types

declaring data types is uglier

```hs
data List a = Cons a (List a) | Nil
```

becomes

```scala
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]
```

#### Companion Objects
In scala we tend to create 'companion objects' to our data types - these contain
functions which operator on the data type.

For example a companion object for the List data type above might contain
functions to reverse, filter, or sort the list.

```
object List
  def reverse [A](xs: List[A]): List[A] = reverseHelper(xs, Nil)
  // a function that we are exposing

  private def reverseHelper [A](xs: List[A], acc: List[A]): List [A]
    = xs match {
      case Nil => acc
      case Cons(head, tail) = reverseHelper(tail, Cons(head, acc))
    }
  // a privater helper function that we use internally
```



### Stuff about functions

All functions are declared as part of an object or class..
Functions in scala implicitly return

```scala
object IHoldFunctions {
  def identity[A](a: A): A = a
}
// this returns its argument
```

we can also write multi line functions - by wrapping the function body in
curlies. If we do this the last line of the function is implicitly returned
like so

```scala
def badIdentity[A](a: A): A = {
  launchThe(missiles)
  a
}
// this still returns its argument
```

maybe this is time to mention

#### Functions with variable numbers of arguments
We can define functions that for a given type, accept zero or more arguments of
that type.

```
  def why(as: Int*): Int = 
    if (as.isEmpty) 0
    else as.head + (why(as.tail: _*))
```



Why not define these as functions that take a list you ask? 

Why not indeed

These sorts of functions are actually syntactic sugar for using the interface `Seq`.


Scala data types often have an `apply` function, which is used to construct
instances of that datatype. These variadic functions are thus required to build
instances of data types like Lists - for example the idiomatic `List(1,2,3)`
requires an apply method to work

### Scala has absolutely out of control side effects

Do anything you want, anywhere, all the time. Assume that every line of every
program says `unsafePerformIO`

### Stuff about currying

Scala functions are not all automatically curried. If we define a function like this

```
def add(a: Int, b: Int): Int = a + b
```

it is not curried

instead we should write it like so

```
def add(a: Int)(b: Int): Int = a + b
```

scalazors refer to this recall to such functions as having [Multiple Parameter Lists](https://docs.scala-lang.org/tour/multiple-parameter-lists.html).

But a better way to think about it is that all scala functions have accept some
number of tuples of various sizes.

### Non Strictness
We can manually force our functions to be non strict

```
def lazyIf[A](cond: Boolean, onTrue: () => A, onFalse: () => A): A =
  if (cond) onTrue() else onFalse()

lazyIf(a < 22,
  () => "a",
  () => "b"
)
```

Essentially we are just turning arguments we want to be lazy into thunks.


defining functions like this - and having to manually thunkify our function calls
is kind of lame though. So Scala provides some syntactic sucre.

```
def lazyIf[A](cond: Boolean, onTrue: => A, onFalse: => A): A =
  if (cond) onTrue else onFalse
```

We can then call this function like so
```
lazyIf(a < 22, "a", "b")
```



### But HOW do we do a type class

Scala gives us the tool to roughly emulate Haskell's type classes.

For example

```scala
trait Monoid[A] {
  def mappend(a1: A, a2: A): A
  def mempty: A
}
// this is like a type class definition. We describe what it takes to make a
// monoid
// 1) some type A
// 2) an append operation
// 3) a member of the type A - which acts as an identity element


implict object IntMonoid extends Monoid[Int] {
  def mappend(a1: Int, a2: Int): Int = a1 + a2
  def mempty: Int = 0
}
// Defining the sum monoid over integers
// note the implicit keyword - that tells the compiler to use this
implementation when we ask for an implicit implementation


def mconcat (xs: List[A])(implicit m: Monoid[A]): A =
  xs.foldRight(m.mempty)(m.mappend)
// We can then implement functions based on our monoid. Wow

mconcat(List(1,2,3,4))

// We can also add monoids for other types
implicit object StringMonoid extends Monoid[String] {
  def mapp(a1: String, a2: String): String = a1 ++ a2
  def memp: String = ""
}

// and reuse our functions
mconcat(List("hey", "hun"))
```

But what if I want to define multipl monoids for the same type?!

```
object IntProdMonoid extends Monoid[Int] {
  def mappend(a1: Int, a2: Int): Int = a1 * a2
  def mempty: Int = 1
}
// we can't label this as implicit as well - or everything will break
```

In Haskell we would use newtypes. I've no idea what the scala approach is!

### Stuff about MAIN functions

Much like Haskell, scalasters like having a main function. There are some
differences though. Rather having a type of `IO ()` scala expects main functions
to have a type of `(args: Array[String]): Unit`.

This type tells us that scala expects to recieve some arguments passed in when
run. Unit represents that the main function doesn't actually return anything.

Another interesting quirk of scala is that you can define multiple main
functions. If you do, everything will compile - and when you run the program you
will be asked which main function you want to run.

### Imports

Like in many languages Scala has modules - and you can import them into other
modules.

```
import module._                            // import everything
import module.SomeThing                    // import a thing
import module.{SomeThing, SomeThingElse}   // import a multitude of thigns
import module.{Horse => Pferd}             // import a thing and rename it
```

## Tools

SBT is the go to tools
useful stuff

- `sbt` will open up the interactive sbt tool. From there you can do a number of
  useful commands such as

- `console` will open a repl. You can load modules into it with :load:w
- `~compile` will watch your code for changes and tell you if it is wrong
