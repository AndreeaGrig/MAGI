package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Favourites;

public interface FavouritesService {
     Favourites saveFavourites(Favourites favourite);
     void deleteById(Long id);
}
