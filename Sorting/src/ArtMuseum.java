import java.util.List;

public class ArtMuseum {

    private String museumName;

    public ArtMuseum(String museumName) {
        this.museumName = museumName;
    }

    public boolean add(Art art) {
        return false;
    }

    public List<Art> sort(String attribute, int direction) {
        return null;
    }

    public List<Art> findArts(String artistName) {
        return null;
    }

    public List<Art> randomizeArts(int n) {
        return null;
    }

    public List<Art> randomSort(List<Art> arts) {
        return null;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }
}
