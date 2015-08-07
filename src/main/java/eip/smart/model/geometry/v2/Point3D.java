package eip.smart.model.geometry.v2;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Point3D extends Vector3D implements Point {

	public static void main(String[] args) {
		Point3D toto = new Point3D(Vector3D.ZERO).add(new Point3D(5, 4, 6));
		System.out.println(toto);
	}

	/**
	 * @see Vector3D#Vector3D(double, double)
	 */
	public Point3D(double alpha, double delta) {
		super(alpha, delta);
	}

	/**
	 * @see Vector3D#Vector3D(double, double, double)
	 */
	public Point3D(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * @see Vector3D#Vector3D(double, Vector3D)
	 */
	public Point3D(double a, Vector3D u) {
		super(a, u);
	}

	/**
	 * @see Vector3D#Vector3D(double, Vector3D, double, Vector3D)
	 */
	public Point3D(double a1, Vector3D u1, double a2, Vector3D u2) {
		super(a1, u1, a2, u2);
	}

	/**
	 * @see Vector3D#Vector3D(double, Vector3D, double, Vector3D, double, Vector3D)
	 */
	public Point3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3) {
		super(a1, u1, a2, u2, a3, u3);
	}

	/**
	 * @see Vector3D#Vector3D(double, Vector3D, double, Vector3D, double, Vector3D, double, Vector3D)
	 */
	public Point3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3, double a4, Vector3D u4) {
		super(a1, u1, a2, u2, a3, u3, a4, u4);
	}

	/**
	 * @see Vector3D#Vector3D(double[])
	 */
	public Point3D(double[] v) throws DimensionMismatchException {
		super(v);
	}

	public Point3D(Vector3D u) {
		this(u.getX(), u.getY(), u.getZ());
	}

	@Override
	public Point3D add(Vector<Euclidean3D> v) {
		// TODO Auto-generated method stub
		return (Point3D) super.add(v);
	}

	@Override
	public double[] getPoint() {
		return this.toArray();
	}

}
