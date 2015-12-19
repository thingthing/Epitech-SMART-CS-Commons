package eip.smart.cscommons.model.geometry;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;

public class Point3D extends Vector3D implements Point {

	private Color color = new Color(java.awt.Color.BLACK);

	public Point3D() {
		super(0, 0, 0);
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

	public Point3D(float x, float y, float z, float r, float g, float b, float a) {
		this(x, y, z);
		try {
			this.color = new Color(Math.round(r), Math.round(g), Math.round(b), (int) a);
		} catch (Exception e) {}
	}

	public Point3D(Vector3D u) {
		this(u.getX(), u.getY(), u.getZ());
	}

	@JsonView({ JSONViews.HTTP.class })
	public Color getColor() {
		return this.color;
	}

	@Override
	public double[] getPoint() {
		return this.toArray();
	}

	@JsonView({ JSONViews.HTTP.class, JSONViews.TCP.class })
	@Override
	public double getX() {
		return super.getX();
	}

	@JsonView({ JSONViews.HTTP.class, JSONViews.TCP.class })
	@Override
	public double getY() {
		return super.getY();
	}

	@JsonView({ JSONViews.HTTP.class, JSONViews.TCP.class })
	@Override
	public double getZ() {
		return super.getZ();
	}

	public Point2D to2D() {
		return new Point2D(this.getX(), this.getY());
	}

}
