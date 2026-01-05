package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderInsertDTO;
import com.brenobaise.hometeq_spring.dtos.order.ProductOrderItem;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.entities.Product;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.mappers.OrderMapper;
import com.brenobaise.hometeq_spring.repositories.OrderRepository;
import com.brenobaise.hometeq_spring.repositories.ProductRepository;
import com.brenobaise.hometeq_spring.services.exceptions.OrderDoesNotExist;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Fire is the process of creating a new order, assigning the user to it, and adding all desired products to that order.
     * @param user The user assigned to this new order
     * @param order The dto containing all the product ids
     * @return {@code Order}
     */
    @Transactional()
    public OrderDTO fire(User user, @Valid OrderInsertDTO order) {


        Order newOrder = createOrder(user);


        // fetches products from the database based on the items in the cart given by the DTO
        Map<Long, Product> mappedProducts = loadProducts(order);

        BigDecimal orderTotal  = addProductsToOrder(order.getItemsInCart(), mappedProducts, newOrder);

        newOrder.setOrderTotal(orderTotal);


        newOrder = orderRepository.save(newOrder);
        return  orderMapper.toDTO(newOrder);

    }

    /**
     * Adds products to the order based on the ids in the cart.
     *
     * @param itemsInCart list of items with product IDs and quantities
     * @param products    map of productId -> Product, pre-fetched from DB
     * @param newOrder    order being populated
     * @return updated order total
     */
    private BigDecimal addProductsToOrder(List<ProductOrderItem> itemsInCart, Map<Long, Product> products,
                                          Order newOrder) {
        BigDecimal orderTotal = BigDecimal.ZERO;

        // Adds a Product to the Order based on the map of  products
        for(ProductOrderItem item: itemsInCart){
            // get the product to be added to the order  from the map
            Product product = products.get(item.getProdId());

            if(product == null){
                throw new ResourceNotFoundException(item.getProdId() );
            }

            // add the product to the order
            newOrder.addProduct(product, item.getProdQuantity());
            orderTotal = orderTotal.add(calculateLineTotal(product.getProdPrice(), item.getProdQuantity()));


        }
        return orderTotal;
    }

    /**
     * Prepares a map of products from the Order dto.
     * @param order The dto containing all the product ids
     * @return {@code Map<Long,Product>} a map with the product id and the corresponding product.
     */
    private Map<Long, Product> loadProducts(OrderInsertDTO order) {

        // creates a list of product ids based from what's in the order
        List<Long> listOfProductIds = order.getItemsInCart()
                .stream()
                .map(ProductOrderItem::getProdId)
                .toList();

        // retrieves the products using the ids.
        List<Product> fetchedProducts = fetchProducts(listOfProductIds);

        // Hash map is for the index look up to become  O(1)
        // rather than iterating over a list with a loop O(n)
        return fetchedProducts.stream()
                .collect(Collectors.toMap(Product::getProdId, product -> product));
    }

    /**
     * Creates a new Order Object
     * @param user The user assigned to this new order
     * @return {@code Order}
     */
    private static Order createOrder(User user) {
        return new Order(user, LocalDateTime.now(), "PENDING", LocalDate.now().plusDays(3) );
    }

    /**
     * Fetches all products from the database, based on a list of ids.
     * @param listOfProductIds a list of ids
     * @return {@code List<Product>}
     */
    private List<Product> fetchProducts(List<Long> listOfProductIds) {
        return productRepository.findAllByProdIds(listOfProductIds);
    }


    /**
     * Helper function to calculate the total price of all products and it's quantities.
     * @param unitPrice the value of each item
     * @param prodQuantity the number of times that item has been selected
     */
    private BigDecimal calculateLineTotal(BigDecimal unitPrice, Long prodQuantity){
        return  unitPrice.multiply(BigDecimal.valueOf(prodQuantity));
    }

    public Order findById(Long orderNo){
        return orderRepository.findById(orderNo)
                .orElseThrow(() -> new OrderDoesNotExist(orderNo));
    }
}
