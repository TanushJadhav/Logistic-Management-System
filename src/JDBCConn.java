import java.sql.*;

public class JDBCConn {
    private Connection connection;

    public JDBCConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/logistic management",
                    "root",
                    "cityzen"
            );
        } catch (ClassNotFoundException | SQLException cnf) {
            System.out.println(cnf.getMessage());
        }
    }

    public void addProduct(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO product (name, id, quantity) VALUES (?, ?, ?)");
            statement.setString(1, product.getName());
            statement.setInt(2, product.getId());
            statement.setInt(3, product.getQuantity());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewProducts() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                int quantity = resultSet.getInt("quantity");
                System.out.println("Product Name: " + name + ", ID: " + id + ", Quantity: " + quantity);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quantity = resultSet.getInt("quantity");
                System.out.println("Product Name: " + name + ", ID: " + id + ", Quantity: " + quantity);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                System.out.println("Product Name: " + name + ", ID: " + id + ", Quantity: " + quantity);
            } else {
                System.out.println("Product not found with ID: " + id);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProductWithId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product with ID " + id + " has been removed.");
            } else {
                System.out.println("Product not found with ID: " + id);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProductWithName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE name = ?");
            statement.setString(1, name);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product with Name " + name + " has been removed.");
            } else {
                System.out.println("Product not found with ID: " + name);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void reduceQuantityById(int id, int quantity) {
        try {
            // Check if the product exists in the database
            PreparedStatement selectStatement = connection.prepareStatement("SELECT quantity FROM product WHERE id = ?");
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int currentQuantity = resultSet.getInt("quantity");

                // Check if the quantity to remove is greater than the current quantity
                if (quantity > currentQuantity) {
                    System.out.println("Cannot remove more than the current quantity of the product.");
                    return;
                }

                // Update the quantity of the product in the database
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
                updateStatement.setInt(1, currentQuantity - quantity);
                updateStatement.setInt(2, id);
                updateStatement.executeUpdate();
                updateStatement.close();

                System.out.println("Product with ID " + id + " removed " + quantity + " units.");
            } else {
                System.out.println("Product not found with ID: " + id);
            }

            resultSet.close();
            selectStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addQuantityById(int id, int quantity) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE product SET quantity = quantity + ? WHERE id = ?");
            statement.setInt(1, quantity);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Product not found with ID: " + id);
            } else {
                System.out.println("Quantity updated for product with ID: " + id);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}