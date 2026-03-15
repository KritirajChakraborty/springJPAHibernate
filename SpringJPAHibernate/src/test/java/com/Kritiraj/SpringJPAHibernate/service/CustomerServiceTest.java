    package com.Kritiraj.SpringJPAHibernate.service;

    import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
    import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
    import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerUpdateRequest;
    import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
    import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
    import com.Kritiraj.SpringJPAHibernate.model.Customer;
    import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
    import io.micrometer.core.instrument.Counter;
    import io.micrometer.core.instrument.MeterRegistry;
    import org.junit.jupiter.api.*;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.junit.jupiter.params.ParameterizedTest;
    import org.junit.jupiter.params.provider.ValueSource;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;

    import java.util.Optional;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;


    @ExtendWith(MockitoExtension.class)
    public class CustomerServiceTest {

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private MeterRegistry meterRegistry;

        @Mock
        private Counter counter;

        @InjectMocks
        private CustomerService customerService;

        @BeforeAll
        public static void initBeforeAll() {
            System.out.println("Before all called!");
        }
        @BeforeEach
        public void initBeforeEach() {
            System.out.println("Before Each called!");
        }

        @Test
        @DisplayName("Test for adding customer successful")
        @Tag("post")
        void addCustomer_shouldReturnCustomerResponse_whenSaved() {

            CustomerRequest request = new CustomerRequest();
            request.setName("Kriti");
            request.setAge(25);
            request.setEmail("k@gmail.com");
            request.setGender(Gender.MALE);

            Customer customer = new Customer();
            customer.setCustomerId(1);
            customer.setName("Kriti");
            customer.setAge(25);
            customer.setGender(Gender.MALE);
            customer.setEmail("k@gmail.com");


            when(customerRepository.save(any(Customer.class))).thenReturn(customer);
            when(meterRegistry.counter("custom.metric.numberOfCustomer")).thenReturn(counter);

            CustomerResponse response = customerService.addCustomer(request);

            assertNotNull(response);
            assertEquals(customer.getName(),response.getName());
            assertEquals(customer.getAge(),response.getAge());
            assertEquals(customer.getEmail(),response.getEmail());

            verify(customerRepository,times(1)).save(any(Customer.class));
            verify(meterRegistry,times(1)).counter("custom.metric.numberOfCustomer");
            verify(counter,times(1)).increment();
        }

        @Test
        @DisplayName("Test for adding customer failure")
        void addCustomer_shouldThrowException_whenCustomerFailedToSave() {
            CustomerRequest request = new CustomerRequest();
            request.setName("Kriti");
            request.setAge(25);
            request.setEmail("k@gmail.com");
            request.setGender(Gender.MALE);

            Customer customer = new Customer();
            customer.setCustomerId(1);
            customer.setName("Kriti");
            customer.setAge(25);
            customer.setGender(Gender.MALE);
            customer.setEmail("k@gmail.com");

            when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("Database error"));

            Exception exception = assertThrows(RuntimeException.class, ()->customerService.addCustomer((request)));

            assertEquals("Database error", exception.getMessage());

            verify(customerRepository,times(1)).save(any(Customer.class));
            verify(meterRegistry, never()).counter("custom.metric.numberOfCustomer");
            verify(counter,never()).increment();
        }

        @ParameterizedTest
        @ValueSource(ints = {1})
        @DisplayName("Test for updating customer when successful")
        void updateCustomer_shouldReturnCustomerResponse_whenSuccessful(int customerId) {
            CustomerUpdateRequest customerUpdateRequest = CustomerUpdateRequest.builder()
                    .name("Raju")
                    .age(25)
                    .email("raju@g.com")
                    .build();

            Customer expectedCustomer = Customer.builder()
                    .name("Raju")
                    .age(25)
                    .email("raju@g.com")
                    .gender(Gender.MALE)
                    .build();

            when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));
            when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

            CustomerResponse retrievedCustomerResponse = customerService.updateCustomerData(customerUpdateRequest,customerId);

            assertNotNull(retrievedCustomerResponse);
            assertEquals(expectedCustomer.getName(), retrievedCustomerResponse.getName());
            assertEquals(expectedCustomer.getEmail(), retrievedCustomerResponse.getEmail());
            assertEquals(expectedCustomer.getAge(), retrievedCustomerResponse.getAge());

            verify(customerRepository,times(1)).findById(customerId);
            verify(customerRepository,times(1)).save(any(Customer.class));

        }

        @ParameterizedTest
        @ValueSource(ints = {1})
        @DisplayName("Test for updating customer when failure")
        void updateCustomer_shouldThrowException_whenFailure(int customerId) {
            CustomerUpdateRequest customerUpdateRequest = CustomerUpdateRequest.builder()
                    .name("Raju")
                    .age(25)
                    .email("raju@g.com")
                    .build();

            when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,()-> customerService.updateCustomerData(customerUpdateRequest,customerId));

            assertEquals("Invalid Customer ID! Cant update!",exception.getMessage());

            verify(customerRepository,times(1)).findById(customerId);
            verify(customerRepository,never()).save(any(Customer.class));

        }


        @AfterEach
        public void afterAll() {
            System.out.println("After each called");
        }
        @AfterAll
        static void afterEach() {
            System.out.println("After All called");
        }
    }
