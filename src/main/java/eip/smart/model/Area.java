package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.geometry.Point;
import eip.smart.model.geometry.Polygon;
import eip.smart.util.PointCloudGenerator;

/**
 * <b>Area est la classe repr�sentant et permettant de g�rer les zones.</b>
 * @author Pierre Demessence
 * @version 1.0
*/
public class Area implements Serializable {

	/**
	 * nombre entier (int), d�terminant le niveau de priorit� de la zone, Ce niveau sera pris en compte quant � la direction des agents.
	 */
	private int							priority			= 0;
	
	/**
	 * tableau de polygones (ArrayList<Polygon>), permettant de d�terminer la forme et l'emplacement de la zone
	 */
	private ArrayList<Polygon>			zoneToMap			= new ArrayList<>();

	/**
	 * Liste des points (ArrayList<Point>) mapp�s par les agents au sein de la zone
	 * 
	 * @see Point
	 */
	private ArrayList<Point>			points				= new ArrayList<>();
	
	/**
	 * Liste de zones (ArrayList<Area>), utiles pour d�composer une zone en sous-zones afin de faciliter sa mod�lisation par plusieurs agents
	 * 
	 * @see Area
	 */
	private ArrayList<Area>				subAreas			= new ArrayList<>();
	
	/**
	 * Liste des types d'agents (ArrayList<Agent.AgentType>) pouvants circuler sur la zone
	 * 
	 * @see Agent
	 */
	private ArrayList<Agent.AgentType>	capableAgentTypes	= new ArrayList<>();
	
	/**
	 * niveau de completion (double) de la zone
	 */
	private double						completion			= 0.0d;

    /**
     * Expressed in Point / (unit of polygon surface)
     */
    private int                         requiredDensity     = 1000;


    /**
	 * Constructeur de test, g�n�rant al�atoirement un nuage de point
	 */
	public Area() {
		this.points = new PointCloudGenerator().generatePointCloud(20);
	}

	/**
	 * Constructeur permettant de donner � la zone une forme et un emplacement
	 * 
	 * @param polygon Polygone qui repr�sentera la limite de la zone
	 */
	public Area(Polygon polygon) {
		this.zoneToMap.add(polygon);
	}

	/**
	 * m�thode prenant un point en param�tre et det�rminant si ce point se situe � l'int�rieur de la zone
	 * 
	 * @param point le point � contr�ler
	 * @return "true" si le point est situ� � l'int�rieur de la zone, "false" dans le cas contraire
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

	/**
	 * M�thode de test, g�n�re al�atoirement des points dans la zone
	 * @param nb nombre de points � g�n�rer
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
