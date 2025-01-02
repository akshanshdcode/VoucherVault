package com.VoucherVault.VoucherVault.Service;

import com.VoucherVault.VoucherVault.Entity.Product;
import com.VoucherVault.VoucherVault.Entity.User;
import com.VoucherVault.VoucherVault.Entity.Voucher;
import com.VoucherVault.VoucherVault.Repository.ProductRepo;
import com.VoucherVault.VoucherVault.Repository.UserRepo;
import com.VoucherVault.VoucherVault.Repository.VoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private VoucherRepo voucherRepo;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private VoucherService voucherService;

    private static final String DEFAULT_ROLE = "USER";
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(DEFAULT_ROLE);
        userRepository.save(user);
    }

    public String addToCart(User user, String productId){
        if(user.getCart() == null){
            user.setCart(new ArrayList<>());
        }

        List<String> cart = user.getCart();
        cart.add(productId);
        userRepository.save(user);
        return "Product added to cart successfully";
    }

    public String removeFromCart(User user, String productId){
        List<String> cart = user.getCart();
        if(cart != null && cart.contains(productId)){
            cart.remove(productId);
            userRepository.save(user);
            return "Product removed from cart successfully";
        }
        return "Product not found in cart";
    }

    public List<Product> getCartProducts(User user){
        List<String> cartProductIds = user.getCart();
        return cartProductIds == null ? Collections.emptyList() : productRepository.findAllById(cartProductIds);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<Voucher> getVouchersByIds(List<String> voucherIds){
        return voucherRepo.findAllById(voucherIds);
    }

    public double applyVouchers(String username, List<String> voucherIds){
        User user = findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Product> cartProducts = getCartProducts(user);
        if(cartProducts.isEmpty()){
            throw new IllegalArgumentException("Cart is empty");
        }

        double totalCartBill = 0.0;

        for(Product product : cartProducts){
            System.out.println("productID: "+product.getId());
            double productPrice = product.getPrice();
            double productDiscount = 0.0;

            for(String voucherId : voucherIds){
                System.out.println("voucherID: "+voucherId);
                Voucher voucher = voucherService.getVoucherById(voucherId);

                if(isVoucherExpired(voucher)){
                    handleExpiredVoucher(voucher, user, voucherId);
                    continue;
                }

                if(product.getCategory().equalsIgnoreCase(voucher.getCategory())){
                    validateVoucherForProduct(voucher, productPrice);
                    productDiscount += applyDiscountToProduct(productPrice, voucher);
                    System.out.println("productDiscount: "+productDiscount);
                    updateVoucherAndUser(voucher, user, voucherId);
                }
            }

            totalCartBill += (productPrice-productDiscount);
        }

        return Math.max(totalCartBill, 0);
    }

    private void validateVoucherForProduct(Voucher voucher, double productPrice){
        if(voucher == null){
            throw new IllegalArgumentException("Invalid voucher");
        }

        if(productPrice<voucher.getMinimumOrderValue()){
            throw new IllegalArgumentException("Product price does not satisfy minimum order value for the voucher");
        }
    }

    private double applyDiscountToProduct(double productPrice, Voucher voucher) {
        double discount = 0.0;

        if("Percentage".equalsIgnoreCase(voucher.getDiscountType())){
            discount = productPrice * (voucher.getDiscountValue()/100);
        }
        else if("Fixed".equalsIgnoreCase(voucher.getDiscountType())){
            discount = voucher.getDiscountValue();
        }

        discount = Math.min(discount, productPrice);
        return discount;
    }

    private boolean isVoucherExpired(Voucher voucher){
        System.out.println("isVoucherExpired Called");
        System.out.println("isVoucherExpired: "+LocalDateTime.now().isAfter(voucher.getValidTo()));
        return LocalDateTime.now().isAfter(voucher.getValidTo());
    }

    private void handleExpiredVoucher(Voucher voucher, User user, String voucherId){

        System.out.println("handleExpiredVoucher Called");
        user.getVouchers().remove(voucherId);
        userRepository.save(user);

        voucher.getEmails().remove(user.getEmail());
        voucherService.updateVoucher(voucher);
    }

    private void updateVoucherAndUser(Voucher voucher, User user, String voucherId){
        user.getVouchers().remove(voucherId);
        userRepository.save(user);

        voucher.getEmails().remove(user.getEmail());
        voucherService.updateVoucher(voucher);
    }
}