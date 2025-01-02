package com.VoucherVault.VoucherVault.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Document(collection = "vouchers")
public class Voucher {
    @Id
    private String id;
    @NonNull
    private String code;
    @NonNull
    private String category;
    @NonNull
    private String discountType;
    @NonNull
    private double discountValue;
    private double minimumOrderValue;
    @NonNull
    private LocalDateTime validFrom;
    @NonNull
    private LocalDateTime validTo;
    private List<String> emails;
    private int usageLimit;
}