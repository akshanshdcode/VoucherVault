package com.VoucherVault.VoucherVault.Repository;

import com.VoucherVault.VoucherVault.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {
}
