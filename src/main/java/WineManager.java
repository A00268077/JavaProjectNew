
// Import necessary Java Core APIs
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

// -----------------------------
// Enum for Wine Types
// -----------------------------
enum WineType {
    RED, WHITE, ROSE, SPARKLING
}

// -----------------------------
// Interface demonstrating default, static, and private methods
// -----------------------------
interface WineOperations {
    // Abstract method to display wine details
    void displayWineDetails(BaseWine wine);

    // Default method
    default void printSeparator() {
        System.out.println("========================================");
    }

    // Static method
    static void printHeader() {
        System.out.println("********** Wine Collection **********");
    }

    // Private method
    private void privateMethod() {
        System.out.println("This is a private method in the interface.");
    }

    // Method using the private method
    default void usePrivateMethod() {
        privateMethod();
    }
}

// -----------------------------
// Sealed Class Hierarchy for Wine
// -----------------------------
sealed class BaseWine permits PremiumWine, StandardWine {
    private String name;
    private WineType type;
    private int year;
    private String country;
    private Double rating;
    private String comments;

    public BaseWine(String name, WineType type, int year, String country, Double rating, String comments) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.country = country;
        this.rating = rating;
        this.comments = comments;
    }
    public String getName(){
        return name;
    }
    public WineType getType(){
        return type;
    }
    public int getYear(){
        return year;
    }
    public String getCountry(){
        return country;
    }
    public Double getRating(){
        return rating;
    }
    public String getComments(){
        return comments;
    }
    // Getters (unchanged)

    public String getRecommendedStorageTemperature() {
        return switch (this.type) {
            case RED -> "15-17 degrees Celsius";
            case WHITE, ROSE -> "10-12 degrees Celsius";
            case SPARKLING -> "5-7 degrees Celsius";
        };
    }

    public String getAdditionalRecommendations() {
        if (this.type == WineType.RED) {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int age = currentYear - this.year;

            if (age > 2 && age < 5) {
                return "Recommended to consume no earlier than when the wine is 5 years old (Year: " + (this.year + 5) + ")";
            } else if (age >= 5 && age < 15) {
                return "Can be consumed at any time.";
            } else if (age >= 15 && age < 25) {
                return "Can be consumed, but it is already old.";
            } else if (age >= 25) {
                return "Better not to consume, as it is only suitable for collecting.";
            }
        }
        return ""; // For non-red wines or no special recommendation
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wine name: ").append(this.name).append("\n")
                .append("Type: ").append(this.type.name()).append("\n")
                .append("Year: ").append(this.year).append("\n")
                .append("Country: ").append(this.country).append("\n")
                .append("Rating: ").append(this.rating != null ? this.rating : "n/a").append("\n")
                .append("Comments: ").append(this.comments != null ? this.comments : "n/a").append("\n")
                .append("Recommended Storage Temperature: ").append(getRecommendedStorageTemperature()).append("\n");

        String additionalRecommendations = getAdditionalRecommendations();
        if (!additionalRecommendations.isEmpty()) {
            sb.append(additionalRecommendations).append("\n");
        }
        return sb.toString();
    }
}

// -----------------------------
// PremiumWine Class demonstrating inheritance, overriding, and polymorphism
// -----------------------------
final class PremiumWine extends BaseWine implements WineOperations {
    private String vintage;

    public PremiumWine(String name, WineType type, int year, String country, Double rating, String comments, String vintage) {
        super(name, type, year, country, rating, comments);
        this.vintage = vintage;
    }

    public String getVintage() {
        return vintage;
    }

    @Override
    public void displayWineDetails(BaseWine wine) {
        printSeparator();
        System.out.println("Premium Wine Details:");
        System.out.println(wine);
        printSeparator();
    }

    public void displayWineDetails(BaseWine wine, boolean showVintage) {
        printSeparator();
        System.out.println("Premium Wine Details:");
        System.out.println(wine);
        if (showVintage) {
            System.out.println("Vintage: " + this.vintage);
        }
        printSeparator();
    }

    @Override
    public void usePrivateMethod() {
        WineOperations.super.usePrivateMethod();
    }
}

// -----------------------------
// StandardWine Class demonstrating inheritance
// -----------------------------
final class StandardWine extends BaseWine {
    public StandardWine(String name, WineType type, int year, String country, Double rating, String comments) {
        super(name, type, year, country, rating, comments);
    }
}

// -----------------------------
// ImmutableWineRecord demonstrating records (Java 16+)
// -----------------------------
record ImmutableWineRecord(String name, WineType type, int year, String country, Double rating, String comments) {}

