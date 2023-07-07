/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer.objects;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Materials.Material;
import edu.up.isgc.raytracer.Materials.Texture;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.tools.Barycentric;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Rene Jorge Avila Galvan
 */

public class Polygon extends Object3D {

    public List<Triangle> triangles;

    public List<Triangle> getTriangles() {
        return triangles;
    }
    /**
     * @param position is used to set the position of the polygon
     * @param triangles is used to set the triangles that the polygon is made of
     * @param color is used to set the color of the polygon
     * @param material is used to set the material of the polygon
     * */
    public Polygon(Vector3D position, Triangle[] triangles, Color color, Material material){
        super(position, color, material);
        setTriangles(triangles);
    }

    /**
     * @param triangles is used to set the triangles
     * **/
    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<Vector3D>();
        for(Triangle triangle : triangles){
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for(Vector3D vertex : uniqueVertices){
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }

        this.triangles = Arrays.asList(triangles);
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();
        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if(intersection != null && intersectionDistance > 0 && (intersectionDistance < distance ||distance < 0)){
                distance = intersectionDistance;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
                normal = Vector3D.ZERO();
                double[] uVw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uVw.length; i++) {
                    if(getMaterial().getTexture() == Texture.smooth || getMaterial().getTexture() == null)
                        normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                    if(getMaterial().getTexture() == Texture.hard)
                        normal = normals[i];
                }

            }
        }

        if(distance == -1){
            return null;
        }
        return new Intersection(position, distance, normal, this);
    }
}
