# IDE Setup

1. Alle .idea files oder ähnliches löschen, am besten im `pr_se` Verzeichnis `git clean -fxd` ausführen 
1. IntelliJ IDEA öffnen
1. Neues Projekt öffnen
1. __Gesamten pr_se__ Ordner auswählen
1. Nach einer Weile sollte rechts unten ein PopUp mit dem Button `Configure` kommen, da einfach draufklicken und
dann akzeptieren.
1. Als letztes machts euch noch a Run-Konfiguration für die Angular App
   * Rechts oben wo de "StockmanagementApiApplication" zum starten geht klickts drauf und daun __Edit Configurations ...__
   * Dann auf de '+' links oben in dem Fenster klicken und __Gradle__ auswählen.
   * Als __Gradle project__ `pr_se:stockmanagement-app` auswählen
   * Als __Gradle task__ `run` auswählen
   * Namen vergeben wie zb `Angular App`
1. Jetzt könnts de `StockmanagementApiApplication` und de `Angular App` über de IDE starten

##### Falls des ganze langsam is meldets eich, de Angular App könnts sonst über `npm` oder de Angular CLI starten.

Komplette standalone App erzeugen die auf Port 8080 läuft:
`gradlew bootJar`

Jar unter `./stockmanagement-api/build/libs/stockmanagement-api-<version>.jar` ist das fertige Jar.