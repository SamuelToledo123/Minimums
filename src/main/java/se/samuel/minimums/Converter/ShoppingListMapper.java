package se.samuel.minimums.Converter;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.ShoppingListDto;
import se.samuel.minimums.Models.ShoppingList;


@Component
public class ShoppingListMapper {

    @Autowired
    ModelMapper modelMapper;

    public ShoppingList toEntity(ShoppingListDto shoppingListDto) {
        return modelMapper.map(shoppingListDto, ShoppingList.class);

    }
    public ShoppingListDto toDto(ShoppingList shoppingList) {
        return modelMapper.map(shoppingList, ShoppingListDto.class);
    }





}
