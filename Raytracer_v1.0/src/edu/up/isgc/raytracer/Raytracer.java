/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer;

import edu.up.isgc.raytracer.Materials.BlinnPhong;
import edu.up.isgc.raytracer.Materials.Material;
import edu.up.isgc.raytracer.Materials.Phong;
import edu.up.isgc.raytracer.Materials.Texture;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.objects.*;
import edu.up.isgc.raytracer.tools.OBJReader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Rene Jorge Avila Galvan
 */

public class Raytracer {
    static private BufferedImage image;
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene(Color.black);
        scene01.setCamera(new Camera(new Vector3D(0, 0, -10), 160, 160, 1200, 1200, 3f, 50f));
        //luces generales
        scene01.addLight(new PointLight(new Vector3D(-3f, 1f, 6f),Color.red,8));
        scene01.addLight(new PointLight(new Vector3D(3f, 1f, 6f),Color.blue,8));
        scene01.addLight(new PointLight(new Vector3D(3f, 1f, 6f),Color.blue,8));
//        scene01.addLight(new PointLight(new Vector3D(0f, 5f, 6f),Color.white,8));
        scene01.addLight(new PointLight(new Vector3D(-3f, 5f, 0f),Color.red,8));
        //0
//        scene01.addLight(new DirectionalLight(new Vector3D(0, 0, 0), new Vector3D(1, 0, 0), Color.WHITE, 3f));
//        scene01.addLight(new DirectionalLight(new Vector3D(0, 0, 0), new Vector3D(0, 1, 0), Color.WHITE, 3f));
//        scene01.addObject(new Sphere(new Vector3D(-2f, -1f, 1f), 0.3f, Color.black,new BlinnPhong(Texture.smooth, 0,1f,3)));
//        scene01.addObject(new Sphere(new Vector3D(-2f, 1f, 3f), 2f, Color.RED,new BlinnPhong(Texture.smooth,1,0,3)));
//        scene01.addObject(OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(-2f, -2f, 3f), Color.black,new BlinnPhong(Texture.smooth,1f,0f,3)));
        //scene02
//            scene01.addObject(new Sphere(new Vector3D(-0.5f, 1f, 3f), 0.3f, Color.red,new BlinnPhong(Texture.smooth, 0f,0,3)));
//            scene01.addObject(new Sphere(new Vector3D(-3f, 1f, 2f), 1f, Color.blue,new BlinnPhong(Texture.smooth, 0.5f,0,3)));
//            scene01.addObject(new Sphere(new Vector3D(-2f, -2f, 4f), 0.5f, Color.pink,new BlinnPhong(Texture.smooth, 0f,0,3)));
//            scene01.addObject(new Sphere(new Vector3D(3f, 2f, 5f), 1f, Color.orange,new BlinnPhong(Texture.smooth, 0f,0,3)));
//            scene01.addObject(new Sphere(new Vector3D(1.5f, 1f, 1f), 0.5f, Color.black,new BlinnPhong(Texture.smooth, 1f,0,3)));
//            scene01.addObject(new Sphere(new Vector3D(0f, 1f, 1f), 0.5f, Color.black,new BlinnPhong(Texture.smooth, 0f,1.8,3)));
        //scene 01
//        scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(-2f, -2f, 5.5f), Color.RED,new Phong(Texture.smooth,0,0)));
//        scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(2f, -2f, 5.5f), Color.green,new Phong(Texture.smooth,0,0)));
//        scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(0f, -2f, 0f), Color.pink,new Phong(Texture.smooth,0,0)));
//        scene01.addObject(new Sphere(new Vector3D(-0.5f, 1f, 15f), 7f, Color.black,new BlinnPhong(Texture.smooth, 1f,0,3)));
//        scene01.addObject(new Sphere(new Vector3D(-2f, -1f, 3f), 1f, Color.black,new BlinnPhong(Texture.smooth, 0f,1.8f,3)));
//        scene01.addObject(new Sphere(new Vector3D(2f, -1f, 3f), 1f, Color.black,new BlinnPhong(Texture.smooth, 0f,1.8f,1)));
//        scene01.addObject(new Sphere(new Vector3D(0f, -1f, 3f), 1f, Color.black,new BlinnPhong(Texture.smooth, 0f,1f,1)));
        //scene 03 refraccion
//        scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(-2f, -2f, 0f),3, Color.black,new BlinnPhong(Texture.smooth,0f,1f,3)));
//        scene01.addObject(OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(-4f, -2f, 3f),0.1f, new Color(0, 220, 255),new Phong(Texture.smooth,0f,0f)));
//        scene01.addObject(OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(-3f, -2f, 3f),0.5f, new Color(85, 180, 252),new BlinnPhong(Texture.hard,0f,0f,2)));
//        scene01.addObject(OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(-0.7f, -2f, 3f),1f, new Color(138, 139, 212),new BlinnPhong(Texture.smooth,0f,0f,3)));
//        scene01.addObject(OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(3.5f, -2f, 3f),1.5f, new Color(157, 100, 155),new BlinnPhong(Texture.hard,0f,0f,3)));
//        scene01.addObject(OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(0f, -2f, 10f),4f, new Color(146, 70, 96),new BlinnPhong(Texture.smooth,0f,0f,3)));
//        scene01.addObject(new Sphere(new Vector3D(2.5f, -1f, -1f), 1f, Color.black,new BlinnPhong(Texture.smooth, 0f,1.8,3)));
        //scene 04
        scene01.addObject(new Polygon(new Vector3D(0f, -2f, 40f), new Triangle[]{new Triangle(new Vector3D(60f,0f,60f), new Vector3D(-60f,0f,60f), new Vector3D(-60f,0f,-60f)),
                new Triangle(new Vector3D(-60f,0f,-60f), new Vector3D(60f,0f,-60f), new Vector3D(60f,0f,60f))}, Color.black,new Phong(Texture.smooth,1,0f)));

