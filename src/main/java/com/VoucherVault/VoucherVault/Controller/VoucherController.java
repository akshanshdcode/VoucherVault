package com.VoucherVault.VoucherVault.Controller;

import com.VoucherVault.VoucherVault.Entity.Voucher;
import com.VoucherVault.VoucherVault.Service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @GetMapping("/test")
    public String testVoucher(){
        return "Test Voucher Api";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestBody Voucher voucher){
        String response = voucherService.createVoucher(voucher);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Voucher>> getAllVouchers(){
        List<Voucher> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    @PostMapping("/id")
    public ResponseEntity<Voucher> getVoucherById(@RequestBody Voucher voucher){
        Voucher response = voucherService.getVoucherById(voucher.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<String> updateVoucher(@RequestBody Voucher updatedVoucher){
        String response = voucherService.updateVoucher(updatedVoucher);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteVoucher(@RequestBody Voucher voucher){
        String response = voucherService.deleteVoucher(voucher.getId());
        return ResponseEntity.ok(response);
    }
}
