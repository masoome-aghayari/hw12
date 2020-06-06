package view;

import dto.cart.Cart;
import dto.cart.CartItem;
import dto.product.Product;
import services.CartServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CartView {
    public static void printOrder(CartServices cartServices, Cart cart) {
        CartItemView cartItemView = new CartItemView();
        if (cart.getOrderedItems().isEmpty())
            System.out.println("your orderList is empty\n");
        else {
            List<CartItem> cartItems = cart.getOrderedItems();
            Comparator<CartItem> comparator = new Comparator<CartItem>() {
                @Override
                public int compare(CartItem orderItem1, CartItem orderItem2) {
                    return orderItem1.getProduct().compareTo(orderItem2.getProduct());
                }
            };
            Collections.sort(cartItems, comparator);
            cartItemView.printOrderItems(cart.getOrderedItems());
            System.out.println("total cost: " + cartServices.getTotalCost(cart) + "\n");
        }
    }

    public Cart deleteOperation(Cart cart, ArrayList<Product> products) {
        CartServices cartServices = new CartServices();
        CartItemView cartItemView = new CartItemView();

        System.out.println("1-Delete An Item\n2-Empty Order List\n3-Main Menu");
        int editItem = GetUserInputs.getInBoundDigitalInput(3);
        if (editItem == 1) {
            cartItemView.deleteAnOrderItem(cart, products);
        } else if (editItem == 2) {
            cart = cartServices.cancelOrder(cart);
            cart.setOrderedItems(new ArrayList<>());
        }
        return cart;
    }

    public void finalizeOrder(Cart cart) {
        CartServices cartServices = new CartServices();
        if (cart.getOrderedItems().isEmpty())
            System.out.println("your orderList is empty\n");
        else {
            cart.setDate();
            System.out.println("where do you wanna receive your purchase:\n1-My Account Address\n2-another Address");
            int addressItem = GetUserInputs.getInBoundDigitalInput(2);
            if (addressItem == 1)
                cart.setAddress(cart.getCustomer().getAddress());
            else
                cart.setAddress(GettingAddress.getAddress());
            cartServices.addingOrder(cart);
            cart.setOrderedItems(new ArrayList<>());
        }
    }
}