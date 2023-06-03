package org.ternlang.tru.codegen.common

import org.ternlang.tru.model.{Mode, Version}

class GeneratedFile(val path: String, val source: String, val version: Version, val mode: Mode) {
  def getVersion: Version = version
  def getMode: Mode = mode
  def getSource: String = source
  def getPath: String = mode.getPath(path, version)
}