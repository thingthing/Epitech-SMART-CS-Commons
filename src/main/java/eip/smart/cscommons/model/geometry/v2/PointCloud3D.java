package eip.smart.cscommons.model.geometry.v2;

import java.util.Collection;

public class PointCloud3D extends PointCloud<Point3D> {

	public Collection<PointCloud2D> layerCutting() {
		int layers = 0;
		double height = 0;
		return this.layerCutting(layers, height);
	}

	public Collection<PointCloud2D> layerCutting(double height) {
		int layers = 0;
		return this.layerCutting(layers, height);
	}

	public Collection<PointCloud2D> layerCutting(int layers) {
		double height = 0;
		return this.layerCutting(layers, height);
	}

	/**
	 * TODO create the algorithm
	 * This method should contains the algorithm.
	 * The 3 other method should call this method with the parameters, given or calculated.
	 */
	@SuppressWarnings({ "static-method", "unused" })
	private Collection<PointCloud2D> layerCutting(int layers, double height) {
		return null;
	}

}
