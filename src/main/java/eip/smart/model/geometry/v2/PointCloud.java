package eip.smart.model.geometry.v2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

public abstract class PointCloud<T extends Point> {

	// TODO use configuration
	private static final double	DBSCAN_DEFAULT_EPS		= 80;
	private static final int	DBSCAN_DEFAULT_MINPTS	= 3;

	private List<T>				points					= new ArrayList<>();

	public void add(List<T> points) {
		for (T point : points)
			this.points.add(point);
	}

	public void add(T point) {
		this.points.add(point);
	}

	public List<Cluster<T>> findClusters() {
		return this.findClusters(PointCloud.DBSCAN_DEFAULT_EPS, PointCloud.DBSCAN_DEFAULT_MINPTS);
	}

	/**
	 * @param eps
	 *            maximum radius of the neighborhood to be considered
	 * @param minPts
	 *            minimum number of points needed for a cluster
	 * @see DBSCANClusterer#DBSCANClusterer(double, int)
	 */
	public List<Cluster<T>> findClusters(double eps, int minPts) {
		DBSCANClusterer<T> clusterer = new DBSCANClusterer<>(eps, minPts);
		return clusterer.cluster(this.points);
	}

	public List<T> getPoints() {
		// TODO Create a copy so it can't be modified.
		return this.points;
	}
}
