package eip.smart.cscommons.model.geometry.v2;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;

public class Point3D extends Vector3D implements Point {

	public static void main(String[] args) {
		Point3D toto = new Point3D(new Point3D(Vector3D.ZERO).add(new Point3D(5, 4, 6)));
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
	public double[] getPoint() {
		return this.toArray();
	}

	@JsonView(JSONViews.IMPORTANT.class)
	@Override
	public double getX() {
		return super.getX();
	}

	@JsonView(JSONViews.IMPORTANT.class)
	@Override
	public double getY() {
		return super.getY();
	}

	@JsonView(JSONViews.IMPORTANT.class)
	@Override
	public double getZ() {
		return super.getZ();
	}

	public Point2D to2D() {
		return new Point2D(this.getX(), this.getY());
	}

}
