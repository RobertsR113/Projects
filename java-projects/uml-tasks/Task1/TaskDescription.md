# Task Description:
### 1. Create a UML Class Diagram for a Space Exploration Mission Management System.
### Description:
Imagine you are designing a Space Exploration Mission Management System.
You need to create a UML class diagram to represent its key components.
The system consists of the following classes: Astronaut, SpaceMission, and SpaceAgency.
Below are the details for each class:
#### 1) Astronaut Class:
Attributes:
- Id
- name
- specialization (e.g., Pilot, Engineer, Scientist)
- experienceYears
* Methods:
- assignMission(SpaceMission mission) (to assign an astronaut to a mission)

#### 2) SpaceMission Class:
* Attributes:
- Id
- missionName
- destination
- duration (in days)
- crew (a list of Astronaut objects)
* Methods:
- addCrewMember(Astronaut astronaut) (to add an astronaut to the mission crew)
- removeCrewMember(Astronaut astronaut) (to remove an astronaut from the mission
crew)
- isReady() (to check if the crew and resources are sufficient for the mission)

#### 3) SpaceAgency Class:
* Attributes:
- agencyName
- budget
- missions (a list of SpaceMission objects)
- astronauts (a list of Astronaut objects)
* Methods:
- launchMission(SpaceMission mission) (to initiate a mission)
- recruitAstronaut(Astronaut astronaut) (to recruit a new astronaut)
- terminateMission(SpaceMission mission) (to terminate an ongoing mission)

### 2. Showcase the Relationships Among Classes
#### 1) A SpaceMission can have one to many Astronauts assigned to it, and an Astronaut can
participate in multiple missions.
#### 2) A SpaceAgency can manage zero to many SpaceMissions and Astronauts, but a
SpaceMission must belong to one SpaceAgency.
#### 3) SpaceAgency recruits and manages Astronauts who participate in the missions.
