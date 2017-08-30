import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class UnionIterator<T> implements Iterator<T> {

    private Queue<PeekIterator<T>> heap;

    public UnionIterator(Iterable<Iterator<T>> iterators, Comparator<T> comparator) {
        heap = new PriorityQueue<>(new PeekComparator<>(comparator));

        for (Iterator<? extends T> iterator : iterators) {
            if (iterator.hasNext()) {
                heap.add(new PeekIterator<T>(iterator));
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !heap.isEmpty();
    }

    @Override
    public T next() {
        PeekIterator<T> nextIter = heap.remove();
        T nextElement = nextIter.next();
        if (nextIter.hasNext())
            heap.add(nextIter);
        return nextElement;
    }
}