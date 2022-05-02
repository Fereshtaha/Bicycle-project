package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.service.CustomerService;
import no.ntnu.bicycle.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    CustomerService customerService;

    public ProductController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Integer id) {
        ResponseEntity<Product> response;
        Product product = productService.findOrderById(id);
        if (product != null) {
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping(value = "/addToCart", consumes = "application/json")
    public ResponseEntity<String> addProductToCart(@RequestBody String idJsonObject){
        try {
            String[] stringArray = idJsonObject.split("\"" );
            int id = Integer.parseInt(stringArray[3]);

            Product product = productService.findOrderById(id);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            if (email.equals("anonymousUser")){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }else{

                Customer customer = customerService.findCustomerByEmail(email);

                customer.addProductToShoppingCart(product);

                customerService.updateCustomer(customer.getId(), customer);

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "shopping-cart", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsInCart(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Customer customer = customerService.findCustomerByEmail(email);
            List<Product> products = customer.getShoppingCart();
            return new ResponseEntity<>(products,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}


