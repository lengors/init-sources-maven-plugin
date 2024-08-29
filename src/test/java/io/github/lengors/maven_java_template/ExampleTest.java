package io.github.lengors.maven_java_template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

/**
 * Example test suite for {@link Example}.
 *
 * @author lengors
 */
public class ExampleTest {
  /**
   * Exampel test for {@link Example#Example()}.
   */
  @Test
  public void shouldThrowUnsupportedOperationExceptionWhenInstantiateExample() {
    Assertions.assertThrows(UnsupportedOperationException.class, () -> {
      ReflectionUtils.newInstance(Example.class);
    });
  }

  /**
   * Exampel test for {@link Example#example()}.
   */
  @Test
  public void shouldGetHelloWorldFromExampleFunction() {
    Assertions.assertEquals("Hello, World!", Example.example());
  }

  /**
   * Exampel test for {@link Example#main(String[])}.
   */
  @Test
  public void shouldExecuteMainFunctionWithoutErrors() {
    Example.main(new String[] {});
  }
}
