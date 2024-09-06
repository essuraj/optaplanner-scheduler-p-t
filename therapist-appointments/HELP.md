Open in intelliJ and run the main Application class

```
 ./gradlew bootRun
 ```

to see if it works

 http://localhost:8080/swagger-ui/index.html

------------
*POC plan*
--------
**POC Development for Appointment Scheduler**

**Overview:**
Sanctum is looking to develop an advanced appointment scheduler that can efficiently manage scheduling between patients and therapists. The scheduler will cater to multiple patients and therapists, ensuring that appointments are optimized based on specific constraints. For the Proof of Concept (POC), the focus will be on selecting a patient and suggesting the most suitable therapist based on the constraints outlined below.

**Constraints:**

1.  **Patient Factors:**

-   **Availability:** The patient's available times for therapy sessions.
-   **Type of Therapy Needed:** The specific therapy required by the patient, such as Speech Therapy, Occupational Therapy, or Physical Therapy.
-   **Location:** The geographical location of the patient.
-   **Criticality of Therapy:** The urgency or importance of the therapy, rated on a scale from 1 to 5, with 5 being the most critical.

3.  **Therapist Factors:**

-   **Availability:** The therapist's available times for conducting therapy sessions.
-   **Location:** The geographical location of the therapist.
-   **Proximity to Patient:** The relative distance between the therapist and the patient.
-   **Skills:** The therapist's expertise in specific types of therapy, ensuring they are qualified to address the patient's needs.

**Objective of the POC:**The primary goal of this POC is to develop a system that can determine the next available appointment for a patient by considering all the constraints mentioned above. The system should suggest the most suitable therapist and time slot based on the patient's needs and the therapist's qualifications and availability.

**Expected Outcomes:**

-   A working prototype that can accept a patient’s details and recommend the best available therapist and appointment time.
-   A demonstration of how the system prioritizes and matches patients with therapists based on critical factors like therapy type, availability, and location.