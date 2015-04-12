package eip.smart.model.geometry.completion;

import eip.smart.model.geometry.Point;

/**
 * Created by Vincent Buresi on 4/4/15.
 *
 * This class is immutable.
 * Point approximation expressed in integer values. Its use is mostly to optimize some complex calculations by
 * removing the need to compute point float values
 */
public final class IntPoint {
    public final long x, y, z;

    public IntPoint(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @see eip.smart.model.geometry.Point#add(eip.smart.model.geometry.Point)
     * @param p2
     * @return
     */
    public IntPoint add(IntPoint p2) {
        return new IntPoint(x + p2.x, y + p2.y, z + p2.z);
    }

    /**
     * @see eip.smart.model.geometry.Point#substract(eip.smart.model.geometry.Point)
     * @param p2
     * @return
     */
    public IntPoint substract(IntPoint p2) {
        return new IntPoint(x - p2.x, y - p2.y, z - p2.z);
    }

    /**
     * @see eip.smart.model.geometry.Point#getDistance(eip.smart.model.geometry.Point)
     * @param p
     * @return
     */
    public double getDistance(IntPoint p) {
        return (Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2) + Math.pow(this.z - p.z, 2)));
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ";" + z + ")";
    }

    /**
     * TODO Implement precision system
     * @param precision
     * @return a new Point class from this IntPoint values.
     */
    public Point toPoint(int precision) {
        return new Point(x, y, z);
    }
}
