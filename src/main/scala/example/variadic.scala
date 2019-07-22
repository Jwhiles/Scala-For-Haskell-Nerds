object variadic {
  def why(as: Int*): Int = 
    if (as.isEmpty) 0
    else as.head + (why(as.tail: _*))
}