        scene01.addObject(OBJReader.GetPolygon("eyes.obj", new Vector3D(0f, 0f, 8f),3f, Color.red,new BlinnPhong(Texture.smooth,0f,0f,3)));
//        scene01.addObject(OBJReader.GetPolygon("jaw.obj", new Vector3D(0f, 0f, 8),3f, Color.white,new BlinnPhong(Texture.smooth,0f,0f,3)));
//        scene01.addObject(OBJReader.GetPolygon("theeth.obj", new Vector3D(0f, 0f, 8f),3f, Color.white,new BlinnPhong(Texture.smooth,0f,0f,3)));
//        scene01.addObject(OBJReader.GetPolygon("head.obj", new Vector3D(0f, 0f, 8f),3f, Color.darkGray,new Phong(Texture.smooth,0f,0f)));

//        raytrace(scene01);
        raytraceParalel(scene01);

        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ioe) {
            System.out.println("Something failed");
        }
        System.out.println(new Date());
    }

    /**
     * @param scene get the attributes of the scene and render it
     * */
    public static void raytraceParalel(Scene scene) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Camera mainCamera = scene.getCamera();
        ArrayList<Light> lights = scene.getLights();
        float[] nearFarPlanes = mainCamera.getNearFarPlanes();
        image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                Runnable runnable = parallelRaytrace(i,j,mainCamera,lights,nearFarPlanes,objects,positionsToRaytrace,scene);
                executorService.execute(runnable);
            }
        }
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(1, TimeUnit.HOURS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException var7) {
            var7.printStackTrace();
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("Cancel non-finished");
            }

        }

        executorService.shutdownNow();

    }

    /**
     * @param scene get the attributes of the scene and render it
     * */
    public static void raytrace(Scene scene) {

        Camera mainCamera = scene.getCamera();
        ArrayList<Light> lights = scene.getLights();
        float[] nearFarPlanes = mainCamera.getNearFarPlanes();
        image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                float cameraZ = (float) mainCamera.getPosition().getZ();
                Intersection closestIntersection = raycast(ray, objects, null, new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                //Background color
                Color pixelColor = scene.getBackgroundColor();
                if (closestIntersection != null) {
                    for (Light light : lights) {
                        float[] objColors = null;
                        Color diffuse = null;
                        float[] reflectivityColor = closestIntersection.getObject().getMaterial().getReflectivityColor(closestIntersection,closestIntersection,ray,objects,light,null,4);
                        float[] refractivityColor = closestIntersection.getObject().getMaterial().getReflactivityColor(closestIntersection,closestIntersection,ray,objects,light,null,4);
                        objColors = closestIntersection.getObject().getMaterial().getObjectColor(light, closestIntersection, ray);
                        if(reflectivityColor != null){
                            objColors =  Material.balanceReflectivityColor(objColors,reflectivityColor,closestIntersection);
                        }
                        if(refractivityColor != null){
                            objColors =  refractivityColor;
                        }
                        diffuse = new Color(clamp(objColors[0], 0, 1), clamp(objColors[1], 0, 1), clamp(objColors[2], 0, 1));
                        pixelColor = addColor(pixelColor, diffuse);

                        Vector3D shadowDirection = Vector3D.substract(light.getPosition(),closestIntersection.getPosition());
                        Ray shadowRay = new Ray(closestIntersection.getPosition(), shadowDirection);
                        Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, closestIntersection.getObject(),null);
                        if(shadowIntersection != null){
                            pixelColor = Color.BLACK;
                        }
                    }
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }


    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static Color addColor(Color original, Color otherColor){
        float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
        float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
        float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
        return new Color(red, green, blue);
    }

    /**
     * @param ray is used to get the direction and origin of the ray
     * @param objects is used to see if the ray intersects with any object
     * @param caster  is used to exclude and intersection with an object
     * @param clippingPlanes is used to determine the max and min intersection distance
     * */
    public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int k = 0; k < objects.size(); k++) {
            Object3D currentObj = objects.get(k);
            if (caster == null || !currentObj.equals(caster)) {
                Intersection intersection = currentObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersection.getPosition().getZ() >= clippingPlanes[0] &&
                                    intersection.getPosition().getZ() <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }

    public static Runnable parallelRaytrace(final int i, final int j,Camera mainCamera,ArrayList<Light> lights,float[] nearFarPlanes,ArrayList<Object3D> objects,Vector3D[][] positionsToRaytrace,Scene scene) {
        Runnable aRunnable = new Runnable() {
            public void run() {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                float cameraZ = (float) mainCamera.getPosition().getZ();
                Intersection closestIntersection = raycast(ray, objects, null, new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                //Background color
                Color pixelColor = scene.getBackgroundColor();
                if (closestIntersection != null) {
                    for (Light light : lights) {
                        float[] objColors = null;
                        Color diffuse = null;
                        float[] reflectivityColor = closestIntersection.getObject().getMaterial().getReflectivityColor(closestIntersection,closestIntersection,ray,objects,light,null,6);
                        float[] refractivityColor = closestIntersection.getObject().getMaterial().getReflactivityColor(closestIntersection,closestIntersection,ray,objects,light,null,6);
                        objColors = closestIntersection.getObject().getMaterial().getObjectColor(light, closestIntersection, ray);
                        if(reflectivityColor != null){
                            objColors =  Material.balanceReflectivityColor(objColors,reflectivityColor,closestIntersection);
                        }
                        if(refractivityColor != null){
                            objColors =  refractivityColor;
                        }
                        diffuse = new Color(clamp(objColors[0], 0, 1), clamp(objColors[1], 0, 1), clamp(objColors[2], 0, 1));
                        pixelColor = addColor(pixelColor, diffuse);

                        Vector3D shadowDirection = Vector3D.substract(light.getPosition(),closestIntersection.getPosition());
                        Ray shadowRay = new Ray(closestIntersection.getPosition(), shadowDirection);
                        Intersection shadowIntersection = Raytracer.raycast(shadowRay, objects, closestIntersection.getObject(),null);
                        if(shadowIntersection != null){
                            pixelColor = Color.BLACK;
                        }
                    }
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        };
        return aRunnable;
    }

}
