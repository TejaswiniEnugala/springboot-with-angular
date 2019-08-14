package com.stackroute.muzixapp.service;

import com.stackroute.muzixapp.Exception.TrackAlreadyExistsException;
import com.stackroute.muzixapp.Exception.TrackNotFoundException;
import com.stackroute.muzixapp.domain.Track;
import com.stackroute.muzixapp.repository.TrackRepository;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackDAOImpl implements TrackDAO {
    private TrackRepository trackRepository;
@Autowired
private MongoTemplate mongoTemplate;
    @Autowired
    public TrackDAOImpl(TrackRepository trackRepository)
    {
        this.trackRepository=trackRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {

        Track savedTrack=null;
        if(!trackRepository.findById(track.getId()).isPresent())
        {
            savedTrack=trackRepository.save(track);
        }

        return savedTrack;
    }
    @Override
    public Track deleteTrack(int id){
       Track deleteTrack=null;
       trackRepository.delete(getTrackById(id));
       deleteTrack=getTrackById(id);
       return deleteTrack;
    }

    //getting all the tracks
    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public Track getTrackById(int id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Track savedTrack = mongoTemplate.findOne(query,Track.class);
        if(savedTrack == null){
            throw new TrackNotFoundException();
        }
        return savedTrack;
    }
    //updating the track by setting name and comment
    @Override
    public Track UpdateTrack(Track track) throws  TrackNotFoundException {
        Track track1=new Track();
        if(!trackRepository.findById(track.getId()).isPresent())
        {
           track1=  trackRepository.save(track);
        }
        return track1;
    }
  /* public List<Track> findByName(String name){
        return trackRepository.findByName(name);
    }*/
}