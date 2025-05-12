package se.samuel.minimums.ShoppingList;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.ShoppingList.ShoppingListDto;
import se.samuel.minimums.ShoppingList.ShoppingList;


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
