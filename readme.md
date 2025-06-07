## 📋 Requisitos

- **Java JDK 24**  
  [Download oficial - Adoptium](https://adoptium.net/en-GB/download/)  
  [Download direto (Windows)](https://github.com/adoptium/temurin24-binaries/releases/download/jdk-24.0.1%2B9/OpenJDK24U-jdk_x64_windows_hotspot_24.0.1_9.zip)

- **Apache Maven 3.9+**  
  [Download oficial](https://maven.apache.org/)  
  [Download direto (ZIP)](https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip)

---

## ⚙️ Configuração

### Variáveis de ambiente (Windows)

```cmd
set JAVA_HOME=<caminho_para_o_jdk>
set PATH=%JAVA_HOME%\bin;%PATH%

set MAVEN_HOME=<caminho_para_o_maven>
set PATH=%MAVEN_HOME%\bin;%PATH%
```

### Variáveis de ambiente (Linux/macOS)

```bash
export JAVA_HOME=/caminho/para/o/jdk
export PATH=$JAVA_HOME/bin:$PATH

export MAVEN_HOME=/caminho/para/o/maven
export PATH=$MAVEN_HOME/bin:$PATH
```

---

## 🚀 Como executar o projeto

Clone o projeto:

```bash
git clone <url-do-repositório>
cd Campeonato
```

Compile e empacote com dependências:

```bash
mvn -s maven-settings.xml clean package
```

### Executar diretamente com Maven

```bash
mvn -s maven-settings.xml clean javafx:run
```

### Executar o JAR final

```bash
java -jar dist/Campeonato-1.0-shaded.jar
```

---


## 📦 Dependência JDBC do SQLite

- Driver usado: [`sqlite-jdbc`](https://github.com/xerial/sqlite-jdbc)
- Versão: `3.49.1.0`
- O Maven gerencia automaticamente essa dependência no `pom.xml`:

```xml
<dependency>
  <groupId>org.xerial</groupId>
  <artifactId>sqlite-jdbc</artifactId>
  <version>3.49.1.0</version>
</dependency>
```