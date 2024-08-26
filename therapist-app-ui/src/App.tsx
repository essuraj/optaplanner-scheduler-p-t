import { MapContainer } from "react-leaflet/MapContainer";
import {
  Circle,
  Marker,
  Polyline,
  Popup,
  TileLayer,
  Tooltip,
} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import { useDemoData, useRun } from "./api/apiComponents";
import dayjs from "dayjs";
import { Appointment } from "./api/apiSchemas";
const fillBlueOptions = { fillColor: "blue", strokeColor: "black" };
const fillRedOptions = { fillColor: "red" };
const greenOptions = { color: "green", fillColor: "green" };
const purpleOptions = { color: "purple" };
export interface Coordinates {
  latitude: number;
  longitude: number;
}

/**
 * Calculates the distance (in kms) between point A and B using earth's radius as the spherical surface
 * @param pointA Coordinates from Point A
 * @param pointB Coordinates from Point B
 * Based on https://www.movable-type.co.uk/scripts/latlong.html
 */
function haversineDistance(pointA: Coordinates, pointB: Coordinates): number {
  const radius = 6371; // km

  //convert latitude and longitude to radians
  const deltaLatitude = ((pointB.latitude - pointA.latitude) * Math.PI) / 180;
  const deltaLongitude =
    ((pointB.longitude - pointA.longitude) * Math.PI) / 180;

  const halfChordLength =
    Math.cos((pointA.latitude * Math.PI) / 180) *
      Math.cos((pointB.latitude * Math.PI) / 180) *
      Math.sin(deltaLongitude / 2) *
      Math.sin(deltaLongitude / 2) +
    Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2);

  const angularDistance =
    2 * Math.atan2(Math.sqrt(halfChordLength), Math.sqrt(1 - halfChordLength));

  return radius * angularDistance;
}
function App() {
  // const api = useDemoData({
  //   headers: { "Content-Type": "application/json" },
  //   queryParams: {},
  // });
  const api = useRun({
    headers: { "Content-Type": "application/json" },
    queryParams: {},
  });

  return (
    <>
      {api.isLoading && <div>Loading...</div>}

      <MapContainer
        center={[17.419227, 78.510442]}
        zoom={11}
        scrollWheelZoom={false}
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {api.data?.patientList!.map((item) => (
          <Circle
            weight={1}
            key={item.name}
            center={[item.location!.latitude!, item.location!.longitude!]}
            pathOptions={{ color: "green", fillColor: "green" }}
            radius={500}
          >
             <Tooltip   permanent>
              <small>{item.criticality} ⚠️</small>
            </Tooltip>
            <Popup>
              <pre>
                {JSON.stringify({ ...item, availability: [] }, null, 2)}
              </pre>
            </Popup>
          </Circle>
        ))}
        {api.data?.therapistList!.map((item) => (
          <Circle
            key={item.name}
            center={[item.location!.latitude!, item.location!.longitude!]}
            pathOptions={{ color: "purple", fillColor: "purple" }}
            radius={500}
            weight={1}
          >
            <Tooltip direction="right" offset={[0, 0]} opacity={1} permanent>
            <small> {item.skills?.join(",")}</small>
            </Tooltip>
            <Popup>
              <pre>
                {JSON.stringify({ ...item, availability: [] }, null, 2)}
              </pre>
            </Popup>
          </Circle>
        ))}
        {api.data?.appointmentList?.map((item) => (
          <Polyline
            weight={2}
            pathOptions={{
              color:
                howFar(item) > (item.therapist?.maxTravelDistanceKm??0)
                  ? "red"
                  : "blue",
            }}
            positions={[
              [
                item.patient!.location!.latitude!,
                item.patient!.location!.longitude!,
              ],
              [
                item.therapist!.location!.latitude!,
                item.therapist!.location!.longitude!,
              ],
            ]}
          >
            <Tooltip>
              {item.patient?.name + " --> " + item.therapist?.name}&nbsp;
              <br />
              <small>{dayjs(item.timeslot?.date).format("DD MMM,YYYY")} at {JSON.stringify(item.timeslot?.startTime)}</small>
              <br />
              <b>{howFar(item).toFixed(2) + " km"}</b>
              <br />
              <small>
                Therapist can travel max of{" "}
                {item.therapist?.maxTravelDistanceKm + " km"}
              </small>
            </Tooltip>
          </Polyline>
        ))}
      </MapContainer>
      {/* <pre>
        {JSON.stringify(
          api.data?.patientList?.map((x) => x.location),
          null,
          2
        )}
      </pre>
      <pre>
        {JSON.stringify(
          api.data?.therapistList?.map((x) => x.location),
          null,
          2
        )}
      </pre> */}
    </>
  );

  function howFar(item: Appointment) {
    return haversineDistance(
      {
        latitude: item.therapist!.location!.latitude!,
        longitude: item.therapist!.location!.longitude!,
      },
      {
        latitude: item.patient!.location!.latitude!,
        longitude: item.patient!.location!.longitude!,
      }
    );
  }
}

export default App;
