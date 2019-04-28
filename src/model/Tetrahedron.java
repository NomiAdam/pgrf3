package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trida reprezentujici Ctyrsten
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Tetrahedron extends SolidBase {
    public Tetrahedron() {
        transform = true;
        Integer[] indices = new Integer[]{0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 2, 3};
        indexBuffer = new ArrayList<>(Arrays.asList(indices));

        parts = new ArrayList<>();
        parts.add(new Part(Part.Type.TETRAHEDRON, 0, 12));

        vertexBuffer = new ArrayList<>();
        vertexBuffer.add(new Vertex(1, 0, 0));
        vertexBuffer.add(new Vertex(0, 0, 0));
        vertexBuffer.add(new Vertex(0, 1, 0));
        vertexBuffer.add(new Vertex(0, 0, 1));

    }
}
