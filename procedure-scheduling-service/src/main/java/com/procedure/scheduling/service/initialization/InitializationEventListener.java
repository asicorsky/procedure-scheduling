package com.procedure.scheduling.service.initialization;

import com.procedure.scheduling.common.utils.DateUtils;
import com.procedure.scheduling.dto.doctor.DoctorDto;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.dto.patient.Sex;
import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.dto.study.Status;
import com.procedure.scheduling.dto.study.StudyDto;
import com.procedure.scheduling.service.doctor.DoctorService;
import com.procedure.scheduling.service.patient.PatientService;
import com.procedure.scheduling.service.room.RoomService;
import com.procedure.scheduling.service.study.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class InitializationEventListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final int ROOMS_COUNT = 15;
	private static final int PATIENTS_COUNT = 35;
	private static final int DOCTORS_COUNT = 35;
	private static final int STUDIES_COUNT = 20;

	private final RoomService roomService;
	private final PatientService patientService;
	private final DoctorService doctorService;
	private final StudyService studyService;

	@Autowired
	public InitializationEventListener(RoomService roomService, PatientService patientService, DoctorService doctorService, StudyService studyService) {

		this.roomService = roomService;
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.studyService = studyService;
	}

	private static int randomIntFromRange(int min, int max) {

		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	private <T> T randomElement(List<T> list) {

		Random random = new Random();
		return list.get(random.nextInt(list.size()));
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

		var rooms = IntStream.range(0, ROOMS_COUNT).mapToObj(idx -> roomService.addRoom(new RoomDto(null, "Room #" + String.valueOf(idx)))).collect(Collectors.toList());
		var patients = IntStream.range(0, PATIENTS_COUNT).mapToObj(idx -> patientService.addPatient(
				new PatientDto(null, "Patient " + String.valueOf(idx), idx % 2 == 0 ? Sex.Male : Sex.Female,
						DateUtils.from(randomIntFromRange(1940, 2018), randomIntFromRange(0, 11), randomIntFromRange(1, 28))))).collect(Collectors.toList());
		var doctors = IntStream.range(0, DOCTORS_COUNT).mapToObj(idx -> doctorService.addDoctor(new DoctorDto(null, "Doctor " + String.valueOf(idx)))).collect(Collectors.toList());

		var patientIds = new HashSet<Long>();
		var doctorIds = new HashSet<Long>();
		IntStream.range(0, STUDIES_COUNT).forEach(index -> {

			RoomDto room = randomElement(rooms);
			DatePair pair = getNonOverlappedDatesForRoom(room);

			PatientDto patient = randomElement(patients);
			while (patientIds.contains(patient.getId())) {
				patient = randomElement(patients);
			}
			patientIds.add(patient.getId());
			DoctorDto doctor = randomElement(doctors);
			while(doctorIds.contains(doctor.getId())) {
				doctor = randomElement(doctors);
			}
			doctorIds.add(doctor.getId());

			StudyDto dto = new StudyDto(null, "Description " + String.valueOf(index), patient, room, doctor, Status.None, pair.from, pair.to);
			studyService.addStudy(dto);

		});
	}

	//looks too complicated, but it just for testing data with non-overlapped dates
	private DatePair getNonOverlappedDatesForRoom(RoomDto room) {

		Calendar calendar = Calendar.getInstance();
		int plannedHour = randomIntFromRange(2, 19);
		int estimatedHour = randomIntFromRange(plannedHour + 1, 22);
		int plannedMinute = randomIntFromRange(0, 40);
		int estimatedMinute = randomIntFromRange(plannedMinute + 15, 60);
		Date plannedStartTime = DateUtils
				.roundQuarterHour(DateUtils.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), plannedHour, plannedMinute));
		Date estimatedEndTime = DateUtils
				.roundQuarterHour(DateUtils.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), estimatedHour, estimatedMinute));

		while (studyService.isOverlapped(plannedStartTime, estimatedEndTime, room)) {

			plannedHour = randomIntFromRange(0, 19);
			estimatedHour = randomIntFromRange(plannedHour + 1, 22);
			plannedMinute = randomIntFromRange(0, 40);
			estimatedMinute = randomIntFromRange(plannedMinute + 15, 60);
			plannedStartTime = DateUtils
					.roundQuarterHour(DateUtils.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), plannedHour, plannedMinute));
			estimatedEndTime = DateUtils.roundQuarterHour(
					DateUtils.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), estimatedHour, estimatedMinute));
		}
		return new DatePair(plannedStartTime, estimatedEndTime);
	}

	private static class DatePair {

		private final Date from;
		private final Date to;

		private DatePair(Date from, Date to) {

			this.from = from;
			this.to = to;
		}
	}
}
