package eip.smart.model.geometry;

import java.io.Serializable;

/**
 * Created by Pierre Demessence on 09/10/2014.
 *
 * This class is immutable.
 */
public final class Point_old implements Serializable {

	/**
	 * Deprecated
	 * Refactored into non-static add(Point_old p) version to be consistent with getDistance(Point_old p) and IntPoint_old methods.
	 * To be removed
	 */
	@Deprecated
	public static Point_old add(Point_old p1, Point_old p2) {
		return new Point_old(p1.getX() + p2.getX(), p1.getY() + p2.getY(), p1.getZ() + p2.getZ());
	}

	/**
	 * Deprecated
	 * Equivalent to add(p1, new Point_old(d, d, d)).
	 * Translates only on line from equation x = y = z
	 * Both not very useful and source of confusion.
	 * To be removed.
	 */
	@Deprecated
	public static Point_old tranlate(Point_old p, double d) {
		return Point_old.add(p, new Point_old(d));
	}

	private double	x	= 0;

	private double	y	= 0;

	private double	z	= 0;

	public Point_old() {}

	public Point_old(double c) {
		this.x = c;
		this.y = c;
		this.z = c;
	}

	public Point_old(double x, double y, double z) {
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
	 * @return A new Point_old resulting from the addition of given coordinates
	 */
	public Point_old add(Point_old p) {
		return new Point_old(this.x + p.x, this.y + p.y, this.z + p.z);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point_old))
			return false;
		Point_old other = (Point_old) obj;
		if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	public double getDistance(Point_old p) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Substracts another point from this.
	 * Same as a translation following a vector having coordinates equivalent to -p.
	 * Same as calculating the vector from this point to p.
	 *
	 * @param p
	 *            The point to be substracted, the translation vector negative coordinates or the end point of the vector to be calculated.
	 * @return A new Point_old or vector coordinates resulting from the substraction of given coordinates
	 */
	public Point_old substract(Point_old p) {
		return new Point_old(this.x - p.x, this.y - p.y, this.z - p.z);
	}

	/**
	 * WARNING This function creates an approximation of the Point_old. Be aware that precision is lost through this call.
	 *
	 * @param precision
	 *            a multiplier applied to the given point coordinates. Should never ever been 0.
	 * @return a new IntPoint_old from this Point_old values
	 */
	public IntPoint toIntPoint(double precision) {
		return new IntPoint((long) (this.x * precision), (long) (this.y * precision), (long) (this.z * precision));
	}

	/**
	 * TODO Implement precision system
	 * WARNING This function creates an approximation of the Point_old. Be aware that precision is lost through this call.
	 *
	 * @param precision
	 * @return a new IntPoint_old from this Point_old values
	 */
	public IntPoint toIntPoint(int precision) {
		return new IntPoint((long) this.x, (long) this.y, (long) this.z);
	}

	@Override
	public String toString() {
		return "(" + this.getX() + ";" + this.getY() + ";" + this.getZ() + ")";
	}
}
