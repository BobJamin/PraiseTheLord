package com.bobjamin.kratosplugin.utils;

import java.awt.*;

public class ColorUtil {

    private static final float SATURATION = 70;
    private static final float BRIGHTNESS = 61;
    private static final float MAX_HUE = 120;

    public static Color generateScoreColor(double score) {
        int hue = score > 0 ? (int)Math.min(score, MAX_HUE) : 0;
        return getColorFromHSL(hue, SATURATION, BRIGHTNESS);
    }

    public static Color getColorFromHSL(float h, float s, float l) {
        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        int r = Math.round(Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)) * 256));
        int g = Math.round(Math.max(0, HueToRGB(p, q, h) * 256));
        int b = Math.round(Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)) * 256));

        return new Color(r,g,b);
    }

    private static float HueToRGB(float p, float q, float h) {
        if (h < 0)
            h += 1;

        if (h > 1)
            h -= 1;

        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1) {
            return q;
        }

        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
    }
}
