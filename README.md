# CommonAPI

## Informations

**Catégorie :** Library  
**Langage :** ☕ Java  
**État du projet :** 🟢 Terminé/Fonctionnel  
**Description :** API   
**Liens** : https://library.nimelia.fr

## Développeurs

**Administrator :** Zoxouu  
**Developer :** minemobs  
**External :** _Neww  
**External :** Veko  

## Dependances

**Maven**
```XML
    <repositories>
        <repository>
            <id>nimelia-repository</id>
            <name>NimeliaMC Repository</name>
            <url>http://zoxouu.me:8080/snapshots</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>fr.nimelia</groupId>
            <artifactId>common</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```
**Gradle**
```GROOVI
repositories {
    maven {
        name = 'NimeliaMC Repository'
        url = 'http://zoxouu.me:8080/snapshots'
    }
}

dependencies {
    compileOnly 'fr.nimelia:common:1.0'
}
```
## Utilisation

**Main**
```JAVA
public CommonAPI common;

public void onEnable() {
  common = new CommonAPI();
  //For Velocity
  VelocityRegister velocityRegister = new VelocityRegister(this.server, this.logger);
  velocityRegister.registerCommands(this.getClass().getPackageName() + ".commands");

  //For Bukkit
  BukkitRegister bukkitRegister = new BukkitRegister(this);
  bukkitRegister.registerCommands(this.getClass().getPackageName() + ".commands");
}
```
**Commandes**

*Velocity*
```JAVA
@CommandInfo(name = "test" , ect")
public class ExempleCommand extends VelocityPluginCommand {

    public GroupCommand() {
        super(Velocity.INSTANCE);
    }

    @Override
    public void onCommand(Invocation invocation, String[] args) {
       //Your code here
    }
}
```

*Bukkit*
```JAVA
@CommandInfo(name = "test" , ect")
public class ExempleCommand extends BukkitPluginCommand {

    public GroupCommand() {
        super(Bukkit.INSTANCE);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
       //Your code here
    }
}
```

**AccountInfo**
```JAVA
  //Exemple code
  AccountManager account = api.getAccountManager();
  Account playerAccount = account.getAccount(player.getUniqueId());
  playerAccount.setCoins(accountTarget.getCoins() + 1000);
  account.updateAccount(playerAccount);
```

## Licence

@Nimelia détient tous les droits sur le code ci-dessous, il est formellement interdit de le divulguer ou de l'utiliser à des fins personnelles.
