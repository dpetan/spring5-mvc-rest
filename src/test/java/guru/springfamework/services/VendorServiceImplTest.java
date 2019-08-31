package guru.springfamework.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class VendorServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Exotic Fruits Company";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
    }

    @Test
    public void getAllVendors() {
        //given
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(new Vendor(), new Vendor()));

        //when
        List<VendorDTO> vendors = vendorService.getAllVendors();

        //then
        assertEquals(2, vendors.size());
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(ID)).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        assertNotNull(vendorDTO);
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(savedDto.getName(), vendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
    }

    @Test
    public void updateVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.updateVendor(ID, vendorDTO);

        //then
        assertEquals(savedDto.getName(), vendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());

    }

    @Test
    public void deleteVendorById() {
        vendorService.deleteVendorById(ID);

        verify(vendorRepository).deleteById(anyLong());

    }
}
