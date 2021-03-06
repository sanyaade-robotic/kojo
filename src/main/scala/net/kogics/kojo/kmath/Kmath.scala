package net.kogics.kojo
package kmath

import scala.language.implicitConversions

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.util.MathUtils

object Kmath {
  implicit def seqToArrD(seq: Seq[Double]): Array[Double] = seq.toArray
  implicit def seqToArrI(seq: Seq[Int]): Array[Double] = seq.map { _.toDouble }.toArray

  def mean(nums: Array[Double]) = StatUtils.mean(nums)
  def variance(nums: Array[Double]) = StatUtils.variance(nums)
  def variance(nums: Array[Double], mean: Double) = StatUtils.variance(nums, mean)

  def constrain(value: Double, min: Double, max: Double) = util.Math.constrain(value, min, max)
  def map(value: Double, low1: Double, high1: Double, low2: Double, high2: Double) = util.Math.map(value, low1, high1, low2, high2)
  def lerp(value1: Double, value2: Double, amt: Double) = util.Math.lerp(value1, value2, amt)
  
  def gcd(p: Int, q: Int) = MathUtils.gcd(p, q)
  def gcd(p: Long, q: Long) = MathUtils.gcd(p, q)
  def hcf(p: Int, q: Int) = MathUtils.gcd(p, q)
  def hcf(p: Long, q: Long) = MathUtils.gcd(p, q)
  def lcm(p: Int, q: Int) = MathUtils.lcm(p, q)
  def lcm(p: Long, q: Long) = MathUtils.lcm(p, q)
}


