package eip.smart.cscommons.model.geometry;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;

public abstract class PointCloud<T extends Point> implements Serializable {

	// TODO use configuration
	private static final double	DBSCAN_DEFAULT_EPS		= 80;
	private static final int	DBSCAN_DEFAULT_MINPTS	= 3;

	@JsonView(JSONViews.HTTP.class)
	private Set<T>				points					= new HashSet<>();

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
		List<T> res = Collections.emptyList();
		res.addAll(this.points);
		return res;
	}
}
