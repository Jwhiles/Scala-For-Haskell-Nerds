# scala-stuff

scala-stuff

## Differences from Haskell

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

### Scala has absolutely out of control side effects
Do anything you want, anywhere, all the time. Assume that every line of every
program says `unsafePerformIO`


### Stuff about currying

Scala functions are not all automatically curried
if we define a function like this

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



## Tools

SBT is the go to tools
useful stuff

- `sbt console` will open a repl. You can load modules into it with :load:w
