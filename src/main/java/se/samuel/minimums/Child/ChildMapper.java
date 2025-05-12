package se.samuel.minimums.Child;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ChildMapper {

    private final ModelMapper modelMapper;

    public ChildDto toDto(Child child) {
        return modelMapper.map(child, ChildDto.class);
    }

    public Child toEntity(ChildDto childDto) {
        return modelMapper.map(childDto, Child.class);
    }
}


