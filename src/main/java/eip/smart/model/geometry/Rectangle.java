package eip.smart.model.geometry;

/**
 * <b>Rectangle est la classe facilitant la cr�ation des rectangles.</b>
 * @author Pierre Demessence
 *
 */
public class Rectangle extends Polygon {

	public Rectangle(Point upperleft, double width, double height) {
		this.add(upperleft);
		this.add(new Point(upperleft.getX() + width, upperleft.getY(), upperleft.getZ()));
		this.add(new Point(upperleft.getX() + width, upperleft.getY() + height, upperleft.getZ()));
		this.add(new Point(upperleft.getX(), upperleft.getY() + height, upperleft.getZ()));
	}

}