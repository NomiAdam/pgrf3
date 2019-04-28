package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trida reprezentujici Osy
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Axis extends SolidBase {

    public Axis() {
        transform = false;
        // Vertex na pozici 0 bude spojen s Vertexy na pozicích 1, 2 a 3;
        Integer[] ints = new Integer[]{0, 1, 0, 2, 0, 3};
        // Lines 0, 1, 0, 2, 0, 3
        indexBuffer = new ArrayList<>(Arrays.asList(ints));

        parts = new ArrayList<>();
        parts.add(new Part(Part.Type.LINES, 0, 6));

        vertexBuffer = new ArrayList<>();
        // Lines
        vertexBuffer.add(new Vertex(0, 0, 0, 0xFFFFFF));
        vertexBuffer.add(new Vertex(0, 0, 1, 0x00FF00));
        vertexBuffer.add(new Vertex(0, 1, 0, 0xFF0000));
        vertexBuffer.add(new Vertex(1, 0, 0, 0x0000FF));
    }

}
