package model;

import java.util.List;

/**
 * Abstraktni Trida reprezentujici Geometricky Objekt
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public abstract class SolidBase {
    protected List<Vertex> vertexBuffer;
    protected List<Integer> indexBuffer;
    protected List<Part> parts;
    protected boolean transform;

    public List<Vertex> getVertices() {
        return vertexBuffer;
    }

    public List<Integer> getIndices() {
        return indexBuffer;
    }

    public List<Part> getParts() {
        return parts;
    }

    public boolean getTransform() {
        return transform;
    }
}
