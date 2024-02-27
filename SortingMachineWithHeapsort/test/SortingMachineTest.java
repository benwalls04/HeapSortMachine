import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.queue.Queue;
import components.queue.Queue2;
import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Ben Walls, Matt Chandran
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /**
     * constructor test.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /**
     * add test empty case.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");

        Queue<String> mEntries = new Queue2<>();
        for (String entry : m) {
            mEntries.enqueue(entry);
        }

        Queue<String> expEntries = new Queue2<>();
        for (String entry : mExpected) {
            expEntries.enqueue(entry);
        }

        assertEquals(mEntries, expEntries);
    }

    /**
     * add test size 1 case.
     */
    @Test
    public final void testAddSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue");
        m.add("blue");

        Queue<String> mEntries = new Queue2<>();
        for (String entry : m) {
            mEntries.enqueue(entry);
        }

        Queue<String> expEntries = new Queue2<>();
        for (String entry : mExpected) {
            expEntries.enqueue(entry);
        }

        assertEquals(mEntries, expEntries);
    }

    /**
     * add test duplicates case.
     */
    @Test
    public final void testAddDuplicate() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "green");
        m.add("green");

        Queue<String> mEntries = new Queue2<>();
        for (String entry : m) {
            mEntries.enqueue(entry);
        }

        Queue<String> expEntries = new Queue2<>();
        for (String entry : mExpected) {
            expEntries.enqueue(entry);
        }

        assertEquals(mEntries, expEntries);
    }

    /**
     * add test normal case.
     */
    @Test
    public final void testAddSize3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red", "brown");
        m.add("brown");

        Queue<String> mEntries = new Queue2<>();
        for (String entry : m) {
            mEntries.enqueue(entry);
        }

        Queue<String> expEntries = new Queue2<>();
        for (String entry : mExpected) {
            expEntries.enqueue(entry);
        }

        assertEquals(mEntries, expEntries);
    }

    /**
     * extraction mode test empty case.
     */
    @Test
    public final void testExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(m, mExpected);
    }

    /**
     * extraction mode test non empty case.
     */
    @Test
    public final void testExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "brown");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red", "brown");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(m, mExpected);
    }

    /**
     * remove first test size 1 case.
     */
    @Test
    public final void testRemoveFirstSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        String first = m.removeFirst();
        String firstExp = "green";

        assertEquals(first, firstExp);
        assertEquals(m, mExpected);
    }

    /**
     * remove first test normal case.
     */
    @Test
    public final void testRemoveFirstSize3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "bird");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "bird");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        String first = m.removeFirst();
        String firstExp = "apple";

        assertEquals(first, firstExp);
        assertEquals(m, mExpected);
    }

    /**
     * remove first test duplicates case.
     */
    @Test
    public final void testRemoveFirstDuplicate() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "green", "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "green");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        String first = m.removeFirst();
        String firstExp = "apple";

        assertEquals(first, firstExp);
        assertEquals(m, mExpected);
    }

    /**
     * remove first test normal case 2.
     */
    @Test
    public final void testRemoveFirstSize5() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "bird", "truck", "aaron");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "green", "bird", "truck");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        String first = m.removeFirst();
        String firstExp = "aaron";

        assertEquals(first, firstExp);
        assertEquals(m, mExpected);
    }

    /**
     * remove first test normal case 3.
     */
    @Test
    public final void testRemoveFirstSize8() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "bird", "truck", "aaron", "black", "zebra", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "green", "bird", "truck", "black", "zebra", "blue");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        String first = m.removeFirst();
        String firstExp = "aaron";

        assertEquals(first, firstExp);
        assertEquals(m, mExpected);
    }

    /**
     * insertion mode test empty case.
     */
    @Test
    public final void testInsertionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        boolean insertionMode = m.isInInsertionMode();

        assertEquals(insertionMode, true);
    }

    /**
     * insertion mode test normal case.
     */
    @Test
    public final void testInsertionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "bird", "truck", "aaron", "black", "zebra", "blue");

        boolean insertionMode = m.isInInsertionMode();

        assertEquals(insertionMode, true);
    }

    /**
     * insertion mode test normal false case.
     */
    @Test
    public final void testInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "bird", "truck", "aaron", "black", "zebra", "blue");

        m.changeToExtractionMode();
        boolean insertionMode = m.isInInsertionMode();

        assertEquals(insertionMode, false);
    }

    /**
     * insertion mode test empty false case.
     */
    @Test
    public final void testInsertionModeEmptyFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        m.changeToExtractionMode();
        boolean insertionMode = m.isInInsertionMode();

        assertEquals(insertionMode, false);
    }

    /**
     * order test empty case.
     */
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(m.order(), mExpected.order());
    }

    /**
     * order test normal case.
     */
    @Test
    public final void testOrderNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "bird", "truck", "aaron", "black", "zebra", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "green", "bird", "truck", "black", "zebra", "blue");

        assertEquals(m.order(), mExpected.order());
    }

    /**
     * size test empty case.
     */
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        m.changeToExtractionMode();
        int size = m.size();
        int sizeExp = 0;

        assertEquals(size, sizeExp);
    }

    /**
     * size test size 1 case.
     */
    @Test
    public final void testSizeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");

        m.changeToExtractionMode();
        int size = m.size();
        int sizeExp = 1;

        assertEquals(size, sizeExp);
    }

    /**
     * size test normal case.
     */
    @Test
    public final void testSizeThree() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red");

        m.changeToExtractionMode();
        int size = m.size();
        int sizeExp = 3;

        assertEquals(size, sizeExp);
    }

    /**
     * size test duplicates case.
     */
    @Test
    public final void testSizeDuplicates() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "red");

        m.changeToExtractionMode();
        int size = m.size();
        int sizeExp = 4;

        assertEquals(size, sizeExp);
    }

    /**
     * size test insertion mode empty case.
     */
    @Test
    public final void testSizeInsertionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        int size = m.size();
        int sizeExp = 0;

        assertEquals(size, sizeExp);
    }

    /**
     * size test insertion mode case.
     */
    @Test
    public final void testSizeInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "red");

        int size = m.size();
        int sizeExp = 4;

        assertEquals(size, sizeExp);
    }

}
