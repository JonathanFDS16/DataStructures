import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Art Museum Class that stores Art in its database
 */
public class ArtMuseum {

    private ArrayList<Art> artList = new ArrayList<>();

    private String museumName;

    public ArtMuseum(String museumName) {
        this.museumName = museumName;
    }

    public boolean add(Art art) {
        return artList.add(art);
    }

    public List<Art> sort(String attribute, int direction) {
        // Direction changes the sing in compareTo method
        // mergeSort(array, attribute)
        // sort (left, right, original array, attribute)
        Art[] arrayType = {};
        Art[] artList = this.artList.toArray(arrayType);
        mergeSort(artList, attribute, direction);
        return Arrays.asList(artList);
    }

    private void mergeSort(Art[] artList, String attribute, int direction) {
        //Divide artList into two arrays
        int listLength = artList.length;
        if (listLength <= 1) {
            return; //base case
        }

        int middle = listLength / 2;
        Art[] leftArray = new Art[middle];
        Art[] rightArray = new Art[listLength - middle];

        for (int i = 0, j = 0; i < listLength; i++) {
            if (i < middle) {
                leftArray[i] = artList[i];
            }
            else rightArray[j++] = artList[i];
        }

        mergeSort(leftArray, attribute, direction);
        mergeSort(rightArray, attribute, direction);
        merge(leftArray, rightArray, artList, attribute, direction);
    }

    private void merge(Art[] leftArray, Art[] rightArray, Art[] artList, String attribute, int direction) {
        int i = 0;
        int l = 0;
        int r = 0;
        while (l < leftArray.length && r < rightArray.length) {
            if (leftArray[l].compareTo(rightArray[r], attribute, direction) <= 0)
                artList[i++] = leftArray[l++];
            else if (leftArray[l].compareTo(rightArray[r], attribute, direction) > 0)
                artList[i++] = rightArray[r++];
        }

        while (l < leftArray.length) {
            artList[i++] = leftArray[l++];
        }
        while (r < rightArray.length) {
            artList[i++] = rightArray[r++];
        }

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
