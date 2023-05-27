package org.ternlang.tru.model

import org.ternlang.core.annotation.Annotation

trait Annotated {
  def getAnnotations(): Map[String, Annotation];
}
