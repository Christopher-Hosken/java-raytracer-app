package io.cjhosken.javaraytracerapp.rendering.paver.data;

import io.cjhosken.javaraytracerapp.core.Face;
import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.core.Tri;

public class PaverMeshObject extends PaverObject {
  protected Face[] mesh;

  public PaverMeshObject() {
    super();
  }

  public double intersect(Ray ray) {
    Tri tc = new Tri();
        boolean hit = false;
        tmpT = Double.POSITIVE_INFINITY;

        if (mesh != null && mesh.length > 0) {
            for (Face f : mesh) {
                for (Tri tri : f.tris()) {
                    double t0 = tri.intersect(ray);
                    if (t0 > 0.0001 && t0 < tmpT) {
                        tmpT = t0;
                        tc = tri;
                        hit = true;
                    }
                }
            }
        }

        if (hit) {
            this.tmpNormal = tc.normal();
            return tmpT;
        }

    return -1;
  }
}
