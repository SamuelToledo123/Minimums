package se.samuel.minimums.Child;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.AppUser.Service.AppUserService;
import se.samuel.minimums.Child.*;
import se.samuel.minimums.AppUser.AppUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChildServiceTest {

    @Mock
    private ChildRepo repo;

    @Mock
    private AppUserService appUserService;
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
        when(mapper.toDto(child)).thenReturn(childDto);

        List<ChildDto> result = childService.getAllChildren();

        assertEquals(1, result.size());
        assertEquals("Ella", result.get(0).getName());
        verify(repo).findAll();
    }

    @Test
    void createChild() {
        AppUser user = AppUser.builder().email("test@example.com").build();

        when(appUserService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(mapper.toEntity(childDto)).thenReturn(child);
        when(repo.save(child)).thenReturn(child);
        when(mapper.toDto(child)).thenReturn(childDto);

        ChildDto result = childService.createChild(childDto, "test@example.com");

        assertNotNull(result);
        assertEquals("Ella", result.getName());
        verify(appUserService).getUserByEmail("test@example.com");
    }

    @Test
    void updateChild() {
        Child updatedEntity = Child.builder().id(1L).name("Maria").allergies("Pollen").age(12).build();
        ChildDto updatedDto = ChildDto.builder().id(1L).name("Maria").allergies("Pollen").age(12).build();

        when(repo.findById(1L)).thenReturn(Optional.of(child));
        when(repo.save(child)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);

        ChildDto result = childService.updateChild(1L, updatedDto);

        assertEquals("Maria", result.getName());
    }

    @Test
    void getChildById() {
        when(repo.findById(1L)).thenReturn(Optional.of(child));
        Optional<Child> result = childService.getChildById(1L);
        assertTrue(result.isPresent());
    }

  /*  @Test
    void deleteChild_Found() {
        Long id = 1L;
        when(repo.findById(id)).thenReturn(Optional.of(child));
        String result = childService.deleteChild(id);
        assertEquals("Child with id 1 deleted.", result);
        verify(repo).deleteById(id);
    }
 */

   /* @Test
    void deleteChild_notFound() {
        Long id = 999L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> childService.deleteChild(id));
        assertEquals("Child by id " + id + " not found.", ex.getMessage()); */
    }


