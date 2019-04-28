package view;

import model.*;
import raster.RasterizerLine;
import render.FrameRenderer;
import transforms.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * Trida pro kresleni na platno
 *
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas extends JFrame {
    private static final int BACKGROUND_COLOR = 0x2f4f4f;
    private static final double STEP = 0.05;

    private JPanel panel;
    private BufferedImage img;
    private SelectionPanel selectionPanel;

    private Camera camera;
    private FrameRenderer fr;
    private Axis axis;
    private Mat4 model;
    private Mat4 translation;

    private int nextX;
    private int nextY;
    private int lastX;
    private int lastY;
    private double transX;
    private double transY;
    private double transZ;

    public Canvas(int width, int height) {
        setLayout(new BorderLayout());
        setTitle("Adam Kvasnicka PGRF1 Uloha 3");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        selectionPanel = new SelectionPanel((int) (width * 0.2), height, this);
        fr = new FrameRenderer(new RasterizerLine(img));
        axis = new Axis();

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        add(panel, BorderLayout.CENTER);
        add(selectionPanel, BorderLayout.EAST);
        pack();
        setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                nextX = e.getX();
                nextY = e.getY();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                lastX = nextX;
                lastY = nextY;

                nextX = e.getX();
                nextY = e.getY();

                double dx = nextX - lastX;
                double dy = nextY - lastY;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    camera = camera.addAzimuth(Math.PI * dx / width);
                    camera = camera.addZenith(Math.PI * dy / width);
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    translation = new Mat4Transl(transX, transY, transZ);
                    model = model.mul(translation.inverse().get());
                    model = model.mul(new Mat4RotXYZ(-dx * Math.PI / 180, dy * Math.PI / 180, 0));
                    model = model.mul(translation);
                }

                draw();
            }
        });

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 1) {
                    camera = camera.forward(STEP);
                } else {
                    camera = camera.backward(STEP);
                }
                draw();
            }
        });

        panel.requestFocus();
        panel.requestFocusInWindow();

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moveUp");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moveDown");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moveLeft");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moveRight");

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "transUp");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "transDown");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "transLeft");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "transRight");

        panel.getActionMap().put("transUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transZ += STEP;
                model = model.mul(new Mat4Transl(0, 0, STEP));
                draw();
            }
        });
        panel.getActionMap().put("transDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transZ -= STEP;
                model = model.mul(new Mat4Transl(0, 0, -STEP));
                draw();
            }
        });
        panel.getActionMap().put("transLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transY += STEP;
                model = model.mul(new Mat4Transl(0, STEP, 0));
                draw();
            }
        });
        panel.getActionMap().put("transRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transY -= STEP;
                model = model.mul(new Mat4Transl(0, -STEP, 0));
                draw();
            }
        });

        panel.getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera = camera.up(STEP);
                draw();
            }
        });

        panel.getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera = camera.down(STEP);
                draw();
            }
        });

        panel.getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera = camera.left(STEP);
                draw();
            }
        });

        panel.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera = camera.right(STEP);
                draw();
            }
        });
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(BACKGROUND_COLOR));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();

        fr.setModel(model);
        fr.setProjection(selectionPanel.getProjectionMat());
        fr.setView(camera.getViewMatrix());

        fr.render(selectionPanel.getSelectedObject());
        fr.render(selectionPanel.getSelectedCurve());

        fr.render(axis);

        panel.repaint();
    }

    /**
     * Metoda pro reset transformaci
     */
    public void init() {
        model = new Mat4Identity().mul(new Mat4RotX(Math.PI / 180 * 45).mul(new Mat4RotY(Math.PI / 180 * 65)));
        camera = new Camera(new Vec3D(-7, 0, 0), 0, 0, 1, true);
        transX = 0;
        transY = 0;
        transZ = 0;
        translation = new Mat4Transl(transX, transY, transZ);
    }

    public void start() {
        clear();
        init();
        draw();
        panel.repaint();
    }
}