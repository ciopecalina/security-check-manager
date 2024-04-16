import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class SecurityCheck {
    private Set<String> personsIn = new HashSet<>(100, 0.5f);

    public boolean goesIn(String person) {
        if (personsIn.contains(person)) {
            System.out.println("Security alert! Person already in! [" + person + "]");
            return false;
        }
        personsIn.add(person);
        return true;
    }

    public boolean goesOut(String person) {
        if (!personsIn.contains(person)) {
            System.out.println("Security alert! Person was not in! [" + person + "]");
            return false;
        }
        personsIn.remove(person);
        return true;
    }
}

enum SecurityState {
    IN, OUT;
}

class SecurityEvent {
    private String person;
    private SecurityState state;

    public SecurityEvent(String person, SecurityState state) {
        this.person = person;
        this.state = state;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public SecurityState getState() {
        return state;
    }

    public void setState(SecurityState state) {
        this.state = state;
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        SecurityCheck securityCheck = new SecurityCheck();

        File file = new File("src/resources/input");

        Scanner scanner;
        if (file.exists()) {
            scanner = new Scanner(file);
        } else {
            System.out.println("The input file could not be found - Starting using the command line ...");
            scanner = new Scanner(System.in);
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            if (parts.length != 2) {
                System.out.println("Invalid input format: " + line);
                continue;
            }

            String person = parts[0];
            String action = parts[1];

            SecurityState state = SecurityState.valueOf(action);
            SecurityEvent event = new SecurityEvent(person, state);

            if (event.getState() == SecurityState.IN) {
                securityCheck.goesIn(event.getPerson());
            } else if (event.getState() == SecurityState.OUT) {
                securityCheck.goesOut(event.getPerson());
            } else {
                System.out.println("Invalid action: " + action);
            }
        }

        scanner.close();
    }
}