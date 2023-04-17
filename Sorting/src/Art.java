import java.util.Comparator;

public class Art {

    private int height;

    private int price;

    private int width;

    private String name;

    private String artistName;

    public Art(int height, int price, int width, String name, String artistName) {
        this.height = height;
        this.price = price;
        this.width = width;
        this.name = name;
        this.artistName = artistName;
    }

    /**
     * Compare two arts with respect to an attribute and direction
     * @param art the art to be compared with the instance calling the method
     * @param attribute the attribute used to compare two arts.
     *                  The only allowed attributes are:
     *                  [height, price, width, name, artistName]
     * @param direction If direction is >= 0 will compare arts in ascending order, otherwise descending.
     * @return negative integer if this.art < art, 0 if this.art == art and, positive if this.art > art
     */
    public int compareTo(Art art, String attribute, int direction) {
        switch (attribute) {
            case "height":
                if (getHeight() == art.getHeight())                         // If heights are equal compare by name
                    return compareTo(art, "name", direction);
                else if (direction >= 0)
                    return this.getHeight() - art.getHeight();
                else
                    return art.getHeight() - this.getHeight();
            case "width":
                if (getWidth() == art.getWidth())                           // If widths are equal compare by name
                    return compareTo(art, "name", direction);
                else if (direction >= 0)
                    return this.getWidth() - art.getWidth();
                else
                    return art.getWidth() - this.getWidth();
            case "price":
                if (getPrice() == art.getPrice())                           // If prices are equal compare by name
                    return compareTo(art, "name", direction);
                else if (direction >= 0)
                    return this.getPrice() - art.getPrice();
                else
                    return art.getPrice() - this.getPrice();
            case "name":
                if (direction >= 0)
                    return this.getName().compareTo(art.getName());
                else
                    return art.getName().compareTo(this.getName());
            default:
                if (getArtistName().equals(art.getArtistName()))            // If artistNames are equal compare by name
                    return compareTo(art, "name", direction);
                else if (direction >= 0)
                    return this.getArtistName().compareTo(art.getArtistName());
                else
                    return art.getArtistName().compareTo(this.getArtistName());
        }
    }

    /**
     * Compare two arts and return true if they are the same.
     * Used for the JUnit test when testing for equal arrays.
     * @param obj   the reference object with which to compare.
     * @return True if two arts are the same.
     */
    @Override
    public boolean equals(Object obj) {
        Art a = (Art) obj;
        return this.getHeight() == a.getHeight() &&
                this.getPrice() == a.getPrice() &&
                this.getWidth() == a.getWidth() &&
                this.getName().equals(a.getName()) &&
                this.getArtistName().equals(a.getArtistName());
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


}
