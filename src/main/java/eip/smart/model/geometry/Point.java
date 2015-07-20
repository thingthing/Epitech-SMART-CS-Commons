package eip.smart.model.geometry;

import java.io.Serializable;

/**
 * Created by Pierre Demessence on 09/10/2014.
 *
 * This class is immutable.
 */
public final class Point implements Serializable {

	/**
	 * Deprecated
	 * Refactored into non-static add(Point p) version to be consistent with getDistance(Point p) and IntPoint methods.
	 * To be removed
	 */
	@Deprecated
	public static Point add(Point p1, Point p2) {
		return new Point(p1.getX() + p2.getX(), p1.getY() + p2.getY(), p1.getZ() + p2.getZ());
	}

	/**
	 * Deprecated
	 * Equivalent to add(p1, new Point(d, d, d)).
	 * Translates only on line from equation x = y = z
	 * Both not very useful and source of confusion.
	 * To be removed.
	 */
	@Deprecated
	public static Point tranlate(Point p, double d) {
		return Point.add(p, new Point(d));
	}

	private double	x	= 0;

	private double	y	= 0;

	private double	z	= 0;

	public Point() {}

	public Point(double c) {
		this.x = c;
		this.y = c;
		this.z = c;
	}

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Adds another point to this.
	 * Same as a translation following a vector having coordinates equivalent to p.
	 *
	 * @param p
	 *            The point to be added or the translation vector coordinates.
	 * @return A new Point resulting from the addition of given coordinates
	 */
	public Point add(Point p) {
		return new Point(this.x + p.x, this.y + p.y, this.z + p.z);
	}

	public double getDistance(Point p) {
		return (Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2) + Math.pow(this.z - p.z, 2)));
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	/**
	 * Substracts another point from this.
	 * Same as a translation following a vector having coordinates equivalent to -p.
	 * Same as calculating the vector from this point to p.
	 *
	 * @param p
	 *            The point to be substracted, the translation vector negative coordinates or the end point of the vector to be calculated.
	 * @return A new Point or vector coordinates resulting from the substraction of given coordinates
	 */
	public Point substract(Point p) {
		return new Point(this.x - p.x, this.y - p.y, this.z - p.z);
	}

	/**
	 * WARNING This function creates an approximation of the Point. Be aware that precision is lost through this call.
	 * 
	 * @param precision
	 *            a multiplier applied to the given point coordinates. Should never ever been 0.
	 * @return a new IntPoint from this Point values
	 */
	public IntPoint toIntPoint(double precision) {
		return new IntPoint((long) (this.x * precision), (long) (this.y * precision), (long) (this.z * precision));
	}

	/**
	 * TODO Implement precision system
	 * WARNING This function creates an approximation of the Point. Be aware that precision is lost through this call.
	 *
	 * @param precision
	 * @return a new IntPoint from this Point values
	 */
	public IntPoint toIntPoint(int precision) {
		return new IntPoint((long) this.x, (long) this.y, (long) this.z);
	}

	@Override
	public String toString() {
		return "(" + this.getX() + ";" + this.getY() + ";" + this.getZ() + ")";
	}
}
