import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.util.Pair;

class Showcase {
    // use static class to showcase singly linked list in main()
    static class Node {
        public Integer value;
        private String label;
        public Node next;
    }

    public static void main(String[] args) {
        // Variables
        System.out.println("*** Intro into variables ***");
        String name; // declaration of variable(must be assigned)
        name = ""; // must assign or will get "variable name might not have been initialized" error
        String nickname = "";

        String a = name, b = nickname; // multiple declarations
        String tmp;
        tmp = a;
        a = b;
        b = tmp; // in general swap requires temp variable

        int num = 0, num2; // num, num2 are primitives
        try {
            num2 = Integer.parseInt("123"); // will throw exception if not an int say "abc123"
        } catch (NumberFormatException e) {
        }
        Integer num3 = num; // object, no explicit pointers, but will be passed by reference

        Node n = new Node(); // will have a reference to instance of Node.
        n.value = Integer.MAX_VALUE;
        n.label = "example";
        n.next = new Node();

        // Example of recursive function
        dfs(3);

        System.out.printf("\nnext up:\n");
        // One dimensional lists
        System.out.println("*** Arrays and lists demo ***");
        int[] fixedArray = new int[2]; // array of size 2, values are zeros
        for (int i = 0; i < fixedArray.length; i++) {
            fixedArray[i] = i * 10;
        }
        double[] fixedTwo = new double[] { 3.0, 1.0, 2.0f }; // array of size 3 with values defined, 2.0float will be
                                                             // cast to double
        Arrays.sort(fixedTwo); // Arrays sort() can dealt with array of primitives, for Objects
                               // Collections.sort is used
        List<Integer> numberList = new ArrayList<>(); // list is interface, array list is implementation
        numberList.add(3);
        numberList.addAll(Arrays.asList(1, 2));
        // show use of streams (from Java 8+)
        List squares = numberList.stream().map(x -> x * x).collect(java.util.stream.Collectors.toList());
        Collections.sort(numberList); // sort in ascending order
        Collections.sort(numberList, Collections.reverseOrder()); // optional Comparator as second arg, descending order
        for (Integer number : numberList) { // using objects implementing Iterable (which all collection do)
            System.out.printf("Next number from list: %d\n", number);
        }
        for (int i = 0; i < squares.size(); i++) {
            System.out.printf("Squared number from list (using index access): %d\n", squares.get(i));
        }

        List<Integer> destList = numberList;
        numberList.set(1, 25);
        System.out.printf(
                "Source and destination reference same memory (either list changes will affect both): %s == %s\n",
                numberList, destList);
        destList = new ArrayList<>(numberList);
        numberList.set(1, 2);
        System.out.printf("New list can be created from existing one, and they will change independently: %s != %s\n",
                numberList, destList);

        System.out.printf("\nnext up:\n");
        // Multi dimensional lists (matrix)
        System.out.println("*** Multidimensional lists demo ***");
        int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                System.out.printf("%d is the value as row=%d and column=%d\n", matrix[r][c], r, c);
            }
        }
        int[][][] dp = new int[2][3][4]; // i,j,k; 2x3x4 matrix of zeros (default for primitive int)

        int[][] intervals = new int[][] { { 3, 5 }, { 2, 6 } };
        // sort by ascending start
        // second param is comparator,
        // where ...[0] is a start and ...[1] is an end of i-th interval
        Arrays.sort(intervals, (one, two) -> Integer.compare(one[0], two[0]));

        // Convert int[][] to List<List<Integer>> to print:
        System.out.println(
                Arrays.stream(intervals)
                        .map(innerArray -> Arrays.stream(innerArray).boxed()
                                .collect(java.util.stream.Collectors.toList()))
                        .collect(java.util.stream.Collectors.toList()));

        System.out.printf("\nnext up:\n");
        // Maps
        System.out.println("*** Maps demo ***");
        Map<String, Integer> mapOne = new HashMap<>(4); // new map with element optional capacity hint
        Map<String, Integer> mapTwo = new HashMap<String, Integer>() {
            {
                put("Alice", 3);
                put("Bob", 5);
            }
        };
        for (Map.Entry<String, Integer> entry : mapTwo.entrySet()) {
            System.out.printf("%s is %d\n", entry.getKey(), entry.getValue());
        }
        if (!mapOne.containsKey("java")) {
            mapOne.put("java", 1); // element not present
        }
        // there is also mapOne.getOrDefault(key, defaultValue)
        if (mapOne.containsKey("java")) {
            System.out.printf("%s is found with value of %d\n", "java", mapOne.get("java"));
        }

        // can use any Object relies on hashCode() for keys, equals() for values.
        Map<Pair<Integer, Integer>, Boolean> mapThree = new HashMap<>();
        // there are other maps such as TreeMap (red-black tree);
        // LinkedHashMap (keeps insert order)
        // Note: better not use arrays Map<Integer[],Boolean> mapThree (which is valid)
        // since mapThree.put(new Integer[]{1, 3}, true); mapThree.getOrDefault(new
        // Integer[]{1, 3}, false) => false
        // two new Integer[] allocations will have different addresses in memory
        mapThree.put(new Pair<Integer, Integer>(1, 3), true); // can use Set<>, HashSet<> instead of boolean map
        // declare new pair to show in case of Object we can have 2 matching instances:
        Pair<Integer, Integer> key = new Pair<>(1, 3);
        if (mapThree.getOrDefault(key, false)) {
            System.out.println("Map entry with complex key (1,3) is present and set to true!");
        }
        mapThree.remove(key);
        // yes, redundant with isEmpty(), but here to stress out the fact
        if (!mapThree.containsKey(key) && mapThree.isEmpty()) {
            System.out.println("Map entry with complex key (1,3) is gone!");
        }

        System.out.printf("\nnext up:\n");
        System.out.println("*** Misc demo ***");
        System.out.printf("Call to custom variadic function (vararg method): %d\n", max(4, 8, 1));

        System.out.printf("\nnext up:\n");
        System.out.println("*** Queue and stack demo ***");
        // https://docs.oracle.com/javase/8/docs/technotes/guides/collections/reference.html
        // https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html,
        // implementations:
        // ArrayBlockingQueue, ArrayDeque, ConcurrentLinkedDeque, ConcurrentLinkedQueue,
        // DelayQueue, LinkedBlockingDeque, LinkedBlockingQueue, LinkedList,
        // LinkedTransferQueue, PriorityBlockingQueue, PriorityQueue, SynchronousQueue

        // Can define as Queue<Integer> queue = new LinkedList<>(); to get only classic
        // queue methods
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.addAll(Arrays.asList(2, 3, 4));
        // queue.addFirst(42); // add to front, available if queue defined as
        // `LinkedList<Integer> queue = new LinkedList<>();`
        while (queue.size() > 0) { // alternative: !queue.isEmpty()
            Integer next = queue.peek();
            System.out.printf("%d...", next);
            queue.poll();// also returns value == next (returned by peek())
        }
        System.out.println("queue is now empty");

        // There is a Stack<E> stack = new Stack<>(), but docs recommend Deque for LIFO:
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        // do not use addAll(), need to push values
        Arrays.asList(2, 3, 4).stream().forEach(v -> stack.push(v));
        while (stack.size() > 0) {// alternative: !queue.isEmpty()
            Integer top = stack.peek();
            System.out.printf("%d...", top);
            stack.pop(); // also returns value == top (returned by peek())
        }
        System.out.println("stack is now empty");

        System.out.printf("\nnext up:\n");
        System.out.println("*** Strings demo ***");

        String s = "Hello, 世界";
        for (char c : s.toCharArray()) { // iterate over characters (9 in total)
            // if c='B', than c-'A' == 1
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c <= 255)) { // a bit redundant, just to show
                                                                                   // comparison ops
                System.out.printf("Non-ascii char: %c\n", c);
            }
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i); // same as above (iterate over 9 chars)
        }
        for (byte bt : s.getBytes()) {
            // will iterate over 13 bytes,
            // 世界 take 6 bytes
        }
        int i = 0;
        for (String word : s.split(",")) {
            System.out.printf("Word #%d: %s\n", ++i, word.trim());
        }
        System.out.printf("Checking s.contains() => %b\n", s.contains("llo")); // true
        System.out.printf("Checking strings.startsWith() => %b\n", s.startsWith("Hello")); // true
        System.out.printf("Checking strings.endsWith() => %b\n", s.endsWith("界")); // true
        System.out.printf("Checking String.join() => %s\n", String.join(" ", new String[] { "Hello", "World" }));
        System.out.printf("Checking String.join() - alt => %s\n", String.join(" ", Arrays.asList("Hello", "World")));

        // StringBuffer() if thread-safety is needed
        StringBuilder sb = new StringBuilder(); // StringBuilder is faster for single thread
        sb.append("Building formatted string");
        sb.append(String.format(" with some numbers: %6.2f and date: ", 12.5));
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E MMM dd")));

        System.out.println(sb.toString());

        System.out.printf("\nnext up:\n");
        System.out.println("*** Heap demo ***");

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // shows custom comparator (usually it will operate on <T> in PriorityQueue<T>)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((Integer v1, Integer v2) -> Integer.compare(v2, v1));

        for (int val = 1; val < 5; val++) {
            minHeap.add(val);
            maxHeap.add(val);
        }
        System.out.printf("Max in max heap: %d\n", maxHeap.peek());
        System.out.printf("Min in min heap: %d\n", minHeap.peek());
        while (!maxHeap.isEmpty()) {
            System.out.printf("Min: %d and max: %d\n", minHeap.poll(), maxHeap.poll());
        }

    }

    public static boolean dfs(Integer num) {
        System.out.printf("dfs(%d)...", num);
        if (num == 0) {
            System.out.println("Reached zero in dfs() call. Will return!");
            return true;
        } else if (num < 0) {
            System.out.println("Only values >=0 are allowed");
            return false;
        }
        return dfs(num - 1);
    }

    public static int max(int... nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > res) {
                res = nums[i];
            }
        }
        return res;
    }
}