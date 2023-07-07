package edu.up.isgc.raytracer.lights;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;

import java.awt.*;
/**
 * @author Rene Jorge Avila Galvan
 */
public class PointLight extends Light {
    /**
     * @param position is used to set the light position into the scene
     * @param color is used to set the color of the light
     * @param intensity is used to set the intensity of the light
     * */
    public PointLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }

    /**
     * @param intersection is needed to get the NDotL and to set the direction of the light
     * @return should return the NDotL of the light
     * */
    @Override
    public float getNDotL(Intersection intersection) {
        Vector3D direction = Vector3D.substract(getPosition(),intersection.getPosition());
        this.setDirection(direction);
        direction = Vector3D.normalize(direction);
        return (float)Math.max(Vector3D.dotProduct(intersection.getNormal(), direction), 0.0);
    }

}
