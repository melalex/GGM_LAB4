package ua.room414;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class TrainScene extends JFrame implements KeyListener {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Lab4";

    private Canvas3D canvas;

    private SimpleUniverse universe;

    private BranchGroup root;
    private Train train;

    private void configureWindow() {
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int locationX = (screenSize.width - getWidth()) / 2;
        int locationY = (screenSize.height - getHeight()) / 2;

        setLocation(locationX,locationY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void addModelToUniverse() {
        root = new BranchGroup();
        train = Train.create();

        root.addChild(train.getTransformGroup());
    }

    private void addLightsToUniverse() {
        Bounds influenceRegion = new BoundingSphere();
        Color3f lightColor = new Color3f(Color.BLUE);
        Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);

        light.setInfluencingBounds(influenceRegion);
        root.addChild(light);
    }

    private void render() {
        configureWindow();
        configureCanvas();
        configureUniverse();
        addModelToUniverse();
        addLightsToUniverse();

        universe.addBranchGraph(root);
    }

    public static void main(String[] args) {
        TrainScene trainScene = new TrainScene();

        trainScene.render();

        trainScene.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == 'w') {
            train.rotateUp();
            System.out.println("X: " + train.getAngleX());
        } else if (keyEvent.getKeyChar() == 'a') {
            train.rotateLeft();
            System.out.println("Y: " + train.getAngleY());
        } else if (keyEvent.getKeyChar() == 's') {
            train.rotateDown();
            System.out.println("X: " + train.getAngleX());
        } else if (keyEvent.getKeyChar() == 'd') {
            train.rotateRight();
            System.out.println("Y: " + train.getAngleY());
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}