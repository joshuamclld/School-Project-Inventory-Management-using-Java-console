import java.util.*;
import java.io.*;

public class InventoryManager {
    private static final String INVENTORY_FILE = "inventory.txt";
    private Map<String, Item> inventory;
    private Scanner scanner;

    public InventoryManager() {
        this.inventory = loadInventoryFromFile();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = getMenuChoice();
            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    addItemToInventory();
                    saveInventoryToFile();
                    break;
                case 3:
                    editItemCategory();
                    saveInventoryToFile();
                    break;
                case 4:
                    removeItemFromInventory();
                    saveInventoryToFile();
                    break;
                case 5:
                    exportInventoryToCSV();
                    break;
                case 6:
                    if (confirmExit()) {
                        saveInventoryToFile();
                        System.out.println("Exiting the inventory management system.");
                        scanner.close();
                        System.exit(0);
                    } else {
                        System.out.println("Exit canceled.");
                    }
                    break;
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("§========================§");
        System.out.println("|                        |");
        System.out.println("|  •Cabanding's Eatery•  |");
        System.out.println("|  Inventory Management  |");
        System.out.println("|  1. View Inventory     |");
        System.out.println("|  2. Add Item           |");
        System.out.println("|  3. Edit Item          |");
        System.out.println("|  4. Remove Item        |");
        System.out.println("|  5. Export to CSV      |");
        System.out.println("|  6. Save & Exit        |");
        System.out.println("|                        |");
        System.out.println("§========================§");
    }

    private int getMenuChoice() {
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter Code from 1 to 6 : ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input.trim());
                if (choice >= 1 && choice <= 6) {
                    validInput = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return choice;
    }

