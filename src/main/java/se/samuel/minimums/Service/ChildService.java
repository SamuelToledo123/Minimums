package se.samuel.minimums.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.ChildMapper;
import se.samuel.minimums.Dto.ChildDto;
import se.samuel.minimums.Models.Child;
import se.samuel.minimums.Repo.ChildRepo;
import java.util.List;
import java.util.Optional;


@Service
public class ChildService {

    @Autowired
    ChildRepo childRepo;
    @Autowired
    ChildMapper childMapper;

    public List<ChildDto> getAllChildren() {
        return childRepo.findAll()
                .stream()
                .map(childMapper::ChildToChildDto).toList();

    }

    public ChildDto createChild(ChildDto childDto) {
        Child newChild = childMapper.ChildDtoToChild(childDto);
        Child savedChild = childRepo.save(newChild);
        return childMapper.ChildToChildDto(savedChild);
    }

    public Optional<Child> getChildById(Long id) {
       return childRepo.findById(id);
    }

    public ChildDto updateChild(Long id, ChildDto updatedDto) {
        Child child = childRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        child.setName(updatedDto.getName());
        child.setAge(updatedDto.getAge());
        child.setAllergies(updatedDto.getAllergies());
        Child savedChild = childRepo.save(child);
        // UPPDATERA SENARE MED RECEPT & REKOMMENDATIONER
        return childMapper.ChildToChildDto(savedChild);
    }

    public String deleteChild(Long id) {
        childRepo.findById(id).orElseThrow(() -> new RuntimeException("Child by id " + id + " not found."));
        childRepo.deleteById(id);
        return "Child with id " + id + "deleted.";
    }


}
