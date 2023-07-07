/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer.lights;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;

import java.awt.*;

/**
 * @author Rene Jorge Avila Galvan
 */
public class DirectionalLight extends Light {
    /**
     * @param position is used to set the light position into the scene
     * @param color is used to set the color of the light
     * @param intensity is used to set the intensity of the light
     * @param direction is used to set the direction fo the light
     * */
    public DirectionalLight(Vector3D position, Vector3D direction, Color color, double intensity){
        super(position, color, intensity);
        setDirection(Vector3D.normalize(Vector3D.scalarMultiplication(direction, -1.0)));
    }

    /**
     * @param intersection is needed to get the NDotL
     * @return should return the NDotL of the light
     * */
    @Override
    public float getNDotL(Intersection intersection) {
        return (float)Math.max(Vector3D.dotProduct(intersection.getNormal(), getDirection()), 0.0);
    }
}
