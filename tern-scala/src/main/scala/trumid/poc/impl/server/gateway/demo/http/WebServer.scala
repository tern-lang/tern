package trumid.poc.impl.server.gateway.demo.http

import org.simpleframework.http._
import org.simpleframework.http.core._
import org.simpleframework.http.socket._
import org.simpleframework.http.socket.service._
import org.simpleframework.transport.connect.SocketConnection

import java.io.File
import java.io.FileInputStream
import java.net.InetSocketAddress

class WebServer(port: Int, publisher: JsonPublisher) extends Container with Service {

  def start(): Unit = {
    val router = new DirectRouter(this)
    val container = new RouterContainer(this, router, 2)
    val processor = new ContainerSocketProcessor(container)
    val connection = new SocketConnection(processor)
    val address = new InetSocketAddress(port)

    connection.connect(address)
  }

  override def handle(request: Request, response: Response): Unit = {
    val time = System.currentTimeMillis()

    println(request)
    response.setStatus(Status.OK)
    response.setLong(Protocol.DATE, time)
    request.getPath.getPath match {
      case "/index.js" =>
        response.setContentType("application/javascript")
        handle("/index.js", response)
      case "/logo.png" =>
        response.setContentType("image/png")
        handle("/logo.png", response)
      case _ =>
        response.setContentType("text/html")
        handle("/index.html", response)
    }
  }

  override def connect(session: Session): Unit = {
    publisher.onJoin((message) => {
      session.getChannel().send(message)
    })
  }

  private def handle(path: String, response: Response) = {
    //val file = new File("C:/Work/development/tern-lang/tern/tern-scala/src/main/resources", path)
    //val source = new FileInputStream(file)
    try {
      val source = getClass.getResourceAsStream(path)
      val out = response.getOutputStream(1024)

      try {
        var running = true

        while (running) {
          val octet = source.read()

          if (octet <= -1) {
            running = false
          } else {
            out.write(octet)
          }
        }
      } finally {
        source.close()
        out.close()
      }
    } catch {
      case e: Throwable => {
        val out = response.getPrintStream()
        e.printStackTrace(out)
        out.close()
      }
    }
  }
}
