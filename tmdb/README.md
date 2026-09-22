# tmdb

**Exercice 3** — **Propriétaire : Anas**

Client REST vers l'API publique TheMovieDB (TMDb). Aucune dépendance aux autres modules du projet.

À implémenter (voir Figure 3 du sujet) :
- `MovieInformationClient` (interface) : `findMovieInformation(title): MovieInfoDto`
  (combine recherche film → détails → casting → bio acteur)
- `TMDbClientImpl` : implémentation concrète
- `MovieInformationClientFactory` (singleton) : `getClient()`
- `MovieInfoDto` (titre, genres, poster_path, year, liste de `CharacterDto`)
- `CharacterDto` (characterName, imgUrl, actorName, birthday, deathday, placeOfBirth)
- `MovieInfoNotFoundException`

Dépend de : aucun module interne (appelle l'API TMDb externe)
