import java.util.Collection;
import java.util.Iterator;

public interface Ring<E> extends Collection<E> {

    E get(int index) throws IndexOutOfBoundsException;

    Iterator<E> iterator();

    int size();
}