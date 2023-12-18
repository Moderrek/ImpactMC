<div align="center">
    <h1>⚡ ImpactMC </h1>
</div>

# Impact is Announcement for 2024
Impact makes Minecraft plugins faster, safer and easily modified.  
Support with commandblock macros and community scripts.

# About The Project
Make Minecraft plugin **faster** and **safer**, save boilerplate code to focus on putting your ideas out there instead of dealing with limited APIs  
Its not only library for creating plugins but its **shared content** on Minecraft Server and compatible **scripts** in **ImpactScript** language 
## Features Included
 * Modern, fast, null safe syntax
 * **Dynamic GUI**s with editable per player elements
 * Creating dynamic runtime command **without plugin.yml**
 * Easy Configs _(.yml, .json, .ini)_
 * **Custom Items**
 * **Custom Blocks**
 * Chat/Anvil text inputs
 * Server/Players Statistics
 * Player Cache
 * **ImpactScript support**
 * Many utilities class for easier interacting with world
## Required
 * Java **17+**
# Documentation
* [Plugin Main Class]()
* [Configs]()
* General
    * [Math]()
    * [Item Builders]()
    * [Data Structures]()
    * [TextComponent]()
    * [Chat/Anvil Text Inputs]()
* Commands
  * [Dynamic commands]()
  * [Command support]()
  * [Bundled command]()
* Dynamic GUI
    * [Creating GUI]()
* Region
  * [Regions]()
* Custom
  * [Custom Items]()
  * [Custom Blocks]()
## Quick Start
 1. Import library using Maven/Gradle
    ```xml
    repo
    ```
    ```xml
    depend
    ```
 2. Create plugin main class ``extends ImpactMCPlugin``
    ```java
    public class ExamplePlugin extends ImpactMCPlugin {}
    ```
 3. Generate needed inherited functions
    ```java
    public class ExamplePlugin extends ImpactMCPlugin {
     @Override
     public void onPluginStart() {
       logger.info("Plugin enabled in {}ms", getMillisFromStart());
     }
     @Override
     public void onPluginStop() { }
    }
    ```
For a template plugin, see [ImpactTemplatePlugin]().  
Step-by-step tutorial for beginners, see [Impact Plugin Tutorial]().
# Templates
 * [Pure Plugin]()
 * [Discord BOT]()
 * [GUIs Template]()
 * [GUI Tic Tac Toe with BOT]()
# Community
## ImpactMC Plugins
## ImpactScripts Library
# Compatibility
We aim to provide support to modern Minecraft versions.
* 1.20.x
# License
Distributed under the MIT License. See ``LICENSE.md`` for more information.
# For Developers
1. Clone repository
    ```shell
    git clone https://github.com/Moderrek/ImpactMC.git
    ```
2. Enter the project directory
    ```shell
    cd ImpactMC
    ```
# Contact
Tymon Woźniak *(owner)* <[tymon.student@gmail.com](mailto:tymon.student@gmail.com)>  
Project: [https://github.com/Moderrek/ImpactMC](https://github.com/Moderrek/ImpactMC)