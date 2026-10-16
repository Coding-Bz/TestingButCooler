import ch.tbz.m450.repository.Address;
import ch.tbz.m450.repository.AddressRepository;
import ch.tbz.m450.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressService addressService;
    private Address sampleAddress;

    @BeforeEach
    void setUp() {
        addressRepository = Mockito.mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);

        sampleAddress = new Address(1, 0, "Max", "Mustermann", "123456789", new Date());
    }

    @Test
    void testSaveAddress() {
        when(addressRepository.save(any(Address.class))).thenReturn(sampleAddress);

        Address saved = addressService.save(sampleAddress);

        assertNotNull(saved);
        assertEquals("Max", saved.getFirstname());
        verify(addressRepository, times(1)).save(sampleAddress);
    }

    @Test
    void testGetAllAddresses() {
        Address secondAddress = new Address(2, 0, "Erika", "Mustermann", "987654321", new Date());
        when(addressRepository.findAll()).thenReturn(Arrays.asList(sampleAddress, secondAddress));

        List<Address> addresses = addressService.getAll();

        assertEquals(2, addresses.size());
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    void testGetAddressById() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(sampleAddress));

        Optional<Address> result = addressService.getAddress(1);

        assertTrue(result.isPresent());
        assertEquals("Max", result.get().getFirstname());
    }
}