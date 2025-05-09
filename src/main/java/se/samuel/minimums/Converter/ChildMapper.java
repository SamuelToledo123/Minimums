package se.samuel.minimums.Converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.ChildDto;
import se.samuel.minimums.Models.Child;


@Component
@RequiredArgsConstructor
public class ChildMapper {

    private final ModelMapper modelMapper;

    public ChildDto toDto(Child child) {
        ChildDto dto = modelMapper.map(child, ChildDto.class);
        return dto;
    }

    public Child toEntity(ChildDto childDto) {
        return modelMapper.map(childDto, Child.class);
    }
}


