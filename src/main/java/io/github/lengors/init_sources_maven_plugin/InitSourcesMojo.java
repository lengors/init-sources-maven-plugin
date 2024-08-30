package io.github.lengors.init_sources_maven_plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Mojo implementation for initializing source roots in a Maven project during the build process.
 * <p>
 * This Mojo resets the compile and test compile source roots to the specified values at the initialization phase of the
 * Maven lifecycle.
 * </p>
 *
 * @author lengors
 */
@Mojo(
    name = "init-sources",
    defaultPhase = LifecyclePhase.INITIALIZE,
    requiresDependencyResolution = ResolutionScope.COMPILE,
    threadSafe = true)
public class InitSourcesMojo extends AbstractMojo {
  /**
   * Determines whether to skip processing of specific source types (compile, test compile, or both).
   */
  @Parameter(property = "init-sources.skip")
  private @Nullable SourceTypeFilter skip;

  /**
   * A list of directories for the compile source roots to be set during the initialization phase.
   */
  @Parameter(property = "init-sources.compileSourceRoots")
  private @Nullable List<File> compileSourceRoots;

  /**
   * A list of directories for the test compile source roots to be set during the initialization phase.
   */
  @Parameter(property = "init-sources.testCompileSourceRoots")
  private @Nullable List<File> testCompileSourceRoots;

  /**
   * The MavenProject object representing the current Maven project, required for accessing project properties.
   */
  @Parameter(property = "project", required = true, readonly = true)
  private @MonotonicNonNull MavenProject mavenProject;

  /**
   * Executes the Mojo, resetting and setting the source roots as configured.
   * <p>
   * Skips execution based on the value of {@code skip}. If {@code skip} is set to {@code BOTH}, the execution is
   * skipped entirely. Otherwise, it processes the source types that are not skipped.
   * </p>
   *
   * @throws MojoFailureException if an error occurs during the execution.
   */
  @Override
  public void execute() throws MojoFailureException {
    if (skip == SourceTypeFilter.BOTH) {
      return;
    }

    final var loadedMavenProject = getMavenProject();
    if (skip != SourceTypeFilter.COMPILE) {
      execute(SourceType.COMPILE, loadedMavenProject);
    }
    if (skip != SourceTypeFilter.TEST_COMPILE) {
      execute(SourceType.TEST_COMPILE, loadedMavenProject);
    }
  }

  private void execute(final SourceType sourceType, final MavenProject loadedMavenProject) throws MojoFailureException {
    final var canonicalSourceRoots = getCanonicalSourceRoots(sourceType);
    final var sourceRoots = switch (sourceType) {
      case COMPILE -> loadedMavenProject.getCompileSourceRoots();
      case TEST_COMPILE -> loadedMavenProject.getTestCompileSourceRoots();
    };

    getLog().debug(String.format("Setting source paths of {type=%s} to: %s", sourceType, canonicalSourceRoots));

    sourceRoots.clear();
    sourceRoots.addAll(canonicalSourceRoots);
  }

  /**
   * Converts the configured source roots for the specified {@link SourceType} into their canonical paths.
   *
   * @param sourceType the type of source roots to process (compile or test compile).
   * @return a list of canonical paths corresponding to the source roots for the specified source type.
   * @throws MojoFailureException if an error occurs while obtaining the canonical paths.
   */
  public final List<String> getCanonicalSourceRoots(final SourceType sourceType) throws MojoFailureException {
    final var canonicalSourceRoots = new ArrayList<String>();
    for (final var sourceRoot : getSourceRoots(sourceType)) {
      final String canonicalSourceRoot;
      try {
        canonicalSourceRoot = sourceRoot.getCanonicalPath();
      } catch (final IOException exception) {
        throw new MojoFailureException("couldn't obtain source root's canonical path", exception);
      }
      canonicalSourceRoots.add(canonicalSourceRoot);
    }
    return Collections.unmodifiableList(canonicalSourceRoots);
  }

  /**
   * Returns the list of directories for compile source roots configured for this Mojo.
   *
   * @return the list of compile source root directories, or an empty list if none are configured.
   */
  public final List<File> getCompileSourceRoots() {
    return compileSourceRoots == null ? Collections.emptyList() : compileSourceRoots;
  }

  /**
   * Returns the MavenProject associated with this Mojo.
   *
   * @return the current MavenProject.
   * @throws MojoFailureException if the MavenProject is null.
   */
  public final MavenProject getMavenProject() throws MojoFailureException {
    return Optional
        .ofNullable(mavenProject)
        .orElseThrow(() -> new MojoFailureException("couldn't get mavenProject",
            new NullPointerException("mavenProject is null")));
  }

  /**
   * Returns the source type set that should be skipped.
   *
   * @return the source type set to skip, or {@code null} if none are specified.
   */
  public final @Nullable SourceTypeFilter getSkip() {
    return skip;
  }

  /**
   * Returns the list of source root directories for the specified {@link SourceType}.
   *
   * @param sourceType the type of source roots to retrieve (compile or test compile).
   * @return the list of source root directories corresponding to the specified source type.
   */
  public final List<File> getSourceRoots(final SourceType sourceType) {
    return switch (sourceType) {
      case COMPILE -> getCompileSourceRoots();
      case TEST_COMPILE -> getTestCompileSourceRoots();
    };
  }

  /**
   * Returns the list of directories for test compile source roots configured for this Mojo.
   *
   * @return the list of test compile source root directories, or an empty list if none are configured.
   */
  public final List<File> getTestCompileSourceRoots() {
    return testCompileSourceRoots == null ? Collections.emptyList() : testCompileSourceRoots;
  }
}
