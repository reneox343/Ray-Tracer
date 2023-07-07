package edu.up.isgc.raytracer.Materials;

import edu.up.isgc.raytracer.*;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Rene Jorge Avila Galvan
 */

public abstract class Material {
    private Texture texture;
    private double reflectivity;
    private double refractivity;

    /**
     * @param texture is used to set if the shading sould be Smooth or hard shading
     * @param reflectivity is used to set how much color of the original color should be in the object
     * @param refractivity is used to set how the refraction should behave
     * */
    public Material(Texture texture, double reflectivity, double refractivity) {
        this.texture = texture;
        this.reflectivity = reflectivity;
        this.refractivity = refractivity;
    }

    /**
     * @return the texture of this
     * */
    public Texture getTexture() {
        return texture;
    }

    /**
     * @param texture is used to set the texture
     * */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * @return returns the reflectivity index of this
     * */
    public double getReflectivity() {
        return reflectivity;
    }

    /**
     * @param reflectivity is used to set the reflectivity
     * */
    public void setReflectivity(double reflectivity) {
        this.reflectivity = reflectivity;
    }

    /**
     * @return returns the refractivity index of this
     * */
    public double getRefractivity() {
        return refractivity;
    }
    /**
     * @param refractivity is used to set the refractivity
     * */
    public void setRefractivity(double refractivity) {
        this.refractivity = refractivity;
    }

    /**
     * @param light  is used to calculate the nDotL and fallOff
     * @param closestIntersection  is used to calculate the nDotL and
     * @return the fallOff of the light witch means how it will dim with distance
     * */
    public double getFallOff(Light light,Intersection closestIntersection){

        float nDotL = light.getNDotL(closestIntersection);
        float intensity = (float) light.getIntensity() * nDotL;
        Vector3D distance = Vector3D.substract(closestIntersection.getPosition(), light.getPosition());
        float fallOff = (float) (intensity / Math.pow(Vector3D.magnitude(distance), 1));
        return fallOff;
    }
    /**
     * @param originalIntersection is used to calculate how the fallOff,ambient and specular in the case of BlinnPhong will behave
     * @param reflectionIntersection is used to get the current object will reflect a ray
     * @param viewer is used to calculate how the ray will behave in relation with the camara ray
     * @param objects is used to see if reflectionIntersection hit another object
     * @param light is used to see how the light is afecting the current object
     * @param lastIntersection is used to make the function recursive in order to have multiple reflection and it saves the last reflection intersection
     * @param bounce is used to determine how much a ray can bounce
     * */
    public abstract float[] getReflectivityColor(Intersection originalIntersection, Intersection reflectionIntersection, Ray viewer, ArrayList<Object3D> objects, Light light,Intersection lastIntersection,int bounce);

    /**
     * @param originalIntersection is used to calculate how the fallOff,ambient and specular in the case of BlinnPhong will behave
     * @param refractivityIntersection is used to get the current object will reflect a ray
     * @param viewer is used to calculate how the ray will behave in relation with the camara ray
     * @param objects is used to see if reflectionIntersection hit another object
     * @param light is used to see how the light is afecting the current object
     * @param lastIntersection is used to make the function recursive in order to have multiple reflection and it saves the last reflection intersection
     * @param bounce is used to determine how much a ray can bounce
     * */
    public abstract float[] getReflactivityColor(Intersection originalIntersection, Intersection refractivityIntersection, Ray viewer, ArrayList<Object3D> objects, Light light,Intersection lastIntersection,int bounce);
    
    /**
     * @param viewer is used to calculate how the color will behave in relation with the camara ray
     * @param light is used to see how the light is afecting the current object
     * @param originalIntersection is used to get the closest intersection properties
     * */
    public abstract float[] getObjectColor(Light light, Intersection originalIntersection, Ray viewer);

    /**
     * @param objColor is used to get the current object color
     * @param reflectionColor is used to the the reflection colors
     * @param closestIntersection is used to get the reflectivity index in order the mix the colors
     * */
    public static float[] balanceReflectivityColor(float[] objColor,float[] reflectionColor,Intersection closestIntersection){
        float[] finalColor = new float[]{(float) (objColor[0] * (1-closestIntersection.getObject().getMaterial().getReflectivity())  + reflectionColor[0] * closestIntersection.getObject().getMaterial().getReflectivity()),
                (float) (objColor[1] * (1-closestIntersection.getObject().getMaterial().getReflectivity())  + reflectionColor[1] * closestIntersection.getObject().getMaterial().getReflectivity()),
                (float) (objColor[2] * (1-closestIntersection.getObject().getMaterial().getReflectivity())  + reflectionColor[2] * closestIntersection.getObject().getMaterial().getReflectivity())};
        return finalColor;
    }    


}
