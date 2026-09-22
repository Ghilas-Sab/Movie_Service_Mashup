# virtual

**Exercice 4** — **Propriétaire : Kamlia**

Service REST (Spring Boot) `VirtualMoviesInformationService` qui combine le client `tmdb` et le
client `thrift` pour produire une information de film unifiée.

À implémenter (voir section 4 du sujet) :
- `findMovieInformation(title: string): VirtualServiceMovieDTO`
- `VirtualServiceMovieDTO` : structure libre, combine les infos de `MovieInfoDto` (tmdb) et
  `MovieDto` (thrift) sans dupliquer l'information
- Utilisation de patterns factory/façade recommandée
- Codes d'erreur mappés en codes HTTP cohérents

Dépend de : `thrift`, `tmdb`
