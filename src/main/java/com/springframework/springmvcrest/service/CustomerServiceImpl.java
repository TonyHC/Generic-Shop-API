package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.CustomerMapper;
import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.controller.CustomerController;
import com.springframework.springmvcrest.domain.Customer;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return savedAndReturnDTO(customerMapper.customerDTOtoCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);

        return savedAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            returnCustomerDTO.setCustomerUrl(getCustomerUrl(id));

            return returnCustomerDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO savedAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnCustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return returnCustomerDTO;
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.CUSTOMER_BASE_URL + "/" + id;
    }
}