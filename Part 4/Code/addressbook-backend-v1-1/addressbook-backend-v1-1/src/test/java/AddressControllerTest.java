import ch.tbz.m450.controller.AddressController;
import ch.tbz.m450.repository.Address;
import ch.tbz.m450.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressControllerTest {

    private AddressService addressService;
    private AddressController addressController;
    private Address sampleAddress;

    @BeforeEach
    void setUp() {
        addressService = Mockito.mock(AddressService.class);
        addressController = new AddressController(addressService);

        sampleAddress = new Address(1, 0, "Max", "Mustermann", "123456", new Date());
    }

    @Test
    void testCreateAddressEndpoint() {
        when(addressService.save(sampleAddress)).thenReturn(sampleAddress);

        ResponseEntity<Address> response = addressController.createAddress(sampleAddress);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Max", response.getBody().getFirstname());
    }

    @Test
    void testGetAddressesEndpoint() {
        when(addressService.getAll()).thenReturn(Arrays.asList(sampleAddress));

        ResponseEntity<List<Address>> response = addressController.getAddresses();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAddressByIdEndpoint() {
        when(addressService.getAddress(1)).thenReturn(Optional.of(sampleAddress));

        ResponseEntity<Address> response = addressController.getAddress(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Max", response.getBody().getFirstname());
    }

    @Test
    void testGetAddressNotFoundEndpoint() {
        when(addressService.getAddress(99)).thenReturn(Optional.empty());

        ResponseEntity<Address> response = addressController.getAddress(99);

        assertEquals(404, response.getStatusCode().value());
    }
}