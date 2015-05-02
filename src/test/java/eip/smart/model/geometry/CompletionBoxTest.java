package eip.smart.model.geometry;

import org.junit.Test;

/**
 * Created by Vincent Buresi on 5/2/15.
 */
public class CompletionBoxTest {

    @Test
    public void isInsideTest() {
        //Creating an arbitrary box
        CompletionBox box = new CompletionBox(5, 10, 15, 7, 14, 21);

        //Dummy tests
        assert box.isInside(6, 11, 16);
        assert box.isInside(new Point(6, 11, 16).toIntPoint(1));

        assert !box.isInside(4, 11, 16);
        assert !box.isInside(new Point(4, 11, 16).toIntPoint(1));
        assert !box.isInside(6, 9, 16);
        assert !box.isInside(new Point(6, 9, 16).toIntPoint(1));
        assert !box.isInside(6, 11, 14);
        assert !box.isInside(new Point(6, 11, 14).toIntPoint(1));

        assert !box.isInside(8, 11, 16);
        assert !box.isInside(new Point(8, 11, 16).toIntPoint(1));
        assert !box.isInside(6, 15, 16);
        assert !box.isInside(new Point(6, 15, 16).toIntPoint(1));
        assert !box.isInside(6, 11, 22);
        assert !box.isInside(new Point(6, 11, 22).toIntPoint(1));

        //Floating points tests
        assert box.isInside(new Point(5.000001, 11, 16).toIntPoint(1));
        assert !box.isInside(new Point(4.99999, 11, 16).toIntPoint(1));

        assert box.isInside(new Point(6.99999, 11, 16).toIntPoint(1));
        //Specific case due to zero-truncated values. box actually goes from 5.0 to 7.999999.
        assert box.isInside(new Point(7.000001, 11, 16).toIntPoint(1));
        assert box.isInside(new Point(7.99999, 11, 16).toIntPoint(1));
        assert !box.isInside(new Point(8.0, 11, 16).toIntPoint(1));

        //Edge cases test
        assert box.isInside(new Point(7.0, 11, 16).toIntPoint(1));
        assert box.isInside(new Point(5.0, 11, 16).toIntPoint(1));
    }

    @Test
    public void completionTest() {
        CompletionBox box = new CompletionBox(0, 0, 0, 99, 99, 99);

        assert box.getCurrentCompletion() == 0.0;
        box.addPoint(0, 0, 0);
        assert box.getCurrentCompletion() > 0;

        //Filling the first half
        for (int i = 0; i < 50; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    box.addPoint(i, j, k);
                }
            }
        }
        assert Math.abs(box.getCurrentCompletion() - 50.0) < 0.01;

        //Filling the whole box
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    box.addPoint(i, j, k);
                }
            }
        }
        assert Math.abs(box.getCurrentCompletion() - 100.0) < 0.01;
    }
}
