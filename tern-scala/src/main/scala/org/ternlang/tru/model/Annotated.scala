package org.ternlang.tru.model

import org.ternlang.core.annotation.Annotation
import java.util.Map

trait Annotated {
  def getAnnotations(): Map[String, Annotation];
}
