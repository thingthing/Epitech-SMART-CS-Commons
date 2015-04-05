package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.geometry.Point;
import eip.smart.model.geometry.Polygon;
import eip.smart.util.PointCloudGenerator;

/**
 * <b>Area est la classe représentant et permettant de gérer les zones.</b>
 * @author Pierre Demessence
 * @version 1.0
*/
public class Area implements Serializable {
	
	/**
	 * nombre entier (int), déterminant le niveau de priorité de la zone, Ce niveau sera pris en compte quant à la direction des agents.
	 */
	private int							priority			= 0;
	
	/**
	 * tableau de polygones (ArrayList<Polygon>), permettant de déterminer la forme et l'emplacement de la zone
	 */
	private ArrayList<Polygon>			areaToMap			= new ArrayList<>();

	/**
	 * Liste des points (ArrayList<Point>) mappés par les agents au sein de la zone
	 * 
	 * @see Point
	 */
	private ArrayList<Point>			points				= new ArrayList<>();
	
	/**
	 * Liste de zones (ArrayList<Area>), utiles pour décomposer une zone en sous-zones afin de faciliter sa modélisation par plusieurs agents
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
	 * Constructeur de test, générant aléatoirement un nuage de point
	 */
	public Area() {
		this.points = new PointCloudGenerator().generatePointCloud(20);
	}

	/**
	 * Constructeur permettant de donner à la zone une forme et un emplacement
	 * 
	 * @param polygon Polygone qui représentera la limite de la zone
	 */
	public Area(Polygon polygon) {
		this.areaToMap.add(polygon);
	}

	/**
	 * méthode prenant un point en paramètre et detérminant si ce point se situe à l'intérieur de la zone
	 * 
	 * @param point le point à contrôler
	 * @return "true" si le point est situé à l'intérieur de la zone, "false" dans le cas contraire
	 */
	public boolean contains(Point point) {
		for (Polygon polygon : this.areaToMap)
			if (polygon.includes(point))
				return (true);
		return (false);
	}

	public ArrayList<Polygon> getAreaToMap() {
		return this.areaToMap;
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
	 * Met à jour la completion de la zone
	 */
	public void updateCompletion() {
		this.completion += 5.0d + (10.0d - 5.0d) * new Random().nextDouble();
		this.completion = Math.min(this.completion, 100.0d);
	}

	/**
	 * Méthode de test, génère aléatoirement des points dans la zone
	 * @param nb nombre de points à générer
	 */
    public void generateTestPoints(int nb) {
        this.points = new PointCloudGenerator().generatePointCloud(nb);
    }
}
