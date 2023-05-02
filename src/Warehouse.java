import java.util.ArrayList;
public class Warehouse {
    private  ArrayList<Product> products;
    public Warehouse(){
        products = new ArrayList<Product>();
    }

    public void AddProduct(Product p){
        products.add(p);
    }

    public void RemoveProduct(Product p){
        products.remove(p);
    }

    public void SearchByName(String name) {
        for(Product p : products){
            if(p.getName().equals(name)){
                System.out.println(p);
                break;
            }
            else{
                System.out.println("The Product you ar looking for is not there in the Warehouse!\nPlease check the spelling for the name of the product you are looking for");
            }
        }
    }
    public void searchById(int id){
        for(Product p : products){
            if(p.getId() == id){
                System.out.println(p);
                break;
            }
            else{
                System.out.println("The Product you ar looking for is not there in the Warehouse!\nPlease check the ID of the product you are looking for");
            }
        }
    }
    public ArrayList<Product> getProducts() {
        return products;
    }

}
