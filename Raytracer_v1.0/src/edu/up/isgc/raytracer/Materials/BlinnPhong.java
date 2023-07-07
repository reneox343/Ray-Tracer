package edu.up.isgc.raytracer.Materials;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Raytracer;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.objects.Object3D;
import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Rene Jorge Avila Galvan
 */

public class BlinnPhong extends Material {
    private double shininess;
    /**
     * @param texture is used to set if the shading sould be Smooth or hard shading
     * @param reflectivity is used to set how much color of the original color should be in the object
     * @param refractivity is used to set how the refraction should behave
     * @param shininess is used to set how the specular will shine the bigger the number it will shine less
     * */
    public BlinnPhong(Texture texture, double reflectivity, double refractivity, double shininess) {
        super(texture, reflectivity, refractivity);
        this.setShininess(shininess);
    }

    /**
     * @return the shininess of this
     * */
    public double getShininess() {
        return this.shininess;
    }

    /**
     * @param shininess is used to set this shininess
     * */
    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    /**
     * @param viewer is used to see how the specular will behave in relation to the camara ray
     * @param light is used to see how the specular will behave in relation to the camara light
     * @param originalIntersection  is used to see how the specular will behave in relation to the originalIntersection
     * */
    private double[] getSpecular(Ray viewer, Light light, Intersection originalIntersection) {
        Vector3D H = Vector3D.normalize(Vector3D.add(viewer.getDirection(), light.getDirection()));
        double nDotH = Vector3D.dotProduct(H, originalIntersection.getNormal());
        double specular = Math.pow(Vector3D.dotProduct(H, originalIntersection.getNormal()), this.getShininess());
        return new double[]{nDotH, specular};
    }

