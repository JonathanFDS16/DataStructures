import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ArtMuseumTest {

    @Test
    public void sortTest() {
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
        /*
        for (Art art : museum.sort("artistName", -1)) {
            System.out.println(art.toString());
        }
         */
        for (Art art : museum.findArts("")) {
            System.out.println(art.toString());
        }
    }

    @Test
    public void findArttest() {

    }
}