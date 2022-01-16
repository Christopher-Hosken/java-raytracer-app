package io.cjhosken.javaraytracerapp.rendering.core;

public class ArrayHelper {
    public static float[] addToFloat(float[] arr, float f) {
        float[] tmp = new float[arr.length + 1];
        for (int idx = 0; idx < arr.length; idx++) {
            tmp[idx] = arr[idx];
        }

        tmp[tmp.length - 1] = f;

        return tmp;
    }

    public static int[] addToInt(int[] arr, int n) {
        int[] tmp = new int[arr.length + 1];
        for (int idx = 0; idx < arr.length; idx++) {
            tmp[idx] = arr[idx];
        }

        tmp[tmp.length - 1] = n;

        return tmp;
    }
}
