package com.sc.therapist_appointments.domain.actors;


public class Patient {
    private String name;
    //        private List<LocalDateTime> availability;
    private String therapyType;
    private String location;
    private int criticality;

    public Patient(String name, String therapyType, String location,
                   int criticality) {
        this.name = name;
//            this.availability = availability;
        this.therapyType = therapyType;
        this.location = location;
        this.criticality = criticality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //        public List<LocalDateTime> getAvailability() {
//            return availability;
//        }
//        public void setAvailability(List<LocalDateTime> availability) {
//            this.availability = availability;
//        }
    public String getTherapyType() {
        return therapyType;
    }

    public void setTherapyType(String therapyType) {
        this.therapyType = therapyType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCriticality() {
        return criticality;
    }

    public void setCriticality(int criticality) {
        this.criticality = criticality;
    }


}
 
 

    
   
    
 