# Wurst Addon Template

A template for building addons that plug into the [Wurst 7 CevAPI](https://github.com/cev-api/Wurst7-CevAPI) addon API.

## How to use

### Clone manually

```bash
git clone --depth 1 https://github.com/YOUR_NAME/wurst-addon-template your-addon-name
cd your-addon-name
rm -rf .git
git init
git add .
git commit -m "Initial commit from template"
```

## Development

### 1. Build the Wurst dev jar (first)

From your Wurst source project, run:

```bash
cd /path/to/Wurst7-master/Wurst7-master
./gradlew jar
```

This generates:

- Dev jar: `build/devlibs/Wurst-Client-...-dev.jar`
- Runtime jar: `build/libs/Wurst-Client-....jar`

### 2. Configure the Wurst dev jar

This template compiles against Wurst's dev jar. You can do either of these:

- Put the jar at `libs/wurst-dev.jar` (default path), or
- Set a custom path in `gradle.properties`:
  - `wurst_dev_jar=/absolute/or/relative/path/to/Wurst-Client-...-dev.jar`

Optional (recommended for IDE run configs):

- Put the runtime jar at `libs/wurst.jar`, or
- Set `wurst_runtime_jar=/path/to/Wurst-Client-...jar`.

You can also pass it on the command line:

```bash
./gradlew build -Pwurst_dev_jar=../Wurst7-master/build/devlibs/Wurst-Client-...-dev.jar
```

### 3. Test in IDE

- Run the `Minecraft Client` run configuration.
- This starts a client with your addon loaded.

### 4. Build

- Run `./gradlew build`
- Your addon jar will be in `build/libs`.
- Put it in your Minecraft `mods` folder alongside your CevAPI Wurst fork
  (`wurst` or `nicewurst` build).

## Important files

- `src/main/java/com/example/addon/AddonTemplate.java`
  - Addon entrypoint (`WurstAddon`)
  - Register hacks/commands here
- `src/main/java/com/example/addon/hacks/HackExample.java`
  - Example addon hack
  - Uses a custom category name (`Template`) to demonstrate addon-defined categories
- `src/main/java/com/example/addon/commands/CommandExample.java`
  - Example addon command
- `src/main/resources/fabric.mod.json`
  - Addon metadata and entrypoint
  - Uses Fabric entrypoint key: `wurst`
- `src/main/resources/wurst-addon-template.mixins.json`
  - Mixin config (empty by default)
- `build.gradle.kts`
  - Build logic and dependency setup
- `gradle/libs.versions.toml`
  - Minecraft/Fabric/Loom versions
- `gradle.properties`
  - `archives_base_name`, `maven_group`, and `wurst_dev_jar`

## Updating to newer Minecraft / Wurst versions

1. Update versions in `gradle/libs.versions.toml`.
2. Update `wurst_dev_jar` to a dev jar built for that version.
3. Refresh Gradle in your IDE.
4. Run `./gradlew build` and test in-game.

## Project structure

```text
.
|-- .github
|   `-- workflows
|-- gradle
|   |-- libs.versions.toml
|   `-- wrapper
|-- src
|   `-- main
|       |-- java
|       |   `-- com/example/addon
|       |       |-- commands/CommandExample.java
|       |       |-- hacks/HackExample.java
|       |       `-- AddonTemplate.java
|       `-- resources
|           |-- assets/template/icon.png
|           |-- wurst-addon-template.mixins.json
|           `-- fabric.mod.json
|-- build.gradle.kts
|-- gradle.properties
|-- settings.gradle.kts
`-- README.md
```
