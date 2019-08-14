package com.stackroute.muzixapp.controller;
import com.stackroute.muzixapp.Exception.TrackAlreadyExistsException;
import com.stackroute.muzixapp.Exception.TrackNotFoundException;
import com.stackroute.muzixapp.service.TrackDAO;
import com.stackroute.muzixapp.domain.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "api/v1")
public class TrackController {

	private TrackDAO trackDAO;

	@Autowired
	public TrackController(TrackDAO trackDAO)
	{
		this.trackDAO=trackDAO;
	}
	//to save the track
	@PostMapping("track")
	public ResponseEntity<?> saveUser(@RequestBody Track track) throws TrackAlreadyExistsException{
		ResponseEntity responseEntity;
		trackDAO.saveTrack(track);
		responseEntity = new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
		return responseEntity;

	}
	@DeleteMapping("track/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id")int trackId) throws TrackNotFoundException {
		ResponseEntity responseEntity;
		responseEntity=new ResponseEntity<Track>(trackDAO.deleteTrack(trackId),HttpStatus.OK);
		return responseEntity;

	}
	//to get all the tracks
	@GetMapping("track")
	public ResponseEntity<?> getAllUsers(){
		return new ResponseEntity<List<Track>>(trackDAO.getAllTracks(),HttpStatus.OK);
	}
		//to update a track
	@PutMapping("track/{id}")
	public ResponseEntity<?> updateTrack(@RequestBody Track track) throws TrackNotFoundException
	{
		ResponseEntity responseEntity;
		trackDAO.UpdateTrack(track);
		responseEntity = new ResponseEntity<Track>(track, HttpStatus.OK);
		return responseEntity;
	}
/*	@GetMapping("/retrieve/{name}")
	public ResponseEntity<?> findByName(@PathVariable(value = "name") String name){
		ResponseEntity responseEntity;
		try {
			responseEntity= new ResponseEntity<List>(trackDAO.findByName(name),HttpStatus.OK);
		}
		catch (Exception e){
			responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return responseEntity;
	}*/
}