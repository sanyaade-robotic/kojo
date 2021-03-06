package net.kogics.kojo
package core

import java.awt.Paint
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

import com.vividsolutions.jts.geom.Geometry

import net.kogics.kojo.kgeom.PolyLine
import net.kogics.kojo.util.Utils
import net.kogics.kojo.util.Vector2D

import edu.umd.cs.piccolo.PNode
import edu.umd.cs.piccolo.util.PBounds

trait Picture extends InputAware {
  def canvas: SCanvas
  def myNode = tnode
  def draw(): Unit
  def erase(): Unit
  def isDrawn(): Boolean
  def bounds: PBounds
  def rotate(angle: Double): Unit
  def rotateAboutPoint(angle: Double, x: Double, y: Double): Unit
  def scale(factor: Double): Unit
  def scale(x: Double, y: Double): Unit
  def translate(x: Double, y: Double): Unit
  def translate(v: Vector2D): Unit = translate(v.x, v.y): Unit
  def transv(v: Vector2D) = translate(v.x, v.y): Unit
  def offset(x: Double, y: Double): Unit
  def offset(v: Vector2D): Unit = offset(v.x, v.y): Unit
  def offsetv(v: Vector2D) = offset(v.x, v.y): Unit
  def flipX(): Unit
  def flipY(): Unit
  def opacityMod(f: Double): Unit
  def hueMod(f: Double): Unit
  def satMod(f: Double): Unit
  def britMod(f: Double): Unit
  def transformBy(trans: AffineTransform)
  def setTransform(trans: AffineTransform)
  def dumpInfo(): Unit
  def copy: Picture
  def tnode: PNode
  def axesOn(): Unit
  def axesOff(): Unit
  def visible(): Unit
  def invisible(): Unit
  def toggleV(): Unit
  def isVisible(): Boolean
  def intersects(other: Picture): Boolean
  def collidesWith(other: Picture) = intersects(other)
  def collisions(others: Set[Picture]): Set[Picture] = {
    others.filter { this intersects _ }
  }
  def collision(others: Seq[Picture]): Option[Picture] = {
    others.find { this intersects _ }
  }
  def intersection(other: Picture): Geometry
  def contains(other: Picture): Boolean
  def distanceTo(other: Picture): Double
  def area: Double
  def perimeter: Double
  def picGeom: Geometry

  def position: Point
  def setPosition(x: Double, y: Double): Unit
  def setPosition(p: Point): Unit = setPosition(p.x, p.y)
  def heading: Double
  def setHeading(angle: Double)
  def scaleFactor: (Double, Double)
  def setScaleFactor(x: Double, y: Double): Unit
  def transform: AffineTransform
  def setPenColor(color: Paint)
  def setPenThickness(th: Double)
  def setFillColor(color: Paint)
  def opacity: Double
  def setOpacity(o: Double)
  @deprecated("Use picture.react instead", "2.1")
  def act(fn: Picture => Unit) = react(fn)
  def react(fn: Picture => Unit): Unit
  @deprecated("Use picture.react instead", "2.1")
  def animate(fn: => Unit) {
    react { me =>
      fn
    }
  }
  def stopReactions(): Unit
  // provide these explicitly, so that subclasses that are case
  // classes can live within sets and maps
  override def equals(other: Any) = this eq other.asInstanceOf[AnyRef]
  override def hashCode = System.identityHashCode(this)

  def morph(fn: Seq[PolyLine] => Seq[PolyLine])
  def foreachPolyLine(fn: PolyLine => Unit)
  def toImage: BufferedImage
  def forwardInputTo(p: Picture) = Utils.runInSwingThread {
    tnode.getInputEventListeners.foreach { tnode.removeInputEventListener(_) }
    p.tnode.getInputEventListeners.foreach { tnode.addInputEventListener(_) }
  }
  def moveToFront() {
    tnode.moveToFront()
  }
  def moveToBack() {
    tnode.moveToBack()
  }
  def showNext(): Unit = showNext(100)
  def showNext(gap: Long): Unit
  def update(newData: Any): Unit
  def checkDraw(msg: String): Unit
  def beside(other: Picture): Picture
  def above(other: Picture): Picture
  def below(other: Picture): Picture = other.above(this)
  def on(other: Picture): Picture
  def under(other: Picture): Picture = other.on(this)
}