// -----------------------------
// Custom Immutable Type demonstrating immutability
// -----------------------------
final class ImmutableWineClass {
    private final String name;
    private final WineType type;
    private final int year;
    private final String country;
    private final Double rating;
    private final String comments;

    public ImmutableWineClass(String name, WineType type, int year, String country, Double rating, String comments) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.country = country;
        this.rating = rating;
        this.comments = comments;
    }

    // Getters (unchanged)

    @Override
    public String toString() {
        return "ImmutableWineClass{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", rating=" + (rating != null ? rating : "n/a") +
                ", comments='" + (comments != null ? comments : "n/a") + '\'' +
                '}';
    }
}

// -----------------------------
// Custom Checked Exception
// -----------------------------
class InvalidWineAgeException extends Exception {
    public InvalidWineAgeException(String message) {
        super(message);
    }
}

// -----------------------------
// Main WineManager Class demonstrating various Java features
// -----------------------------
public class WineManager implements WineOperations {
    private List<BaseWine> wineList;

    public WineManager() {
        var list = new ArrayList<BaseWine>();
        this.wineList = list;
    }

    public void addWine(BaseWine... wines) {
        for (BaseWine wine : wines) {
            this.wineList.add(wine);
            System.out.println("Wine added successfully!");
        }
    }

    public void addWine(BaseWine wine) {
        this.wineList.add(wine);
        System.out.println("Wine added successfully!");
    }

    @Override
    public void displayWineDetails(BaseWine wine) {
        printSeparator();
        System.out.println("Wine Details:");
        System.out.println(wine);
        printSeparator();
    }

    public void promptAndAddWine() {
        Scanner scanner = new Scanner(System.in);
        String name, country, ratingInput, comments;
        WineType type = null;

        System.out.print("Enter Wine Name (mandatory): ");
        name = scanner.nextLine();

        while (type == null) {
            try {
                System.out.print("Enter Wine Type (RED, WHITE, ROSE, SPARKLING): ");
                type = WineType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Please enter a valid Wine Type (RED, WHITE, ROSE, SPARKLING).");
            }
        }

        int year = 0;
        boolean validYear = false;
        while (!validYear) {
            System.out.print("Enter year of production (mandatory): ");
            String yearInput = scanner.nextLine();
            try {
                year = Integer.parseInt(yearInput);

                LocalDate currentDate = LocalDate.now();
                LocalDate sixMonthsAgo = currentDate.minusMonths(6);

                LocalDate wineProductionDate = LocalDate.of(year, 7, 1);

                if (wineProductionDate.isBefore(sixMonthsAgo)) {
                    validYear = true;
                } else {
                    throw new InvalidWineAgeException("Error: The wine cannot be younger than 6 months.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for the year.");
            } catch (InvalidWineAgeException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter country of production (mandatory): ");
        country = scanner.nextLine();

        Double rating = null;
        System.out.print("Enter rating (optional, press enter to skip): ");
        ratingInput = scanner.nextLine();
        if (!ratingInput.isEmpty()) {
            try {
                rating = Double.parseDouble(ratingInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid rating format. Skipping rating.");
            }
        }

        System.out.print("Enter comments (optional, press enter to skip): ");
        comments = scanner.nextLine();

        BaseWine wine;
        if (rating != null || !comments.isEmpty()) {
            wine = new PremiumWine(name, type, year, country, rating, comments, "Special Vintage");
        } else {
            wine = new StandardWine(name, type, year, country, rating, comments);
        }

        addWine(wine);
    }

    public void displayAllWines() {
        WineOperations.printHeader();

        System.out.println("\nList of all entered wines:");

        Predicate<BaseWine> hasRating = w -> w.getRating() != null;

        wineList.stream()
                .filter(hasRating)
                .forEach(wine -> {
                    if (wine instanceof PremiumWine premiumWine) {
                        premiumWine.displayWineDetails(wine, true);
                    } else {
                        displayWineDetails(wine);
                    }
                });

        wineList.stream()
                .filter(wine -> wine.getRating() == null)
                .forEach(System.out::println);

        printSeparator();
    }

    public List<BaseWine> getWineList() {
        return new ArrayList<>(this.wineList);
    }

    public static void main(String[] args) {
        WineManager manager = new WineManager();
        Scanner scanner = new Scanner(System.in);
        String continueInput;

        WineOperations.printHeader();

        do {
            manager.promptAndAddWine();
            System.out.print("Do you want to add another wine? (yes/no): ");
            continueInput = scanner.nextLine().trim().toLowerCase();
        } while (continueInput.equals("yes") || continueInput.equals("y"));

        manager.displayAllWines();
    }
}