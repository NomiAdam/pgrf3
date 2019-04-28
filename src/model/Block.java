package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trida reprezentujici Kvadr
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Block extends SolidBase {
    public Block() {
        transform = true;
        vertexBuffer = new ArrayList<>();
        parts = new ArrayList<>();
        parts.add(new Part(Part.Type.BLOCK, 0, 24));

        Integer[] indices = {0, 1, 0, 2, 2, 3, 3, 1, 0, 4, 4, 5, 5, 7, 7, 6, 6, 4, 2, 6, 3, 7, 1, 5};
        indexBuffer = new ArrayList<>(Arrays.asList(indices));

        vertexBuffer.add(new Vertex(0, 0, 0));
        vertexBuffer.add(new Vertex(1.5, 0, 0));
        vertexBuffer.add(new Vertex(0, 1, 0));
        vertexBuffer.add(new Vertex(1.5, 1, 0));
        vertexBuffer.add(new Vertex(0, 0, 1));
        vertexBuffer.add(new Vertex(1.5, 0, 1));
        vertexBuffer.add(new Vertex(0, 1, 1));
        vertexBuffer.add(new Vertex(1.5, 1, 1));
    }
}
