package io.github.lengors.init_sources_maven_plugin;

/**
 * Enum representing a filter for the source types to skip during the execution of the plugin.
 *
 * @author lengors
 */
public enum SourceTypeFilter {
  /**
   * Represents the compile source roots.
   */
  COMPILE,

  /**
   * Represents the test compile source roots.
   */
  TEST_COMPILE,

  /**
   * Represents both compile and test compile source roots.
   */
  BOTH
}
