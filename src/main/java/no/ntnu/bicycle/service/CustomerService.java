package no.ntnu.bicycle.service;

import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.repository.CustomerRepository;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            //todo: Sett inn exception
        }


        return optionalCustomer.get();
    }

    public Customer findCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.get();
    }


    public boolean addNewCustomer(Customer customer) {
        boolean added = false;
        if (customer != null && customer.isValid()) {
            Customer existingCustomer = findCustomerById((long) customer.getId());
            if (existingCustomer == null) {
                customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
                customer.updateAge();
                customer.setRoleAsUser();
                customerRepository.save(customer);
                added = true;
            }
        }
        return added;
    }

    public String resetPassword(String email){
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        String generatedPassword = passwordGenerator.generatePassword(10,alphabets,digits);
        Optional<Customer> customer = customerRepository.findByEmail(email);

        if (customer.isPresent()) {
            customer.get().setPassword(new BCryptPasswordEncoder().encode(generatedPassword));
        }else{
            generatedPassword = null;
        }

        return generatedPassword;
    }

    public boolean deleteCustomer(int customerId) {
        Optional<Customer> customer = customerRepository.findById((long) customerId);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        }
        return customer.isPresent();
    }

    @Transactional
    public String updateCustomer(int customerId, Customer customer) {
     String errorMessage = null;
     Customer existingCustomer = findCustomerById((long) customerId);
     if (existingCustomer == null) {
         errorMessage = "No customer with id " + customerId + "exists";
     } else if (customer == null || !customer.isValid()) {
         errorMessage = "Invalid data";
     } else if (customer.getId() != customerId) {
         errorMessage = "Id does not match";
     }

     if (errorMessage == null) {
         customerRepository.save(customer);
     }
     return errorMessage;

    }
}
