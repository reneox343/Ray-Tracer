/**
 * [1968] - [2021] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.raytracer;

/**
 * @author Rene Jorge Avila Galvan
 */

public class Vector3D {

    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * @param x is used to set the x cordinate
     * @param y is used to set the y cordinate
     * @param z is used to set the z cordinate
     * **/
    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * @param vectorA is a vector
     * @param vectorB is a vector
     * @return the dot product between vectorA and vectorB
     * */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB){
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    /**
     * @param vectorA is a vector
     * @param vectorB is a vector
     * @return the cross product between vectorA and vectorB
     * */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    /**
     * @param vectorA is a vector
     * @return returns the magnitude of the vectorA
     * */
    public static double magnitude(Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /**
     * @param vectorA is a vector
     * @param vectorB is a vector
     * @return a addition between vectorA and vectorB
     * */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }

    /**
     * @param vectorA is a vector
     * @param vectorB is a vector
     * @return a subtraction between vectorA and vectorB
     * */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }
    /**
     * @param vectorA is a vector
     * @return returns the normalization of the vectorA
     * */
    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }
    /**
     * @param vectorA is a vector
     * @param scalar is a number
     * @return a scalar multiplication between vectorA and the scalar number
     * */    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }

    public static Vector3D ZERO(){
        return ZERO.clone();
    }
}
