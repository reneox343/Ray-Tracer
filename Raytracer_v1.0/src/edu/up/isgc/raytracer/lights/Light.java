/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer.lights;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.objects.Object3D;

import java.awt.*;

/**
 * @author Rene Jorge Avila Galvan
 */
public abstract class Light extends Object3D {
    private double intensity;
    private Vector3D direction;
    /**
     * @param position is used to set the light position into the scene
     * @param color is used to set the color of the light
     * @param intensity is used to set the intensity of the light
     * */
    public Light(Vector3D position, Color color, double intensity){
        super(position, color,null);
        setIntensity(intensity);
    }
    /**
     * @return the intensity of this light
     * */
    public double getIntensity() {
        return intensity;
    }
    /**
     * @param intensity is used to set intensity
     * */
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
    /**
     * @return should return the NDotL of the light
     * */
    public abstract float getNDotL(Intersection intersection);

    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
    /**
     * @return the direction of this light
     * */
    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }
    /**
     * @return the direction of this light
     * */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
}
