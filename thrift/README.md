# thrift

**Exercice 2** — **Propriétaire : Ghilas**

Service Apache Thrift qui expose les opérations du module `model`.

À implémenter (voir Figure 2 du sujet) :
- `MovieService` (interface Thrift) : `addMovie`, `findMovieByTitle`, `findMoviesByYear`
- `MovieServiceThriftImpl` : délègue au `MovieModel` du module `model`
- `MovieDto` (attention : distinct de `Movie`, volontairement incompatibles)
- `ServiceMovieNotFoundException`

Dépend de : `model`
