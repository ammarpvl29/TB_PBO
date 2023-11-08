package assignments.assignment2;

// TODO
public class Category {
    private String name;
    private int point;

    @Override
    public String toString() {
        // TODO
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

/*
 * +-----------------+
 * | Category |
 * +-----------------+
 * | - name: String
 * | - point: int
 * +-----------------+
 * | + getName(): String
 * | + setName(String): void
 * | + getPoint(): int
 * | + setPoint(int): void
 * | + toString(): String
 * +-----------------+
 */