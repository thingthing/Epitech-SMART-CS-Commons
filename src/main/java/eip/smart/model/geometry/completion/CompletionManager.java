package eip.smart.model.geometry.completion;

import eip.smart.model.geometry.Point;

import java.util.Collection;

/**
 * Created by Vincent Buresi on 4/4/15.
 */
public class CompletionManager {

    /**
     * Adds a snap to the completion box approximation.
     * @param origin
     * @param points the subset of points on which we want to apply the algorithm
     * @param box
     * @param precision the precision used for Point conversion
     */
    public static void addSnapToCompletion(
            Point origin,
            PointSubsetGenerator<?> points,
            CompletionBox box,
            int precision
    ) {
        for (Point p : points) {
            addRayToCompletion(origin.toIntPoint(precision), p.toIntPoint(precision), box);
        }
    }

    /**
     * Adds the approximation of a segment to the actual completion box (Voxel approximation)
     * @param start
     * @param end
     * @param box
     */
    public static void addRayToCompletion(
            IntPoint start, IntPoint end,
            CompletionBox box
    ) {
        //For optimization purposes
        if (!box.isInside(start)) {
            IntPoint swap = start;
            start = end;
            end = swap;
        }

        //We use these values to swap coordinates during calculation.
        //Since the algorithm only iterates on X, we 'trick' to get the greatest delta.x value so we do not lose precision
        boolean swapXY = end.y - start.y > end.x - start.x;
        if (swapXY) {
            start = new IntPoint(start.y, start.x, start.z);
            end = new IntPoint(end.y, end.x, end.z);
        }
        boolean swapXZ = end.z - start.z > end.x - start.x;
        if (swapXZ) {
            start = new IntPoint(start.z, start.y, start.x);
            end = new IntPoint(end.z, end.y, end.x);
        }

        IntPoint delta = end.substract(start);
        IntPoint step = new IntPoint((delta.x > 0) ? 1 : -1, (delta.y > 0) ? 1 : -1, (delta.z > 0) ? 1 : -1);
        long driftXY = delta.x; // /2
        long driftXZ = delta.x; // /2
        long y = start.y;
        long z = start.z;

        for (long x = start.x; x < end.x; x += step.x) {
            if (swapXY && swapXZ)
                if (!box.addPoint(z, x, y))
                    break;
            else if (swapXY)
                if (!box.addPoint(y, x, z))
                    break;
            else if (swapXZ)
                if (!box.addPoint(z, y, x))
                    break;
            else
                if (!box.addPoint(x, y, z))
                    break;

            driftXY -= delta.y * 2; //Compensation for removed /2
            driftXZ -= delta.z * 2;
            if (driftXY < 0) {
                y += step.y;
                driftXY += delta.x * 2; //Compensation
            }
            if (driftXZ < 0) {
                z += step.z;
                driftXZ += delta.x * 2;
            }
        }
    }
}
