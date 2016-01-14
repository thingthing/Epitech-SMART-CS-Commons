package eip.smart.cscommons.model.geometry;

import com.fasterxml.jackson.annotation.JsonView;
import eip.smart.cscommons.model.JSONViews;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PointCloud<T extends Point> implements Serializable, Cloneable {

	// TODO use configuration
	private static final double	DBSCAN_DEFAULT_EPS		= 80;
	private static final int	DBSCAN_DEFAULT_MINPTS	= 3;

	@JsonView({JSONViews.HTTP.class, JSONViews.DISK.class})
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

	public PointCloud<T> getSubPointCloud(int from, int nb) {
		ArrayList<T> points = new ArrayList<T>(this.points);
		try {
			PointCloud<T> res = this.clone();
			from = Math.max(from, 0);
			nb = Math.max(nb, 0);
			if (from >= points.size())
				from = points.size();
			if (from + nb >= points.size())
				nb = points.size() - from;
			res.points.clear();
			res.add(points.subList(from, from + nb));
			return (res);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return (null);
		}
	}

	@Override
	protected PointCloud<T> clone() throws CloneNotSupportedException {
		return (PointCloud<T>) super.clone();
	}
	
	public List<T> getPoints() {
		// TODO Create a copy so it can't be modified.
		List<T> res = new ArrayList<>();// Collections.emptyList();
		res.addAll(this.points);
		return res;
	}

	public int size() {
		return this.points.size();
	}
}
