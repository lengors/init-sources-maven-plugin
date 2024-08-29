# Welcome to Maven Java Template &middot; [![GitHub license](https://img.shields.io/github/license/lengors/maven-java-template?color=blue)](https://github.com/facebook/react/blob/main/LICENSE) [![javadoc](https://javadoc.io/badge2/io.github.lengors/maven-java-template/javadoc.svg?color=red)](https://javadoc.io/doc/io.github.lengors/maven-java-template) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lengors_maven-java-template&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lengors_maven-java-template)

Welcome to **maven-java-template**, a flexible starting point for creating Maven-based Java projects. This template is designed to streamline the setup process for new Java projects, incorporating essential plugins and dependencies to get you up and running quickly.

## Features

- **Dependencies**: Preconfigured with Lombok, Checker Framework, Mockito, and JUnit.
- **Plugins**: Includes Checkstyle, Source, Javadoc, GPG, Central Publishing, JaCoCo, Checker Framework, Jar, and Assembly.
- **Branching Strategy**: Four branches (`main`, `beta`, `alpha`, and `dev`) to manage different stages of development and release.
- **CI/CD Pipelines**: Automated checks for code style, build, testing, static code analysis, and publishing to Maven Central.

## Getting Started

#### Clone the repository

```bash
git clone https://github.com/lengors/maven-java-template.git
cd maven-java-template
```

#### Build the project

Ensure you have Maven and JDK installed. Run:

```bash
./mvnw clean install
```

#### Run tests

```bash
./mvnw clean test
```

## Usage

This template is designed to be as customizable as you need. You can modify dependencies, plugins, or other configurations to suit your specific project needs.

### Configuration

- **GPG Signing**: Ensure GPG is set up in your environment with the necessary private key. The passphrase can be set via the `MAVEN_GPG_PASSPHRASE` environment variable or directly in the `pom.xml`.
- **Maven Central Publishing**: Set your Maven Central credentials in your `settings.xml` file or via environment variables `MAVEN_CENTRAL_USERNAME` and `MAVEN_CENTRAL_PASSWORD`.
- **Publishing Releases**: The publishing action relies on [semantic-release](https://semantic-release.gitbook.io/semantic-release/) and [semantic-release-action](https://github.com/cycjimmy/semantic-release-action). The `.releaserc.yml` file defines the release pipeline and automates versioning based on semantic versioning.
- **Jar and Assembly Plugins**: The `jar` and `assembly` plugins are configured in separate Maven profiles (`jar` and `assemble`, respectively). These profiles are intended for building final applications and should be activated only if you’re creating a standalone application. For libraries, these profiles can be removed as they aren’t necessary.

## Documentation and Resources

For detailed guides and additional information, please refer to our [GitHub Wiki](https://github.com/lengors/maven-java-template/wiki).

If you wish to check the detailed API documentation, visit the [Javadoc](https://javadoc.io/doc/io.github.lengors/maven-java-template) page.

## Contributing

Contributions are welcome! Please refer to our [Contribution Guidelines](./CONTRIBUTING.md) for more information on how to get involved.

## License

This project is licensed under [The Unlicense](./LICENSE), which places it in the public domain.
