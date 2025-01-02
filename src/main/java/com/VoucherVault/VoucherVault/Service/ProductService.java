package com.VoucherVault.VoucherVault.Service;

import com.VoucherVault.VoucherVault.Entity.Product;
import com.VoucherVault.VoucherVault.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Transactional
    public String createProduct(Product product){
        System.out.println("Product Service Called");
        if(product.getPrice() <= 0){
            return "Invalid product data. Name and price must be provided.";
        }
        productRepo.save(product);
        return "Product created successfully with ID: " + product.getId();
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Product getProductById(String id){
        Optional<Product> productOptional = productRepo.findById(id);
        if(productOptional.isPresent()){
            return productOptional.get();
        }
        else{
            throw new RuntimeException("Product not found with id: " +id);
        }
    }

    @Transactional
    public String updateProduct(Product updatedProduct){
        Optional<Product> existingProduct = productRepo.findById(updatedProduct.getId());
        if(existingProduct.isPresent()){
            productRepo.save(updatedProduct);
            return "Product Updated Successfully!";
        }
        else{
            throw new RuntimeException("Product not found with id: " +updatedProduct.getId());
        }
    }

    @Transactional
    public String deleteProduct(String id){
        if(productRepo.existsById(id)){
            productRepo.deleteById(id);
            return "Product Deleted Successfully!";
        }
        else{
            throw new RuntimeException("Product not found with id: " +id);
        }
    }

    public List<Product> getProductsByCategory(String category){
        List<Product> products = productRepo.findAll();
        List<Product> filteredProducts = new ArrayList<>();
        for(Product product : products){
            if(product.getCategory().equalsIgnoreCase(category)){
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

}
