package eip.smart.cscommons.model.geometry;

import eip.smart.cscommons.model.geometry.v2.Point3D;

/**
 * Created by Vincent Buresi on 4/4/15.
 *
 * This class is immutable.
 * Point approximation expressed in integer values. Its use is mostly to optimize some complex calculations by
 * removing the need to compute point float values
 */
public final class IntPoint {
	public final long	x, y, z;

	public IntPoint(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public IntPoint(Point3D point3d) {
		this.x = (long) point3d.getX();
		this.y = (long) point3d.getY();
		this.z = (long) point3d.getZ();
	}

	/**
	 * See {@link eip.smart.model.geometry.Point#add(Point)}
	 *
	 * @param p2
	 * @return
	 */
	public IntPoint add(IntPoint p2) {
		return new IntPoint(this.x + p2.x, this.y + p2.y, this.z + p2.z);
	}

	/**
	 * See {@link eip.smart.model.geometry.Point#getDistance(Point)}
	 *
	 * @param p
	 * @return
	 */
	public double getDistance(IntPoint p) {
		return (Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2) + Math.pow(this.z - p.z, 2)));
	}

	/**
	 * See {@link eip.smart.model.geometry.Point#substract(Point)}
	 *
	 * @param p2
	 * @return
	 */
	public IntPoint substract(IntPoint p2) {
		return new IntPoint(this.x - p2.x, this.y - p2.y, this.z - p2.z);
	}

	/**
	 * @param precision
	 *            a divider applied to the point coordinates. Should never ever been 0.
	 * @return a new Point class from this IntPoint values.
	 */
	public Point3D toPoint(long precision) {
		return new Point3D(this.x / precision, this.y / precision, this.z / precision);
	}

	@Override
	public String toString() {
		return "(" + this.x + ";" + this.y + ";" + this.z + ")";
	}
}
