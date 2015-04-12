package eip.smart.model.geometry.completion;

import eip.smart.model.geometry.Point;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Vincent Buresi on 4/12/15.
 */
public abstract class PointSubsetGenerator<C extends Collection<Point>> implements Iterable<Point>, Iterator<Point> {
    private final int countMax;
    private int count = 0;
    private C collection;

    public PointSubsetGenerator(C collection, int subsetSize) {
        countMax = (subsetSize <= collection.size()) ? subsetSize : collection.size();
        this.collection = collection;
    }

    @Override
    public Iterator<Point> iterator() {
        return this;
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        while (hasNext())
            action.accept(next());
    }

    @Override
    public Spliterator<Point> spliterator() {
        return null;
    }

    @Override
    public boolean hasNext() {
        if (count < countMax) {
            ++count;
            return true;
        } else
            return false;
    }

    @Override
    public abstract Point next();
}
