package eip.smart.cscommons.model.geometry;

import eip.smart.cscommons.model.geometry.v2.Point2D;

/**
 * <b>Rectangle is the class facilitating the creation of rectangles.</b>
 *
 * @author Pierre Demessence
 *
 */
public class Rectangle extends Polygon {

	public Rectangle(Point2D upperleft, double width, double height) {
		this.add(upperleft);
		this.add(new Point2D(upperleft.getX() + width, upperleft.getY()));
		this.add(new Point2D(upperleft.getX() + width, upperleft.getY() + height));
		this.add(new Point2D(upperleft.getX(), upperleft.getY() + height));
	}

}