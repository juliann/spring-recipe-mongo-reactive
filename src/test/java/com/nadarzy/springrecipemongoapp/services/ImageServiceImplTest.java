package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

  @Mock RecipeRepository recipeRepository;

  ImageService imageService;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    imageService = new ImageServiceImpl(recipeRepository);
  }

  @Test
  public void saveImageFile() throws Exception {
    // given
    String id = "1";
    MultipartFile multipartFile =
        new MockMultipartFile(
            "imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

    Recipe recipe = new Recipe();
    recipe.setId(id);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

    // when
    imageService.saveImageFile(id, multipartFile);

    // then
    verify(recipeRepository, times(1)).save(argumentCaptor.capture());
    Recipe savedRecipe = argumentCaptor.getValue();
    Assertions.assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
  }
}
