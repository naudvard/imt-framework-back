package imt.framework.back.imtframeworkback.presentation.rest.favorites;

import imt.framework.back.imtframeworkback.domain.models.Dish;
import imt.framework.back.imtframeworkback.domain.results.OrderDetailsRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/favorites")
public interface FavoriteResources {
    @GetMapping
    @Operation(summary = "Get user's favorites")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get user's favorites",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Dish.class)))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not valid"
            )
    })
    ResponseEntity<List<Dish>> getFavorites(@RequestParam Integer userId);

    @PutMapping
    @Operation(summary = "Update user's favorites")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update user's favorites"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not valid"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dish not found\t\nUser not found"
            )
    })
    ResponseEntity<Void> updateFavorites(@RequestParam Integer userId, @RequestParam Integer dishId);
}
