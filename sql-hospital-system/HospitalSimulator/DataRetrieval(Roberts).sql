USE HospitalSystem
GO

SELECT *
FROM VPatientAdmissions

SELECT *
FROM VPatientAppointments

SELECT *
FROM Appointments AS ap
JOIN Patients AS p ON p.PatientID = ap.PatientID
JOIN Rooms AS r ON r.RoomID = ap.RoomID
JOIN Doctors as d ON d.DoctorID = ap.DoctorID
WHERE p.FirstName = 'Jack'
ORDER BY ap.PatientID

SELECT *
FROM Admission as a
JOIN Patients AS p ON p.PatientID = a.PatientID
JOIN Rooms AS r ON r.RoomID = a.RoomID
WHERE p.FirstName = 'Bob'
ORDER BY a.PatientID



