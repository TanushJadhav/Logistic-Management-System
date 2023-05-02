import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Warehouse wh = new Warehouse();
        JDBCConn db = new JDBCConn();

        int ch = 0;

        while(ch <= 8) {
            System.out.println("\nMenu:");
            System.out.println("\t1. Add product");
            System.out.println("\t2. View products");
            System.out.println("\t3. Add Quantity of Product");
            System.out.println("\t4. Reduce Quantity of Product");
            System.out.println("\t5. Remove Product with ID");
            System.out.println("\t6. Remove Product with Name");
            System.out.println("\t7. Search by name");
            System.out.println("\t8. Search by ID");
            System.out.println("\t9. Exit\n");

            System.out.println("Enter your choice: ");
            ch = scanner.nextInt();

            switch (ch) {
                case 1:
                    System.out.println("\nEnter Product Details:");
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    if (name.matches(".*\\d.*")) {
                        try {
                            throw new InvalidProductNameException("Product name cannot contain numbers!");
                        } catch (InvalidProductNameException ipne) {
                            System.out.println("Error: " + ipne.getMessage());
                            break;
                        }
                    }
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int quant = scanner.nextInt();
                    try {
                        if (quant < 1) {
                            throw new InvalidQuantityException("Quantity must be greater than 0!");
                        }
                        Product p = new Product(name, id, quant);
                        wh.AddProduct(p);
                        db.addProduct(p);
                        System.out.println("\n");
                    } catch (InvalidQuantityException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                case 2:
                    System.out.println("\nEntered Items In Inventory: ");
                    for (Product product : wh.getProducts()) {
                        System.out.println(product);
                    }
                    db.viewProducts();
                    break;

                case 3:
                    System.out.println("\nEnter Product Id: ");
                    int product_id = scanner.nextInt();
                    System.out.println("Enter Quantity to Add: ");
                    int qt = scanner.nextInt();
                    db.addQuantityById(product_id, qt);
                    break;

                case 4:
                    System.out.println("\nEnter Product Id: ");
                    int prod_id = scanner.nextInt();
                    System.out.println("Enter Quantity to reduce: ");
                    int q = scanner.nextInt();
                    db.reduceQuantityById(prod_id, q);
                    break;

                case 5:
                    System.out.println("\nEnter Product Id to Remove: ");
                    int findId = scanner.nextInt();
                    db.removeProductWithId(findId);
                    break;

                case 6:
                    System.out.println("\nEnter Product Name to Remove: ");
                    String findName = scanner.next();
                    if (findName.matches(".*\\d.*")) {
                        try {
                            throw new InvalidProductNameException("Product name cannot contain numbers!");
                        } catch (InvalidProductNameException ipne) {
                            System.out.println("Error: " + ipne.getMessage());
                            break;
                        }
                    }
                    db.removeProductWithName(findName);
                    break;

                case 7:
                    System.out.print("\nEnter Name to search the product: ");
                    String searchName = scanner.next();
                    if (searchName.matches(".*\\d.*")) {
                        try {
                            throw new InvalidProductNameException("Product name cannot contain numbers!");
                        } catch (InvalidProductNameException ipne) {
                            System.out.println("Error: " + ipne.getMessage());
                            break;
                        }
                    }
//                    wh.SearchByName(searchName);
                    db.searchByName(searchName);
                    break;

                case 8:
                    System.out.println("\nEnter Product Id to search: ");
                    int searchId = scanner.nextInt();
//                    wh.searchById(searchId);
                    db.searchById(searchId);
                    break;
            }
        }
        db.close();
    }
}