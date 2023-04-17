import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Art Museum Class that stores Art in its database
 * @author Jonathan Da Silva
 */
public class ArtMuseum {

    private ArrayList<Art> artList = new ArrayList<>();

    private String museumName;

    /**
     * Instantiate a new Museum with a name.
     * @param museumName
     */
    public ArtMuseum(String museumName) {
        this.museumName = museumName;
    }

    /**
     * Add an Art to the Museum
     * @param art the Art object.
     * @return return true if adding the Art was successful.
     * OBS: The museum database does not allow two arts with the same name and author.
     * For example: two Mona Lisa by da Vinci.
     */
    public boolean add(Art art) {
        return artList.add(art);
    }

    /**
     * Sort the Museum database according to a given attribute and direction, and returns a sorted List with the given
     * parameters.
     * @param attribute The attribute that the list will be sorted from. Valid attributes are only:
     *                  "height", "price", "width", "name", and "artistName"
     * @param direction If direction >= 0 the list will be in ascending order. If < 0 it will be decresing.
     * @return A list sorted by the given attribute and direction.
     */
    public List<Art> sort(String attribute, int direction) {
        Art[] arrayType = {};
        Art[] artList = this.artList.toArray(arrayType);
        mergeSort(artList, attribute, direction);
        return Arrays.asList(artList);
    }

    /**
     * Searches for arts from the given artist
     * @param artistName The artist name.
     * @return A list of arts from the given artist.
     */
    public List<Art> findArts(String artistName) {
        List<Art> sortedList = sort("artistName", 0);
        ArrayList<Art> artistArts = new ArrayList<>();
        // Loop checks for arts with same artistName
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).getArtistName().equals(artistName)) {
                artistArts.add(sortedList.get(i));
            }
        }
        return artistArts;
    }

    /**
     * Randomly generates a list with n number of arts.
     * @param n the amount of arts to be in the List
     * @return a List with n random arts from the Museum database
     */
    public List<Art> randomizeArts(int n) {
        // If database is empty or n <= 0
        if (this.artList.isEmpty() || n <= 0) {
            return new ArrayList<>();
        }

        ArrayList<Art> randomArts = new ArrayList<>();
        // Loop adds random copies of art from the databse to a new list
        for (int i = 0; i < n; i++) {
            int randomIndex = (int)(Math.random() * artList.size());
            Art copy = copy(artList.get(randomIndex));
            randomArts.add(copy);
        }
        return randomArts;
    }

    /**
     * Randomize 1/5 of a given list of Arts with respect to the 5 attributes.
     * @param arts  Given list of arts
     * @return a List with arts randomSorted
     */
    public List<Art> randomSort(List<Art> arts) {
        ArrayList<Art> randomSortList = new ArrayList<>();
        String[] attributesArray = {"height", "price", "width", "name", "artistName"};
        int interval = arts.size() / 5;                         // Keeps track of what indexes should be added to temp.
        Art[] temp = new Art[arts.size() / 5];                  // Array that stores the 1/5 of the list

        // Loop through the given list
        for (int i = 0, j = 0, k = 0; i < arts.size(); i++) {
            // If given list have 5 elements or less, there is no sorting happening.
            if (arts.size() <= 5) {
                randomSortList.add(arts.get(i));
            }
            else {
                // If sorting using the last attribute and
                if (k == 4 && interval != arts.size()) {
                    interval = arts.size();
                    temp = new Art[arts.size() - i];
                }
                if (i < interval) {
                    temp[j++] = arts.get(i);
                }
                // If next index is the next 1/5 of the arts list.
                if (i + 1 == interval) {
                    // If the last attribute is "artistName"
                    if (k == 4) {
                        // Sort temp and add it to randomSortList
                        sortAndAdd(temp, randomSortList, attributesArray[k], 1);
                    }
                    else {
                        // Adds the interval again for the next 1/5.
                        interval = interval + (arts.size() / 5);
                        // Sort temp, add it to randomSortList and go to the next attribute
                        sortAndAdd(temp, randomSortList, attributesArray[k++], 1);
                        // Reset temp and its index for an empty array for the 1/5.
                        temp = new Art[arts.size() / 5];
                        j = 0;
                    }
                }
            }
        }

        return randomSortList;
    }

    // Sorts temp and add to randomSort list.
    private void sortAndAdd(Art[] temp, List<Art> randomSort, String attribute, int direction) {
        mergeSort(temp, attribute, direction);
        randomSort.addAll(Arrays.asList(temp));
    }

    /**
     * Sort a given array with a MergeSort algorithm.
     * @param artList array to be sorted
     * @param attribute the attribute given to sort the array
     * @param direction if >= 0 merge into ascending order, otherwise into descending order.
     */
    private void mergeSort(Art[] artList, String attribute, int direction) {
        int listLength = artList.length;
        if (listLength <= 1) {
            return; //base case
        }

        // Creates two arrays that will store the left and right side of the original array.
        int middle = listLength / 2;
        Art[] leftArray = new Art[middle];
        Art[] rightArray = new Art[listLength - middle];

        // Loop separates elements between left and right arrays.
        for (int i = 0, j = 0; i < listLength; i++) {
            if (i < middle) {
                leftArray[i] = artList[i];
            }
            else rightArray[j++] = artList[i];
        }

        // Recursively creates sub-arrays in the left, then right, and then merge the results.
        mergeSort(leftArray, attribute, direction);
        mergeSort(rightArray, attribute, direction);
        merge(leftArray, rightArray, artList, attribute, direction);
    }


    private void merge(Art[] leftArray, Art[] rightArray, Art[] artList, String attribute, int direction) {
        int i = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        // Loop that compares elements and sort them into an array
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            // If left is smaller than right add to left array
            if (leftArray[leftIndex].compareTo(rightArray[rightIndex], attribute, direction) <= 0)
                artList[i++] = leftArray[leftIndex++];
                // If right is smaller than left, add to right.
            else
                artList[i++] = rightArray[rightIndex++];
        }

        // Those following loops exist to save elements that was left in either arrays.
        while (leftIndex < leftArray.length) {
            artList[i++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            artList[i++] = rightArray[rightIndex++];
        }
    }

    private Art copy(Art art) {
        return new Art(art.getHeight(), art.getPrice(), art.getWidth(), art.getName(), art.getArtistName());
    }

    public String getMuseumName() {
        return museumName;
    }
    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }
}
