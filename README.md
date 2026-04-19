# Ninja Avoid 🥷

**Ninja Avoid** és un joc d'acció i reflexos desenvolupat amb el framework [libGDX](https://libgdx.com/). L'objectiu principal és controlar un ninja i esquivar els shurikens que cauen constantment per sobreviure el màxim temps possible.

## 📝 Descripció del Projecte

Aquest projecte ha estat creat com a part de la **PT12** del mòdul de Desenvolupament d'Aplicacions Multiplataforma (DAM). Utilitza la potència de libGDX per oferir una experiència de joc fluida tant en escriptori com en dispositius Android.

### Característiques principals:
- **Mecànica de Joc:** Esquiva shurikens per mantenir les teves vides.
- **Sistema de Puntuació:** Guanya punts a mesura que sobrevius al temps.
- **Gestió de Vides:** Tens un nombre limitat de vides; si et toquen massa vegades, la partida s'acaba.
- **Interfície Gràfica:** Inclou pantalles de presentació (Splash), menú principal i pantalla de joc.
- **Àudio:** Música ambiental i efectes de so integrats.

## 🛠️ Estructura del Projecte

El projecte està dividit en diversos mòduls per facilitar la portabilitat:

- **`core`**: Conté tota la lògica del joc, actors, pantalles i gestió d'assets. És el codi compartit.
- **`android`**: Configuració específica per a l'execució en dispositius Android.
- **`lwjgl3`**: Configuració per a l'execució en escriptori (Windows, macOS, Linux).

## 🚀 Com executar el joc

Aquest projecte utilitza **Gradle**. Pots utilitzar el wrapper inclòs (`./gradlew` a Linux/macOS o `gradlew.bat` a Windows).

### Escriptori (Desktop)
Per executar el joc en el teu ordinador:
```bash
./gradlew lwjgl3:run
```

### Android
Per instal·lar i executar el joc en un dispositiu o emulador connectat:
```bash
./gradlew android:installDebug
```

### Generar executable (JAR)
Si vols generar un fitxer executable per a escriptori:
```bash
./gradlew lwjgl3:jar
```

## 🎮 Controls

- **Ratolí / Pantalla tàctil:** Arrossega el ninja o prem a la pantalla per moure'l i evitar els shurikens.
- **Inici:** Fes clic a la pantalla del menú principal per començar l'acció.

## 📦 Tecnologies utilitzades

- **Llenguatge:** Java
- **Framework:** libGDX
- **Gestor de dependències:** Gradle
- **Eines:** gdx-liftoff

---
*Desenvolupat per Arnau Figueres*
