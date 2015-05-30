package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.geometry.Point;
import eip.smart.model.geometry.Polygon;
import eip.smart.util.PointCloudGenerator;

/**
 * <b>Area is the class allowing to manage areas.</b>
 * @author Pierre Demessence
 * @version 1.0
*/
public class Area implements Serializable {

	/**
	 * number (int), define the priority level of the area, this level will be taken into account fot the agents'direction.
	 */
	private int							priority			= 0;
	
	/**
	 * polygones'array (ArrayList<Polygon>), allowing to define the shape and the place of the area
	 */
	private ArrayList<Polygon>			zoneToMap			= new ArrayList<>();

	/**
	 * list of the points (ArrayList<Point>) mapped by the agents inside the area
	 * 
	 * @see Point
	 */
	private ArrayList<Point>			points				= new ArrayList<>();
	
	/**
	 * areas'list (ArrayList<Area>), usefull to split an area into some sub-areas to facilitate it modeling by several agents
	 * 
	 * @see Area
	 */
	private ArrayList<Area>				subAreas			= new ArrayList<>();
	
	/**
	 * types agents'list (ArrayList<Agent.AgentType>), define what agents'type can be used on the area
	 * 
	 * @see Agent
	 */
	private ArrayList<Agent.AgentType>	capableAgentTypes	= new ArrayList<>();

	/**
	 * Cached completion level of the zone
     * <b>Never use this value if completionIsValid is false, always call updateCompletion() instead.</b>
	 */
	private double						cachedCompletion			= 0.0d;
    private double						cachedweight		        = 0.0d;
    private boolean                     cachedCompletionValid       = false;

    /**
     * Expressed in Point / (unit of polygon surface)
     */
    private int                         requiredDensity     = 1000;

    /**
	 * test constructor, generate a random point cloud
	 */
	public Area() {
		this.points = new PointCloudGenerator().generatePointCloud(20);
	}

	/**
	 * Constructor allowing to define the shape and the place of the area
	 * 
	 * @param polygon Polygon that'll represent the limit of the area
	 */
	public Area(Polygon polygon) {
		this.zoneToMap.add(polygon);
	}

	/**
	 * method taking a point as parameter et define if this point is situated in the area
	 * 
	 * @param point the point to check
	 * @return "true" if the point is situated in the areat, "false" if it's not.
	 */
	public boolean contains(Point point) {
		for (Polygon polygon : this.zoneToMap)
			if (polygon.includes(point))
				return (true);
		return (false);
	}

    public ArrayList<Polygon> getZoneToMap() {
        return this.zoneToMap;
    }

	public ArrayList<Agent.AgentType> getCapableAgentTypes() {
		return this.capableAgentTypes;
	}

	public double getCompletion() {
		return updateCompletion(false);
	}

	@JsonIgnore
	public int getNbPoints() {
		return (this.points.size());
	}

	public ArrayList<Point> getPoints() {
		return this.points;
	}

	public int getPriority() {
		return this.priority;
	}

	public ArrayList<Area> getSubAreas() {
		return this.subAreas;
	}

    /**
     * Calculates the percent completion of the Area.
     *
     * This percentage is calculated using the required density for the corresponding zones and recursively calling
     *  updateCompletion() for every subArea().
     * A more precise calculation require the subdivision in smaller zones or subAreas and a higher requiredDensity.
     * @return the new completion in percents.
     */
    public double updateCompletion(boolean force) {
        //Deprecated : Fully simulated completion update. Could be used for demonstration purpose.
        // Should ultimately be removed from project code.
//        this.completion += 5.0d + (10.0d - 5.0d) * new Random().nextDouble();
//        this.completion = Math.min(this.completion, 100.0d);

        double areaCompletion;
        double weightSum;

        if (!this.cachedCompletionValid && !force) {
            areaCompletion = 0.0d;
            weightSum = 0.0d;

            //Calculating the completion and coefficient sums of the area.
            for (Polygon zone : zoneToMap) {
                double zoneCompletion;
                double zoneWeight = zone.getArea();
                int zoneObjective = (int) (zoneWeight) * requiredDensity;

                zoneCompletion = (double) ((zone.getPoints().size() * 100) / zoneObjective);
                areaCompletion += zoneCompletion * zoneWeight;
                weightSum += zoneWeight;
            }
            this.cachedCompletion = areaCompletion;
            this.cachedweight = weightSum;
        } else {
            areaCompletion = this.cachedCompletion;
            weightSum = this.cachedweight;
        }

        //Adding the completion and coefficients of the subAreas
        for (Area subArea : subAreas) {
            areaCompletion += subArea.updateCompletion(force);
            weightSum += subArea.getTotalArea();
        }
        areaCompletion *= 100;
        areaCompletion /= weightSum;

        this.cachedCompletionValid = true;
        return areaCompletion;
    }

	/**
	 * test method, generate a random point cloud
	 * @param nb numbre of points to generate
	 */
    public void generateTestPoints(int nb) {
        this.points = new PointCloudGenerator().generatePointCloud(nb);
    }

    public double getTotalArea() {
        double  result = 0.0d;

        for (Polygon zone : zoneToMap)
            result += zone.getArea();
        for (Area subArea : subAreas)
            result += subArea.getTotalArea();

        return result;
    }

    public int getRequiredDensity() {
        return requiredDensity;
    }

    public void setRequiredDensity(int requiredDensity) {
        this.requiredDensity = requiredDensity;
    }
}
