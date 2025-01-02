package com.VoucherVault.VoucherVault.Service;

import com.VoucherVault.VoucherVault.Entity.User;
import com.VoucherVault.VoucherVault.Entity.Voucher;
import com.VoucherVault.VoucherVault.Repository.UserRepo;
import com.VoucherVault.VoucherVault.Repository.VoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

    @Autowired
    VoucherRepo voucherRepo;

    @Autowired
    UserRepo userRepository;

    @Transactional
    public String createVoucher(Voucher voucher) {
        Voucher savedVoucher = voucherRepo.save(voucher);
        List<User> eligibleUsers = userRepository.findByEmailIn(voucher.getEmails());

        for(User user : eligibleUsers){
            if(!user.getVouchers().contains(savedVoucher.getId())){
                user.getVouchers().add(savedVoucher.getId());
                userRepository.save(user);
            }
        }

        return "Voucher Created Successfully";
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepo.findAll();
    }

    public Voucher getVoucherById(String id){
        Optional<Voucher> voucherOptional = voucherRepo.findById(id);
        if(voucherOptional.isPresent()){
            return voucherOptional.get();
        }
        else{
            throw new RuntimeException("Voucher not found with id: " +id);
        }
    }

    @Transactional
    public String updateVoucher(Voucher updatedVoucher){

        Optional<Voucher> existingVoucherOptional = voucherRepo.findById(updatedVoucher.getId());

        if(existingVoucherOptional.isEmpty()){
            throw new RuntimeException("Voucher not found with id: " + updatedVoucher.getId());
        }

        Voucher existingVoucher = existingVoucherOptional.get();

        List<String> removedEmails = existingVoucher.getEmails();
        removedEmails.removeAll(updatedVoucher.getEmails());

        if(!removedEmails.isEmpty()){
            List<User> usersToUpdate = userRepository.findByEmailIn(removedEmails);
            for(User user : usersToUpdate){
                if(user.getVouchers().contains(existingVoucher.getId())){
                    user.getVouchers().remove(existingVoucher.getId());
                    userRepository.save(user);
                }
            }
        }

        voucherRepo.save(updatedVoucher);

        List<String> addedEmails = updatedVoucher.getEmails();
        addedEmails.removeAll(existingVoucher.getEmails());

        if(!addedEmails.isEmpty()){
            List<User> usersToUpdate = userRepository.findByEmailIn(addedEmails);
            for(User user : usersToUpdate){
                if(!user.getVouchers().contains(updatedVoucher.getId())){
                    user.getVouchers().add(updatedVoucher.getId());
                    userRepository.save(user);
                }
            }
        }

        return "Voucher Updated Successfully";
    }

    @Transactional
    public String deleteVoucher(String id){
        Optional<Voucher> voucherOptional = voucherRepo.findById(id);
        if(voucherOptional.isEmpty()){
            throw new RuntimeException("Voucher not found with id: " + id);
        }

        Voucher voucherToDelete = voucherOptional.get();

        List<User> associatedUsers = userRepository.findByEmailIn(voucherToDelete.getEmails());
        for(User user : associatedUsers){
            if(user.getVouchers().contains(id)){
                user.getVouchers().remove(id);
                userRepository.save(user);
            }
        }
        voucherRepo.deleteById(id);

        return "Voucher Deleted Successfully";
    }
}
