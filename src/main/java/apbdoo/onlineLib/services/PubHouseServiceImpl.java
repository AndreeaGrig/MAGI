package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.PubHouse;
import apbdoo.onlineLib.repositories.PubHouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class PubHouseServiceImpl implements PubHouseService {

    PubHouseRepository pubHouseRepository;

    public PubHouseServiceImpl(PubHouseRepository pubHouseRepository) {
        this.pubHouseRepository = pubHouseRepository;
    }

    @Override
    public Set<PubHouse> getPubHouses(){
        log.info("Retrieving list of publication houses:");
        Set<PubHouse> pubHouses = new HashSet<PubHouse>();
        pubHouseRepository.findAll().iterator().forEachRemaining(pubHouses::add);
        pubHouses.forEach(pubHouse -> log.info(pubHouse.getName()));
        return pubHouses;
    }
}
