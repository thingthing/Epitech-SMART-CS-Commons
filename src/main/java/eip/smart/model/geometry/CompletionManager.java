package eip.smart.model.geometry;

/**
 * Created by Vincent Buresi on 4/4/15.
 */
public class CompletionManager {

    private static boolean addPointToCompletion(boolean swapXY, boolean swapXZ, CompletionBox box, long x, long y, long z) {
        if (swapXY && swapXZ) {
            return box.addPoint(z, x, y);
        }
        else if (swapXY) {
            return box.addPoint(y, x, z);
        }
        else if (swapXZ) {
            return box.addPoint(z, y, x);
        }
        else {
            return box.addPoint(x, y, z);
        }
    }

    public static void addRayToCompletion(
            IntPoint start, IntPoint end,
            CompletionBox box
    ) {
        boolean outOfBox = false;

        //For optimization purposes
        if (!box.isInside(start)) {
            if (!box.isInside(end)) {
                outOfBox = true;
            } else {
                IntPoint swap = start;
                start = end;
                end = swap;
            }
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
            if (addPointToCompletion(swapXY, swapXZ, box, x, y, z))
                outOfBox = false;
            else if (!outOfBox)
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
