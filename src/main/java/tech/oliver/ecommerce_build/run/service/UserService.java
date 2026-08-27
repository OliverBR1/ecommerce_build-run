package tech.oliver.ecommerce_build.run.service;

import org.springframework.stereotype.Service;
import tech.oliver.ecommerce_build.run.controller.dto.CreateUserDto;
import tech.oliver.ecommerce_build.run.entities.BillingAddressEntity;
import tech.oliver.ecommerce_build.run.entities.UserEntiy;
import tech.oliver.ecommerce_build.run.repository.BillingAddressRepository;
import tech.oliver.ecommerce_build.run.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UserEntiy createUser(CreateUserDto dto){

        var billingAddress =  new BillingAddressEntity();
        billingAddress.setAddress(dto.address());
        billingAddress.setNumber(dto.number());
        billingAddress.setComplement(dto.complement());

       var savedBillingAddress = billingAddressRepository.save(billingAddress);

       var user = new UserEntiy();
       user.setFullName(dto.fullName());
       user.setBillingAddress(savedBillingAddress);

        return userRepository.save(user);
    }
}
