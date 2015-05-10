package eip.smart.model.geometry;

/**
 * <b>Square is the class facilitating the creation of squares.</b>
 * @author Pierre Demessence
 *
 */
public class Square extends Rectangle {

	public Square(Point upperleft, double size) {
		super(upperleft, size, size);
	}
}
