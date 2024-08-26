/**
 * Generated by @openapi-codegen
 *
 * @version v0
 */
export type Appointment = {
  /**
   * @format int64
   */
  id?: number;
  patient?: Patient;
  therapist?: Therapist;
  timeslot?: Timeslot;
};

export type LatLng = {
  /**
   * @format double
   */
  latitude?: number;
  /**
   * @format double
   */
  longitude?: number;
  /**
   * @format int64
   */
  latitudeInternal?: number;
  /**
   * @format int64
   */
  longitudeInternal?: number;
  polar?: boolean;
};

export type LocalTime = {
  /**
   * @format int32
   */
  hour?: number;
  /**
   * @format int32
   */
  minute?: number;
  /**
   * @format int32
   */
  second?: number;
  /**
   * @format int32
   */
  nano?: number;
};

export type Patient = {
  name?: string;
  availability?: Timeslot[];
  therapyType?: string;
  /**
   * @format int32
   */
  criticality?: number;
  location?: LatLng;
};

export type Schedule = {
  therapistList?: Therapist[];
  timeslotList?: Timeslot[];
  patientList?: Patient[];
  appointmentList?: Appointment[];
};

export type Therapist = {
  name?: string;
  availability?: Timeslot[];
  /**
   * @format int32
   */
  maxTravelDistanceKm?: number;
  location?: LatLng;
  skills?: string[];
};

export type Timeslot = {
  /**
   * @format date
   */
  date?: string;
  startTime?: LocalTime;
  endTime?: LocalTime;
};
