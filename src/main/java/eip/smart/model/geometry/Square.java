package eip.smart.model.geometry;

import eip.smart.model.geometry.v2.Point2D;

/**
 * <b>Square is the class facilitating the creation of squares.</b>
 *
 * @author Pierre Demessence
 *
 */
public class Square extends Rectangle {

	public Square(Point2D upperleft, double size) {
		super(upperleft, size, size);
	}
}
