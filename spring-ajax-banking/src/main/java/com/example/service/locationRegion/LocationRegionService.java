package com.example.service.locationRegion;

import com.example.model.LocationRegion;
import com.example.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationRegionService implements ILocationRegion{
    @Autowired
    private LocationRegionRepository locationRegionRepository;
    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocationRegion getById(Long id) {
        return null;
    }

    @Override
    public LocationRegion save(LocationRegion locationRegion) {
        return null;
    }

    @Override
    public void deleted(LocationRegion locationRegion) {

    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existByIdEqual(Long id) {
        return false;
    }
}
