package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

  @Mock
  RecipeReactiveRepository recipeRepository;

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
            "imagefile", "testing.txt", "text/plain", "some random text".getBytes());

    Recipe recipe =  new Recipe();
    recipe.setId(id);

    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
    when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

    ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

    // when
    imageService.saveImageFile(id, multipartFile);

    // then
    verify(recipeRepository, times(1)).save(argumentCaptor.capture());
    Recipe savedRecipe = argumentCaptor.getValue();
    Assertions.assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
  }
}
