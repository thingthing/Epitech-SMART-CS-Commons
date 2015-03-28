package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.geometry.Point;
import eip.smart.model.geometry.Polygon;
import eip.smart.util.PointCloudGenerator;

/**
 * Created by Pierre Demessence on 10/10/2014.
 */
public class Area implements Serializable {
    private int							priority			= 0;
    private ArrayList<Polygon>          zoneToMap           = new ArrayList<>();
    private ArrayList<Point>			points				= new ArrayList<>();
    private ArrayList<Area>				subAreas			= new ArrayList<>();
    private ArrayList<Agent.AgentType>	capableAgentTypes	= new ArrayList<>();
    private double						completion			= 0.0d;

    private int                         requiredDensity     = 1000; //Expressed in Points/(unit of polygon surface)

    public Area() {
        this.points = new PointCloudGenerator().generatePointCloud(20);
    }

    public Area(Polygon polygon) {
        this.zoneToMap.add(polygon);
    }

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
        return (this.completion);
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
     * Recalculates the percent complletion of the Area.
     *
     * This percentage is calculated using the required density for the corresponding zones and recursively calling
     *  updateCompletion() for every subArea().
     * A more precise calculation require the subdivision in smaller zones or subAreas and a higher requiredDensity.
     * @return the new completion in percents.
     */
    public double updateCompletion() {
        //Deprecated : Fully simulated completion update. Could be used for demonstration purpose.
        // Should ultimately be removed from project code.
//        this.completion += 5.0d + (10.0d - 5.0d) * new Random().nextDouble();
//        this.completion = Math.min(this.completion, 100.0d);

        double areaCompletion = 0.0d;
        double weightSum = 0.0d;

        //Calculating the completion and coefficient of the area.
        for (Polygon zone : zoneToMap) {
            double zoneCompletion;
            double zoneWeight = zone.getArea();
            int zoneObjective = (int) (zoneWeight) * requiredDensity;

            zoneCompletion = (double) ((zone.getPoints().size() * 100) / zoneObjective);
            areaCompletion += zoneCompletion * zoneWeight;
            weightSum += zoneWeight;
        }
        for (Area subArea : subAreas) {
            areaCompletion += subArea.updateCompletion();
            weightSum += subArea.getTotalArea();
        }
        areaCompletion *= 100;
        areaCompletion /= weightSum;


        this.completion = areaCompletion;
        return this.completion;
    }

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