    private Map<String, Item> loadInventoryFromFile() {
        Map<String, Item> inventory = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length >= 2) {
                    String name = parts[0].trim().toLowerCase();
                    double quantity = Double.parseDouble(parts[1].trim());
                    String description = parts.length >= 3 ? parts[2].trim() : "";
                    inventory.put(name, new Item(name, quantity, description));
                }
            }
        } catch (IOException e) {
            // File may not exist on first run
        }
        return inventory;
    }

    private void displayInventory() {
        System.out.println();
        System.out.println("§==============================§");
        System.out.println("|                              |");
        System.out.println("|  1. View Full Inventory      |");
        System.out.println("|  2. View Low Quantity Items  |");
        System.out.println("|                              |");
        System.out.println("§==============================§");
        System.out.print("Enter Code from 1 to 2 : ");
        int inventoryChoice = getIntInput(1, 2);
        switch (inventoryChoice) {
            case 1:
                System.out.println("Current Inventory");
                System.out.println("+-------------------------------+----------+-------------------------------+");
                System.out.printf("| %-29s | %-8s | %-29s |%n", "Item Name", "Quantity", "Quantity Description");
                System.out.println("+-------------------------------+----------+-------------------------------+");
                for (Item item : inventory.values()) {
                    System.out.printf("| %-29s | %-8.2f | %-29s |%n", item.name, item.quantity, item.description);
                }
                System.out.println("+-------------------------------+----------+-------------------------------+");
                break;
            case 2:
                System.out.println("Low Quantity Items");
                boolean foundLow = false;
                System.out.println("+-------------------------------+----------+-------------------------------+");
                System.out.printf("| %-29s | %-8s | %-29s |%n", "Item Name", "Quantity", "Quantity Description");
                System.out.println("+-------------------------------+----------+-------------------------------+");
                for (Item item : inventory.values()) {
                    if (item.quantity < 25) {
                        System.out.printf("| %-29s | %-8.2f | %-29s |%n", item.name, item.quantity, item.description);
                        foundLow = true;
                    }
                }
                if (!foundLow) {
                    System.out.println("| No low quantity items in the inventory.                              |");
                }
                System.out.println("+-------------------------------+----------+-------------------------------+");
                break;
        }
    }

    private void addItemToInventory() {
        System.out.print("Enter the item name: ");
        String name = scanner.nextLine().trim().toLowerCase();
        while (name.isEmpty()) {
            System.out.print("Item name cannot be empty. Please enter the item name: ");
            name = scanner.nextLine().trim().toLowerCase();
        }
        double quantity = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter the quantity: ");
            String qtyInput = scanner.nextLine();
            try {
                quantity = Double.parseDouble(qtyInput.trim());
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Try again.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a valid number.");
            }
        }
        System.out.print("Enter the quantity description: ");
        String description = scanner.nextLine().trim();
        if (inventory.containsKey(name)) {
            Item existing = inventory.get(name);
            existing.quantity += quantity;
            if (!description.isEmpty()) existing.description = description;
        } else {
            inventory.put(name, new Item(name, quantity, description));
        }
        System.out.println("Item added to inventory.");
    }

    private void editItemCategory() {
        System.out.print("Enter the item name to edit: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        while (itemName.isEmpty()) {
            System.out.print("Item name cannot be empty. Please enter the item name: ");
            itemName = scanner.nextLine().trim().toLowerCase();
        }
        if (!inventory.containsKey(itemName)) {
            System.out.println("Item not found in inventory.");
            return;
        }
        Item item = inventory.get(itemName);
        System.out.println("§==============================§");
        System.out.println("|                              |");
        System.out.println("|  What do you want to edit?   |");
        System.out.println("|  1. Name                     |");
        System.out.println("|  2. Quantity                 |");
        System.out.println("|  3. Quantity Description     |");
        System.out.println("|                              |");
        System.out.println("§==============================§");
        System.out.print("Enter choice (1-3): ");
        int choice = getIntInput(1, 3);
        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine().trim().toLowerCase();
                if (newName.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                    return;
                }
                if (inventory.containsKey(newName)) {
                    System.out.println("An item with that name already exists.");
                    return;
                }
                inventory.remove(itemName);
                item.name = newName;
                inventory.put(newName, item);
                System.out.println("Item name updated.");
                break;
            case 2:
                double newQuantity = 0;
                boolean valid = false;
                while (!valid) {
                    System.out.print("Enter new quantity: ");
                    String qtyInput = scanner.nextLine();
                    try {
                        newQuantity = Double.parseDouble(qtyInput.trim());
                        if (newQuantity < 0) {
                            System.out.println("Quantity cannot be negative. Try again.");
                        } else {
                            valid = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity. Please enter a valid number.");
                    }
                }
                item.quantity = newQuantity;
                System.out.println("Item quantity updated.");
                break;
            case 3:
                System.out.print("Enter new quantity description: ");
                String newDesc = scanner.nextLine().trim();
                item.description = newDesc;
                System.out.println("Item quantity description updated.");
                break;
        }
    }

    private void removeItemFromInventory() {
        System.out.print("Enter the item name to remove: ");
        String itemToRemove = scanner.nextLine().trim().toLowerCase();
        while (itemToRemove.isEmpty()) {
            System.out.print("Item name cannot be empty. Please enter the item name: ");
            itemToRemove = scanner.nextLine().trim().toLowerCase();
        }
        if (inventory.containsKey(itemToRemove)) {
            System.out.print("Are you sure you want to remove '" + itemToRemove + "'? \n('y' for yes / 'n' for no) : ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                inventory.remove(itemToRemove);
                System.out.println("Item removed from inventory.");
            } else {
                System.out.println("Removal canceled.");
            }
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    private void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Item item : inventory.values()) {
                writer.write(item.name + " : " + item.quantity + " : " + item.description + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportInventoryToCSV() {
        String csvFile = "inventory_export.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("Item Name,Quantity,Quantity Description\n");
            for (Item item : inventory.values()) {
                String itemName = item.name.replace("\"", "'");
                String desc = item.description.replace("\"", "'");
                writer.write("\"" + itemName + "\"," + item.quantity + ",\"" + desc + "\"\n");
            }
            System.out.println("Inventory exported to " + csvFile);
        } catch (IOException e) {
            System.out.println("Error exporting inventory to CSV: " + e.getMessage());
        }
    }

    private boolean confirmExit() {
        System.out.print("Are you sure you want to save and exit? ('y' for yes / 'n' for no): ");
        String confirmExit = scanner.nextLine().trim().toLowerCase();
        return confirmExit.equals("y");
    }

    private int getIntInput(int min, int max) {
        int value = min - 1;
        boolean valid = false;
        while (!valid) {
            String input = scanner.nextLine();
            try {
                value = Integer.parseInt(input.trim());
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.print("Invalid choice. Please enter a number from " + min + " to " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        return value;
    }
}
