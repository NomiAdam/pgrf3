package raster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import transforms.Vec3D;

/**
 * Trida slouzici k rasterizaci na platno
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class RasterizerLine {
    private BufferedImage img;

    public RasterizerLine(BufferedImage img) {
        this.img = img;
    }

    /**
     * Metoda pro resterizaci hrany na platno
     *
     * @param v1
     * @param v2
     * @param color
     */
    public void draw(Vec3D v1, Vec3D v2, int color) {
        int x1 = (int) v1.getX();
        int y1 = (int) v1.getY();

        int x2 = (int) v2.getX();
        int y2 = (int) v2.getY();

        Graphics g = img.getGraphics();
        g.setColor(new Color(color));
        g.drawLine(x1, y1, x2, y2);
    }

    public int getWidth() {
        return img.getWidth() - 1;
    }

    public int getHeight() {
        return img.getHeight() - 1;
    }
}
