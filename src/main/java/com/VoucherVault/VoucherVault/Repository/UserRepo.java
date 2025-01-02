package com.VoucherVault.VoucherVault.Repository;

import com.VoucherVault.VoucherVault.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByEmailIn(List<String> emails);
}
