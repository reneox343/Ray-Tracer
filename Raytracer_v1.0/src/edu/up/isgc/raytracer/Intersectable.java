/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer;

/**
 * @author Rene Jorge Avila Galvan
 */

public interface Intersectable {
    /**
     * @return an intersection with an object
     * */
    public abstract Intersection getIntersection(Ray ray);
}
