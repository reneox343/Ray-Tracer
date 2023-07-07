/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer.objects;

import edu.up.isgc.raytracer.Intersectable;
import edu.up.isgc.raytracer.Materials.Material;
import edu.up.isgc.raytracer.Vector3D;

import java.awt.*;

/**
 * @author Rene Jorge Avila Galvan
 */

public abstract class Object3D implements Intersectable {

    private Vector3D position;
    private Color color;
    private Material material;

    /**
     * @return the position of the object
     * */
    public Vector3D getPosition() {
        return position;
    }
    /**
     * @param position is used to set the position of the object
     * **/
    public void setPosition(Vector3D position) {
        this.position = position;
    }
    /**
     * @return the color of the object
     * */
    public Color getColor() {
        return color;
    }
    /**
     * @param color is used to set the color of the object
     * **/
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * @return the material of the object
     * */
    public Material getMaterial() {
        return material;
    }
    /**
     * @param material is used to set the material of the object
     * **/
    public void setMaterial(Material material) {
        this.material = material;
    }
    /**
     * @param position is used to set the position of the object
     * @param color is used to set the color of the object
     * @param material is used to set the material of the object
     * */
    public Object3D(Vector3D position, Color color, Material material) {
        setPosition(position);
        setColor(color);
        setMaterial(material);
    }

}
