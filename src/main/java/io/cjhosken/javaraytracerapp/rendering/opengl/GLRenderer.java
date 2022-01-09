package io.cjhosken.javaraytracerapp.rendering.opengl;

import java.util.ArrayList;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

import io.cjhosken.javaraytracerapp.rendering.core.*;
import io.cjhosken.javaraytracerapp.rendering.paver.base.*;

public class GLRenderer
        implements GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private ArrayList<CoreObject> scene;
    private int shaderProgram;
    private int modelViewProjectionMatrix;
    private int vertShader;
    private int fragShader;
    static final int VERTICES_IDX = 0;
    static final int INDICES_IDX = 2;
    static final int COLOR_IDX = 1;
    private int[] vboHandles;


    private vec3 pos;
    private vec3 last;
    private vec3 angle;
    private vec3 click;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 4);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glDepthRange(0.001, 100);

        gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glClearStencil(0);
        gl.glClearDepth(1.0f);
        gl.glDepthFunc(GL2.GL_LEQUAL); // the type of depth test to do


        scene = new ArrayList<CoreObject>();
        vertShader = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
        fragShader = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);

        try {
            Path vertexShaderPath = Paths
                    .get(this.getClass().getClassLoader().getResource("shaders/default.vs").toURI());
            Path fragmentShaderPath = Paths
                    .get(this.getClass().getClassLoader().getResource("shaders/default.fs").toURI());
            String[] vlines = new String[] { Files.readString(vertexShaderPath) };
            int[] vlengths = new int[] { vlines[0].length() };
            gl.glShaderSource(vertShader, vlines.length, vlines, vlengths, 0);
            gl.glCompileShader(vertShader);

            int[] compiled = new int[1];
            gl.glGetShaderiv(vertShader, GL2ES2.GL_COMPILE_STATUS, compiled,0);
            if(compiled[0]!=0){System.out.println("Horray! vertex shader compiled");}
            else {
                int[] logLength = new int[1];
                gl.glGetShaderiv(vertShader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

                byte[] log = new byte[logLength[0]];
                gl.glGetShaderInfoLog(vertShader, logLength[0], (int[])null, 0, log, 0);

                System.err.println("Error compiling the vertex shader: " + new String(log));
                System.exit(1);
            }

            String[] flines = new String[] { Files.readString(fragmentShaderPath) };
            int[] flengths = new int[] { flines[0].length() };
            gl.glShaderSource(fragShader, flines.length, flines, flengths, 0);
            gl.glCompileShader(fragShader);
            gl.glGetShaderiv(fragShader, GL2ES2.GL_COMPILE_STATUS, compiled,0);
            if(compiled[0]!=0){System.out.println("Horray! fragment shader compiled");}
            else {
                int[] logLength = new int[1];
                gl.glGetShaderiv(fragShader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

                byte[] log = new byte[logLength[0]];
                gl.glGetShaderInfoLog(fragShader, logLength[0], (int[])null, 0, log, 0);

                System.err.println("Error compiling the fragment shader: " + new String(log));
                System.exit(1);
            }
        }

        catch (Exception e) {
            System.out.println(e);
        }

        shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertShader);
        gl.glAttachShader(shaderProgram, fragShader);

        gl.glBindAttribLocation(shaderProgram, 0, "inPosition");
        gl.glBindAttribLocation(shaderProgram, 1, "inColor");
        gl.glLinkProgram(shaderProgram);

        modelViewProjectionMatrix = gl.glGetUniformLocation(shaderProgram, "matProjection");

        //addObject("cube");

        pos = new vec3(0, -0.6, 0.5);
        last = new vec3();
        angle = new vec3(0, 0, 0);
        click = new vec3();

        vboHandles = new int[3];
        gl.glGenBuffers(3, vboHandles, 0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1f); // Purple
        gl.glClear(GL2ES2.GL_STENCIL_BUFFER_BIT |
                GL2ES2.GL_COLOR_BUFFER_BIT |
                GL2ES2.GL_DEPTH_BUFFER_BIT);

        gl.glUseProgram(shaderProgram);

        float[] model_view_projection;
        float[] identity_matrix = {
                (float)pos.z, 0.0f, 0.0f, 0.0f,
                0.0f, (float)pos.z, 0.0f, 0.0f,
                0.0f, 0.0f, (float)pos.z, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
        };


    
        model_view_projection = translate(identity_matrix, (float) pos.x, (float) pos.y, (float) pos.z);

        model_view_projection = rotate(model_view_projection, (float) angle.y, 1, 0, 0);
        model_view_projection = rotate(model_view_projection, (float) angle.x, 0, 1, 0);
        model_view_projection = rotate(model_view_projection, (float) angle.z, 0, 0, 1);

        

        
        

        gl.glUniformMatrix4fv(modelViewProjectionMatrix, 1, false, model_view_projection, 0);

        FloatBuffer fbVertices = Buffers.newDirectFloatBuffer(sceneVerts());
        gl.glBindBuffer(GL2ES2.GL_ARRAY_BUFFER, vboHandles[VERTICES_IDX]);

        int numBytes = sceneVerts().length * 4;
        gl.glBufferData(GL.GL_ARRAY_BUFFER, numBytes, fbVertices, GL.GL_STATIC_DRAW);
        fbVertices = null;

        gl.glVertexAttribPointer(0, 3, GL2ES2.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        FloatBuffer fbColors = Buffers.newDirectFloatBuffer(sceneColors());
        gl.glBindBuffer(GL2ES2.GL_ARRAY_BUFFER, vboHandles[COLOR_IDX]);

        numBytes = sceneColors().length * 4;
        gl.glBufferData(GL.GL_ARRAY_BUFFER, numBytes, fbColors, GL.GL_STATIC_DRAW);
        fbColors = null;

        gl.glVertexAttribPointer(1, 3, GL2ES2.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        IntBuffer ibIndices = Buffers.newDirectIntBuffer(sceneIndices());
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, vboHandles[INDICES_IDX]);

        numBytes = sceneIndices().length * 4;
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, numBytes, ibIndices, GL.GL_STATIC_DRAW);

        //gl.glDrawArrays(GL2.GL_QUADS, 0, sceneVerts().length);
        gl.glDrawElements(GL2.GL_TRIANGLES, sceneIndices().length, GL2.GL_UNSIGNED_INT, 0);

        /*
         * for (CoreObject obj : scene) {
         * gl.glDrawElements(GL2ES2.GL_TRIANGLES, obj.Indices().length,
         * obj.verts().length);
         * }
         */

        gl.glDisableVertexAttribArray(0);
        gl.glDisableVertexAttribArray(1);

        click.z = 0;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        
        gl.glViewport(0, 0, w, w);
        float aspect = (float)w / (float)h;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, aspect, 0.1f, 100.0f);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2ES2 gl = drawable.getGL().getGL2ES2();
        gl.glUseProgram(0);
        gl.glDeleteBuffers(2, vboHandles, 0);
        vboHandles = null;
        gl.glDetachShader(shaderProgram, vertShader);
        gl.glDeleteShader(vertShader);
        gl.glDetachShader(shaderProgram, fragShader);
        gl.glDeleteShader(fragShader);
        gl.glDeleteProgram(shaderProgram);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        last.x = e.getX();
        last.y = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            click.x = e.getX();
            click.y = e.getY();
            click.z = 1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == 1) {
            if (e.isShiftDown()) {
                pos.x += ((e.getX() - last.x) / 100.0);
                pos.y -= ((e.getY() - last.y) / 100.0);
            }

            else if (e.isAltDown()) {
                angle.x += ((e.getX() - last.x) / 2.0);
                angle.y += ((e.getY() - last.y) / 2.0);
            }
        }

        else if (e.getButton() == 2) {
            pos.x += ((e.getX() - last.x) / 100.0);
            pos.y -= ((e.getY() - last.y) / 100.0);
        }

        else if (e.getButton() == 3) {
            if (e.isAltDown()) {
                pos.z -= (((e.getY() - last.y) * Math.abs(pos.z)) / 100.0);
                if (pos.z < 0) {
                    pos.z = 0;
                }
            }
        }

        last.x = e.getX();
        last.y = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        pos.z -= ((e.getWheelRotation() * Math.abs(pos.z)) / 10);
        if (pos.z < 0) {
            pos.z = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    public int makeID() {
        int tmpID = -1;
        boolean foundID = false;
        while (!foundID) {
            foundID = true;
            tmpID = (int) (Math.random() * Integer.MAX_VALUE);
            for (CoreObject obj : scene) {
                if (tmpID == obj.ID()) {
                    foundID = false;
                    break;
                }
            }
        }

        return tmpID;
    }

    public void addObject(String type) {
        int id = makeID();
        CoreObject newObj = new CoreObject("tmp", id, new vec3());

        /*
         * if (type.equals("sphere")) {
         * newObj = new CoreSphere("Sphere_" + scene.size(), id, new vec3(), 0.5);
         * }
         */

        if (type.equals("plane")) {
            newObj = new CorePlane("Plane_" + scene.size(), id, new vec3());
        }

        else if (type.equals("cube")) {
            newObj = new CoreCube("Cube_" + scene.size(), id, new vec3());
        }

        scene.add(newObj);
    }

    public float[] sceneVerts() {
        float[] verts = new float[0];

        for (CoreObject obj : scene) {
            float[] tmpVerts = new float[verts.length + obj.verts().length];
            System.arraycopy(verts, 0, tmpVerts, 0, verts.length);
            System.arraycopy(obj.verts(), 0, tmpVerts, verts.length, obj.verts().length);
            verts = tmpVerts;
        }

        return verts;
    }

    public int[] sceneIndices() {
        int[] indices = new int[0];

        for (CoreObject obj : scene) {
            int[] tmpIndices = new int[indices.length + obj.indices().length];
            System.arraycopy(indices, 0, tmpIndices, 0, indices.length);
            System.arraycopy(obj.indices(), 0, tmpIndices, indices.length, obj.indices().length);
            indices = tmpIndices;
        }

        return indices;
    }

    public float[] sceneColors() {
        float[] colors = new float[0];

        for (CoreObject obj : scene) {
            float[] tmpColors = new float[colors.length + obj.colors().length];
            System.arraycopy(colors, 0, tmpColors, 0, colors.length);
            System.arraycopy(obj.colors(), 0, tmpColors, colors.length, obj.colors().length);
            colors = tmpColors;
        }

        return colors;
    }

    private void glMultMatrixf(FloatBuffer a, FloatBuffer b, FloatBuffer d) {
        final int aP = a.position();
        final int bP = b.position();
        final int dP = d.position();
        for (int i = 0; i < 4; i++) {
            final float ai0 = a.get(aP + i + 0 * 4), ai1 = a.get(aP + i + 1 * 4), ai2 = a.get(aP + i + 2 * 4),
                    ai3 = a.get(aP + i + 3 * 4);
            d.put(dP + i + 0 * 4, ai0 * b.get(bP + 0 + 0 * 4) + ai1 * b.get(bP + 1 + 0 * 4)
                    + ai2 * b.get(bP + 2 + 0 * 4) + ai3 * b.get(bP + 3 + 0 * 4));
            d.put(dP + i + 1 * 4, ai0 * b.get(bP + 0 + 1 * 4) + ai1 * b.get(bP + 1 + 1 * 4)
                    + ai2 * b.get(bP + 2 + 1 * 4) + ai3 * b.get(bP + 3 + 1 * 4));
            d.put(dP + i + 2 * 4, ai0 * b.get(bP + 0 + 2 * 4) + ai1 * b.get(bP + 1 + 2 * 4)
                    + ai2 * b.get(bP + 2 + 2 * 4) + ai3 * b.get(bP + 3 + 2 * 4));
            d.put(dP + i + 3 * 4, ai0 * b.get(bP + 0 + 3 * 4) + ai1 * b.get(bP + 1 + 3 * 4)
                    + ai2 * b.get(bP + 2 + 3 * 4) + ai3 * b.get(bP + 3 + 3 * 4));
        }
    }

    private float[] multiply(float[] a, float[] b) {
        float[] tmp = new float[16];
        glMultMatrixf(FloatBuffer.wrap(a), FloatBuffer.wrap(b), FloatBuffer.wrap(tmp));
        return tmp;
    }

    private float[] translate(float[] m, float x, float y, float z) {
        float[] t = { 1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                x, y, z, 1.0f };
        return multiply(m, t);
    }

    private float[] rotate(float[] m, float a, float x, float y, float z) {
        float s, c;
        s = (float) Math.sin(Math.toRadians(a));
        c = (float) Math.cos(Math.toRadians(a));
        float[] r = {
                x * x * (1.0f - c) + c, y * x * (1.0f - c) + z * s, x * z * (1.0f - c) - y * s, 0.0f,
                x * y * (1.0f - c) - z * s, y * y * (1.0f - c) + c, y * z * (1.0f - c) + x * s, 0.0f,
                x * z * (1.0f - c) + y * s, y * z * (1.0f - c) - x * s, z * z * (1.0f - c) + c, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f };
        return multiply(m, r);
    }
}