    /**
     * @param viewer is used to calculate how the color will behave in relation with the camara ray
     * @param light is used to see how the light is afecting the current object
     * @param originalIntersection is used to get the closest intersection properties
     * */
    @Override
    public float[] getObjectColor(Light light, Intersection originalIntersection, Ray viewer) {
        float fallOff = (float)this.getFallOff(light, originalIntersection);
        double[] specular = this.getSpecular(viewer, light, originalIntersection);
        Color lightColor = light.getColor();
        Color objColor = originalIntersection.getObject().getColor();
        float[] lightColors = new float[]{(float)lightColor.getRed() / 255.0F, (float)lightColor.getGreen() / 255.0F, (float)lightColor.getBlue() / 255.0F};
        float[] objColors = new float[]{(float)objColor.getRed() / 255.0F, (float)objColor.getGreen() / 255.0F, (float)objColor.getBlue() / 255.0F};

        for(int colorIndex = 0; colorIndex < objColors.length; ++colorIndex) {
            objColors[colorIndex] *= fallOff * lightColors[colorIndex];
            if (specular[0] > 0.0D) {
                objColors[colorIndex] = (float)((double)objColors[colorIndex] + (double)lightColors[colorIndex] * specular[1] * (double)fallOff);
            }
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
    public float[] getReflectivityColor(Intersection originalIntersection, Intersection reflectionIntersection, Ray viewer, ArrayList<Object3D> objects, Light light, Intersection lastIntersection, int bounce) {
        if (this.getReflectivity() <= 0.0D) {
            return null;
        } else {
            Vector3D B = Vector3D.scalarMultiplication(reflectionIntersection.getNormal(), -2.0D * Vector3D.dotProduct(reflectionIntersection.getNormal(), viewer.getDirection()));
            Vector3D reflectionDirection = Vector3D.add(viewer.getDirection(), B);
            Ray reflectionRay = new Ray(reflectionIntersection.getPosition(), reflectionDirection);
            Intersection reflectivityIntersection = Raytracer.raycast(reflectionRay, objects, reflectionIntersection.getObject(), (float[])null);
            float fallOff;
            double[] specular;
            Color lightColor;
            Color objColor;
            float[] lightColors;
            float[] objColors;
            int colorIndex;
            if (reflectivityIntersection != null && reflectivityIntersection.getObject().getMaterial().getReflectivity() <= 0 && reflectivityIntersection.getObject().getMaterial().getRefractivity() <= 0) {
                fallOff = (float)this.getFallOff(light, reflectivityIntersection);
                specular = this.getSpecular(viewer, light, reflectivityIntersection);
                lightColor = light.getColor();
                objColor = reflectivityIntersection.getObject().getColor();
                lightColors = new float[]{(float)lightColor.getRed() / 255.0F, (float)lightColor.getGreen() / 255.0F, (float)lightColor.getBlue() / 255.0F};
                objColors = new float[]{(float)objColor.getRed() / 255.0F, (float)objColor.getGreen() / 255.0F, (float)objColor.getBlue() / 255.0F};

                for(colorIndex = 0; colorIndex < objColors.length; ++colorIndex) {
                    objColors[colorIndex] *= fallOff * lightColors[colorIndex];
                    if (specular[0] > 0.0D) {
                        objColors[colorIndex] = (float)((double)objColors[colorIndex] + (double)lightColors[colorIndex] * specular[1] * (double)fallOff);
                    }
                }
                Vector3D shadowDirection = Vector3D.substract(light.getPosition(),reflectivityIntersection.getPosition());
                Ray shadowRay = new Ray(reflectivityIntersection.getPosition(), shadowDirection);
                Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, reflectivityIntersection.getObject(),null);
                if(shadowIntersection != null){
                    return new float[]{0,0,0};
                }

                return objColors;
            }
            if (reflectivityIntersection != null && reflectivityIntersection.getObject().getMaterial().getReflectivity() > 0 && bounce > 0) {
                return this.getReflectivityColor(originalIntersection, reflectivityIntersection, viewer, objects, light, reflectivityIntersection, bounce - 1);
            }
            if (reflectivityIntersection != null && reflectivityIntersection.getObject().getMaterial().getRefractivity() > 0 && bounce > 0) {
                return this.getReflactivityColor(originalIntersection, reflectivityIntersection, viewer, objects, light, reflectivityIntersection, bounce - 1);
            }
            if (reflectivityIntersection == null && lastIntersection != null && bounce > 0) {
                fallOff = (float)this.getFallOff(light, lastIntersection);
                specular = this.getSpecular(viewer, light, lastIntersection);
                lightColor = light.getColor();
                objColor = lastIntersection.getObject().getColor();
                lightColors = new float[]{(float)lightColor.getRed() / 255.0F, (float)lightColor.getGreen() / 255.0F, (float)lightColor.getBlue() / 255.0F};
                objColors = new float[]{(float)objColor.getRed() / 255.0F, (float)objColor.getGreen() / 255.0F, (float)objColor.getBlue() / 255.0F};

                for(colorIndex = 0; colorIndex < objColors.length; ++colorIndex) {
                    objColors[colorIndex] *= fallOff * lightColors[colorIndex];
                    if (specular[0] > 0.0D) {
                        objColors[colorIndex] = (float)((double)objColors[colorIndex] + (double)lightColors[colorIndex] * specular[1] * (double)fallOff);
                    }
                }
                Vector3D shadowDirection = Vector3D.substract(light.getPosition(),lastIntersection.getPosition());
                Ray shadowRay = new Ray(lastIntersection.getPosition(), shadowDirection);
                Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, lastIntersection.getObject(),null);
                if(shadowIntersection != null){
                    return new float[]{0,0,0};
                }

                return objColors;
            } else {
                return null;
            }
        }
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
    public float[] getReflactivityColor(Intersection originalIntersection, Intersection refractivityIntersection, Ray viewer, ArrayList<Object3D> objects, Light light, Intersection lastIntersection, int bounce) {
        if (this.getRefractivity() <= 0.0D) {
            return null;
        } else {
            double n1 = 1.0D;
            double n2 = this.getRefractivity();
            double n = n1 / n2;
            double c1 = Vector3D.dotProduct(refractivityIntersection.getNormal(), viewer.getDirection());
            double c2 = Math.pow(1.0D - Math.pow(n1 / n2, 2.0D) * (1.0D - c1), 0.0D);
            Vector3D reflactivityDirection = Vector3D.substract(Vector3D.scalarMultiplication(Vector3D.add(viewer.getDirection(), Vector3D.scalarMultiplication(refractivityIntersection.getNormal(), c1)), n), Vector3D.scalarMultiplication(refractivityIntersection.getNormal(), c2));
            Ray reflactivityRay = new Ray(refractivityIntersection.getPosition(), reflactivityDirection);
            Intersection refracIntersection = Raytracer.raycast(reflactivityRay, objects, refractivityIntersection.getObject(), (float[])null);
            float fallOff;
            double[] specular;
            Color lightColor;
            Color objColor;
            float[] lightColors;
            float[] objColors;
            int colorIndex;
            if (refracIntersection != null && refracIntersection.getObject().getMaterial().getReflectivity() <= 0.0 && refracIntersection.getObject().getMaterial().getRefractivity() <= 0.0) {
                fallOff = (float)this.getFallOff(light, refracIntersection);
                specular = this.getSpecular(viewer, light, refracIntersection);
                lightColor = light.getColor();
                objColor = refracIntersection.getObject().getColor();
                lightColors = new float[]{(float)lightColor.getRed() / 255.0F, (float)lightColor.getGreen() / 255.0F, (float)lightColor.getBlue() / 255.0F};
                objColors = new float[]{(float)objColor.getRed() / 255.0F, (float)objColor.getGreen() / 255.0F, (float)objColor.getBlue() / 255.0F};

                for(colorIndex = 0; colorIndex < objColors.length; ++colorIndex) {
                    objColors[colorIndex] *= fallOff * lightColors[colorIndex];
                    if (specular[0] > 0.0D) {
                        objColors[colorIndex] = (float)((double)objColors[colorIndex] + (double)lightColors[colorIndex] * specular[1] * (double)fallOff);
                    }
                }
                Vector3D shadowDirection = Vector3D.substract(light.getPosition(),refracIntersection.getPosition());
                Ray shadowRay = new Ray(refracIntersection.getPosition(), shadowDirection);
                Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, refracIntersection.getObject(),null);
                if(shadowIntersection != null){
                    return new float[]{0,0,0};
                }

                return objColors;
            }
            if (refracIntersection != null && refracIntersection.getObject().getMaterial().getReflectivity() > 0.0 && bounce > 0) {
                return this.getReflectivityColor(originalIntersection, refracIntersection, viewer, objects, light, refracIntersection, bounce - 1);
            }
            if (refracIntersection != null && refracIntersection.getObject().getMaterial().getRefractivity() > 0.0 && bounce > 0) {
                return this.getReflactivityColor(originalIntersection, refracIntersection, viewer, objects, light, refracIntersection, bounce - 1);
            }
            if (refracIntersection == null && lastIntersection != null && bounce > 0) {
                fallOff = (float)this.getFallOff(light, lastIntersection);
                specular = this.getSpecular(viewer, light, lastIntersection);
                lightColor = light.getColor();
                objColor = lastIntersection.getObject().getColor();
                lightColors = new float[]{(float)lightColor.getRed() / 255.0F, (float)lightColor.getGreen() / 255.0F, (float)lightColor.getBlue() / 255.0F};
                objColors = new float[]{(float)objColor.getRed() / 255.0F, (float)objColor.getGreen() / 255.0F, (float)objColor.getBlue() / 255.0F};

                for(colorIndex = 0; colorIndex < objColors.length; ++colorIndex) {
                    objColors[colorIndex] *= fallOff * lightColors[colorIndex];
                    if (specular[0] > 0.0D) {
                        objColors[colorIndex] = (float)((double)objColors[colorIndex] + (double)lightColors[colorIndex] * specular[1] * (double)fallOff);
                    }
                }
                Vector3D shadowDirection = Vector3D.substract(light.getPosition(),lastIntersection.getPosition());
                Ray shadowRay = new Ray(lastIntersection.getPosition(), shadowDirection);
                Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, lastIntersection.getObject(),null);
                if(shadowIntersection != null){
                    return new float[]{0,0,0};
                }

                return objColors;
            } else {
                return null;
            }
        }
    }
}
