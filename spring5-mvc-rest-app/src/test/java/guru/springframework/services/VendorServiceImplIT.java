package guru.springframework.services;

import static org.junit.Assert.*;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void patchVendorUpdateName() {
        //given
        String updatedName = "some updated name";
        Long id = findVendorIdValue();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        Vendor vendor = vendorRepository.getOne(id);
        assertNotNull(vendor);

        //when
        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.getOne(id);

        assertNotNull(updatedVendor);
        assertEquals(updatedName, updatedVendor.getName());

    }

    private Long findVendorIdValue() {
        return vendorRepository.findAll().get(0).getId();
    }
}
