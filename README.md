# Welcome to Init Sources Maven Plugin &middot; [![GitHub license](https://img.shields.io/github/license/lengors/init-sources-maven-plugin?color=blue)](https://github.com/lengors/init-sources-maven-plugin/blob/main/LICENSE) [![javadoc](https://javadoc.io/badge2/io.github.lengors/init-sources-maven-plugin/javadoc.svg?color=red)](https://javadoc.io/doc/io.github.lengors/init-sources-maven-plugin) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lengors_init-sources-maven-plugin&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lengors_init-sources-maven-plugin)

Welcome to **init-sources-maven-plugin**, a plugin that provides a way to reset and set both the compile source roots and the test-compile source roots for any Maven-based project. This allows you to keep the default `sourceDirectory` and `testSourceDirectory` properties of the project (usually used by IDEs to enable IntelliSense capabilities) while still allowing for custom source roots, which is especially useful with plugins like the [`lombok-maven-plugin`](https://anthonywhitford.com/lombok.maven/lombok-maven-plugin).

## Features

- **Reset source roots**: Easily reset your project's compile and test-compile source roots without affecting default configurations.
- **Set custom source roots**: Define new source directories for compilation or testing while maintaining existing IDE integrations.
- **Conditional skipping**: Skip setting compile or test-compile source roots based on user configurations.

## Getting Started

To use the **init-sources-maven-plugin**, add it to your Maven `pom.xml` in the `build` section:

```xml
<build>
  <plugins>
    <plugin>
      <groupId>io.github.lengors</groupId>
      <artifactId>init-sources-maven-plugin</artifactId>
      <version>1.0.0</version> <!-- replace with the latest version -->
      <executions>
          <execution>
            <id>default-initSources</id>
            <goals>
              <goal>init-sources</goal>
            </goals>
            <phase>initialize</phase> <!-- Optional -->
        </execution>
      </executions>
      <configuration>
        <compileSourceRoots>
          <compileSourceRoot>generated-sources/generated</compileSourceRoot>
        </compileSourceRoots>
        <testCompileSourceRoots>
          <testCompileSourceRoot>generated-test-sources/generated</testCompileSourceRoot>
        </testCompileSourceRoots>
        <skip>BOTH</skip> <!-- Skip both compile and test source root modification -->
      </configuration>
    </plugin>
  </plugins>
</build>
```

Replace the version with the latest available, and adjust the configuration parameters based on your project setup.

## Usage

The **init-sources-maven-plugin** can be customized via the following parameters in your `pom.xml`:

- `compileSources`: List of directories to set as the new compile source roots. Example:

```xml
<compileSourceRoots>
  <compileSourceRoot>path/to/your/sources</compileSourceRoot>
</compileSourceRoots>
```

- `testCompileSources`: List of directories to set as the new test-compile source roots. Example:

```xml
<testCompileSourceRoots>
  <testCompileSourceRoot>path/to/your/test-sources</testCompileSourceRoot>
</testCompileSourceRoots>
```

- `skip`: Controls which source roots are skipped when setting. Can be set to `COMPILE`, `TEST_COMPILE`, or `BOTH`.

Once configured, running the Maven build process will automatically apply the configured source roots:

```bash
mvn initialize
```

### Example Scenarios

1. **Using custom directories for both compile and test sources**: Specify custom source roots for both main and test source directories, allowing you to set generated or external sources as the project’s root.

```xml
<compileSourceRoots>
  <compileSourceRoot>generated/main/java</compileSourceRoot>
</compileSourceRoots>
<testCompileSourceRoots>
  <testCompileSourceRoot>generated/test/java</testCompileSourceRoot>
</testCompileSourceRoots>
```

2. **Skipping specific source modifications**: If you only want to modify one of the source roots (e.g., just compile), you can skip the other:

```xml
<skip>TEST_COMPILE</skip>
```

## Documentation and Resources

For detailed guides and additional information, please refer to our [GitHub Wiki](https://github.com/lengors/init-sources-maven-plugin/wiki).

If you wish to check the detailed API documentation, visit the [Javadoc](https://javadoc.io/doc/io.github.lengors/init-sources-maven-plugin) page.

## Contributing

Contributions are welcome! Please refer to our [Contribution Guidelines](./CONTRIBUTING.md) for more information on how to get involved.

## License

This project is licensed under [The Unlicense](./LICENSE), which places it in the public domain.
