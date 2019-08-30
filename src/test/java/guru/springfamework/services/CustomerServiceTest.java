package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceTest {

    public static final String FIRSTNAME = "John";
    public static final String LASTNAME = "Doe";
    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("John");
        customer1.setLastname("Doe");

        Customer customer2 = new Customer();
        customer1.setId(2L);
        customer1.setFirstname("John2");
        customer1.setLastname("Doe2");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        List<CustomerDTO> customers = customerService.getAllCustomers();

        //then
        assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerById() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        //then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals(customerDTO.getLastname(), savedDto.getLastname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }
}
