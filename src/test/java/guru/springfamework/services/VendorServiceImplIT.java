package guru.springfamework.services;

import static org.junit.Assert.*;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
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
