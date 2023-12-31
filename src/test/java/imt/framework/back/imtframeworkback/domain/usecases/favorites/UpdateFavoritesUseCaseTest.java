package imt.framework.back.imtframeworkback.domain.usecases.favorites;

import imt.framework.back.imtframeworkback.core.errors.DishNotFoundException;
import imt.framework.back.imtframeworkback.core.errors.UserNotFoundException;
import imt.framework.back.imtframeworkback.core.errors.UserNotValidException;
import imt.framework.back.imtframeworkback.data.services.DishService;
import imt.framework.back.imtframeworkback.data.services.FavoriteService;
import imt.framework.back.imtframeworkback.data.services.TokenService;
import imt.framework.back.imtframeworkback.data.services.UserService;
import imt.framework.back.imtframeworkback.domain.models.Dish;
import imt.framework.back.imtframeworkback.domain.models.Favorite;
import imt.framework.back.imtframeworkback.domain.models.User;
import imt.framework.back.imtframeworkback.domain.requests.UpdateFavoritesReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateFavoritesUseCaseTest {
    @InjectMocks
    private UpdateFavoritesUseCase updateFavoritesUseCase;

    @Mock
    private FavoriteService favoriteService;
    @Mock
    private UserService userService;
    @Mock
    private DishService dishService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void updateFavoritesWithoutUserShouldThrowUserNotValidException() {
        Integer userId = 0;
        UpdateFavoritesReq updateFavoritesReq = UpdateFavoritesReq.builder()
                .userId(userId)
                .dishId(0)
                .build();

        try (MockedStatic<TokenService> mocked = Mockito.mockStatic(TokenService.class)) {
            mocked.when(() -> TokenService.isUserValid(userId)).thenReturn(false);

            assertThrows(UserNotValidException.class, () -> updateFavoritesUseCase.command(updateFavoritesReq));
        }
    }

    @Test
    public void updateFavoritesWithoutUserShouldThrowUserNotFoundException() {
        Integer userId = 0;
        UpdateFavoritesReq updateFavoritesReq = UpdateFavoritesReq.builder()
                .userId(userId)
                .dishId(0)
                .build();

        try (MockedStatic<TokenService> mocked = Mockito.mockStatic(TokenService.class)) {
            mocked.when(() -> TokenService.isUserValid(userId)).thenReturn(true);
            Mockito.when(userService.findById(userId)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> updateFavoritesUseCase.command(updateFavoritesReq));
        }
    }

    @Test
    public void updateFavoritesWithoutDishShouldThrowDishNotFoundException() {
        Integer userId = 0;
        User user = User.builder().id(userId).build();
        Integer dishId = 0;
        UpdateFavoritesReq updateFavoritesReq = UpdateFavoritesReq.builder()
                .userId(userId)
                .dishId(0)
                .build();

        try (MockedStatic<TokenService> mocked = Mockito.mockStatic(TokenService.class)) {
            mocked.when(() -> TokenService.isUserValid(userId)).thenReturn(true);
            Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
            Mockito.when(dishService.findById(dishId)).thenReturn(Optional.empty());

            assertThrows(DishNotFoundException.class, () -> updateFavoritesUseCase.command(updateFavoritesReq));
        }
    }

    @Test
    public void updateFavoritesShouldRemoveIt() {
        Integer userId = 0;
        User user = User.builder().id(userId).build();
        Integer dishId = 0;
        Dish dish = Dish.builder().id(dishId).build();
        Favorite favorite = Favorite.builder().id(0).build();
        UpdateFavoritesReq updateFavoritesReq = UpdateFavoritesReq.builder()
                .userId(userId)
                .dishId(0)
                .build();

        try (MockedStatic<TokenService> mocked = Mockito.mockStatic(TokenService.class)) {
            mocked.when(() -> TokenService.isUserValid(userId)).thenReturn(true);
            Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
            Mockito.when(dishService.findById(dishId)).thenReturn(Optional.of(dish));
            Mockito.when(favoriteService.findByUserAndDish(userId, dishId)).thenReturn(Optional.of(favorite));
            Mockito.doNothing().when(favoriteService).remove(favorite);

            updateFavoritesUseCase.command(updateFavoritesReq);

            Mockito.verify(favoriteService, Mockito.times(1)).remove(favorite);
        }
    }

    @Test
    public void updateFavoritesShouldAddIt() {
        Integer userId = 0;
        User user = User.builder().id(userId).build();
        Integer dishId = 0;
        Dish dish = Dish.builder().id(dishId).build();
        Favorite favorite = Favorite.builder().user(user).dish(dish).build();
        UpdateFavoritesReq updateFavoritesReq = UpdateFavoritesReq.builder()
                .userId(userId)
                .dishId(0)
                .build();

        try (MockedStatic<TokenService> mocked = Mockito.mockStatic(TokenService.class)) {
            mocked.when(() -> TokenService.isUserValid(userId)).thenReturn(true);
            Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
            Mockito.when(dishService.findById(dishId)).thenReturn(Optional.of(dish));
            Mockito.when(favoriteService.findByUserAndDish(userId, dishId)).thenReturn(Optional.empty());
            Mockito.when(favoriteService.save(favorite)).thenReturn(favorite);

            updateFavoritesUseCase.command(updateFavoritesReq);

            Mockito.verify(favoriteService, Mockito.times(1)).save(favorite);
        }
    }
}
