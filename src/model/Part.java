package model;

/**
 * Trida reprezentujici Casti
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Part {
    public enum Type {LINES, CUBE, TETRAHEDRON, AXIS, BLOCK}

    private Type type;
    private int start;
    private int count;

    public Part(Type type, int start, int count) {
        this.type = type;
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public Type getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
