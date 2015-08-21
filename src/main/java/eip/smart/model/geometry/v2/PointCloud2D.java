package eip.smart.model.geometry.v2;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.geometry.euclidean.twod.Segment;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.ConvexHull2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.ConvexHullGenerator2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.MonotoneChain;

public class PointCloud2D extends PointCloud<Point2D> {
	public static void main(String[] args) {
		PointCloud2D pcl2d = new PointCloud2D();
		pcl2d.add(new Point2D(0, 0));
		pcl2d.add(new Point2D(5, 5));
		pcl2d.add(new Point2D(0, 5));
		pcl2d.add(new Point2D(5, 0));
		pcl2d.add(new Point2D(2.5, 2.5));
		pcl2d.add(new Point2D(-5, -5));
		pcl2d.add(new Point2D(8, 9));
		pcl2d.add(new Point2D(8, 6));
		ConvexHull2D hull2d = pcl2d.generateConvexHull2D();
		System.out.println(Arrays.deepToString(hull2d.getVertices()));
		for (Segment segment : hull2d.getLineSegments())
			System.out.println(segment.getStart() + " -> " + segment.getEnd());
	}

	/**
	 * TODO find/create the algorithm
	 */
	public void generateConcaveHull2D() {}

	public ConvexHull2D generateConvexHull2D() {
		ConvexHullGenerator2D hullGenerator2D = new MonotoneChain();
		return hullGenerator2D.generate(new ArrayList<Vector2D>(this.getPoints()));
	}
}
