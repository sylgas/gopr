package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Layer;
import com.springapp.mvc.repository.dao.LayerDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Paulina on 2014-07-18.
 */
@Repository
public class LayerRepository {

    private static final Logger logger = Logger.getLogger(LayerRepository.class);

    @Autowired
    LayerDao layerDao;

    public Layer saveLayer(Layer layer) {
        return layerDao.saveAndFlush(layer);
    }

    public Layer getLayerById(Long layerId) {
        return layerDao.findOne(layerId);
    }

    public Collection<Layer> getLayerByName(String name) { return layerDao.findOneByName(name); }

    public Collection<Layer> getLayerByAction(Action action) { return layerDao.findOneByAction(action); }

    public int getLayersAmount() {
        return layerDao.findAll().size();
    }

    public Collection<Layer> getLayers() { return layerDao.findAll(); }
}
