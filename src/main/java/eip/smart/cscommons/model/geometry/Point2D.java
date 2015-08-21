package eip.smart.cscommons.model.geometry;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;

public class Point2D extends Vector2D implements Point {

	/**
	 * @see Vector2D#Vector2D(double, double)
	 */
	public Point2D(double x, double y) {
		super(x, y);
	}

	/**
	 * @see Vector2D#Vector2D(double, Vector2D)
	 */
	public Point2D(double a, Vector2D u) {
		super(a, u);
	}

	/**
	 * @see Vector2D#Vector2D(double, Vector2D, double, Vector2D)
	 */
	public Point2D(double a1, Vector2D u1, double a2, Vector2D u2) {
		super(a1, u1, a2, u2);
	}

	/**
	 * @see Vector2D#Vector2D(double, Vector2D, double, Vector2D, double, Vector2D)
	 */
	public Point2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3) {
		super(a1, u1, a2, u2, a3, u3);
	}

	/**
	 * @see Vector2D#Vector2D(double, Vector2D, double, Vector2D, double, Vector2D, double, Vector2D)
	 */
	public Point2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3, double a4, Vector2D u4) {
		super(a1, u1, a2, u2, a3, u3, a4, u4);
	}

	/**
	 * @see Vector2D#Vector2D(double[])
	 */
	public Point2D(double[] v) throws DimensionMismatchException {
		super(v);
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

}
