package org.ternlang.tru

import org.ternlang.common.store.ClassPathStore
import org.ternlang.common.thread.ThreadPool
import org.ternlang.compile.StoreContext
import org.ternlang.compile.assemble.OperationBuilder
import org.ternlang.parse.{CharacterToken, Line}
import org.ternlang.tru.domain.DomainLoader
import org.ternlang.tru.model.Version

import java.net.URL
import java.util.concurrent.Executors

object HelloScala extends App {

  private val store = new ClassPathStore
  private val context = new StoreContext(store)

  val t = context.getLoader().loadType("org.ternlang.tru.Foo")
  t.getFunctions.forEach(x => {
    println(x)
  })

  val a = new OperationBuilder(context, new ThreadPool(1))
  val x = new Array[Object](2)
  x(0) = "x"
  x(1) = java.lang.Boolean.TRUE
  println(x.getClass)
  //AnnotationType(reference: Boolean, name: String, attribute: Option[String], attributes: List[String] = List.empty)
  val i = a.create(t, x, new BasicLine)
  println(i)
  println("Hello Scala"+ new CharacterToken('c'))


  val loader = new DomainLoader(Version(1, true))
  val domain = loader.load(Seq[URL](new java.io.File("C:\\Work\\development\\tern-lang\\tern\\tern-scala\\src\\main\\resources\\example.tru").toURL))
  println(domain.scope)
}

class BasicLine extends Line{
  override def getResource: String = "x"
  override def getSource: String = "x"
  override def getNumber: Int = 1
}

class Foo(a: String, b: Boolean, c: Array[String]) {

  def this(a: String, b: Boolean) = this(a, b, Array.empty)

  override def toString = "Foo....."

}

