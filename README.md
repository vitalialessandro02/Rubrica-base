# Rubrica

Applicazione Java Swing per gestire una rubrica telefonica (contatti con
nome, cognome, indirizzo, telefono, età), con login e persistenza su file
o MySQL.

## Funzionalità
- Creazione, modifica, eliminazione dei contatti
- Login con password salvata come hash SHA-256
- Toolbar nelle finestre
- Persistenza su file singolo, cartella (un file per persona) o MySQL

## Requisiti
- Java 17
- MySQL 8 (solo per la modalità database)

## Avvio rapido
1. Decomprimi l'archivio
2. Modifica `credenziali_database.properties` (vedi sotto)
3. Esegui `schema_database.sql` su MySQL (solo modalità database)
4. Avvia `Rubrica.jar` (doppio clic o `java -jar Rubrica.jar`)
5. Primo accesso: utente `admin`, password `admin`

## Configurazione
| Parametro | Significato |
|---|---|
| modalita | `file`, `cartella` o `database` |
| host, porta | indirizzo e porta di MySQL |
| database | nome del database (`rubrica`) |
| username, password | credenziali MySQL |

## Struttura del progetto
- `rubrica.model`: Persona, Utente
- `rubrica.logica`: RubricaService, AutenticazioneService, ValidazioneException
- `rubrica.persistenza`: interfacce PersonaRepository/UtenteRepository, implementazioni
  su file, cartella e database, PersistenzaFactory, ConnessioneDB
- `rubrica.gui`: FinestraLogin, FinestraPrincipale, EditorPersona, TabellaPersoneModel

## Versioni (tag Git)
v1.0-base, v1.1-toolbar, v1.2-cartella, v1.3-login, v1.4-mysql

