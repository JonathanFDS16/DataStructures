import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ArtMuseumTest {

    @Test
    public void addTest() {
        ArtMuseum museum = createEmptyMuseum();
        assertTrue(museum.add(new Art(10, 8, 6, "Cleveland", "John")));
    }

    @Test
    public void sortTest() {
        // Sorting in an Empty list
        ArtMuseum museum = createEmptyMuseum();
        assertEquals(0, museum.sort("height", 1).size());
        assertEquals(0, museum.sort("price", -12).size());
        assertEquals(0, museum.sort("width", -1).size());
        assertEquals(0, museum.sort("name", 0).size());
        assertEquals(0, museum.sort("artistName", 12).size());

        // Sorting in reference to height
        ArtMuseum museum1 = createMuseumWith15Arts();
        Art[] heightSortedAscending = sortedArrayHeight(15, 0);
        Art[] heightSortedDescending = sortedArrayHeight(15, -1);
        assertArrayEquals(museum1.sort("height", 0).toArray(new Art[15]), heightSortedAscending);
        assertArrayEquals(museum1.sort("height", -1).toArray(new Art[15]), heightSortedDescending);

        // Sorting in reference to price
        Art[] priceSortedAscending = sortedArrayPrice(15, 0);
        Art[] priceSortedDescending = sortedArrayPrice(15, -1);
        assertArrayEquals(museum1.sort("price", 0).toArray(new Art[15]), priceSortedAscending);
        assertArrayEquals(museum1.sort("price", -1).toArray(new Art[15]), priceSortedDescending);

        // Sorting in reference to width
        Art[] widthSortedAscending = sortedArrayWidth(15, 0);
        Art[] widthSortedDescending = sortedArrayWidth(15, -1);
        assertArrayEquals(museum1.sort("width", 0).toArray(new Art[15]), widthSortedAscending);
        assertArrayEquals(museum1.sort("width", -1).toArray(new Art[15]), widthSortedDescending);

        // Sorting in reference to name
        Art[] nameSortedAscending = sortedArrayName(15, 0);
        Art[] nameSortedDescending = sortedArrayName(15, -1);
        assertArrayEquals(museum1.sort("name", 0).toArray(new Art[15]), nameSortedAscending);
        assertArrayEquals(museum1.sort("name", -1).toArray(new Art[15]), nameSortedDescending);

        // Sorting in reference to artistName
        Art[] artistNameAscending = sortedArrayArtistName(15, 0);
        Art[] artistNameDescending = sortedArrayArtistName(15, -1);
        assertArrayEquals(museum1.sort("artistName", 0).toArray(new Art[15]), artistNameAscending);
        assertArrayEquals(museum1.sort("artistName", -1).toArray(new Art[15]), artistNameDescending);
    }

    @Test
    public void findArtTest() {
        // Find Art in an empty
        ArtMuseum emptyMuseum = createEmptyMuseum();
        assertEquals(0, emptyMuseum.findArts("Leonardo Da Vinci").size());
        // Find art but artist name does not exist
        ArtMuseum museum = createMuseumWith15Arts();
        assertEquals(0, museum.findArts("Jonathan Da Silva").size());
        // Find art and the art exist
        Art[] artsFound = {new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
                new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci")};
        assertEquals(2, museum.findArts("Leonardo da Vinci").size());
        assertArrayEquals(artsFound, museum.findArts("Leonardo da Vinci").toArray(new Art[0]));
    }

    @Test
    public void randomizeArtTest() {
        // randomizeArt in empty museum
        ArtMuseum emptyMuseum = createEmptyMuseum();
        assertEquals(0 , emptyMuseum.randomizeArts(1).size());
        // With n being positive
        ArtMuseum museum = createMuseumWith15Arts();
        assertEquals(7, museum.randomizeArts(7).size());
        // N being negative and less than 0
        assertEquals(0, museum.randomizeArts(0).size());
        assertEquals(0, museum.randomizeArts(-1).size());

    }

    @Test
    public void randomSortTest() {
        // randomSort in an Empty list
        ArtMuseum museum = createEmptyMuseum();
        assertEquals(0, museum.randomSort(new ArrayList<>()).size());
        // randomSort in list with less than 5 elements
        Art[] fourElements = {new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
        new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci"),
        new Art(1, 2, 9, "The Starry Night", "Van Gogh"),
        new Art(6, 3, 5, "Scream", "Edvard Munch")};
        assertArrayEquals(fourElements, museum.randomSort(databaseCopy(4)).toArray(new Art[0]));

        // randomSort in list with 5 elements
        Art[] fiveElements = {new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
                new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci"),
                new Art(1, 2, 9, "The Starry Night", "Van Gogh"),
                new Art(6, 3, 5, "Scream", "Edvard Munch"),
                new Art(9, 8, 4, "Guernica", "Pablo Picasso")};
        assertArrayEquals(fiveElements, museum.randomSort(databaseCopy(5)).toArray(new Art[0]));
        // randomSort in list with less than 10 elements
        Art[] sevenElements = {new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
                new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci"),
                new Art(1, 2, 9, "The Starry Night", "Van Gogh"),
                new Art(6, 3, 5, "Scream", "Edvard Munch"),
                new Art(4, 7, 1, "Kiss", "Gustav Klimt"),
                new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer"),
                new Art(9, 8, 4, "Guernica", "Pablo Picasso")};
        assertArrayEquals(sevenElements, museum.randomSort(databaseCopy(7)).toArray(new Art[0]));
        // randomSort in list with 10 elements
        Art[] tenElements = {
                new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci"),
                new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
                new Art(1, 2, 9, "The Starry Night", "Van Gogh"),
                new Art(6, 3, 5, "Scream", "Edvard Munch"),
                new Art(4, 7, 1, "Kiss", "Gustav Klimt"),
                new Art(9, 8, 4, "Guernica", "Pablo Picasso"),
                new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer"),
                new Art(5, 10, 3, "Las Meninas", "Diego Valazquez"),
                new Art(10, 6, 7, "Chapel", "John Doe"),
                new Art(8, 5, 6, "Creation of Adam", "Michelangelo")};
        assertArrayEquals(tenElements, museum.randomSort(databaseCopy(10)).toArray(new Art[0]));
        // randomSort in list with less than 15 elements
        Art[] thirteenElements = {
                new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci"),
                new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
                new Art(1, 2, 9, "The Starry Night", "Van Gogh"),
                new Art(6, 3, 5, "Scream", "Edvard Munch"),
                new Art(4, 7, 1, "Kiss", "Gustav Klimt"),
                new Art(9, 8, 4, "Guernica", "Pablo Picasso"),
                new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer"),
                new Art(5, 10, 3, "Las Meninas", "Diego Valazquez"),
                new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio"),
                new Art(13, 27, 25, "Olympia", "Edouard Manet"),
                new Art(10, 6, 7, "Chapel", "John Doe"),
                new Art(8, 5, 6, "Creation of Adam", "Michelangelo"),
                new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne")
        };
        assertArrayEquals(thirteenElements, museum.randomSort(databaseCopy(13)).toArray(new Art[0]));
        // randomSort in list with 15 elements
        Art[] fifteenElements = {
                new Art(1, 2, 9, "The Starry Night", "Van Gogh"),
                new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci"),
                new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci"),
                new Art(6, 3, 5, "Scream", "Edvard Munch"),
                new Art(4, 7, 1, "Kiss", "Gustav Klimt"),
                new Art(9, 8, 4, "Guernica", "Pablo Picasso"),
                new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer"),
                new Art(5, 10, 3, "Las Meninas", "Diego Valazquez"),
                new Art(8, 5, 6, "Creation of Adam", "Michelangelo"),
                new Art(10, 6, 7, "Chapel", "John Doe"),
                new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio"),
                new Art(13, 27, 25, "Olympia", "Edouard Manet"),
                new Art(50, 60, 70, "Sleepers", "Gustave Courbet"),
                new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai"),
                new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne"),
        };
        assertArrayEquals(fifteenElements, museum.randomSort(databaseCopy(15)).toArray(new Art[0]));
    }

    public ArtMuseum createEmptyMuseum() {
        return new ArtMuseum("Cleveland Of Arts");
    }

    public ArtMuseum createMuseumWith15Arts() {
        ArtMuseum museum = new ArtMuseum("Cleveland of Arts");
        Art art1 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art2 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art3 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        Art art4 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art5 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art6 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art7 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art8 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art9 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art10 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art11 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art12 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art13 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art14 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        Art art15 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);
        return museum;
    }

    public ArrayList<Art> databaseCopy(int size) {
        ArrayList<Art> museum = new ArrayList<>();
        Art art1 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art2 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art3 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        Art art4 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art5 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art6 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art7 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art8 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art9 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art10 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art11 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art12 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art13 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art14 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        Art art15 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);
        int museumSize = museum.size();
        for (int i = 0; i < museumSize - size; i++) {
            museum.remove(size);
        }
        return museum;
    }

    public Art[] sortedArrayHeight(int size, int direction) {
        ArrayList<Art> museum = new ArrayList<>();
        Art art1 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        Art art2 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art3 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art4 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art5 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art6 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art7 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art8 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art9 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art10 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art11 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art12 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art13 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art14 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        Art art15 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);
        Art[] a = new Art[0];
        a = museum.toArray(a);
        Art[] array = new Art[size];
        if (direction >= 0) {
            for (int i = 0; i < size; i++) {
                array[i] = a[i];
            }
        }
        else {
            for (int i = a.length - 1, j = 0; i >= 0; i--) {
                array[j++] = a[i];
            }
        }
        return array;
    }

    public Art[] sortedArrayPrice(int size, int direction) {
        ArrayList<Art> museum = new ArrayList<>();
        Art art1 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art2 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        Art art3 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art4 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art5 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art6 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art7 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art8 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art9 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art10 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art11 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art12 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art13 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art14 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        Art art15 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);

        Art[] a = new Art[0];
        a = museum.toArray(a);
        Art[] array = new Art[size];

        if (direction >= 0) {
            for (int i = 0; i < size; i++) {
                array[i] = a[i];
            }
        }
        else {
            for (int i = a.length - 1, j = 0; i >= 0; i--) {
                array[j++] = a[i];
            }
        }
        return array;
    }

    public Art[] sortedArrayWidth(int size, int direction) {
        ArrayList<Art> museum = new ArrayList<>();
        Art art1 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art2 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art3 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art4 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art5 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art6 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art7 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art8 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art9 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        Art art10 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art11 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art12 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art13 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art14 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        Art art15 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);
        Art[] a = new Art[0];
        a = museum.toArray(a);
        Art[] array = new Art[size];
        if (direction >= 0) {
            for (int i = 0; i < size; i++) {
                array[i] = a[i];
            }
        }
        else {
            for (int i = a.length - 1, j = 0; i >= 0; i--) {
                array[j++] = a[i];
            }
        }
        return array;
    }

    public Art[] sortedArrayName(int size, int direction) {
        ArrayList<Art> museum = new ArrayList<>();
        Art art1 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art2 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art3 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art4 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art5 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        Art art6 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art7 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art8 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art9 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art10 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art11 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art12 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art13 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        Art art14 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art15 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);
        Art[] a = new Art[0];
        a = museum.toArray(a);
        Art[] array = new Art[size];
        if (direction >= 0) {
            for (int i = 0; i < size; i++) {
                array[i] = a[i];
            }
        }
        else {
            for (int i = a.length - 1, j = 0; i >= 0; i--) {
                array[j++] = a[i];
            }
        }
        return array;
    }

    public Art[] sortedArrayArtistName(int size, int direction) {
        ArrayList<Art> museum = new ArrayList<>();
        Art art1 = new Art(12, 15, 30, "Musicians by Caravaggio", "Caravaggio");
        Art art2 = new Art(5, 10, 3, "Las Meninas", "Diego Valazquez");
        Art art3 = new Art(13, 27, 25, "Olympia", "Edouard Manet");
        Art art4 = new Art(6, 3, 5, "Scream", "Edvard Munch");
        Art art5 = new Art(4, 7, 1, "Kiss", "Gustav Klimt");
        Art art6 = new Art(50, 60, 70, "Sleepers", "Gustave Courbet");
        Art art7 = new Art(40, 36, 67, "Great Wave off Kanagawa", "Hokusai");
        Art art8 = new Art(2, 9, 2, "Girl With a Pearl Earring", "Johannes Vermeer");
        Art art9 = new Art(10, 6, 7, "Chapel", "John Doe");
        Art art10 = new Art(7, 1, 10, "Mona Lisa", "Leonardo da Vinci");
        Art art11 = new Art(3, 4, 8, "The Last Supper", "Leonardo da Vinci");
        Art art12 = new Art(8, 5, 6, "Creation of Adam", "Michelangelo");
        Art art13 = new Art(9, 8, 4, "Guernica", "Pablo Picasso");
        Art art14 = new Art(11, 23, 20, "Bathers by Cezanne", "Paul Cezanne");
        Art art15 = new Art(1, 2, 9, "The Starry Night", "Van Gogh");
        museum.add(art1);
        museum.add(art2);
        museum.add(art3);
        museum.add(art4);
        museum.add(art5);
        museum.add(art6);
        museum.add(art7);
        museum.add(art8);
        museum.add(art9);
        museum.add(art10);
        museum.add(art11);
        museum.add(art12);
        museum.add(art13);
        museum.add(art14);
        museum.add(art15);
        Art[] a = new Art[0];
        a = museum.toArray(a);
        Art[] array = new Art[size];
        if (direction >= 0) {
            for (int i = 0; i < size; i++) {
                array[i] = a[i];
            }
        }
        else {
            for (int i = a.length - 1, j = 0; i >= 0; i--) {
                array[j++] = a[i];
            }
        }
        return array;
    }
}