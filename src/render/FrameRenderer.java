package render;

import java.util.ArrayList;
import java.util.List;

import model.*;
import raster.RasterizerLine;
import transforms.*;

/**
 * Trida reprezentujici pohledovy retezec
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class FrameRenderer {
    private List<Integer> ib;
    private List<Vertex> vb;
    private Mat4 model, view, projection;
    private RasterizerLine rasterizer;

    public FrameRenderer(RasterizerLine rasterizer) {
        this.rasterizer = rasterizer;
        ib = new ArrayList<>();
        vb = new ArrayList<>();
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void render(SolidBase sb) {

        ib = sb.getIndices();

        if (!sb.getTransform()) {
            model = new Mat4Identity();
        }

        Mat4 matMVP = model.mul(view).mul(projection);

        for (Vertex v : sb.getVertices()) {
            // Transformace vrcholu modelovou, pohledevou a projekcni matici
            vb.add(v.mul(matMVP));
        }

        for (Part part : sb.getParts()) {
            int indexStart = part.getStart();
            int indexCount = part.getCount();
            if (part.getType() != null) {
                for (int i = indexStart; i < indexCount; i += 2) {
                    line(vb.get(ib.get(i)), vb.get(ib.get(i + 1)));
                }
            }
        }

        vb = new ArrayList<>();
        ib = new ArrayList<>();
    }

    private void line(Vertex a, Vertex b) {
        Vertex vertexA = a;
        Vertex vertexB = b;

        // Rychle orezani
        if (fastCut(vertexA.getPosition()) || fastCut(vertexB.getPosition())) {
            return;
        }

        Vec3D vecA;
        Vec3D vecB;

        // Dehomogenizace
        if (!vertexA.getPosition().dehomog().isPresent() || !vertexB.getPosition().dehomog().isPresent()) {
            return;
        }
        vecA = vertexA.getPosition().dehomog().get();
        vecB = vertexB.getPosition().dehomog().get();

        // Transformace do okna
        int x1 = (int) ((vecA.getX() + 1) * (rasterizer.getWidth() - 1) / 2);
        int y1 = (int) ((1 - vecA.getY()) * (rasterizer.getHeight() - 1) / 2);
        int x2 = (int) ((vecB.getX() + 1) * (rasterizer.getWidth() - 1) / 2);
        int y2 = (int) ((1 - vecB.getY()) * (rasterizer.getHeight() - 1) / 2);

        rasterizer.draw(new Vec3D(x1, y1, 0), new Vec3D(x2, y2, 0), vertexB.getColor());
    }

    /**
     * Metoda pro rychle orezani
     *
     * @param a
     * @return boolean
     */
    public boolean fastCut(Point3D a) {
        return (-a.getW() >= a.getX() || -a.getW() >= a.getY() || a.getX() >= a.getW() || a.getY() >= a.getW()
                || 0 >= a.getZ() || a.getZ() >= a.getW());
    }

}
