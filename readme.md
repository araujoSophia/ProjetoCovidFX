# 🏆 Projeto Campeonato

Sistema desktop Java (Swing + SQLite) para gerenciamento de campeonatos de futebol. Este projeto adota uma arquitetura limpa, com separação de responsabilidades em pacotes específicos para entidades, repositórios, serviços e interface gráfica.

---

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

## 📌 Escopo do Projeto

Funcionalidades disponíveis no sistema:

### ✅ Cadastro de Times

- Inserir, editar e remover times
- Campos por time: `id`, `apelido`, `nome`, `pontos`, `golsMarcados`, `golsSofridos`

### ✅ Registro de Jogos

- Inserir jogos entre dois times
- Informar gols de cada um
- Atualização automática dos pontos e estatísticas dos times

### ✅ Classificação do Campeonato

- Exibição em ranking ordenado por:
  1. Pontos
  2. Saldo de gols
  3. Gols marcados
  4. Apelido (ordem alfabética)

### ✅ Persistência de Dados com SQLite

- Criação automática do banco de dados (arquivo `.db`)
- Tabela `times` gerada automaticamente se não existir
- Driver JDBC incluso (`sqlite-jdbc`)

### ✅ Interface Gráfica (Swing)

- Tela principal com ranking e botões para ações
- Diálogos modais para inserir/editar times e jogos

---

## 🗂 Estrutura de Diretórios

```text
Campeonato/
├── src/
│   ├── Main.java
│   ├── Entities/
│   │   ├── JogoEntity.java
│   │   └── TimeEntity.java
│   ├── Models/
│   │   └── TimeModel.java
│   ├── Repositories/
│   │   ├── SQLiteBaseRepository.java
│   │   └── TimeRepository.java
│   ├── Services/
│   │   └── CampeonatoService.java
│   └── Views/
│       ├── FrmPrincipal.java
│       ├── DlgInserirJogo.java
│       └── DlgTime.java
├── dist/
├── build/
├── config.bat
├── pom.xml
└── maven-settings.xml
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

---

## 📘 Licença

Este projeto é de uso **livre**, inclusive para fins comerciais ou acadêmicos, **desde que seja feita a devida atribuição ao autor**.

> Autor: **Mozar Baptista da Silva**  
> Referência: Projeto Campeonato (Java Desktop - Swing + SQLite)

---