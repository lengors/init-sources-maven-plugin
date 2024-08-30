package io.github.lengors.init_sources_maven_plugin;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

import java.io.File;
import java.io.IOException;

/**
 * Unit tests for {@link InitSourcesMojo}.
 *
 * @author lengors
 */
public class InitSourcesMojoTest {
  /**
   * Tests that when both source types are skipped, the plugin does not attempt to modify the Maven project.
   *
   * @throws MojoFailureException   if there's an error in the Mojo execution.
   * @throws IllegalAccessException if there's an error accessing the Mojo's private fields.
   */
  @Test
  public void shouldSkipSettingBothSourceTypes() throws MojoFailureException, IllegalAccessException {
    final var mojo = new InitSourcesMojo();

    setSkip(mojo, SourceTypeFilter.BOTH);

    final var spiedMojo = Mockito.spy(mojo);
    spiedMojo.execute();

    Mockito
        .verify(spiedMojo, Mockito.never())
        .getMavenProject();
  }

  /**
   * Tests that both compile and test compile source roots are correctly set when specified.
   *
   * @throws MojoFailureException   if there's an error in the Mojo execution.
   * @throws IllegalAccessException if there's an error accessing the Mojo's private fields.
   */
  @Test
  public void shouldCorrectlySetBothSourceTypes() throws MojoFailureException, IllegalAccessException {
    test(Arrays.asList("target/main"), Arrays.asList("target/test"));
  }

  /**
   * Tests that both compile and test compile source roots are reset when no default source roots are specified.
   *
   * @throws MojoFailureException   if there's an error in the Mojo execution.
   * @throws IllegalAccessException if there's an error accessing the Mojo's private fields.
   */
  @Test
  public void shouldCorrectlyResetBothSourceTypes() throws MojoFailureException, IllegalAccessException {
    test(null, null);
  }

  /**
   * Tests that only the test compile source roots are reset when the compile source roots are skipped.
   *
   * @throws MojoFailureException   if there's an error in the Mojo execution.
   * @throws IllegalAccessException if there's an error accessing the Mojo's private fields.
   */
  @Test
  public void shouldCorrectlySkipSourceTypeCompile() throws MojoFailureException, IllegalAccessException {
    test(SourceType.COMPILE);
  }

  /**
   * Tests that only the compile source roots are reset when the test compile source roots are skipped.
   *
   * @throws MojoFailureException   if there's an error in the Mojo execution.
   * @throws IllegalAccessException if there's an error accessing the Mojo's private fields.
   */
  @Test
  public void shouldCorrectlySkipSourceTypeTestCompile() throws MojoFailureException, IllegalAccessException {
    test(SourceType.TEST_COMPILE);
  }

  /**
   * Tests that the plugin throws a {@link MojoFailureException} when the Maven project is not set.
   */
  @Test
  public void shouldFailOnMissingMavenProject() {
    final var mojo = new InitSourcesMojo();
    Assertions.assertThrows(MojoFailureException.class, mojo::execute);
  }

  private static List<String> setSourceRoots(
      final InitSourcesMojo mojo,
      final SourceType sourceType,
      final @Nullable List<String> defaultSourceRoots) throws IllegalAccessException {
    if (defaultSourceRoots == null) {
      return Collections.emptyList();
    }

    final var defaultFileSourceRoots = defaultSourceRoots
        .stream()
        .map(File::new)
        .toList();

    FieldUtils.writeField(mojo, switch (sourceType) {
      case COMPILE -> "compileSourceRoots";
      case TEST_COMPILE -> "testCompileSourceRoots";
    }, defaultFileSourceRoots, true);

    return defaultFileSourceRoots
        .stream()
        .map(defaultFileSourceRoot -> {
          try {
            return defaultFileSourceRoot.getCanonicalPath();
          } catch (final IOException exception) {
            throw new RuntimeException(exception);
          }
        })
        .toList();
  }

  private static void setSkip(final InitSourcesMojo mojo, final @Nullable SourceTypeFilter skip)
      throws IllegalAccessException {
    if (skip != null) {
      FieldUtils.writeField(mojo, "skip", skip, true);
    }
  }

  private static void test(
      final @Nullable List<String> defaultCompileSourceRoots,
      final @Nullable List<String> defaultTestCompileSourceRoots) throws IllegalAccessException, MojoFailureException {
    test(defaultCompileSourceRoots, defaultTestCompileSourceRoots, null);
  }

  private static void test(final SourceType sourceType) throws IllegalAccessException, MojoFailureException {
    test(null, null, sourceType);
  }

  private static void test(
      final @Nullable List<String> defaultCompileSourceRoots,
      final @Nullable List<String> defaultTestCompileSourceRoots,
      final @Nullable SourceType skip) throws IllegalAccessException, MojoFailureException {
    final var mojo = new InitSourcesMojo();
    final var mavenProject = Mockito.mock(MavenProject.class);

    final var originalCompileSourceRoots = Arrays.asList("src/main");
    final var originalTestCompileSourceRoots = Arrays.asList("src/test");

    final var compileSourceRootsContainer = new ArrayList<>(originalCompileSourceRoots);
    final var testCompileSourceRootsContainer = new ArrayList<>(Arrays.asList("src/test"));

    FieldUtils.writeField(mojo, "mavenProject", mavenProject, true);
    if (skip != null) {
      setSkip(mojo, switch (skip) {
        case COMPILE -> SourceTypeFilter.COMPILE;
        case TEST_COMPILE -> SourceTypeFilter.TEST_COMPILE;
      });
    }

    final var compileSourceRoots = setSourceRoots(mojo, SourceType.COMPILE, defaultCompileSourceRoots);
    final var testCompileSourceRoots = setSourceRoots(mojo, SourceType.TEST_COMPILE, defaultTestCompileSourceRoots);

    if (skip != SourceType.COMPILE) {
      Mockito
          .when(mavenProject.getCompileSourceRoots())
          .thenReturn(compileSourceRootsContainer);
    }
    if (skip != SourceType.TEST_COMPILE) {
      Mockito
          .when(mavenProject.getTestCompileSourceRoots())
          .thenReturn(testCompileSourceRootsContainer);
    }

    mojo.execute();

    Assertions.assertEquals(
        skip == SourceType.COMPILE ? originalCompileSourceRoots : compileSourceRoots,
        compileSourceRootsContainer);
    Assertions.assertEquals(
        skip == SourceType.TEST_COMPILE ? originalTestCompileSourceRoots : testCompileSourceRoots,
        testCompileSourceRootsContainer);
  }
}
