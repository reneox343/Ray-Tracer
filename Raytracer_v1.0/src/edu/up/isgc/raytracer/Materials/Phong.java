package edu.up.isgc.raytracer.Materials;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Raytracer;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Rene Jorge Avila Galvan
 */

public class Phong extends Material {
    /**
     * @param texture is used to set if the shading sould be Smooth or hard shading
     * @param reflectivity is used to set how much color of the original color should be in the object
     * @param refractivity is used to set how the refraction should behave
     * */
    public Phong(Texture texture, double reflectivity, double refractivity) {
        super(texture, reflectivity, refractivity);
    }

    /**
     * @param viewer is used to calculate how the color will behave in relation with the camara ray
     * @param light is used to see how the light is afecting the current object
     * @param originalIntersection is used to get the closest intersection properties
     * */
    @Override
    public float[] getObjectColor(Light light, Intersection originalIntersection, Ray viewer) {

        float fallOff = (float) getFallOff(light, originalIntersection);
        Color lightColor = light.getColor();
        Color objColor = originalIntersection.getObject().getColor();
        float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
        float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
            objColors[colorIndex] *= fallOff * lightColors[colorIndex];
        }
        return objColors;
    }
    /**
     * @param originalIntersection is used to calculate how the fallOff,ambient and specular in the case of BlinnPhong will behave
     * @param reflectionIntersection is used to get the current object will reflect a ray
     * @param viewer is used to calculate how the ray will behave in relation with the camara ray
     * @param objects is used to see if reflectionIntersection hit another object
     * @param light is used to see how the light is afecting the current object
     * @param lastIntersection is used to make the function recursive in order to have multiple reflection and it saves the last reflection intersection
     * @param bounce is used to determine how much a ray can bounce
     * @return the reflectivity colors
     * */
    @Override
    public float[] getReflectivityColor(Intersection originalIntersection,Intersection reflectionIntersection,Ray viewer,ArrayList<Object3D> objects,Light light,Intersection lastIntersection,int bounce){
        if(getReflectivity() <=0)
            return null;
        Vector3D B = Vector3D.scalarMultiplication(reflectionIntersection.getNormal(),-2 * Vector3D.dotProduct(reflectionIntersection.getNormal(),viewer.getDirection()));
        Vector3D reflectionDirection = Vector3D.add(viewer.getDirection(),B);
        Ray reflectionRay = new Ray(reflectionIntersection.getPosition(), reflectionDirection);
        Intersection reflectivityIntersection = Raytracer.raycast(reflectionRay, objects, reflectionIntersection.getObject(),null);

        if(reflectivityIntersection != null && reflectivityIntersection.getObject().getMaterial().getReflectivity() <=0 && reflectivityIntersection.getObject().getMaterial().getRefractivity() <=0){
            float fallOff = (float) getFallOff(light,reflectivityIntersection);
            Color lightColor = light.getColor();
            Color objColor = reflectivityIntersection.getObject().getColor();
            float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
            float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
            for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                objColors[colorIndex] *= fallOff * lightColors[colorIndex];
            }
            return objColors;
        }
        if(reflectivityIntersection != null && reflectivityIntersection.getObject().getMaterial().getReflectivity() > 0 && bounce>0){
            return getReflectivityColor(originalIntersection,reflectivityIntersection,viewer,objects,light, reflectivityIntersection,bounce-1);
        }

        if(reflectivityIntersection != null && reflectivityIntersection.getObject().getMaterial().getRefractivity() > 0 && bounce>0){
            return getReflactivityColor(originalIntersection,reflectivityIntersection,viewer,objects,light, reflectivityIntersection,bounce-1);
        }

        if(reflectivityIntersection == null && lastIntersection != null && bounce>0){
            float fallOff = (float) getFallOff(light,lastIntersection);
            Color lightColor = light.getColor();
            Color objColor = lastIntersection.getObject().getColor();
            float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
            float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
            for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                objColors[colorIndex] *= fallOff * lightColors[colorIndex];
            }
            Vector3D shadowDirection = Vector3D.substract(light.getPosition(),lastIntersection.getPosition());
            Ray shadowRay = new Ray(lastIntersection.getPosition(), shadowDirection);
            Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, lastIntersection.getObject(),null);
            if(shadowIntersection != null){
                return new float[]{0,0,0};
            }
            return objColors;
        }

        return null;
    }

    /**
     * @param originalIntersection is used to calculate how the fallOff,ambient and specular in the case of BlinnPhong will behave
     * @param refractivityIntersection is used to get the current object will reflect a ray
     * @param viewer is used to calculate how the ray will behave in relation with the camara ray
     * @param objects is used to see if reflectionIntersection hit another object
     * @param light is used to see how the light is afecting the current object
     * @param lastIntersection is used to make the function recursive in order to have multiple reflection and it saves the last reflection intersection
     * @param bounce is used to determine how much a ray can bounce
     * @return the refractivity colors
     * */
    @Override
    public float[] getReflactivityColor(Intersection originalIntersection, Intersection refractivityIntersection, Ray viewer, ArrayList<Object3D> objects, Light light, Intersection lastIntersection, int bounce) {
        if(getRefractivity() <=0)
            return null;
        double n1 = 1;
        double n2 = getRefractivity();
        double n = n1/n2;
        double c1 = Vector3D.dotProduct(refractivityIntersection.getNormal(),viewer.getDirection());
        double c2 = Math.pow(1-(Math.pow(n1/n2,2)*(1-c1)),1/2);
        Vector3D reflactivityDirection = Vector3D.substract(Vector3D.scalarMultiplication(Vector3D.add(viewer.getDirection(),Vector3D.scalarMultiplication(refractivityIntersection.getNormal(),c1)),n),Vector3D.scalarMultiplication(refractivityIntersection.getNormal(),c2));
        Ray reflectionRay = new Ray(refractivityIntersection.getPosition(), reflactivityDirection);
        Intersection refracIntersection = Raytracer.raycast(reflectionRay, objects, refractivityIntersection.getObject(),null);

        if(refracIntersection != null && refracIntersection.getObject().getMaterial().getReflectivity() <=0 &&  refracIntersection.getObject().getMaterial().getRefractivity() <=0){
            float fallOff = (float) getFallOff(light,refracIntersection);
            Color lightColor = light.getColor();
            Color objColor = refracIntersection.getObject().getColor();
            float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
            float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
            for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                objColors[colorIndex] *= fallOff * lightColors[colorIndex];
            }
            Vector3D shadowDirection = Vector3D.substract(light.getPosition(),refracIntersection.getPosition());
            Ray shadowRay = new Ray(refracIntersection.getPosition(), shadowDirection);
            Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, refracIntersection.getObject(),null);
            if(shadowIntersection != null){
                return new float[]{0,0,0};
            }

            return objColors;
        }

        if(refracIntersection != null && refracIntersection.getObject().getMaterial().getReflectivity() > 0 && bounce>0){
            return getReflectivityColor(originalIntersection,refracIntersection,viewer,objects,light, refracIntersection,bounce-1);
        }

        if(refracIntersection != null && refracIntersection.getObject().getMaterial().getRefractivity() > 0 && bounce>0){
            return getReflactivityColor(originalIntersection,refracIntersection,viewer,objects,light, refracIntersection,bounce-1);
        }

        if(refracIntersection == null && lastIntersection != null && bounce>0){
            float fallOff = (float) getFallOff(light,lastIntersection);
            Color lightColor = light.getColor();
            Color objColor = lastIntersection.getObject().getColor();
            float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
            float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
            for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                objColors[colorIndex] *= fallOff * lightColors[colorIndex];
            }
            Vector3D shadowDirection = Vector3D.substract(light.getPosition(),lastIntersection.getPosition());
            Ray shadowRay = new Ray(lastIntersection.getPosition(), shadowDirection);
            Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, lastIntersection.getObject(),null);
            if(shadowIntersection != null){
                return new float[]{0,0,0};
            }

            return objColors;
        }

        return null;
    }

}
