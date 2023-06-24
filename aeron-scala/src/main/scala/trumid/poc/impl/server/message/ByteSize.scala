package trumid.poc.impl.server.message

object ByteSize {
  val CHAR_SIZE = 2
  val BOOL_SIZE = 1
  val INT_SIZE = 4
  val LONG_SIZE = 8
  val FLOAT_SIZE = 4
  val DOUBLE_SIZE = 8
  val DECIMAL_SIZE = 9
  val SHORT_SIZE = 2
  val BYTE_SIZE = 1
  val STRING_SIZE = 6 // this is the offset and length
  val DATE_SIZE = 4
  val TIME_SIZE = 5
  val DATE_TIME_SIZE: Int = DATE_SIZE + TIME_SIZE
  val KILOBYTE_SIZE = 1024
  val MEGABYTE_SIZE = 1048576
}
