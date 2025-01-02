package com.VoucherVault.VoucherVault.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Setter
@Getter
public class Product {

    @Id
    private String id;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private double price;
    private String category;
    private int stock;
}
