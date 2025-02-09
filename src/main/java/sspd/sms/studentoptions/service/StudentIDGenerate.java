package sspd.sms.studentoptions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StudentIDGenerate {

    private final StudentService studentService;

    @Autowired
    public StudentIDGenerate(StudentService studentService) {
        this.studentService = studentService;
    }

    private static final AtomicInteger _studentcounter = new AtomicInteger(1);
    private static final String prefix = "S";

    public String getStudentIDGenerate() {

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endID = getLastID();

        if (endID != null && endID.matches("^S-\\d{8}-\\d+$")) {
            String[] parts = endID.split("-");
            String endDate = parts[1];
            int endCounter = Integer.parseInt(parts[2]);

            if (currentDate.equals(endDate)) {
                _studentcounter.set(endCounter + 1);
            } else {
                _studentcounter.set(1);
            }
        } else {
            _studentcounter.set(1);
        }

        return prefix + "-" + currentDate + "-" + _studentcounter.getAndIncrement();
    }

    private String getLastID() {
        if (studentService == null || studentService.getAllTask().isEmpty()) {
            return null;
        }
        return studentService.getAllTask().getLast().getStu_id();
    }
}
