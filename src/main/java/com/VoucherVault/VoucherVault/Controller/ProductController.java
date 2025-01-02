package com.VoucherVault.VoucherVault.Controller;

import com.VoucherVault.VoucherVault.Entity.Product;
import com.VoucherVault.VoucherVault.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/test")
    public String testProduct(){
        return "Test Product Api";
    }

    @PostMapping()
    public ResponseEntity<String> createProduct(@RequestBody Product product){

        System.out.println("Product Controller Called");
        String response = productService.createProduct(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/id")
    public ResponseEntity<Product> getProductById(@RequestBody Product product){
        Product response = productService.getProductById(product.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<String> updateProduct(@RequestBody Product updatedProduct){
        String response = productService.updateProduct(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteProduct(@RequestBody Product product){
        String response = productService.deleteProduct(product.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category){
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
}
