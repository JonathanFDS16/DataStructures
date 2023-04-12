public class Art  {

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

    public int compareTo(Art art, String searchAttribute, int direction) {
        if (searchAttribute.equals("height")) {
            if (direction >= 0)
                return this.getHeight() - art.getHeight();
            else
                return art.getHeight() - this.getHeight();
        }
        else if (searchAttribute.equals("width")) {
            if (direction >= 0)
                return this.getWidth() - art.getWidth();
            else
                return art.getWidth() - this.getWidth() ;
        }
        else if (searchAttribute.equals("price")) {
            if (direction >= 0)
                return this.getPrice() - art.getPrice();
            else
                return art.getPrice() - this.getPrice();
        }
        else if (searchAttribute.equals("name")) {
            if (direction >= 0)
                return this.getName().compareTo(art.getName());
            else
                return art.getName().compareTo(this.getName());
        }
        else if (direction >= 0)
            return this.getArtistName().compareTo(art.getArtistName());
        else
            return art.getArtistName().compareTo(this.getArtistName());
    }

    public String toString() {
        return height + " " + price + " " + width + " " + name + " " + artistName;
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
