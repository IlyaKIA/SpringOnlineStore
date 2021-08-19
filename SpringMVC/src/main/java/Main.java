import com.example.SpringMVC.repository.impl.ProductDaoImpl;

public class Main {


    public static void main(String[] args) {
//        Flyway flyway = Flyway.configure()
//                .dataSource("jdbc:postgresql://localhost:5432/simple-app", "postgres", "postgrespass").load();
//        flyway.migrate();

        ProductDaoImpl productDaoImpl = new ProductDaoImpl();
        try {
            productDaoImpl.init();
        } finally {
            productDaoImpl.shutdown();
        }
    }


}
