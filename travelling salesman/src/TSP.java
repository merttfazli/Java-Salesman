import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSP {
    public static double[][] distances;
    private int numCities;
    private List<Integer> tour;
    private int tourLength;

    public static double[][] coordinates;
    public static double[][] readFile(String fileName) {
        try {
            File file = new File("C:\\Java\\Salesman\\travelling salesman\\" + fileName);

            Scanner myReader = new Scanner(file);
            int input = Integer.parseInt(myReader.nextLine());
            coordinates = new double[input][2];
            int index = 0;
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);

                coordinates[index][0] = x;
                coordinates[index][1] = y;

                index++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return coordinates;
    }

    public TSP(double[][] coords) {
        numCities = coords.length;
        distances = new double[numCities][numCities];
        tour = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                distances[i][j] = Math.round(Math.sqrt(Math.pow(coords[i][0] - coords[j][0], 2)
                        + Math.pow(coords[i][1] - coords[j][1], 2)));
            }
        }
    }
    public List<Integer> solve() {
        List<Integer> initialTour = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            initialTour.add(i);
        }
        tourLength = getTourLength(initialTour);
        tour = new ArrayList<>(initialTour);

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < numCities - 1; i++) {
                for (int j = i + 1; j < numCities; j++) {
                    List<Integer> newTour = twoOptSwap(i, j, tour);
                    int newLength = getTourLength(newTour);
                    if (newLength < tourLength) {
                        tourLength = newLength;
                        tour = new ArrayList<>(newTour);
                        improved = true;
                    }
                }
            }
        }
        return tour;
    }
    private List<Integer> twoOptSwap(int i, int j, List<Integer> tour) {
        List<Integer> newTour = new ArrayList<>(tour.subList(0, i));
        for (int k = j; k >= i; k--) {
            newTour.add(tour.get(k));
        }
        newTour.addAll(tour.subList(j + 1, numCities));
        return newTour;
    }

    public int getTourLength(List<Integer> tour) {
        int length = 0;
        for (int i = 0; i < numCities - 1; i++) {
            length += distances[tour.get(i)][tour.get(i + 1)];
        }
        return length;
    }

    public int getTourLength() {
      return tourLength;
    }

    public List<Integer> getTour() {
        return tour;
    }
    public static void main(String[] args) {
        long beginTime = 0, endTime = 0;
        beginTime = System.currentTimeMillis();
        TSP tsp = new TSP(readFile("tsp_5_1.txt"));
        //calculateCost(tsp.solve());
        System.out.println("Maliyet: "+tsp.getTourLength(tsp.solve()));
        System.out.println("Path: "+tsp.solve());
        endTime = System.currentTimeMillis();
        System.out.println("Çalışma Süresi : " + ((double) (endTime - beginTime)) / 1000+" sn");

    }
}
