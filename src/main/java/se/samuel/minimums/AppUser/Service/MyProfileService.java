package se.samuel.minimums.AppUser.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Child.ChildMapper;
import se.samuel.minimums.MealPlan.MealPlanMapper;
import se.samuel.minimums.Child.ChildDto;
import se.samuel.minimums.MealPlan.MealPlanDto;
import se.samuel.minimums.AppUser.Dto.MyProfileDto;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.AppUser.AppUserRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MyProfileService {

    private final AppUserRepo appUserRepo;
    private final ChildMapper childMapper;
    private final MealPlanMapper mealPlanMapper;

    public MyProfileDto getProfile(String email) {
        AppUser user = appUserRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ChildDto> children = user.getChildren().stream()
                .map(childMapper::toDto)
                .toList();

        List<MealPlanDto> mealPlans = user.getMealPlans().stream()
                .map(mealPlanMapper::toDto)
                .toList();

        return MyProfileDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .children(children)
                .mealPlans(mealPlans)
                .build();
    }
}