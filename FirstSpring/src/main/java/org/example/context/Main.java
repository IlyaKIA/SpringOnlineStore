package org.example.context;

import org.example.context.configuration.AppConfig;
import org.example.context.domain.Product;
import org.example.context.repository.impl.Cart;
import org.example.context.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductService productService = context.getBean("productServiceImpl", ProductService.class);
        Cart cart1 = context.getBean("cartImpl", Cart.class);
        Cart cart2 = context.getBean("cartImpl", Cart.class);
        Cart cart = cart1;

        String[] product = new String[]{"Bread", "Apple", "Orange", "Potato", "Wine"};
        for (int i = 0; i < 5; i++) {
            productService.getProducts().add(new Product(i, product[i], (i*10+5)));
        }
        for (Product p : productService.getProducts()) {
            System.out.println(p.toString());
        }

        Scanner in = new Scanner(System.in);
        System.out.println();
        String commandNote = """
                cart - to view your cart;
                add [id] - put the product in your cart;
                del [id] - to remove the product from your cart;
                products - show all products in store;
                product [id] - show the product from ID;
                switch_cart - Switch between Cart1 and Cart2;
                exit - to close the shop;
                help - show the command list.""";
        System.out.println("Input the command: \n" + commandNote);
        System.out.println("You have selected Cart1");
        while (true){
            String command;
            command = in.nextLine();
            command = command.trim().toLowerCase();
            if (command.equals("exit")) {
                break;
            } else if (command.equals("help")) {
                System.out.println("Commands: \n" + commandNote);
            } else if (command.equals("cart")) {
                viewCart(cart);
            } else if (command.equals("switch_cart")) {
                cart = switchCart(cart, cart1, cart2);
            } else if (command.equals("products")) {
                viewProducts(productService);
            } else if (command.startsWith("add")) {
                addProductToCart(command, productService, cart);
            } else if (command.startsWith("del")) {
                delProductFromCart(command, productService, cart);
            } else if (command.startsWith("product")) {
                showProductFromID(command, productService);

            } else System.out.println("Wrong command");
        }
        context.close();
        in.close();
    }

    private static Cart switchCart(Cart cart, Cart cart1, Cart cart2) {
        if(cart.equals(cart1)){
            System.out.println("You have selected Cart2");
            return cart2;
        } else {
            System.out.println("You have selected Cart1");
            return cart1;
        }
    }

    private static void viewProducts(ProductService productService) {
        for (Product p : productService.getProducts()) {
            System.out.println(p.toString());
        }
    }

    private static void showProductFromID(String command, ProductService productService) {
        command = command.replaceAll("product +", "").trim();
        try {
            for (Product p : productService.getProducts()) {
                if (p.getId() == Integer.parseInt(command)){
                    System.out.println(productService.getProduct(p.getId()));
                    return;
                }
            }
        } catch (NumberFormatException e){
            System.out.println("ID must to be a number");
        }
    }

    private static void delProductFromCart(String command, ProductService productService, Cart cart) {
        command = command.replaceAll("del +", "").trim();
        try {
            for (Product p : productService.getProducts()) {
                if (p.getId() == Integer.parseInt(command)){
                    System.out.println(cart.delProductFromCart(p.getId()));
                    return;
                }
            }
        } catch (NumberFormatException e){
            System.out.println("ID must to be a number");
        }
    }

    private static void addProductToCart(String command, ProductService productService, Cart cart) {
        command = command.replaceAll("add +", "").trim();
        try {
            for (Product p : productService.getProducts()) {
                if (p.getId() == Integer.parseInt(command)) {
                    System.out.println(cart.addProductToCart(p));
                    return;
                }
            }
        } catch (NumberFormatException e){
            System.out.println("ID must to be a number");
        }
    }

    private static void viewCart(Cart cart) {
        if (!cart.cartList().isEmpty()) {
            for (Product p : cart.cartList()) {
                System.out.println(p.toString());
            }
        } else System.out.println("Cart is empty");
    }
}
