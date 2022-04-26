package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.BillingAndShippingAddress;
import no.ntnu.bicycle.repository.BillingAndShippingAddressRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BillingAndShippingAddressService {
    private BillingAndShippingAddressRepository billingAndShippingAddressRepository;

    public BillingAndShippingAddressService(BillingAndShippingAddressRepository billingAndShippingAddressRepository){
        this.billingAndShippingAddressRepository = billingAndShippingAddressRepository;
    }

    public List<BillingAndShippingAddress> iterableToList(Iterable<BillingAndShippingAddress> iterable){
        List<BillingAndShippingAddress> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }
}
