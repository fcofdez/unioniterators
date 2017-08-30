import java.util.Comparator

import scala.collection.JavaConverters._
import org.scalatest._


class UnionIteratorSpec extends FlatSpec with Matchers {

  "A UnionIterator" should "return values in order" in {
    val list1 = List[Integer](1, 2, 3).asJava
    val list2 = List[Integer](4, 5, 6).asJava

    val iterators = Iterable(list1.iterator(), list2.iterator()).asJava
    val union = new UnionIterator[Integer](iterators, Comparator.naturalOrder())

    union.next() should be(1)
    union.next() should be(2)
    union.next() should be(3)
    union.next() should be(4)
    union.next() should be(5)
    union.next() should be(6)
  }

  it should "return interleaved values in order" in {
    val list1 = List[Integer](1, 3, 5).asJava
    val list2 = List[Integer](2, 4, 6).asJava

    val iterators = Iterable(list1.iterator(), list2.iterator()).asJava
    val union = new UnionIterator[Integer](iterators, Comparator.naturalOrder())

    union.next() should be(1)
    union.next() should be(2)
    union.next() should be(3)
    union.next() should be(4)
    union.next() should be(5)
    union.next() should be(6)
  }

  it should "support multiple iterators" in {
    val list1 = List[Integer](1, 3, 5).asJava
    val list2 = List[Integer](2, 4, 6).asJava
    val list3 = List[Integer](-1, 8, 9).asJava


    val iterators = Iterable(list1.iterator(), list2.iterator(), list3.iterator()).asJava
    val union = new UnionIterator[Integer](iterators, Comparator.naturalOrder())

    union.next() should be(-1)
    union.next() should be(1)
    union.next() should be(2)
    union.next() should be(3)
    union.next() should be(4)
    union.next() should be(5)
    union.next() should be(6)
    union.next() should be(8)
    union.next() should be(9)
  }

  it should "handle repeated elements" in {
    val list1 = List[Integer](1, 3, 5).asJava
    val list2 = List[Integer](1, 4, 6).asJava

    val iterators = Iterable(list1.iterator(), list2.iterator()).asJava
    val union = new UnionIterator[Integer](iterators, Comparator.naturalOrder())

    union.next() should be(1)
    union.next() should be(1)
    union.next() should be(3)
    union.next() should be(4)
    union.next() should be(5)
    union.next() should be(6)
  }
}