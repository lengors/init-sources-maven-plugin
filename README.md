# Welcome to Init Sources Maven Plugin &middot; [![GitHub license](https://img.shields.io/github/license/lengors/init-sources-maven-plugin?color=blue)](https://github.com/facebook/react/blob/main/LICENSE) [![javadoc](https://javadoc.io/badge2/io.github.lengors/init-sources-maven-plugin/javadoc.svg?color=red)](https://javadoc.io/doc/io.github.lengors/init-sources-maven-plugin) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lengors_init-sources-maven-plugin&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lengors_init-sources-maven-plugin)

Welcome to **init-sources-maven-plugin**, a plugin to provide a way to reset and set both the compile source roots as well as the test-compile source roots for any maven-based project. This allows keeping the default `sourceDirectory` and `testSourceDirectory` properties of the project (usually used by IDEs to determine which files to enable intellisense capabilities for) and still allow to change the source roots, useful for setting with plugins like the [`lombok-maven-plugin`](https://anthonywhitford.com/lombok.maven/lombok-maven-plugin).

## Documentation and Resources

For detailed guides and additional information, please refer to our [GitHub Wiki](https://github.com/lengors/init-sources-maven-plugin/wiki).

If you wish to check the detailed API documentation, visit the [Javadoc](https://javadoc.io/doc/io.github.lengors/init-sources-maven-plugin) page.

## Contributing

Contributions are welcome! Please refer to our [Contribution Guidelines](./CONTRIBUTING.md) for more information on how to get involved.

## License

This project is licensed under [The Unlicense](./LICENSE), which places it in the public domain.
