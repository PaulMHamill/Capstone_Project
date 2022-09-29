package models;

public enum PodType {
    DOUBLE(2);

    private final int capacity;

    PodType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
