package se.samuel.minimums.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.Converter.ChildMapper;
import se.samuel.minimums.Dto.ChildDto;
import se.samuel.minimums.Models.Child;
import se.samuel.minimums.Repo.ChildRepo;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChildServiceTest {

    @ExtendWith(MockitoExtension.class)
    @Mock
    private ChildRepo repo;

    @Mock
    private ChildMapper mapper;

    @InjectMocks
    private ChildService childService;

    private Child child;
    private ChildDto childDto;

    @BeforeEach
    void setUp() {
        child = Child.builder()
                .id(1L)
                .name("Ella")
                .age(10)
                .allergies("Nuts")
                .build();
        childDto = ChildDto.builder()
                .id(1L)
                .name("Ella")
                .age(10)
                .allergies("Nuts")
                .build();
    }
    @Test
    void getAllChildren() {
        when(repo.findAll()).thenReturn(List.of(child));
        when(mapper.ChildToChildDto(child)).thenReturn(childDto);

        List<ChildDto> result = childService.getAllChildren();

        assertEquals(1, result.size());
        assertEquals("Ella", result.get(0).getName());
        assertEquals("Nuts", result.get(0).getAllergies());
        assertEquals(10, result.get(0).getAge());

       //Result print
        System.out.println("Child Age: " + result.get(0).getName());
        verify(repo).findAll();
    }
    @Test
    void createChild() {

        when(mapper.ChildDtoToChild(childDto)).thenReturn(child);

        // Mock the save operation
        when(repo.save(child)).thenReturn(child);

        // Mock the mapping back from entity to DTO
        when(mapper.ChildToChildDto(child)).thenReturn(childDto);

        // Call the method
        ChildDto result = childService.createChild(childDto);

        // Verify
        assertNotNull(result);
        assertEquals("Ella", result.getName());
        assertEquals(10, result.getAge());
        assertEquals("Nuts", result.getAllergies());

        verify(mapper).ChildDtoToChild(childDto);
        verify(repo).save(child);
        verify(mapper).ChildToChildDto(child);
    }
    @Test
    void updateChild() {

        Child updatedEntity = Child.builder()
                .id(1L)
                .name("Maria")
                .allergies("Pollen")
                .age(12)
                .build();

        ChildDto expectedDto = ChildDto.builder()
                .id(1L)
                .name("Maria")
                .allergies("Pollen")
                .age(12)
                .build();

        //Find from setUp
        when(repo.findById(1L)).thenReturn(Optional.of(child));
        when(repo.save(child)).thenReturn(updatedEntity);
        when(mapper.ChildToChildDto(updatedEntity)).thenReturn(expectedDto);

        ChildDto result = childService.updateChild(1L, expectedDto);

        assertEquals("Maria", result.getName());
        assertEquals(12, result.getAge());
        assertEquals("Pollen", result.getAllergies());

        verify(repo).findById(1L);
        verify(repo).save(child);
        verify(mapper).ChildToChildDto(updatedEntity);

    }

    @Test
    void getChildById() {

        when(repo.findById(1L)).thenReturn(Optional.of(child));

        Optional<Child> result = childService.getChildById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ella", result.get().getName());
        assertEquals(10, result.get().getAge());
        assertEquals("Nuts", result.get().getAllergies());

    }
    @Test
    void deleteChild_Found() {
        Long id = 1L;
        when(repo.findById(1L)).thenReturn(Optional.of(child));

        String result = childService.deleteChild(1L);

        assertEquals("Ingredient with id 1 has been deleted.", result);
        verify(repo).deleteById(id);

    }
    @Test
    void deleteChild_notFound() {
        Long id2 = 999L;
        when(repo.findById(id2)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> childService.deleteChild(id2));
        assertTrue(ex.getMessage().contains("Child by id " + id2 + " not found."));
    }
}