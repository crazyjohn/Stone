package com.stone.tools.test

abstract class CCTherm {
  val desc: String
  val yearMade: Int
  val bookPrice: Int

  override def toString = desc

  /**
   * to xml;
   */
  def toXML =
    <cctherm>
      <desc>{ this.desc }</desc>
      <yearMade>{ yearMade }</yearMade>
      <bookPrice>{ bookPrice }</bookPrice>
    </cctherm>

  /**
   * from xml;
   */
  def fromXML(node: scala.xml.Node): CCTherm = {
    new CCTherm {
      val desc = (node \ "desc").text
      val yearMade = (node \ "yearMade").text.toInt
      val bookPrice = (node \ "bookPrice").text.toInt
    }
  }

}