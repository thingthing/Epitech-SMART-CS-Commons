package eip.smart.cscommons.model.geometry;

/**
 * Created by Vincent Buresi on 4/4/15.
 *
 * The use of this class is to approximate a box to be filled as a 3d voxel space
 */
public final class CompletionBox {
	private final long		xStart, yStart, zStart, xEnd, yEnd, zEnd;
	private boolean[][][]	voxelTab;
	private long			count	= 0;
	private final long		size;

	/**
	 * Creates a new completion box with given coordinates.
	 *
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
		this.voxelTab = new boolean[(int) Math.abs(xEnd - xStart) + 1][(int) Math.abs(yEnd - yStart) + 1][(int) Math.abs(zEnd - zStart) + 1];
		this.size = ((int) Math.abs(xEnd - xStart) + 1) * ((int) Math.abs(yEnd - yStart) + 1) * ((int) Math.abs(zEnd - zStart) + 1);
	}

	public boolean addPoint(IntPoint p) {
		return this.addPoint(p.x, p.y, p.z);
	}

	public boolean addPoint(long x, long y, long z) {
		if (!this.isInside(x, y, z))
			return false;
		int tabX = (int) (x - this.xStart);
		int tabY = (int) (y - this.yStart);
		int tabZ = (int) (z - this.yStart);
		if (!this.voxelTab[tabX][tabY][tabZ])
			++this.count;
		this.voxelTab[tabX][tabY][tabZ] = true;
		return true;
	}

	public double getCurrentCompletion() {
		return ((double) this.count / (double) this.size) * 100;
	}

	/**
	 * Returns true if given point is inside the box range.
	 *
	 * @param point3d
	 * @return
	 */
	public boolean isInside(IntPoint p) {
		return this.isInside(p.x, p.y, p.z);
	}

	public boolean isInside(long x, long y, long z) {
		return (((x >= this.xStart) && (x <= this.xEnd)) && ((y >= this.yStart) && (y <= this.yEnd)) && ((z >= this.zStart) && (z <= this.zEnd)));
	}
}
