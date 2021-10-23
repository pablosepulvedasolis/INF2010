package tp2;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    /**
     * Constructeur par défaut
     */
    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    /**
     * Constructeur par parametre
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametres
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType within map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() {
        return size * loadFactor > capacity;
    }

    /**
     * @return Number of elements currently in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space for the map
     */
    public int capacity(){ return capacity; }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** TODO
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        //Node<KeyType, DataType>[] newMap = new Node[this.capacity * CAPACITY_INCREASE_FACTOR];
        Node<KeyType, DataType>[] oldMap = map;
        this.capacity *= CAPACITY_INCREASE_FACTOR;
        this.map = new Node[this.capacity];
        this.size = 0;
        for (int i = 0; i < oldMap.length; i++) {
            Node<KeyType, DataType> element = oldMap[i];
            while (element != null) {
                put(element.key, element.data);
                element = element.next;
            }
        }

    }

    /** TODO
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        Node<KeyType, DataType> element = map[hash(key)];

        while((element != null) && (element.next != null) && !element.key.equals(key))
            element = element.next;

        if (element == null)
            return false;

        if (element.key.equals(key))
            return true;

        return false;
    }

    /** TODO
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        Node<KeyType, DataType> element = map[hash(key)];

        while((element != null) && (element.next != null) && (!element.key.equals(key)))
            element = element.next;

        if (element == null)
            return null;

        else if (element.key.equals(key))
            return element.data;

        else
            return null;

    }

    /**TODO
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        Node<KeyType, DataType> element = map[hash(key)];

        while ((element != null) && (element.next != null) && (!element.key.equals(key)))
            element = element.next;

        if (element == null)
        {
            if (needRehash())
                rehash();
            Node<KeyType, DataType> newNode = new Node(key, value);
            newNode.next = map[hash(newNode.key)];
            map[hash(newNode.key)] = newNode;
            size++;
            return null;
        }

        else if (element.key.equals(key))
        {
            DataType returnValue = element.data;
            element.data = value;
            return returnValue;
        }

        else
        {
            if (needRehash())
                rehash();
            Node<KeyType, DataType> newNode = new Node(key, value);
            newNode.next = map[hash(newNode.key)];
            map[hash(newNode.key)] = newNode;
            size++;
            return null;
        }
    }

    /**TODO
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        Node<KeyType, DataType> currentElement = map[hash(key)];
        Node<KeyType, DataType> previousElement = null;

        while((currentElement != null) && (currentElement.next != null) && (!currentElement.key.equals(key)))
        {
            previousElement = currentElement;
            currentElement = currentElement.next;
        }

        if (currentElement == null)
            return null;

        else if (currentElement.key.equals(key))
        {
            size--;
            if (previousElement != null)
                previousElement.next = currentElement.next;
            else
                map[hash(key)] = currentElement.next;
            return currentElement.data;
        }

        else
            return null;
    }

    /**TODO
     * Removes all nodes contained within the map
     */
    public void clear() {
        for(int i = 0; i < map.length; i++)
            map[i] = null;
        this.size = 0;
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}