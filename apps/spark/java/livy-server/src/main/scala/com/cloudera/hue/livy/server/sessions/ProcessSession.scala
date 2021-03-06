package com.cloudera.hue.livy.server.sessions

import java.lang.ProcessBuilder.Redirect
import java.net.URL

import com.cloudera.hue.livy.{Utils, Logging}
import com.cloudera.hue.livy.server.sessions.Session.SessionFailedToStart

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.io.Source

object ProcessSession extends Logging {
  def create(id: String, lang: String): Session = {
    val process = startProcess(id, lang)
    new ProcessSession(id, process)
  }

  // Loop until we've started a process with a valid port.
  private def startProcess(id: String, lang: String): Process = {
    val pb = new ProcessBuilder(
      "spark-submit",
      "--class", "com.cloudera.hue.livy.repl.Main",
      Utils.jarOfClass(getClass).head,
      lang)

    val callbackUrl = System.getProperty("livy.server.callback-url")
    pb.environment().put("LIVY_CALLBACK_URL", f"$callbackUrl/sessions/$id/callback")
    pb.environment().put("LIVY_PORT", "0")

    pb.redirectOutput(Redirect.INHERIT)
    pb.redirectError(Redirect.INHERIT)

    pb.start()

  }
}

private class ProcessSession(id: String, process: Process) extends WebSession(id) {

  override def stop(): Future[Unit] = {
    super.stop() andThen { case r =>
      // Make sure the process is reaped.
      process.waitFor()

      r
    }
  }
}
