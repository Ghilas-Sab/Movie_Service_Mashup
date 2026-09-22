# Movie Service Mashup

Projet d'architecture logicielle : exposer une base de films personnelle (Walter White) via plusieurs
couches de services (modèle → Thrift → TMDb → REST Spring Boot) et un client en ligne de commande.

Sujet complet : voir l'énoncé fourni (PDF "Project: Movie Service Mashup", deadline **18/12/2026**).

## Architecture générale

```
client → VirtualMoviesInformationService → ThriftMovieService → model
                                          → TMDb client (API externe)
```

## Modules Gradle

| Module    | Exercice | Rôle                                                                 |
|-----------|----------|-----------------------------------------------------------------------|
| `model`   | 1        | Domaine métier : `MovieModel`, `Movie`, `VisualisationInfo`, `MovieNotFoundException` |
| `thrift`  | 2        | Service Thrift qui expose le `model` (`MovieService`, `MovieDto`)     |
| `tmdb`    | 3        | Client REST vers l'API TMDb (`MovieInformationClient`, `MovieInfoDto`, `CharacterDto`) |
| `virtual` | 4        | Service REST Spring Boot combinant `thrift` + `tmdb` (`VirtualMoviesInformationService`) |
| `client`  | 5        | Client en ligne de commande consommant `virtual` et `thrift`          |

## Répartition de l'équipe (3 personnes)

- **Ghilas** — `model` + `thrift` (modules couplés, base du projet)
- **Anas** — `tmdb` (indépendant, aucune dépendance interne)
- **Kamlia** — `virtual` + `client` (intègre le travail de Ghilas et Anas)

Chaque module a son propre `README.md` avec le détail de ce qu'il contient et son propriétaire.

**Règle de travail** : avant de coder, valider ensemble les interfaces/DTOs de chaque module
(déjà décrits par les diagrammes de classes du sujet) pour permettre un développement en parallèle
sans blocage. Diagrammes UML **avant** le code (pas de rétro-ingénierie).

## Build

```bash
./gradlew build
```

## État

Squelette du projet multi-module (structure de base uniquement, implémentation à venir).
