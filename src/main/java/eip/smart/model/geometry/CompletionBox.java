package eip.smart.model.geometry;

import static java.lang.Math.abs;

/**
 * Created by Vincent Buresi on 4/4/15.
 *
 * The use of this class is to approximate a box to be filled as a 3d voxel space
 */
public final class CompletionBox {
    private final long xStart, yStart, zStart, xEnd, yEnd, zEnd;
    private boolean[][][] voxelTab;
    private long count = 0;
    private final long size;

    /**
     * Creates a new completion box with given coordinates.
     * @param xStart
     * @param yStart
     * @param zStart
     * @param xEnd
     * @param yEnd
     * @param zEnd
     */
    public CompletionBox(long xStart, long yStart, long zStart, long xEnd, long yEnd, long zEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.zStart = zStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.zEnd = zEnd;
        voxelTab = new boolean
                [(int) abs(xEnd -xStart) + 1]
                [(int) abs(yEnd - yStart) + 1]
                [(int) abs(zEnd - zStart) + 1];
        size = ((int) abs(xEnd -xStart) + 1)
                * ((int) abs(yEnd - yStart) + 1)
                * ((int) abs(zEnd - zStart) + 1);
    }

    /**
     * Returns true if given point is inside the box range.
     * @param p
     * @return
     */
    public boolean isInside(IntPoint p) {
        return isInside(p.x, p.y, p.z);
    }

    public boolean isInside(long x, long y, long z) {
        return (((x >= xStart) && (x <= xEnd))
                && ((y >= yStart) && (y <= yEnd))
                && ((z >= zStart) && (z <= zEnd)));
    }

    public boolean addPoint(IntPoint p) {
        return addPoint(p.x, p.y, p.z);
    }

    public boolean addPoint(long x, long y, long z) {
        if (!isInside(x, y, z))
            return false;
        int tabX = (int) (x - xStart);
        int tabY = (int) (y - yStart);
        int tabZ = (int) (z - yStart);
        if (!voxelTab[tabX][tabY][tabZ])
            ++count;
        voxelTab[tabX][tabY][tabZ] = true;
        return true;
    }

    public double getCurrentCompletion() {
        return ((double)count / (double)size) * 100;
    }
}
