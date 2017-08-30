import java.util.Comparator;
import java.util.Iterator;

class PeekComparator<T> implements Comparator<PeekIterator<T>> {
    private Comparator<T> comparator;
    public PeekComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(PeekIterator<T> a, PeekIterator<T> b) {
        return comparator.compare(a.peek(), b.peek());
    }
}

public class PeekIterator<T> implements Iterator<T> {
    private final Iterator<? extends T> iter;
    private boolean peeked;
    private T element;

    public PeekIterator(Iterator<? extends T> iterator) {
        this.iter = iterator;
        this.peeked = false;
        this.element = null;
    }

    public T peek() {
        if (!peeked) {
         peeked = true;
         element = iter.next();
        }
        return element;
    }

    @Override
    public boolean hasNext() {
        return peeked || iter.hasNext();
    }

    @Override
    public T next() {
        if (!peeked) {
            return iter.next();
        }
        peeked = false;
        T nextElement = element;
        element = null;
        return nextElement;
    }

}
