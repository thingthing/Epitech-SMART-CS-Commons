package eip.smart.model.geometry;

import java.io.Serializable;

/**
 * Created by Pierre Demessence on 09/10/2014.
 *
 * This class is immutable.
 */
public final class Point implements Serializable {

    private final double	x;
    private final double	y;
    private final double	z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double c) {
        this.x = c;
        this.y = c;
        this.z = c;
    }

    /**
     * Adds another point to this.
     * Same as a translation following a vector having coordinates equivalent to p.
     * @param p The point to be added or the translation vector coordinates.
     * @return A new Point resulting from the addition of given coordinates
     */
    public Point add(Point p) {
        return new Point(x + p.x, y + p.y, z + p.z);
    }

    /**
     * Substracts another point from this.
     * Same as a translation following a vector having coordinates equivalent to -p.
     * Same as calculating the vector from this point to p.
     * @param p The point to be substracted, the translation vector negative coordinates or the end point of the vector to be calculated.
     * @return A new Point or vector coordinates resulting from the substraction of given coordinates
     */
    public Point substract(Point p) {
        return new Point(x - p.x, y - p.y, z - p.z);
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

	@Override
	public String toString() {
		return "(" + this.getX() + ";" + this.getY() + ";" + this.getZ() + ")";
	}

    /**
     * TODO Implement precision system
     * WARNING This function creates an approximation of the Point. Be aware that precision is lost through this call.
     * @param precision
     * @return a new IntPoint from this Point values
     */
    public IntPoint toIntPoint(int precision) {
        return new IntPoint((long)x, (long)y, (long)z);
    }

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
     * Déprecated
     * Equivalent to add(p1, new Point(d, d, d)).
     * Translates only on line from equation x = y = z
     * Both not very useful and source of confusion.
     * To be removed.
     */
    @Deprecated
    public static Point tranlate(Point p, double d) {
        return Point.add(p, new Point(d));
    }
}
