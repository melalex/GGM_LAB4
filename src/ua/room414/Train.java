package ua.room414;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.io.File;

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

        transformGroup.addChild(transformNode(
                createCylinder(0.29f, 0.64f),
                new Vector3d(-0.21, 0, 0),
                createRotationMatrix(0, 0, -Math.PI / 2)
        ));

        transformGroup.addChild(transformNode(
                createBox(0.32f, 0.07f, 0.29f),
                new Vector3d(-0.21, -0.29, 0),
                createRotationMatrix(0, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createBox(0.29f, 0.5f, 0.29f),
                new Vector3d(0.4, 0.14, 0),
                createRotationMatrix(0, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.15f, 0.07f),
                new Vector3d(-0.4, -0.35, 0.32f),
                createRotationMatrix(-Math.PI / 2, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.15f, 0.07f),
                new Vector3d(-0.4, -0.35, -0.32f),
                createRotationMatrix(-Math.PI / 2, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.15f, 0.07f),
                new Vector3d(-0.05, -0.35, 0.32f),
                createRotationMatrix(-Math.PI / 2, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.15f, 0.07f),
                new Vector3d(-0.05, -0.35, -0.32f),
                createRotationMatrix(-Math.PI / 2, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.25f, 0.1f),
                new Vector3d(0.4, -0.25, 0.32f),
                createRotationMatrix(-Math.PI / 2, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.25f, 0.1f),
                new Vector3d(0.4, -0.25, -0.32f),
                createRotationMatrix(-Math.PI / 2, 0, 0)
        ));

        transformGroup.addChild(transformNode(
                createCylinder(0.05f, 0.3f),
                new Vector3d(-0.4, 0.35f, 0f),
                createRotationMatrix(0, 0, 0)
        ));

        return transformGroup;
    }

    private static Box createBox(float x, float y, float z) {
        Box box = new Box(x, y, z, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, null);
        box.setAppearance(defaultAppearance());
        addTexture(box.getAppearance(), "train.jpg");
        return box;
    }

    private static Cylinder createCylinder(float r, float h) {
        Cylinder cylinder = new Cylinder(r, h, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, null);
        cylinder.setAppearance(defaultAppearance());
        addTexture(cylinder.getAppearance(), "train.jpg");
        return cylinder;
    }

    private static TransformGroup transformNode(Node obj, Vector3d pos, Matrix3d rotation) {
        TransformGroup transformGroup = new TransformGroup();
        Transform3D rotationTransform = new Transform3D();
        Transform3D moveTransform = new Transform3D();

        moveTransform.setTranslation(pos);
        rotationTransform.setRotation(rotation);
        moveTransform.mul(rotationTransform);

        transformGroup.setTransform(moveTransform);
        transformGroup.addChild(obj);

        return transformGroup;
    }

    private static Appearance defaultAppearance() {
        Appearance ap = new Appearance();

        Color3f var1 = new Color3f(0.1F, 0.1F, 0.1F);
        Color3f var2 = new Color3f(0.0F, 0.0F, 0.0F);
        Color3f var3 = new Color3f(0.6F, 0.6F, 0.6F);
        Color3f var4 = new Color3f(1.0F, 1.0F, 1.0F);
        Material var5 = new Material(var1, var2, var3, var4, 100.0F);
        var5.setLightingEnable(true);
        ap.setMaterial(var5);

        return ap;
    }

    private static void addTexture(Appearance ap, String picture) {
        TextureLoader loader = new TextureLoader(picture, "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
    }

    private static Matrix3d createRotationMatrix(double x, double y, double z) {
        Matrix3d rotX = new Matrix3d();
        Matrix3d rotY = new Matrix3d();
        Matrix3d rotZ = new Matrix3d();

        rotX.rotX(x);
        rotY.rotY(y);
        rotZ.rotZ(z);

        rotX.mul(rotY);
        rotX.mul(rotZ);

        return rotX;
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
