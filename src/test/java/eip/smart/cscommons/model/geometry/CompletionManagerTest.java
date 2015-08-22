package eip.smart.cscommons.model.geometry;

import org.junit.Test;

import eip.smart.cscommons.model.geometry.completion.CompletionBox;
import eip.smart.cscommons.model.geometry.completion.CompletionManager;
import eip.smart.cscommons.model.geometry.completion.IntPoint;

/**
 * Created by Vincent Buresi on 5/2/15.
 */
@SuppressWarnings("static-method")
public class CompletionManagerTest {

	@Test
	public void addRayToCompletionTest() {
		CompletionBox box = new CompletionBox(0, 0, 0, 99, 99, 99);

		assert box.getCurrentCompletion() == 0.0;

		CompletionManager.addRayToCompletion(new IntPoint(5, 5, 5), new IntPoint(15, 15, 15), box);
		assert box.getCurrentCompletion() > 0.0;

		CompletionManager.addRayToCompletion(new IntPoint(5, 1, 57), new IntPoint(96, 95, 28), box);
		CompletionManager.addRayToCompletion(new IntPoint(85, 71, 57), new IntPoint(12, 65, 75), box);
		CompletionManager.addRayToCompletion(new IntPoint(3, 35, 27), new IntPoint(12, 65, 99), box);

		CompletionManager.addRayToCompletion(new IntPoint(-1, -1, -1), new IntPoint(12, 25, 17), box);
		assert Math.abs(box.getCurrentCompletion() - 0.0194) < 0.0001;

		CompletionManager.addRayToCompletion(new IntPoint(-21, -12, 5), new IntPoint(12, 103, 17), box);
		assert Math.abs(box.getCurrentCompletion() - 0.0234) < 0.01;
	}
}
