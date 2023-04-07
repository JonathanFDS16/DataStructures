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
