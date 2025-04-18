package se.samuel.minimums.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.ChildDto;
import se.samuel.minimums.Models.Child;


@Component
public class ChildMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ChildDto ChildToChildDto(Child child) {
        return modelMapper.map(child, ChildDto.class);
    }
    public Child ChildDtoToChild(ChildDto childDto) {
        return modelMapper.map(childDto, Child.class);
    }

}


