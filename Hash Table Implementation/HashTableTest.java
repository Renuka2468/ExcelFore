class HashTable {

    //Define initial capacity and load factor
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry[] table;
    private int size;

    public HashTable() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    //Adding key pair value by passing agruments
    public void put(String key, String value) {
        int index = hash(key);
        Entry entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        entry = new Entry(key, value);
        entry.next = table[index];
        table[index] = entry;
        size++;
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }
    }

    //Return the corresponding value by key 
    public String get(String key) {
        int index = hash(key);
        Entry entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    //Removing value pair by key
    public void remove(String key) {
        int index = hash(key);
        Entry entry = table[index];
        Entry prev = null;
        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }
    // Creating Hash Code to store values
    private int hash(String key) {
        int hashCode = key.hashCode();
        //System.out.println(hashCode);
        return hashCode & (table.length - 1);
    }

    //Resizing when entries goes beyond the load factor
    private void resize() {
        Entry[] newTable = new Entry[table.length * 2];
        for (Entry entry : table) {
            while (entry != null) {
                int index = hash(entry.key);
                Entry next = entry.next;
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = next;
            }
        }
        table = newTable;
    }
    
    //Base class 
    private static class Entry {
        String key;
        String value;
        Entry next;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }
}
public class HashTableTest{
    public static void main(String[] args){
        
        //Creating Object of HashTable class
        HashTable ht = new HashTable();

        //Adding key pair value into HashTable object
        ht.put("A", "Hello");
        ht.put("B", "This");
        ht.put("C", "is");
        ht.put("D", "HashTable");

        //Accessing value by corresponding key
        System.out.println(ht.get("B"));
        System.out.println(ht.get("C"));
        System.out.println(ht.get("D"));
        System.out.println(ht.get("A"));

        //Removing key pair values
        ht.remove("A");

    }
}
