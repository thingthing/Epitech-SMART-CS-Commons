package eip.smart.model.geometry;

/**
 * <b>Square est la classe facilitant la cr�ation des cercles.</b>
 * @author Pierre Demessence
 *
 */
public class Square extends Rectangle {

	public Square(Point upperleft, double size) {
		super(upperleft, size, size);
	}
}
