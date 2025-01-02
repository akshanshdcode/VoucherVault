package com.VoucherVault.VoucherVault.Controller;

import com.VoucherVault.VoucherVault.Entity.ApplyVouchersRequest;
import com.VoucherVault.VoucherVault.Entity.Product;
import com.VoucherVault.VoucherVault.Entity.User;
import com.VoucherVault.VoucherVault.Entity.Voucher;
import com.VoucherVault.VoucherVault.Service.ProductService;
import com.VoucherVault.VoucherVault.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public ProductService productService;

    @GetMapping("/test")
    public String testUser(){
        return "Test User Api";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<User> getUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByUsername(authentication.getName());
        if(userOptional.isPresent()){
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/vouchers")
    public ResponseEntity<List<Voucher>> getUserVouchers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByUsername(authentication.getName());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<String> voucherIds = user.getVouchers();

            List<Voucher> vouchers = userService.getVouchersByIds(voucherIds);
            for(Voucher voucher : vouchers){
                voucher.setEmails(null);
            }
            return new ResponseEntity<>(vouchers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody Product product){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByUsername(authentication.getName());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            String productId = product.getId();
            String responseMessage = userService.addToCart(user, productId);
            if(responseMessage.equals("Product added to cart successfully")){
                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody Product product){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByUsername(authentication.getName());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            String productId = product.getId();
            String responseMessage = userService.removeFromCart(user, productId);
            if(responseMessage.equals("Product removed from cart successfully")){
                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Product>> getCartProducts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByUsername(authentication.getName());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<Product> cartProducts = userService.getCartProducts(user);
            return new ResponseEntity<>(cartProducts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cart/apply-vouchers")
    public ResponseEntity<String> applyVouchers(@RequestBody ApplyVouchersRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            double finalAmount = userService.applyVouchers(username, request.getVoucherIds());
            return new ResponseEntity<>("Vouchers applied successfully. Total amount: " + finalAmount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while applying vouchers.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
