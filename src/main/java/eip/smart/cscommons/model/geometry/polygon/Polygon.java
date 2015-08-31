package eip.smart.cscommons.model.geometry.polygon;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.cscommons.model.geometry.Point2D;

/**
 * <b>Polygon is the class allowing to manage polygones.</b>
 *
 * @author Pierre Demessence
 *
 */
public class Polygon implements Serializable {

	private ArrayList<Point2D>	points;

	/**
	 * Constructor without argument, create an empty points array.
	 */
	public Polygon() {
		this.points = new ArrayList<>();
	}

	/**
	 * Constructor attributing a list of points to the polygon
	 *
	 * @param points
	 */
	public Polygon(ArrayList<Point2D> points) {
		this.points = points;
	}

	/**
	 * add a point to the point'list
	 *
	 * @param point
	 * @return true
	 */
	public boolean add(Point2D point) {
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

	/**
	 * Return the perimeter of the polygon using only the x and y coordinates of the points.
	 *
	 * @return the perimeter of the polygon.
	 */
	@JsonIgnore
	public double getPerimeter() {
		double perimeter = 0;

		if (!this.isFinite())
			return (0);

		Point2D lastPoint = this.points.get(0);
		for (int i = 1; i < this.points.size(); i++) {
			perimeter += lastPoint.distance(this.points.get(i));
			lastPoint = this.points.get(i);
		}
		perimeter += lastPoint.distance(this.points.get(0));
		return (perimeter);
	}

	public ArrayList<Point2D> getPoints() {
		return (this.points);
	}

	/**
	 * Checks if the point is inside the polygon
	 *
	 * @param point
	 *            the point to check.
	 * @return true if the point is inside the polygon.
	 *         http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html#The C Code
	 */
	public boolean includes(Point2D point) {
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
		for (Point2D point : this.points)
			res += point;
		return (res);
	}
}
