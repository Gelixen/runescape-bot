package lt.kslipaitis.osrs;

import lombok.Data;

@Data
public class Coordinate implements Comparable<Coordinate> {

    private final int x;
    private final int y;

    @Override
    public int compareTo(Coordinate o) {
        if (o.y > this.y) {
            return 1;
        }
        if (o.y == this.y && o.x > this.x) {
            return 1;
        }
        if (o.y == this.y && o.x == this.x) {
            return 0;
        }
        return -1;
    }
}
