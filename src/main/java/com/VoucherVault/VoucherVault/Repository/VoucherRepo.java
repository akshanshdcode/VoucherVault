package com.VoucherVault.VoucherVault.Repository;

import com.VoucherVault.VoucherVault.Entity.Voucher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoucherRepo extends MongoRepository<Voucher, String> {
}
