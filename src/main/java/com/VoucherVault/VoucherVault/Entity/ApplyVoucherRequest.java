package com.VoucherVault.VoucherVault.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplyVoucherRequest {
    private List<String> voucherIds;
}
