package view;

import model.*;
import transforms.Cubic;
import transforms.Mat4;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;

import javax.swing.*;
import java.awt.*;

public class SelectionPanel extends JPanel {
    private static final Mat4 MAT_ORTHO = new Mat4OrthoRH(6, 6, 0.001, 100);
    private static final Mat4 MAT_PERSP = new Mat4PerspRH(Math.PI / 4, 1, 0.001, 100);

    private Canvas parent;
    private SolidBase selectedObject;
    private Curves selectedCurve;
    private JButton hint;
    private ButtonGroup objectGroup, curveGroup, projectionGroup;
    private JRadioButton cube, tetrahedron, block, ferguson, bezier, coons, projectionPers, projectionOtrho;
    private Mat4 projectionMat;

    public SelectionPanel(int width, int height, Canvas parent) {
        this.parent = parent;
        setSize(new Dimension(width, height));
        setFocusable(false);
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        projectionGroup = new ButtonGroup();
        projectionMat = MAT_ORTHO;

        projectionOtrho = new JRadioButton("Orthogonalni", true);
        projectionPers = new JRadioButton("Perspektivni");
        projectionOtrho.setFocusable(false);
        projectionPers.setFocusable(false);
        projectionGroup.add(projectionPers);
        projectionGroup.add(projectionOtrho);

        projectionOtrho.addActionListener((e) -> {
            projectionMat = MAT_ORTHO;
            draw();
        });
        projectionPers.addActionListener((e) -> {
            projectionMat = MAT_PERSP;
            draw();
        });

        add(new JLabel("Projekce"));
        add(projectionOtrho);
        add(projectionPers);

        selectedCurve = new Curves(Cubic.FERGUSON);
        selectedObject = new Tetrahedron();

        cube = new JRadioButton("Krychle");
        tetrahedron = new JRadioButton("Ctyrsten", true);
        block = new JRadioButton("Kvadr");
        cube.setFocusable(false);
        tetrahedron.setFocusable(false);
        block.setFocusable(false);

        cube.addActionListener((e) -> {
            selectedObject = new Cube();
            draw();
        });
        tetrahedron.addActionListener((e) -> {
            selectedObject = new Tetrahedron();
            draw();
        });
        block.addActionListener((e) -> {
            selectedObject = new Block();
            draw();
        });

        objectGroup = new ButtonGroup();
        objectGroup.add(cube);
        objectGroup.add(tetrahedron);
        objectGroup.add(block);

        ferguson = new JRadioButton("Fergusonova", true);
        bezier = new JRadioButton("Bezierova");
        coons = new JRadioButton("Coonsova");
        ferguson.setFocusable(false);
        bezier.setFocusable(false);
        coons.setFocusable(false);

        ferguson.addActionListener(e -> {
            selectedCurve = new Curves(Cubic.FERGUSON);
            draw();
        });
        bezier.addActionListener(e -> {
            selectedCurve = new Curves(Cubic.BEZIER);
            draw();
        });
        coons.addActionListener(e -> {
            selectedCurve = new Curves(Cubic.COONS);
            draw();
        });

        curveGroup = new ButtonGroup();
        curveGroup.add(bezier);
        curveGroup.add(ferguson);
        curveGroup.add(coons);

        add(new JLabel("Objekty"));

        add(tetrahedron);
        add(cube);
        add(block);

        add(new JLabel("Kubiky"));

        add(ferguson);
        add(bezier);
        add(coons);

        hint = new JButton("Napoveda");
        hint.addActionListener(e -> JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
                "Pro Pohyb - W,S,A,D" +
                        "\nPro Rozhlizeni - Leve tlacitko mysi" +
                        "\nPro Rotaci Objektu - Prave tlacitko mysi" +
                        "\nPro Zoom in/Zoom out (pri perspektivni projekci) - Kolečko mysi"
                , "Napoveda", JOptionPane.INFORMATION_MESSAGE));
        add(hint);
    }

    public void draw() {
        parent.draw();
    }

    public SolidBase getSelectedObject() {
        return selectedObject;
    }

    public Curves getSelectedCurve() {
        return selectedCurve;
    }

    public Mat4 getProjectionMat() {
        return projectionMat;
    }
}
