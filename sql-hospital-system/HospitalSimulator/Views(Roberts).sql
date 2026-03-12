USE HospitalSystem
GO

CREATE VIEW VPatientAdmissions AS
SELECT 
    p.FirstName AS [Patient FirstName],
    p.LastName AS [Patient LastName],
    r.RoomNr,
    r.RoomType,
    a.AdmissionTime
FROM Admission a
JOIN Patients p ON a.PatientID = p.PatientID
JOIN Rooms r ON a.RoomID = r.RoomID;

CREATE VIEW VPatientAppointments AS
SELECT 
    p.FirstName AS [Patient FirstName],
    p.LastName  AS [Patient LastName],
    d.DoctorID,
    d.FirstName AS [Doctor FirstName],
    d.LastName  AS [Doctor LastName],
    d.Speciality AS [Doctor Speciality],
    r.RoomNr,
    r.RoomType,
    ap.AppointmentTime
FROM Appointments ap
JOIN Patients p ON ap.PatientID = p.PatientID
JOIN Doctors d ON ap.DoctorID = d.DoctorID
JOIN Rooms r ON ap.RoomID = r.RoomID;
