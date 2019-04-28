package model;

import transforms.Mat4;
import transforms.Point3D;

/**
 * Trida reprezentujici Vrchol
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Vertex {
	private Point3D position;
	private int color;

	public Vertex(double x, double y, double z) {
		position = new Point3D(x, y, z);
		color = 0xffffff;
	}

	public Vertex(double x, double y, double z, int color) {
		position = new Point3D(x, y, z);
		this.color = color;
	}

	public Vertex(Point3D point3D, int color) {
		this.position = point3D;
		this.color = color;
	}

	public Point3D getPosition() {
		return position;
	}

	public int getColor() {
		return color;
	}

	public Vertex mul(Mat4 mat) {
		return new Vertex(this.position.mul(mat), this.color);
	}
}
