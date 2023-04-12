import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtTest {

    /**
     * compareToTest tests following comparisons.
     * 1. Compare arts with different heights.
     * 2. Compare arts with different prices.
     * 3. Compare arts with different widths.
     * 4. Compare arts with different names.
     * 5. Compare arts with different artistNames
     * 6. Compare same arts with same characteristics
     */

    @Test
    public void compareToHeights() {
        Art art1 = new Art(5, 4, 2, "Monalisa", "DaVince");
        Art art2 = new Art(10, 2, 2, "Monalisa", "DaVince");
        Art art3 = new Art(10, 7, 8, "Zatura", "John Doe");
        assertTrue(art1.compareTo(art2, "height", 1) < 0);
        assertTrue(art1.compareTo(art2, "height", -1) > 0);
        assertTrue(art2.compareTo(art3, "height", 1) == 0);
    }

    @Test
    public void compareToPrices() {
        Art art1 = new Art(10, 2, 2, "Monalisa", "DaVince");
        Art art2 = new Art(10, 4, 4, "Monalisa", "DaVince");
        Art art3 = new Art(7, 4, 3, "It's your fault", "Marie Doe");
        assertTrue(art1.compareTo(art2, "price", 1) < 0);
        assertTrue(art1.compareTo(art2, "price", -1) > 0);
        assertTrue(art2.compareTo(art3, "price", 1) == 0);
    }

    @Test
    public void compareToWidths() {
        Art art1 = new Art(10, 4, 2, "Monalisa", "DaVince");
        Art art2 = new Art(10, 2, 4, "Monalisa", "DaVince");
        Art art3 = new Art(1, 10, 4, "Monalisa", "DaVince");
        assertTrue(art1.compareTo(art2, "width", 1) < 0);
        assertTrue(art1.compareTo(art2, "width", -1) > 0);
        assertTrue(art2.compareTo(art3, "width", 1) == 0);
    }

    @Test
    public void compareToNames() {
        Art art1 = new Art(10, 4, 2, "Monaliza", "DaVince");
        Art art2 = new Art(10, 4, 2, "Zebra", "DaVince");
        Art art3 = new Art(10, 4, 2, "Zebra", "DaVince");
        assertTrue(art1.compareTo(art2, "name", 1) < 0);
        assertTrue(art1.compareTo(art2, "name", -1) > 0);
        assertTrue(art2.compareTo(art3, "name", 1) == 0);
    }

    @Test
    public void compareToArtistNames() {
        Art art1 = new Art(10, 4, 2, "Zatura", "DaVince");
        Art art2 = new Art(10, 4, 2, "Monalisa", "John Doe");
        Art art3 = new Art(10, 4, 2, "Monalisa", "John Doe");
        assertTrue(art1.compareTo(art2, "artistName", 1) < 0);
        assertTrue(art1.compareTo(art2, "artistName", -1) > 0);
        assertTrue(art2.compareTo(art3, "artistName", 1) == 0);
    }
}