package cps2.project.temperature.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps2.project.temperature.Entity.Occupation;
import cps2.project.temperature.Entity.SensorID;
import cps2.project.temperature.Entity.Calendars.LessoneSchedulesData;
import cps2.project.temperature.Repository.RepLessoneSchedulesData;
import cps2.project.temperature.Repository.RepSensorID;

@RestController
@CrossOrigin
@Controller
public class OccupationRestController {

	@Autowired
	private RepLessoneSchedulesData repLessoneSchedulesData;

	@Autowired
	private RepSensorID repSensorID;

	@Transactional(readOnly=true)
	@GetMapping("/api/availability")
	public List<Occupation> GetOccupiedRooms(@RequestParam("datetime") String datetime) throws ParseException {
		// get all the rooms
		List<SensorID> sensors = repSensorID.findAll();
		Iterator<SensorID> itSense = sensors.iterator();
		List<String> roomsLabels = new ArrayList<>();
		while (itSense.hasNext()) {
			roomsLabels.add(itSense.next().getRoom());
		}

		List<LessoneSchedulesData> lessons = repLessoneSchedulesData.findAll();
		Iterator<LessoneSchedulesData> it = lessons.iterator();
		List<Occupation> rooms = new ArrayList<>();
		while (it.hasNext()) {
			LessoneSchedulesData lesson = it.next();
			Date start = lesson.getStart();
			Date stop = lesson.getStop();
			

			// convert string to date to compare
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
			Date date = format.parse(datetime);
			date = DateUtils.addHours(date, 1);

			// if the time is between start and stop, add the room to the list if it exists
			// among the room list
			if (((date.after(start)) && (date.before(stop)))) {
				Iterator<SensorID> itCompare = sensors.iterator();
				while (itCompare.hasNext()) {
					String roomLabel = itCompare.next().getRoom();
					Pattern pattern = Pattern.compile(roomLabel);
					Matcher matcher = pattern.matcher(lesson.getLocation());
					while (matcher.find()) {
						Occupation occ = new Occupation();
						occ.setId(lesson.getId());
						occ.setRoom(roomLabel);
						occ.setDesrib(lesson.getDescrib());
						occ.setStart(start);
						occ.setStop(stop);
						rooms.add(occ);
					}
				}
			}
		}
		return rooms;
	}
}
