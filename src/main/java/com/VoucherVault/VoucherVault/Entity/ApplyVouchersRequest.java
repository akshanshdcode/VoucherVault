package com.VoucherVault.VoucherVault.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplyVouchersRequest {
    private List<String> voucherIds;
}
