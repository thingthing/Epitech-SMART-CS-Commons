package eip.smart.model.geometry.v2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

public abstract class PointCloud<T extends Point> {

	protected List<T>	points	= new ArrayList<>();

	public void add(T point) {
		this.points.add(point);
	}

	public List<Cluster<T>> findClusters() {
		DBSCANClusterer<T> clusterer = new DBSCANClusterer<>(10, 2);
		return clusterer.cluster(this.points);
	}
}
