<!-- insert url to net.aether:lib:latest of type release on maven.cheos.dev to -->
[build]: https://maven.cheos.dev/service/rest/repository/browse/maven-default/net/aether/lib/
<!-- static url to net.aether:lib on maven.cheos.dev -->
[build-snapshot]: https://maven.cheos.dev/service/rest/repository/browse/maven-default/net/aether/lib/
<!-- insert latest release url here -->
[release]: https://github.com/net-aether/AetherLib/releases
<!-- insert latest prerelease url here -->
[prerelease]: https://github.com/net-aether/AetherLib/releases/tag/0.1.1-14-SNAPSHOT
[build-shield]: https://github.com/net-aether/AetherLib/workflows/Build/badge.svg
[build-snapshot-shield]: https://github.com/net-aether/AetherLib/workflows/Build%20Snapshot/badge.svg
[release-shield]: https://img.shields.io/github/v/release/net-aether/AetherLib?label=Release&logo=github
[prerelease-shield]: https://img.shields.io/github/v/release/net-aether/AetherLib?include_prereleases&label=Pre-Release&logo=github

Note: Do **NOT** commit to any other branch than **dev**!

# AetherLib

[ ![build-shield][] ][build]
[ ![build-snapshot-shield][] ][build-snapshot]
[ ![release-shield][] ][release]
[ ![prerelease-shield][] ][prerelease]

misc. Java Library

## Download

Latest release: [ ![release-shield][] ][release]<br>
Snapshot versions can be found [here](https://maven.cheos.dev/service/rest/repository/browse/maven-default/net/aether/lib/). Latest snapshot: [ ![prerelease-shield][] ][prerelease]

Don't forget to replace **VERSION** in the snippets below with the version shown above!

**Maven**
```xml
<repository>
    <id>cheos-repo</id>
    <name>cheos-repo</name>
    <url>https://repo.cheos.dev/</url>
</repository>
```
```xml
<dependency>
    <groupId>net.aether</groupId>
    <artifactId>lib</artifactId>
    <version>VERSION</version>
</dependency>
```

**Gradle**

```gradle
repositories {
    maven {
        url "https://repo.cheos.dev/"
    }
}

dependencies {
    implementation 'net.aether:lib:VERSION'
}
```
