package model;

import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trida reprezentujici Kubiky
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Curves extends SolidBase {
    private int color = 0xffff00;

    public Curves() {
        transform = true;

        int numberOfPoints = 10;

        Point3D p1 = new Point3D(0, 0, 0);
        Point3D p2 = new Point3D(0, 1, 1);
        Point3D p3 = new Point3D(1, 1, 0);
        Point3D p4 = new Point3D(1, 0, 3);
        Cubic cubic = new Cubic(Cubic.BEZIER, p1, p2, p3, p4);

        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            vertexBuffer.add(new Vertex(cubic.compute((double) i / numberOfPoints), color));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }

        parts = new ArrayList<>();
        parts.add(new Part(Part.Type.LINES, 0, numberOfPoints));
    }

    public Curves(Mat4 baseMat) {
        transform = true;

        int numberOfPoints = 10;

        Point3D p1 = new Point3D(0, 0, 0);
        Point3D p2 = new Point3D(0, 1, 1);
        Point3D p3 = new Point3D(1, 1, 0);
        Point3D p4 = new Point3D(1, 0, 1);
        Cubic cubic = new Cubic(baseMat, p1, p2, p3, p4);

        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            vertexBuffer.add(new Vertex(cubic.compute((double) i / numberOfPoints), color));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }

        parts = new ArrayList<>();
        parts.add(new Part(Part.Type.LINES, 0, numberOfPoints));
    }

    public Curves(Mat4 baseMat, int numberOfPoints, Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        transform = true;

        Cubic cubic = new Cubic(baseMat, p1, p2, p3, p4);

        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            vertexBuffer.add(new Vertex(cubic.compute((double) i / numberOfPoints), 0xffffff));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }

        parts = new ArrayList<>();
        parts.add(new Part(Part.Type.LINES, 0, numberOfPoints));
    }

    public void setColor(int color) {
        this.color = color;
    }
}
