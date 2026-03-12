USE HospitalSystem
GO

INSERT INTO Patients (FirstName, LastName, DateOfBirth)
VALUES 
	('Bob', 'Jr', '2000-01-05'),
	('Sophie', 'Wood', '2004-02-06'),
	('Jack', 'Jr', '2006-02-20'),
	('Rodin', 'Nether', '2005-10-24');


INSERT INTO Doctors (FirstName, LastName, Speciality)
VALUES
	('Bob', 'Senior', 'Heart Surgeon'),
	('Adam', 'Frost', 'Lung Doctor');

INSERT INTO Rooms (RoomNr, RoomType)
VALUES
	(100, 'ICU'),
	(101, 'Surgery'),
	(102, 'Dr. Bob Office'),
	(103, 'Dr. Adam Office');

INSERT INTO Admission (PatientID, RoomID, AdmissionTime)
VALUES
	(1, 1, '2025-11-10 10:10'),
	(2, 2, '2025-11-11 12:10');

INSERT INTO Appointments (PatientID, DoctorID, RoomID, AppointmentTime)
VALUES
	(3, 1, 3, '2025-09-09 14:25'),
	(4, 2, 4, '2025-09-12 09:30');

