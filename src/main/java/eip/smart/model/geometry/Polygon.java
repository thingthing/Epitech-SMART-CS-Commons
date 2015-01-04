package eip.smart.model.geometry;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Pierre Demessence on 10/10/2014.
 */
public class Polygon implements Serializable {

	private ArrayList<Point>	points;

	public Polygon() {
		this.points = new ArrayList<>();
	}

	public Polygon(ArrayList<Point> points) {
		this.points = points;
	}

	public boolean add(Point point) {
		return (this.points.add(point));
	}

	/**
	 * Return the area of the polygon using only the x and y coordinates of the points.
	 *
	 * @return the area of the polygon.
	 */
	@JsonIgnore
	public double getArea() {
		double area = 0;
		int j = this.points.size() - 1;
		for (int i = 0; i < this.points.size(); i++) {
			area += (this.points.get(j).getX() + this.points.get(i).getX()) * (this.points.get(j).getY() - this.points.get(i).getY());
			j = i;
		}
		return (Math.abs(area / 2.0d));
	}

	@JsonIgnore
	public double getPerimeter() {
		double perimeter = 0;

		if (!this.isFinite())
			return (0);

		Point lastPoint = this.points.get(0);
		for (int i = 1; i < this.points.size(); i++) {
			perimeter += lastPoint.getDistance(this.points.get(i));
			lastPoint = this.points.get(i);
		}
		perimeter += lastPoint.getDistance(this.points.get(0));
		return (perimeter);
	}

	public ArrayList<Point> getPoints() {
		return (this.points);
	}

	/**
	 * Checks if the
	 *
	 * @param point
	 *            the point to check.
	 * @return true if the point is inside the polygon.
	 *         http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html#The C Code
	 */
	public boolean includes(Point point) {
		boolean res = false;
		for (int i = 0, j = this.points.size() - 1; i < this.points.size(); j = i++)
			if (((this.points.get(i).getY() > point.getY()) != (this.points.get(j).getY() > point.getY())) && (point.getX() < ((this.points.get(j).getX() - this.points.get(i).getX()) * (point.getY() - this.points.get(i).getY()) / (this.points.get(j).getY() - this.points.get(i).getY()) + this.points.get(i).getX())))
				res = !res;
		return (res);
	}

	/**
	 * Check if the polygon is "finite".
	 *
	 * @return true if the Polygon contains at least 3 points.
	 */
	@JsonIgnore
	public boolean isFinite() {
		return (this.points.size() > 2);
	}

	@Override
	public String toString() {
		String res = "";
		for (Point point : this.points)
			res += point;
		return (res);
	}
}