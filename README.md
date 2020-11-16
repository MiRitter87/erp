# Dokumentation

## Ziel des Projekts
Das Projekt dient in erster Linie als Spielwiese, um verschiedene Java-Technologien einzusetzen. Dazu wird ein sehr rudimentäres ERP-System entwickelt. Es besteht aus einem Backend, das für die Datenhaltung zuständig ist und einem Frontend, das dem Benutzer eine Anwendungsoberfläche bietet.

## Verwendete Technologien und Frameworks
  -  **Java Persistence API** und **Hibernate** für das ORM (Object Relational Mapping)
  -  **HSQLDB** als Datenbank für die Datenhaltung
  -  **Bean Validation API** für die Validierung von Modellklassen
  -  **Apache CXF** für die Bereitstellung von SOAP WebServices
  -  **Jersey** für die Bereitstellung von RESTful WebServices
  -  **Spring** für die Initialisierung der SOAP WebServices
  -  **Log4J** für das Anwendungslogging in Dateien
  -  **JUnit** für die testgetriebene Entwicklung von Anwendungsteilen
  -  **Swing** für die Entwicklung der Anwendungsoberfläche
  -  **Apache Maven** als Buildsystem
  
## Projektstruktur
Das Projekt ist als Maven Multimodul Projekt aufgebaut. Das Projekt *erp* enthält die beiden Unterprojekte *backend* und *frontend*.

## Build
Im Unterordner *maven run configurations* des Parent Projekts *erp* befinden sich folgende Maven Run Configurations.

  -  **ERP (clean install).launch** Führt Unit-Tests aus und baut bei Erfolg der Tests für das Backend eine deployable .war-Datei sowie eine .-jar Datei für das Frontend.
  -  **ERP (pmd).launch** Führt eine statische Codeanalyse und eine Copy/Paste Detection durch. Das Ergebnis kann als html-Dateien in *target/site* von backend und frontend eingesehen werden.
  
## Deployment
Das Backend wird als .war-Datei auf einem Webserver deployed. Während der Entwicklung wurde Apache Tomcat verwendet.

Hibernate ist so konfiguriert, dass eine Datenbank automatisch angelegt wird, falls noch keine vorhanden ist. Tabellen für die Datenhaltung werden ebenfalls automatisch angelegt.

Das Frontend ist so konfiguriert, dass es das Backend unter folgender URL erwartet: *http://127.0.0.1:8080/backend*. Beim Deployment muss also darauf geachtet werden, dass die Anwendung unter dem Pfad **backend** deployed wird.

## Nachgenerieren von WebService Klassen
Das Frontend konsumiert SOAP-WebServices vom Backend. Dazu werden mit Hilfe des Tools **wsimport** Zugriffsklassen generiert. Diese generierten Klassen befinden sich im Frontend im Paket *frontend.generated.ws.soap*. Wenn Änderungen an der Implementierung des WebServices im Backend vorgenommen werden, dann müssen diese Zugriffsklassen neu generiert werden.

Weil kein Weg gefunden wurde, diesen Prozess per Maven zu automatisieren, wurde hier zunächst JDK1.8.0_251 installiert. In diesem JDK ist das Tool **wsimport** im Gegensatz zu neueren JDKs enthalten. Auf der Kommandozeile kann dann aus dem bin-Verzeichnis des SDKs heraus die Generierung von Klassen über folgenden Befehl angestoßen werden.

	wsimport -keep -d C:\Users\Michael\workspace\erp\frontend\src\main\java -p frontend.generated.ws.soap.employee http://127.0.0.1:8080/backend/services/soap/employeeService?wsdl
	
  -  -keep sorgt dafür, dass bereits generierte Dateien erhalten bleiben
  -  -d gibt den Ordner an, in den die Dateien generiert werden
  -  -p gibt die Paketstruktur für die generierten Dateien an
  -  Als letzter Parameter wird immer die WSDL-Datei übergeben. Diese kann lokal oder remote liegen
  
Weil die Endpunkt-URLs in den generierten Klassen enthalten sind, müssen im Frontend auch dann neue Klassen generiert werden, wenn sich die URL der WebServices ändert.

## Weitere Dokumentation
Im Frontend befindet sich im Ordner *documentation* weitere Dokumentation. Dazu zählen auch MockUps für Bedienoberflächen.