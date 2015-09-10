package eip.smart.cscommons.model.geometry;

import java.math.BigDecimal;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PointCloud3DGenerator {

	private double	max			= 42.0d;

	private double	min			= -42.0d;
	private int		precision	= 3;
	private Random	rng			= new Random();

	public PointCloud3DGenerator() {}

	public PointCloud3DGenerator(int precision, double min, double max) {
		this.precision = precision;
		this.min = min;
		this.max = max;
	}

	public Point3D generateIntPoint() {
		return (new Point3D(this.getRandomInt(), this.getRandomInt(), this.getRandomInt()));
	}

	public Point3D generatePoint() {
		return (new Point3D(this.getRandomDouble(), this.getRandomDouble(), this.getRandomDouble()));
	}

	public PointCloud3D generatePointCloud(int nb) {
		PointCloud3D cloud = new PointCloud3D();
		for (int i = 0; i < nb; i++)
			cloud.add(this.generatePoint());
		return (cloud);
	}

	public String generatePointCloudJSON(int nb) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();

		try {
			json = mapper.writeValueAsString(this.generatePointCloud(nb));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return (json);
	}

	private double getRandomDouble() {
		double res;
		res = (this.min + this.rng.nextDouble() * ((this.max - this.min) + 1));
		res = new BigDecimal(res).setScale(this.precision, BigDecimal.ROUND_HALF_UP).doubleValue();
		return (res);
	}

	private int getRandomInt() {
		return this.rng.nextInt((int) this.max - (int) this.min) + (int) this.min;
	}
}
