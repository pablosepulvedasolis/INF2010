import java.util.Comparator;
import java.util.PriorityQueue;

public class Interview {
        // Complexité : O(log(n))
        public int lastBox(int[] boxes){

            // Ne pas modifier la ligne suivante
            PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
            for(int i = 0; i < boxes.length; ++i) {
                heap.add(boxes[i]);
            }
            while(heap.size() > 1) {
                int box1 = heap.remove();
                int box2 = heap.remove();
                if (box1 != box2) {
                    heap.add(box1 - box2);
                }
            }
            return heap.isEmpty() ? 0 : heap.remove();
        }
}


