package eip.smart.model.geometry.completion;

import eip.smart.model.geometry.Point;

/**
 * Created by Vincent Buresi on 4/4/15.
 *
 * This class is immutable.
 * Point approximation expressed in integer values. Its use is mostly to optimize some complex calculations by
 * removing the need to compute point float values
 *
 * WARNING IntPoints are obtained out of Point through coordinates conversion.
 * you should never mix IntPoints from differents origins, it could cause the result to be totally false.
 * @see eip.smart.model.geometry.Point#toIntPoint(eip.smart.model.geometry.Point, double)
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
     * WARNING You should never try to do any calculation on IntPoints converted from distincts origin and scale.
     * @param p2
     * @return
     */
    public IntPoint add(IntPoint p2) {
        return new IntPoint(x + p2.x, y + p2.y, z + p2.z);
    }

    /**
     * @see eip.smart.model.geometry.Point#substract(eip.smart.model.geometry.Point)
     * WARNING You should never try to do any calculation on IntPoints converted from distincts origin and scale.
     * @param p2
     * @return
     */
    public IntPoint substract(IntPoint p2) {
        return new IntPoint(x - p2.x, y - p2.y, z - p2.z);
    }

    /**
     * @see eip.smart.model.geometry.Point#getDistance(eip.smart.model.geometry.Point)
     * WARNING You should never try to do any calculation on IntPoints converted from distincts origin and scale.
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
     * This method converts an IntPoint back to Point.
     * Arguments should be exactly the same as those given to {@link eip.smart.model.geometry.Point#toIntPoint(eip.smart.model.geometry.Point, double)}
     *
     * WARNING scale should never ever be 0 to avoid divide-by-zero exception on conversion.
     *
     * @param origin
     * @param scale
     * @return a new Point class from this IntPoint values.
     */
    public Point toPoint(Point origin, double scale) {
        return new Point((((double)x) / scale) - origin.getX(),
                (((double)y) / scale) - origin.getY(),
                (((double)z) / scale) - origin.getZ());
    }
}
