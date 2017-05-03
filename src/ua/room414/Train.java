package ua.room414;

import com.sun.j3d.utils.geometry.ColorCube;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix3d;

/**
 * @author Alexander Melashchenko
 * @version 1.0 03 May 2017
 */
public class Train {
    private TransformGroup transformGroup;

    private Transform3D transform3D = new Transform3D();

    private double angleX = 0;
    private double angleY = 0;

    private double deltaX = 0.05;
    private double deltaY = 0.05;

    private Train() {

    }

    static Train create() {
        Train train = new Train();

        train.transformGroup = createTransformGroup();

        return train;
    }

    private static TransformGroup createTransformGroup() {
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        transformGroup.addChild(new ColorCube(0.3));

        return transformGroup;
    }

    TransformGroup getTransformGroup() {
        return transformGroup;
    }

    double getAngleX() {
        return angleX;
    }

    double getAngleY() {
        return angleY;
    }

    void rotateRight() {
        angleY += deltaY;
        rotate();
    }

    void rotateLeft() {
        angleY -= deltaY;
        rotate();
    }

    void rotateUp() {
        angleX -= deltaX;
        rotate();
    }

    void rotateDown() {
        angleX += deltaX;
        rotate();
    }

    private void rotate() {
        Matrix3d rot = new Matrix3d();
        Matrix3d rotY = new Matrix3d();

        rot.rotX(angleX);
        rotY.rotY(angleY);

        rot.mul(rotY);

        transform3D.setRotation(rot);
        transformGroup.setTransform(transform3D);
    }

    @Override
    public String toString() {
        return "Train{" +
                "transformGroup=" + transformGroup +
                ", angleX=" + angleX +
                ", angleY=" + angleY +
                '}';
    }
}
